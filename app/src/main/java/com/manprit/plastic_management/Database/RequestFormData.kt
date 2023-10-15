package com.manprit.plastic_management.Database

data class RequestFormData(
    var reqID :String?=null,
    var Name: String?,
    var Email: String?,
    var Phone: String?,
    var Address: String?,
//    var District: String?,
    var DonationType: String ?= "Organisation",
    var Weight : String?,
    var Date: String?,
    var Time : String?,
    var Status : String ?= "Pending",
    var latitude :String?=null,
    var longitude : String?=null,
    var sourceType : String?=null,
    var uid : String?=null,
    var acceptedByUid : String?=null
)
