package com.manprit.plastic_management.Database

data class RequestDetailsData(
    var reqID :String?=null,
    var Name: String?=null,
    var Email: String?=null,
    var Phone: String?=null,
    var Address: String?=null,
//    var District: String?=null,
    var DonationType: String ?= null,
    var Weight : String? =null,
    var Date: String? = null,
    var Time : String? = null,
    var Status : String ?= null,
    var latitude :String?=null,
    var longitude : String?=null,
    var sourceType : String?=null,
    var uid : String?=null,
    var acceptedByUid : String?=null
)
