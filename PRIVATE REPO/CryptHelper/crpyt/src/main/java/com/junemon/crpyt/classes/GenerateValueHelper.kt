package com.junemon.crpyt.classes

/**
 * Created by Ian Damping on 02,July,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface GenerateValueHelper {

    fun generateKeyForRetrofitPin():HashMap<String, ByteArray>

    fun getRetrofitPin(key : HashMap<String, ByteArray>):String
}