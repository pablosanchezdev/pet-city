package com.pablosanchezegido.petcity.features.offers.list;

import com.pablosanchezegido.petcity.models.LatLng;

interface OffersListPresenter {

    void fetchData(LatLng location, double radius);
    void fetchDataWithoutLocation();
    void retryButtonClicked();
    void itemRequested(String itemId);
}
