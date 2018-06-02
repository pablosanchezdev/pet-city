package com.pablosanchezegido.petcity.features.offers.map;

import android.support.annotation.Nullable;

import com.pablosanchezegido.petcity.utils.LatLng;

interface OffersMapPresenter {

    void fetchData(@Nullable LatLng latLng, double radius);
    void viewHasFocus();
    void offerRequested(String id);
    void destroy();
}
