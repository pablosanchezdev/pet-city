package com.pablosanchezegido.petcity.features.offers.list;

import com.google.android.gms.maps.model.LatLng;
import com.pablosanchezegido.petcity.models.Offer;

import java.util.List;

interface OffersListInteractor {

    interface OnFetchDataListener {
        void onSuccess(List<Offer> offers);
        void onError(String error);
    }

    void fetchData(LatLng position, double radius, OnFetchDataListener listener);
}
