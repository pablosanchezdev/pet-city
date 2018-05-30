package com.pablosanchezegido.petcity.features.publish.place;

public interface PublishPlacePresenter {

    void choosePlace();
    void placeSet(String address);
    void nextPageRequested();
    void destroy();
}
