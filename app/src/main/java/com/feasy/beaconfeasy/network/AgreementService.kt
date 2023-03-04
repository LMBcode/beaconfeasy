package com.feasycom.feasybeacon.logic.network

import com.feasycom.feasybeacon.logic.model.ProtocolParams
import com.feasycom.feasybeacon.logic.model.ProtocolResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AgreementService {

    @POST("agreement")
    suspend fun getProtocol(@Body protocolParams: ProtocolParams, @Header("local") local: String = "en"): ProtocolResponse
}