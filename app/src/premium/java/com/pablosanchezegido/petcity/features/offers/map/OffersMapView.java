package com.pablosanchezegido.petcity.features.offers.map;

import com.pablosanchezegido.petcity.models.LatLng;

import java.util.List;

interface OffersMapView {

    void setProgressVisible(boolean visible);
    void addMarker(double lat, double lng, String title, String id);
    void setViewPosition(List<LatLng> latLngs);
    void openOfferDetail(String id);
    void setError(String error);
}
