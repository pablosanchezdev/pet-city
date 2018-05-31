package com.pablosanchezegido.petcity.features.offers.list;

import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.utils.ModelMapperKt;

import java.util.List;

class OffersListPresenterImpl implements OffersListPresenter {

    private OffersListView view;
    private OffersListInteractor interactor;

    OffersListPresenterImpl(OffersListView view, OffersListInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void fetchData() {
        view.setProgressVisible(true);
        interactor.fetchData(listener);
    }

    @Override
    public void retryButtonClicked() {
        interactor.fetchData(listener);
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
