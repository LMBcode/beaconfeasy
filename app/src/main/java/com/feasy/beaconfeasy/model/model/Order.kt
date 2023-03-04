package com.feasycom.feasybeacon.logic.model

data class Order(val time: String, val name: String?, val address: String?, val connect: Boolean, val map: LinkedHashMap<String, Boolean>?)