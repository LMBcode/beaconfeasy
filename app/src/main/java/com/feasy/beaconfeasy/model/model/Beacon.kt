package com.feasycom.feasybeacon.logic.model

import com.feasycom.bean.BluetoothDeviceWrapper
import com.feasycom.feasybeacon.logic.utils.ShowBroadcastStatus
import kotlin.math.min

class Beacon(var mDevice: BluetoothDeviceWrapper, var timestampLast: Long = 0){

    var intervalNanos = 0L
    var count = 0
    var isFold = ShowBroadcastStatus.NONE

    fun setIntervalIfLower(intervalNanos: Long) {
        if (intervalNanos <= 0L) return
        if (this.intervalNanos == 0L) this.intervalNanos = intervalNanos
        else if (intervalNanos < this.intervalNanos * 0.7 && count < 10)
            this.intervalNanos = intervalNanos
        else if (intervalNanos < this.intervalNanos + 3000000) {
            val limitedCount = min(count, 10)

            this.intervalNanos = (this.intervalNanos * (limitedCount - 1) + intervalNanos) / limitedCount
        } else if (intervalNanos < this.intervalNanos * 1.4) {
            this.intervalNanos = (this.intervalNanos * 29 + intervalNanos) / 30
        }
    }

    override fun equals(other: Any?): Boolean {
        return when(other){
            is Beacon -> {
                other.mDevice.address == mDevice.address
            }
            is BluetoothDeviceWrapper -> {
                other.address == mDevice.address
            }
            else -> {
                return false
            }
        }
    }

    override fun hashCode(): Int {
        return mDevice.hashCode()
    }


}