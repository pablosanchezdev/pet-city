package com.pablosanchezegido.petcity.features.offers.map;

import android.support.annotation.Nullable;

import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.utils.LatLng;

import java.util.ArrayList;
import java.util.List;

public class OffersMapPresenterImpl implements OffersMapPresenter {

    private OffersMapView view;
    private OffersMapInteractor interactor;

    private List<LatLng> latLngs;

    OffersMapPresenterImpl(OffersMapView view, OffersMapInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void fetchData(@Nullable LatLng latLng, double radius) {
        view.setProgressVisible(true);
        interactor.fetchData(latLng, radius, offersListener);
    }

    @Override
    public void viewHasFocus() {
        view.setViewPosition(latLngs);
    }

    @Override
    public void offerRequested(String id) {
        view.openOfferDetail(id);
    }

    @Override
    public void destroy() {
        view = null;
        interactor = null;
    }

    private OffersMapInteractor.OnOffersFetchedListener offersListener =
            new OffersMapInteractor.OnOffersFetchedListener() {
                @Override
                public void onSuccess(List<Offer> offers) {
                    view.setProgressVisible(false);
                    latLngs = new ArrayList<>();
                    for (Offer offer : offers) {
                        view.addMarker(offer.getLocation().getLatitude(),
                                offer.getLocation().getLongitude(), offer.getTitle(), offer.getId());
                        latLngs.add(new LatLng(offer.getLocation().getLatitude(), offer.getLocation().getLongitude()));
                    }
                }

                @Override
                public void onError(String error) {
                    view.setProgressVisible(false);
                    view.setError(error);
                }
            };
}
