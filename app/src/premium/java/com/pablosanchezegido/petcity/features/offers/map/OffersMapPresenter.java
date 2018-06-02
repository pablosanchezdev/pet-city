package com.pablosanchezegido.petcity.features.offers.map;

import com.pablosanchezegido.petcity.models.LatLng;

interface OffersMapPresenter {

    void fetchData(LatLng location, double radius);
    void fetchDataWithoutLocation();
    void offerRequested(String id);
    void destroy();
}
