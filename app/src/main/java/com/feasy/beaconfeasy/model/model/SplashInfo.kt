package com.feasycom.feasybeacon.logic.model

data class SplashInfo(
    val code: Int,
    val `data`: Data,
    val msg: String
){
    data class Data(
        val image: String,
        val verion: Int
    )
}

