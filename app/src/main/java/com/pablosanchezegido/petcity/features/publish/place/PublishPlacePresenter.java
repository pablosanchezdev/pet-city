package com.pablosanchezegido.petcity.features.publish.place;

public interface PublishPlacePresenter {

    void choosePlace();
    void placeSet(CharSequence address);
    void nextPageRequested();
    void destroy();
}
