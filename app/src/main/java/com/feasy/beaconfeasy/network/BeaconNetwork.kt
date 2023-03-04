package com.feasycom.feasybeacon.logic.network

import com.feasycom.feasybeacon.logic.model.BeaconParameter
import com.feasycom.feasybeacon.logic.model.FeedbackParams
import com.feasycom.feasybeacon.logic.model.ProtocolParams

object BeaconNetwork {

    private val splashService = ServiceCreator.create<SplashService>()

    suspend fun getSplash(parameter: Map<String, String>) = splashService.getLanch(parameter)

    private val agreementService = ServiceCreator.create<AgreementService>()

    suspend fun getProtocol(protocolParams: ProtocolParams, local: String = "en") = agreementService.getProtocol(protocolParams, local)

    private val beaconService = ServiceCreator.create<BeaconService>()

    suspend fun getBeacon(beaconParameter: BeaconParameter) = beaconService.getBeacon(beaconParameter)

    private val dfuService = ServiceCreator.create<DfuService>()

    suspend fun downloadDFU(name: String) = dfuService.downloadDFU(name)

    private val feedbackService = ServiceCreator.create<FeedbackService>()

    suspend fun feedback(key: String, content: FeedbackParams) = feedbackService.feedback(key, content)
}