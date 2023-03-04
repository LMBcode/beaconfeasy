package com.feasycom.feasybeacon.logic.model

import com.feasycom.bean.BluetoothDeviceWrapper

data class BatchDevice(var device: BluetoothDeviceWrapper, var isClick: Boolean = false){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BatchDevice) return false

        if (device != other.device) return false

        return true
    }

    override fun hashCode(): Int {
        return device.hashCode()
    }
}