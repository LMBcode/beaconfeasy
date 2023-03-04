package com.feasycom.feasybeacon.logic.model

data class BeaconParameter(
    val deviceType: Int,
    val funcType: Int,
    val hash: String?,
    val haveDetail: Boolean,
    val page: Int,
    val size: Int
)