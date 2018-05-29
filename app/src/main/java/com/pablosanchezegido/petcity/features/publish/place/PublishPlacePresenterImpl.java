package com.pablosanchezegido.petcity.features.publish.place;

public class PublishPlacePresenterImpl implements PublishPlacePresenter {

    private PublishPlaceView view;

    public PublishPlacePresenterImpl(PublishPlaceView view) {
        this.view = view;
    }

    @Override
    public void choosePlace() {
        view.showChoosePlaceDialog();
    }

    @Override
    public void placeSet(CharSequence address) {
        view.setPlaceAddress(address);
        view.setNextButtonVisible(true);
    }

    @Override
    public void nextPageRequested() {
        view.requestNextPage();
    }

    @Override
    public void destroy() {
        view = null;
    }
}
