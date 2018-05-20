package com.pablosanchezegido.petcity.models

import com.google.firebase.firestore.Exclude

class User {

    @get:Exclude var id: String? = null
    var photoUrl: String? = null
    var email: String? = null
    var name: String? = null
    var phoneNumber: String? = null
    var birthDate: Long? = null

    constructor() {}

    constructor(id: String?, photoUrl: String?, email: String?, name: String?, phoneNumber: String?, birthDate: Long?) {
        this.id = id
        this.photoUrl = photoUrl
        this.email = email
        this.name = name
        this.phoneNumber = phoneNumber
        this.birthDate = birthDate
    }
}
