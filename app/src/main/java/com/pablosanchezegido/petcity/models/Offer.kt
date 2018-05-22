package com.pablosanchezegido.petcity.models

import com.google.firebase.firestore.GeoPoint

class Offer {

    var images: List<String>
    var title: String
    var startDate: Long
    var endDate: Long
    var location: GeoPoint
    var price: Double

    constructor() {
        this.images = emptyList()
        this.title = ""
        this.startDate = 0
        this.endDate = 0
        this.location = GeoPoint(0.0, 0.0)
        this.price = 0.0
    }

    constructor(images: List<String>, title: String, startDate: Long, endDate: Long, location: GeoPoint, price: Double) {
        this.images = images
        this.title = title
        this.startDate = startDate
        this.endDate = endDate
        this.location = location
        this.price = price
    }
}
