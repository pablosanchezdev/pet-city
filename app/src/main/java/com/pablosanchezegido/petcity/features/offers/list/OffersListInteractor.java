package com.pablosanchezegido.petcity.features.offers.list;

import com.pablosanchezegido.petcity.models.LatLng;
import com.pablosanchezegido.petcity.models.Offer;

import java.util.List;

interface OffersListInteractor {

    interface OnFetchDataListener {
        void onSuccess(List<Offer> offers);
        void onError(String error);
    }

    void fetchData(LatLng position, double radius, OnFetchDataListener listener);
    void fetchDataWithoutLocation(OnFetchDataListener listener);
}
