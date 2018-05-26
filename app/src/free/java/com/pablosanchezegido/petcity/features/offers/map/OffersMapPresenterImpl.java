package com.pablosanchezegido.petcity.features.offers.map;

public class OffersMapPresenterImpl implements OffersMapPresenter {

    private OffersMapView view;

    public OffersMapPresenterImpl(OffersMapView view) {
        this.view = view;
    }

    @Override
    public void goPremiumButtonClicked() {
        view.openLink();
    }
}
