package com.pablosanchezegido.petcity.features.offers.map;

import android.support.annotation.Nullable;

import com.pablosanchezegido.petcity.models.LatLng;

interface OffersMapPresenter {

    void fetchData(@Nullable LatLng latLng, double radius);
    void offerRequested(String id);
    void destroy();
}
