package com.junemon.crpyt.classes

import android.util.Log
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


/**
 * Created by Ian Damping on 01,July,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SecurityHelper {


    fun encryptValue(
        plainTextBytes: ByteArray,
        passwordString: String
    ): HashMap<String, ByteArray> {
        val map = HashMap<String, ByteArray>()
        try {
            //Random salt for next step
            val random = SecureRandom()
            val salt = ByteArray(256)
            random.nextBytes(salt)

            //PBKDF2 - derive the key from the password, don't use passwords directly
            val passwordChar = passwordString.toCharArray() //Turn password into char[] array


            val pbKeySpec = PBEKeySpec(passwordChar, salt, 1324, 256) //1324 iterations
            val secretKeyFactory: SecretKeyFactory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes: ByteArray = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            //Create initialization vector for AES
            val ivRandom = SecureRandom() //not caching previous seeded instance of SecureRandom
            val iv = ByteArray(16)
            ivRandom.nextBytes(iv)
            val ivSpec = IvParameterSpec(iv)

            //Encrypt
            val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            val encrypted: ByteArray = cipher.doFinal(plainTextBytes)



            map["salt"] = salt
            map["iv"] = iv
            map["encrypted"] = encrypted
        } catch (e: Exception) {
            Log.e("security","encryption exception : $e")
        }
        return map
    }


    fun decryptValue(map: HashMap<String, ByteArray>, passwordString: String): ByteArray? {
        var decrypted: ByteArray? = null
        try {
            require(passwordString != "")
            val salt = map["salt"]
            val iv = map["iv"]
            val encrypted = map["encrypted"]

            //regenerate key from password
            val passwordChar = passwordString.toCharArray()


            val pbKeySpec = PBEKeySpec(passwordChar, salt, 1324, 256)
            val secretKeyFactory: SecretKeyFactory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes: ByteArray = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            //Decrypt
            val cipher: Cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
            val ivSpec = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            decrypted = cipher.doFinal(encrypted)
        } catch (e: java.lang.Exception) {
            Log.e("security","decryption exception : $e")
        }
        return decrypted
    }

    fun keystoreEncrypt(dataToEncrypt: ByteArray): HashMap<String, ByteArray> {
        val map = HashMap<String, ByteArray>()
        try {

            // 1
            //Get the key
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)

            val secretKeyEntry =
                keyStore.getEntry("MyKeyAlias", null) as KeyStore.SecretKeyEntry
            val secretKey = secretKeyEntry.secretKey

            // 2
            //Encrypt data
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val ivBytes = cipher.iv
            val encryptedBytes = cipher.doFinal(dataToEncrypt)

            // 3
            map["iv"] = ivBytes
            map["encrypted"] = encryptedBytes
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return map
    }

    fun keystoreDecrypt(map: HashMap<String, ByteArray>): ByteArray? {
        var decrypted: ByteArray? = null
        try {
            // 1
            //Get the key
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)

            val secretKeyEntry = keyStore.getEntry("MyKeyAlias", null) as KeyStore.SecretKeyEntry
            val secretKey = secretKeyEntry.secretKey

            // 2
            //Extract info from map
            val encryptedBytes = map["encrypted"]
            val ivBytes = map["iv"]

            // 3
            //Decrypt data
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val spec = GCMParameterSpec(128, ivBytes)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
            decrypted = cipher.doFinal(encryptedBytes)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return decrypted
    }

}