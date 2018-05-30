package com.pablosanchezegido.petcity.features.publish.animals;

import com.pablosanchezegido.petcity.models.PetType;

public interface PublishAnimalsPresenter {

    void numPetsChanged(int numPets);
    void kindOfPetsChanged(PetType type, boolean checked);
    void priceChanged(double price);
    void publishOfferRequested(String title, String detail, String firstImage, String secondImage,
                               String placeName, double placeLat, double placeLng, long startDate, long endDate);
    void destroy();
}
