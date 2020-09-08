package com.junemon.gamesapi.data.datasource.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ian Damping on 29,October,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class ResultEntity<T>(@field:SerializedName("results") val data: List<T>)