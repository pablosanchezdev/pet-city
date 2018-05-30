package com.pablosanchezegido.petcity.models

import com.google.firebase.firestore.Exclude

class User {

    @get:Exclude var id: String?
    var photoUrl: String
    var email: String
    var name: String
    var phoneNumber: String
    var birthDate: Long?

    constructor() {
        this.id = ""
        this.photoUrl = ""
        this.email = ""
        this.name = ""
        this.phoneNumber = ""
        this.birthDate = 0
    }

    constructor(id: String?, photoUrl: String, email: String, name: String, phoneNumber: String, birthDate: Long) {
        this.id = id
        this.photoUrl = photoUrl
        this.email = email
        this.name = name
        this.phoneNumber = phoneNumber
        this.birthDate = birthDate
    }
}
