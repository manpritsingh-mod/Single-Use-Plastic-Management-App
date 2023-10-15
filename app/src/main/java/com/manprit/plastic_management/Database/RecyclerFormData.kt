package com.manprit.plastic_management.Database

data class RecyclerFormData(
    var reqID :String?=null,
    var Name: String?=null,
    var Phone: String?=null,
    var DonationType: String ?= "Plastic Granules",
    var Address : String?= null,
    var Weight : String?=null,
    var Date: String?=null,
    var Time : String?=null,
    var Status: String?=null,
    var sourceType : String?=null,
    var uid : String?=null,
    var acceptedByUid : String?=null
)
