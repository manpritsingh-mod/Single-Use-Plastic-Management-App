package com.manprit.plastic_management.Database

data class IndividualRequestFormData(
    var reqID :String?=null,
    var Name: String?= null,
    var Email: String?= null,
    var Phone: String?= null,
    var Address: String?= null,
    var DonationType: String ?= null,
    var Weight : String?= null,
    val nearByBank :String?= null,
    var Date: String?= null,
    var Time : String?= null,
    var Status : String ?= "Pending",
    var sourceType : String?=null,
    var uid : String?=null,
    var acceptedByUid : String?=null
)
