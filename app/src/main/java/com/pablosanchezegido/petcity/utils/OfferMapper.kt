package com.pablosanchezegido.petcity.utils

import com.google.android.gms.maps.model.LatLng
import com.pablosanchezegido.petcity.models.Offer
import com.pablosanchezegido.petcity.models.OfferView

fun offersToOfferViews(offers: List<Offer>): List<OfferView> {
    val result = mutableListOf<OfferView>()
    offers.forEach {
        val distance = getDistanceBetween(LatLng(40.974808, -5.6649207), LatLng(it.location.latitude, it.location.longitude))
        val offerView = OfferView(it.images[0], it.title, it.startDate, it.endDate, Math.round(distance), it.price)
        result.add(offerView)
    }

    return result
}
