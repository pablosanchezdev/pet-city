package com.pablosanchezegido.petcity.models

import com.google.firebase.firestore.GeoPoint

class Offer {

    var images: List<String>
    var title: String
    var detail: String
    var placeName: String
    var location: GeoPoint
    var startDate: Long
    var endDate: Long
    var numPets: Int
    var petTypes: List<String>
    var price: Double
    var user: User?

    constructor() {
        this.images = emptyList()
        this.title = ""
        this.detail = ""
        this.placeName = ""
        this.location = GeoPoint(0.0, 0.0)
        this.startDate = 0
        this.endDate = 0
        this.numPets = 0
        this.petTypes = emptyList()
        this.price = 0.0
        this.user = null
    }

    constructor(images: List<String>, title: String, detail: String, placeName: String, location: GeoPoint,
                startDate: Long, endDate: Long, numPets: Int, petTypes: List<String>, price: Double, user: User) {
        this.images = images
        this.title = title
        this.detail = detail
        this.placeName = placeName
        this.location = location
        this.startDate = startDate
        this.endDate = endDate
        this.numPets = numPets
        this.petTypes = petTypes
        this.price = price
        this.user = user
    }
}
