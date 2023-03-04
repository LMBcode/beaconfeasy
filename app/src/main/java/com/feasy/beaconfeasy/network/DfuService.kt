package com.feasycom.feasybeacon.logic.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface DfuService {

    @Streaming
    @GET("https://dfu.feasycom.com/{file_name}.dfu")
    suspend fun downloadDFU(@Path("file_name") name: String): ResponseBody


}
