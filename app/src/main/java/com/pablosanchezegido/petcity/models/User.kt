package com.pablosanchezegido.petcity.models

import com.google.firebase.firestore.Exclude

class User {

    @get:Exclude var id: String? = null
    var photoUrl: String
    var email: String
    var name: String
    var phoneNumber: String
    var birthDate: Long
    var recentActivity: List<Offer>? = null

    constructor() {
        this.id = ""
        this.photoUrl = ""
        this.email = ""
        this.name = ""
        this.phoneNumber = ""
        this.birthDate = 0
        this.recentActivity = emptyList()
    }

    constructor(photoUrl: String, email: String, name: String, phoneNumber: String, birthDate: Long, recentActivity: List<Offer>?) {
        this.photoUrl = photoUrl
        this.email = email
        this.name = name
        this.phoneNumber = phoneNumber
        this.birthDate = birthDate
        this.recentActivity = recentActivity
    }
}
