package com.junemon.crpyt.helper

/**
 * Created by Ian Damping on 02,July,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
object SecretSecurityHelper {


    val PrivateRsaKey: String
        external get

    val PublicRsaKey: String
        external get

    val Xtoken: String
        external get

    val Pattern: String
        external get

    val RetroPin: String
        external get

    val XaccessPrefKey:String
        external get

    val XrefreshPrefKey:String
        external get

    val ProfilePrefKey:String
        external get

    val DetailOutletKey:String
        external get

    init {
        System.loadLibrary("crpyt_helper")
    }
}