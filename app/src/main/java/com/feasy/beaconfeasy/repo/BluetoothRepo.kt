package com.feasy.beaconfeasy.repo

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import android.util.Log
import android.util.Range
import com.feasy.beaconfeasy.App
import com.feasy.beaconfeasy.interfaces.FscBleCallback
import com.feasycom.bean.BeaconBean
import com.feasycom.bean.BluetoothDeviceWrapper
import com.feasycom.bean.CommandBean
import com.feasycom.bean.FeasyBeacon
import com.feasycom.controler.FscBeaconApiImp
import com.feasycom.controler.FscBeaconCallbacks

import java.util.ArrayList
import java.util.LinkedHashMap

object BluetoothRepo {

    val mSelectedMap = LinkedHashMap<String, Any>()

    var mModule = 0
    var mVersionRange: Range<Int>? = null

    var mDevices = mutableListOf<BluetoothDeviceWrapper>()
    private val mCallbacks = mutableListOf<FscBleCallback>()
    var mConnectPin = "000000"

    val commandBean: CommandBean = CommandBean()

    private val mFscBeaconCentralApi by lazy {
        FscBeaconApiImp.getInstance(App.sContext).apply {
            initialize()
            setCallbacks(BeaconCallbacks())
        }
    }

    fun isBtEnabled() = mFscBeaconCentralApi.isBtEnabled

    fun startScan(){
        Log.e(TAG, "startScan: 开始扫描设备", )
        mFscBeaconCentralApi.startScan()
    }

    fun stopScan() = mFscBeaconCentralApi.stopScan()

    fun connect(device: BluetoothDeviceWrapper, pin: String) = mFscBeaconCentralApi.connect(device, pin)

    fun disconnect() = mFscBeaconCentralApi.disconnect()

    fun isConnect() = mFscBeaconCentralApi.isConnected

    fun isBeaconInfoFull() = mFscBeaconCentralApi.isBeaconInfoFull

    fun addBeaconInfo(beaconBean: BeaconBean) = mFscBeaconCentralApi.addBeaconInfo(beaconBean)

    fun deleteBeaconInfo(beaconBean: BeaconBean){
        mFscBeaconCentralApi.deleteBeaconInfo(beaconBean.index)
        mCallbacks.forEach {
            it.onDeleteBeaconInfo(beaconBean)
        }
    }

    fun setName(name: String) = mFscBeaconCentralApi.setDeviceName(name)

    fun setPin(pin: String) = mFscBeaconCentralApi.setFscPin(pin)

    fun setBroadcastInterval(intervalTime: String) = mFscBeaconCentralApi.setBroadcastInterval(intervalTime)

    fun setTlm(b: Boolean) = mFscBeaconCentralApi.setTlm(b)
    fun setAdvext(b: Boolean) = mFscBeaconCentralApi.setAdvext(b)

    fun checkDfuFile(dfu: ByteArray) = mFscBeaconCentralApi.checkDfuFile(dfu)

    fun startOTA(dfuFile: ByteArray, reset: Boolean) = mFscBeaconCentralApi.startOTA(dfuFile, reset)

    fun save() = mFscBeaconCentralApi.saveBeaconInfo()

    fun saveBatch(){
        mFscBeaconCentralApi.saveBatch(commandBean)
    }

    fun registerViewCallback(callback: FscBleCallback) {
        if (mCallbacks.contains(callback)){
            return
        }
        try {
            mCallbacks.add(callback)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun unRegisterViewCallback(callback: FscBleCallback) {
        try {
            mCallbacks.remove(callback)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun startGetDeviceInfo(moduleString: String?, fb: FeasyBeacon, b: Boolean) {
        mFscBeaconCentralApi.startGetDeviceInfo(moduleString, fb, b)
    }


    class BeaconCallbacks: FscBeaconCallbacks {
        override fun startScan() {
            Log.d(TAG,"开始扫描回调.")
        }

        override fun stopScan() {
            Log.d(TAG,"停止扫描回调.")
        }

        override fun connectProgressUpdate(device: BluetoothDevice?, status: Int) {
            mCallbacks.forEach {
                it.onConnectProgressUpdate(status)
            }
        }

        override fun blePeripheralFound(
            device: BluetoothDeviceWrapper?,
            rssi: Int,
            record: ByteArray?
        ) {
            if (device == null){
                return
            }
            try {
                if (device.getiBeacon() != null || device.getgBeacon() != null || device.altBeacon != null) {
                    mCallbacks.forEach {
                        it.onBeacon(device)
                    }
                }
                device.monitor?.let {
                    mCallbacks.forEach {
                        it.onSensor(device)
                    }
                }
                device.feasyBeacon?.module?.let{
                    mCallbacks.forEach {
                        it.onSetting(device)
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        override fun blePeripheralConnected(gatt: BluetoothGatt?, device: BluetoothDevice?) {
            Log.e(TAG, "blePeripheralConnected: 连接成功", )
            mCallbacks.forEach {
                it.onConnectedSuccess()
            }
        }

        override fun blePeripheralDisconnected(gatt: BluetoothGatt?, device: BluetoothDevice?) {
            Log.e(TAG, "onDisconnect: 断开连接" )
            mCallbacks.forEach {
                it.onDisconnect()
            }
        }

        override fun atCommandCallBack(command: String?, param: String?, status: String?) {
            Log.e(TAG, "atCommandCallBack: command -> $command  param -> $param  status  ->  $status", )
            mCallbacks.forEach {
                it.onAtCommandCallBack(command, param, status)
            }
        }

        override fun servicesFound(
            gatt: BluetoothGatt?,
            device: BluetoothDevice?,
            services: ArrayList<BluetoothGattService>?
        ) {
        }

        override fun characteristicForService(
            gatt: BluetoothGatt?,
            device: BluetoothDevice?,
            service: BluetoothGattService?,
            characteristic: BluetoothGattCharacteristic?
        ) {
        }

        override fun packetReceived(
            gatt: BluetoothGatt?,
            device: BluetoothDevice?,
            service: BluetoothGattService?,
            ch: BluetoothGattCharacteristic?,
            strValue: String,
            hexString: String,
            rawValue: ByteArray,
            timestamp: String?
        ) {
            mCallbacks.forEach {
                it.onPacketReceived(strValue, hexString, rawValue)
            }

        }

        override fun readResponse(
            gatt: BluetoothGatt?,
            device: BluetoothDevice?,
            service: BluetoothGattService?,
            ch: BluetoothGattCharacteristic?,
            strValue: String?,
            hexString: String?,
            rawValue: ByteArray?,
            timestamp: String?
        ) {
        }

        override fun sendPacketProgress(
            gatt: BluetoothGatt?,
            device: BluetoothDevice?,
            ch: BluetoothGattCharacteristic?,
            percentage: Int,
            sendByte: ByteArray?
        ) {
        }

        override fun otaProgressUpdate(percentage: Int, status: Int) {
            mCallbacks.forEach {
                it.onOtaProgressUpdate(percentage)
            }
        }

        override fun deviceInfo(parameterName: String?, parameter: Any?) {
            mCallbacks.forEach {
                it.onDeviceInfo(parameterName, parameter)
            }
        }



    }

    private const val TAG = "BluetoothRepository"



}