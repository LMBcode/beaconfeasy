package com.feasy.beaconfeasy.interfaces

import com.feasycom.bean.BeaconBean
import com.feasycom.bean.BluetoothDeviceWrapper

interface FscBleCallback {

    fun onBeacon(device: BluetoothDeviceWrapper){

    }

    fun onSensor(device: BluetoothDeviceWrapper){

    }

    fun onSetting(device: BluetoothDeviceWrapper){

    }

    fun onConnectedSuccess(){

    }

    fun onConnectProgressUpdate(status: Int){

    }

    fun onDeviceInfo(parameterName: String?, parameter: Any?){

    }

    fun onAtCommandCallBack(command: String?, param: String?, status: String?){

    }

    fun onPacketReceived(strValue: String,
                         hexString: String,
                         rawValue: ByteArray,){

    }

    fun onOtaProgressUpdate(percentage: Int){

    }

    fun onDisconnect(){}

    fun onDeleteBeaconInfo(beaconBean: BeaconBean){}
}