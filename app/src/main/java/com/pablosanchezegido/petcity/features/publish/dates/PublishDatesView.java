package com.pablosanchezegido.petcity.features.publish.dates;

public interface PublishDatesView {

    void showDateDialog();
    void setErrorVisible(boolean visible);
    void setNextButtonVisible(boolean visible);
    void requestNextPage();
}
