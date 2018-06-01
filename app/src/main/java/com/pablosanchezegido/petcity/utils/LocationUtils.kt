package com.pablosanchezegido.petcity.utils

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

fun getDistanceBetween(from: LatLng, to: LatLng): Double =
        SphericalUtil.computeDistanceBetween(from, to) / 1000

class BoundaryLatLng(val minLatLng: LatLng, val maxLatLng: LatLng)

fun getBoundaryLatLngForRadius(center: LatLng, radius: Double): BoundaryLatLng {
    val diagonal = Math.hypot(radius * 1000, radius * 1000)

    // Get upper-right corner of the square with side equal to radius
    val maxLatLng = SphericalUtil.computeOffset(center, diagonal, 45.0)

    // Get bottom-left corner of the square
    val minLatLng = SphericalUtil.computeOffset(center, diagonal, 225.0)

    return BoundaryLatLng(minLatLng, maxLatLng)
}
