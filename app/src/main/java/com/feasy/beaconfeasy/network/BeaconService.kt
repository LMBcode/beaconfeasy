package com.feasycom.feasybeacon.logic.network

import com.feasycom.feasybeacon.logic.model.BeaconParameter
import com.feasycom.feasybeacon.logic.model.Devices
import retrofit2.http.Body
import retrofit2.http.POST

interface BeaconService {

    @POST("allDevice")
    suspend fun getBeacon(@Body beaconParameter: BeaconParameter): Devices?
}
