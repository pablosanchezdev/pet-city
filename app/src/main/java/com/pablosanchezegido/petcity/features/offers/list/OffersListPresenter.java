package com.pablosanchezegido.petcity.features.offers.list;

interface OffersListPresenter {

    void fetchData();
    void retryButtonClicked();
    void itemRequested(String itemId);
}
