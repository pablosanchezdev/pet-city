package com.pablosanchezegido.petcity.features.offers.list;

import com.pablosanchezegido.petcity.models.Offer;

import java.util.List;

interface OffersListInteractor {

    interface OnFetchDataListener {
        void onSuccess(List<Offer> offers);
        void onError(String error);
    }

    void fetchData(OnFetchDataListener listener);
}
