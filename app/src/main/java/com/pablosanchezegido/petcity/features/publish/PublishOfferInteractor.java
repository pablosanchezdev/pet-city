package com.pablosanchezegido.petcity.features.publish;

import com.pablosanchezegido.petcity.models.PetType;

public interface PublishOfferInteractor {

    interface OnPublishOfferListener {
        void onSuccess();
        void onError(String error);
    }

    void publishOffer(String title, String detail, String firstImage, String secondImage,
                      String placeName, double placeLatitude, double placeLongitude,
                      long startDate, long endDate, int numPets, PetType[] petTypes,
                      double price, OnPublishOfferListener listener);
}
