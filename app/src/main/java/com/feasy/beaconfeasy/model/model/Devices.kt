package com.feasycom.feasybeacon.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Devices(
    val code: Int,
    val `data`: Data,
    val msg: String
){
    data class Data(
        val hash: String,
        val list: List<DeviceInfo>,
        val page: Int,
        val total: Int
    )
}


@Entity
data class DeviceInfo(
    val createTime: Int,
    val defaultInterval: String,
    val deviceId: String,
    val deviceType: Int,
    val funcType: Int,
    val gsensorDuration: String,
    val gsensorInterval: String,
    val gsensorSensitivity: String,
    val keyDuration: String,
    val keyInterval: String,
    val modifyTime: Int,
    val name: String,
    val number: Int,
    val txPower: String
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
