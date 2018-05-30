package com.pablosanchezegido.petcity.features.publish.animals;

import com.pablosanchezegido.petcity.features.publish.PublishOfferInteractor;
import com.pablosanchezegido.petcity.models.PetType;

public class PublishAnimalsPresenterImpl implements PublishAnimalsPresenter {

    private PublishAnimalsView view;
    private PublishOfferInteractor interactor;

    private int numPets;
    private boolean dogs, cats;
    private double price;

    PublishAnimalsPresenterImpl(PublishAnimalsView view, PublishOfferInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
        numPets = 0;
        dogs = cats = false;
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
                dogs = checked;
                break;
            case CAT:
                cats = checked;
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
        return numPets > 0 && (dogs || cats) && price > 0.0;
    }
}
