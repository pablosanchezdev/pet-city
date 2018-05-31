package com.pablosanchezegido.petcity.features.offers.list;

import com.google.android.gms.maps.model.LatLng;
import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.utils.ModelMapperKt;

import java.util.List;

import javax.annotation.Nullable;

class OffersListPresenterImpl implements OffersListPresenter {

    private OffersListView view;
    private OffersListInteractor interactor;

    private LatLng position;

    OffersListPresenterImpl(OffersListView view, OffersListInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        position = null;
    }

    @Override
    public void fetchData(@Nullable LatLng position) {
        this.position = position;
        view.setProgressVisible(true);
        interactor.fetchData(position, listener);
    }

    @Override
    public void retryButtonClicked() {
        interactor.fetchData(position, listener);
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
                view.layoutData(ModelMapperKt.offersToOfferViews(offers));
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
