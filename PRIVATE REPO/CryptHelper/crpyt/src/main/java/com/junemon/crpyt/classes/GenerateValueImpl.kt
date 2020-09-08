package com.junemon.crpyt.classes

import android.util.Log
import com.junemon.crpyt.helper.SecretSecurityHelper.RetroPin

/**
 * Created by Ian Damping on 02,July,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class GenerateValueImpl(private val securityHelper: SecurityHelper) : GenerateValueHelper {

    override fun generateKeyForRetrofitPin(): HashMap<String, ByteArray> {
        return securityHelper.encryptValue(
            RetroPin.toByteArray(Charsets.UTF_8),
            "UserSuppliedPassword"
        )
    }

    override fun getRetrofitPin(key: HashMap<String, ByteArray>): String {
        val decrypted: ByteArray? = securityHelper.decryptValue(key, "UserSuppliedPassword")
        return if (decrypted != null) {
            String(decrypted)
        } else return ""
    }
}