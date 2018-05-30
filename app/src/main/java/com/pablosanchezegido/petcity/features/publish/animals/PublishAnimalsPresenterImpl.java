package com.pablosanchezegido.petcity.features.publish.animals;

import com.pablosanchezegido.petcity.features.publish.PublishOfferInteractor;
import com.pablosanchezegido.petcity.models.PetType;

public class PublishAnimalsPresenterImpl implements PublishAnimalsPresenter {

    private PublishAnimalsView view;
    private PublishOfferInteractor interactor;

    private int numPets;
    private PetType[] petTypes;
    private double price;

    PublishAnimalsPresenterImpl(PublishAnimalsView view, PublishOfferInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        numPets = 0;
        petTypes = new PetType[2];
        price = 0.0;
        view.setNumPets(numPets);
    }

    @Override
    public void numPetsChanged(int numPets) {
        this.numPets = numPets;
        view.setNumPets(numPets);
        dataChanged();
    }

    @Override
    public void kindOfPetsChanged(PetType type, boolean checked) {
        switch (type) {
            case DOG:
                if (checked) {
                    petTypes[0] = PetType.DOG;
                } else {
                    petTypes[0] = null;
                }
                break;
            case CAT:
                if (checked) {
                    petTypes[1] = PetType.CAT;
                } else {
                    petTypes[1] = null;
                }
                break;
        }

        dataChanged();
    }

    @Override
    public void priceChanged(double price) {
        this.price = price;
        dataChanged();
    }

    @Override
    public void publishOfferRequested(String title, String detail, String firstImage, String secondImage,
                                      String placeName, double placeLat, double placeLng,
                                      long startDate, long endDate) {
        view.setPublishButtonEnabled(false);
        view.setPublishButtonProgressVisible(true);
        interactor.publishOffer(title, detail, firstImage, secondImage, placeName, placeLat, placeLng,
                startDate, endDate, numPets, petTypes, price, publishListener);
    }

    @Override
    public void destroy() {
        view = null;
        interactor = null;
    }

    private void dataChanged() {
        view.setPublishButtonVisible(isInfoFilled());
    }

    private boolean isInfoFilled() {
        /* Info is filled when user has entered number of pets, at least
           one kind of pet and a price */
        return numPets > 0 && (petTypes[0] != null || petTypes[1] != null) && price > 0.0;
    }

    private PublishOfferInteractor.OnPublishOfferListener publishListener = new PublishOfferInteractor.OnPublishOfferListener() {
        @Override
        public void onSuccess() {
            view.setPublishButtonEnabled(true);
            view.setPublishButtonProgressVisible(false);
            view.onPublishSuccess();
        }

        @Override
        public void onError(String error) {
            view.setPublishButtonEnabled(true);
            view.setPublishButtonProgressVisible(false);
            view.onPublishError(error);
        }
    };
}
