package com.pablosanchezegido.petcity.features.offers.detail;

import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.models.PetType;

public class OfferDetailPresenterImpl implements OfferDetailPresenter {

    private OfferDetailView view;
    private OfferDetailInteractor interactor;
    private Offer offer;

    OfferDetailPresenterImpl(OfferDetailView view, OfferDetailInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void fetchOfferDetail(String offerId) {
        view.setProgressVisible(true);
        interactor.fetchOfferDetail(offerId, offerListener);
    }

    @Override
    public void acceptOfferRequested() {
        view.showConfirmationDialog();
    }

    @Override
    public void acceptOffer() {
        view.setProgressVisible(true);
        interactor.acceptOffer(offer, offerAcceptedListener);
    }

    @Override
    public void destroy() {
        view = null;
        interactor = null;
    }

    private OfferDetailInteractor.OnOfferFetchedListener offerListener =
            new OfferDetailInteractor.OnOfferFetchedListener() {
        @Override
        public void onSuccess(Offer offer) {
            view.setProgressVisible(false);
            view.setOfferImages(offer.getImages());
            view.setOfferTitle(offer.getTitle());
            view.setOfferDates(offer.getStartDate(), offer.getEndDate());
            view.setOfferDetail(offer.getDetail());
            view.setUserImage(offer.getUser().getPhotoUrl());
            view.setUserName(offer.getUser().getName());
            view.setDogsVisible(offer.getPetTypes().contains(PetType.DOG.getType()));
            view.setCatsVisible(offer.getPetTypes().contains(PetType.CAT.getType()));
            view.setOfferNumPets(offer.getNumPets());
            view.setOfferPlace(offer.getPlaceName());
            view.setOfferLocation(offer.getLocation().getLatitude(), offer.getLocation().getLongitude());
            view.setOfferPrice(offer.getPrice());
            OfferDetailPresenterImpl.this.offer = offer;
        }

        @Override
        public void onError(String error) {
            view.setProgressVisible(false);
            view.setError(error);
        }
    };

    private OfferDetailInteractor.OnOfferAcceptedListener offerAcceptedListener =
            new OfferDetailInteractor.OnOfferAcceptedListener() {
                @Override
                public void onSuccess() {
                    view.setProgressVisible(false);
                    view.setOfferAcceptedSuccessfuly();
                }

                @Override
                public void onError(String error) {
                    view.setProgressVisible(false);
                    view.setError(error);
                }
            };
}
