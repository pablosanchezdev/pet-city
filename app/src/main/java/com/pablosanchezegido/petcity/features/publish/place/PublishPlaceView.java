package com.pablosanchezegido.petcity.features.publish.place;

public interface PublishPlaceView {

    void showChoosePlaceDialog();
    void setPlaceName(String address);
    void setNextButtonVisible(boolean visible);
    void requestNextPage();
}
