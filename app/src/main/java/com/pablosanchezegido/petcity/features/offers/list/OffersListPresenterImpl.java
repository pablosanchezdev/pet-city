package com.pablosanchezegido.petcity.features.offers.list;

import com.pablosanchezegido.petcity.models.LatLng;
import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.utils.ModelMapperKt;

import java.util.List;

class OffersListPresenterImpl implements OffersListPresenter {

    private OffersListView view;
    private OffersListInteractor interactor;

    private LatLng position;
    private double radius;

    OffersListPresenterImpl(OffersListView view, OffersListInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        position = null;
    }

    @Override
    public void fetchData(LatLng location, double radius) {
        this.position = location;
        this.radius = radius;
        view.setProgressVisible(true);
        interactor.fetchData(location, radius, listener);
    }

    @Override
    public void fetchDataWithoutLocation() {
        this.position = null;
        interactor.fetchDataWithoutLocation(listener);
    }

    @Override
    public void retryButtonClicked() {
        if (position != null) {
            interactor.fetchData(position, radius, listener);
        } else {
            interactor.fetchDataWithoutLocation(listener);
        }
    }

    @Override
    public void itemRequested(String itemId) {
        view.openItemDetail(itemId);
    }

    private OffersListInteractor.OnFetchDataListener listener = new OffersListInteractor.OnFetchDataListener() {
        @Override
        public void onSuccess(List<Offer> offers) {
            view.setProgressVisible(false);
            view.setRetryButtonVisible(false);
            if (offers.isEmpty()) {
                view.setNoResultsVisible(true);
            } else {
                view.setNoResultsVisible(false);
                if (position != null) {
                    view.layoutData(ModelMapperKt.offersToOfferViews(ModelMapperKt.latLngToLatLng(position), offers));
                } else {
                    view.layoutData(ModelMapperKt.offersToOfferViews(null, offers));
                }
            }
        }

        @Override
        public void onError(String error) {
            view.setProgressVisible(false);
            view.setRetryButtonVisible(true);
            view.setNoResultsVisible(false);
            view.setError(error);
        }
    };
}
