package com.pablosanchezegido.petcity.features.publish.place;

public interface PublishPlaceView {

    void showChoosePlaceDialog();
    void setPlaceAddress(CharSequence address);
    void setNextButtonVisible(boolean visible);
    void requestNextPage();
}
