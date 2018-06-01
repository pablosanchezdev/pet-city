package com.pablosanchezegido.petcity.utils

import com.google.android.gms.maps.model.LatLng
import com.pablosanchezegido.petcity.models.Offer
import com.pablosanchezegido.petcity.models.OfferView
import com.pablosanchezegido.petcity.models.User
import com.pablosanchezegido.petcity.models.UserView

const val DISTANCE_NOT_AVAILABLE = -1.0

fun offersToOfferViews(location: LatLng?, offers: List<Offer>?): List<OfferView> {
    val result = mutableListOf<OfferView>()
    offers?.forEach {
        val distance = when {
            location != null -> getDistanceBetween(location, LatLng(it.location.latitude, it.location.longitude))
            else -> {
                DISTANCE_NOT_AVAILABLE
            }
        }
        val offerView = OfferView(it.id, it.images[0], it.title, it.startDate, it.endDate, distance, it.price)
        result.add(offerView)
    }

    return result
}

fun userToUserView(user: User): UserView {
    return UserView(user.photoUrl, user.name, offersToOfferViews(null, user.recentActivity))
}
