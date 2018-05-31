package com.pablosanchezegido.petcity.features.offers.list;

import com.google.android.gms.maps.model.LatLng;

interface OffersListPresenter {

    void fetchData(LatLng position);
    void retryButtonClicked();
    void itemRequested(String itemId);
}
