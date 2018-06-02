package com.pablosanchezegido.petcity.models

import com.google.firebase.firestore.Exclude

class User {

    @get:Exclude var id: String? = null
    var photoUrl: String
    var email: String
    var name: String
    var phoneNumber: String
    var recentActivity: List<Offer>? = null

    constructor() {
        this.id = ""
        this.photoUrl = ""
        this.email = ""
        this.name = ""
        this.phoneNumber = ""
        this.recentActivity = emptyList()
    }

    constructor(photoUrl: String, email: String, name: String, phoneNumber: String, recentActivity: List<Offer>?) {
        this.photoUrl = photoUrl
        this.email = email
        this.name = name
        this.phoneNumber = phoneNumber
        this.recentActivity = recentActivity
    }
}
