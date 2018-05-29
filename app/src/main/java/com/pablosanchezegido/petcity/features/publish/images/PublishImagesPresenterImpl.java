package com.pablosanchezegido.petcity.features.publish.images;

public class PublishImagesPresenterImpl implements PublishImagesPresenter {

    private PublishImagesView view;

    public PublishImagesPresenterImpl(PublishImagesView view) {
        this.view = view;
    }

    @Override
    public void imageButtonClicked() {
        view.openDialog();
    }

    @Override
    public void cameraItemClicked() {
        view.launchCamera();
    }

    @Override
    public void galleryItemClicked() {
        view.launchGallery();
    }

    @Override
    public void checkBothImages(boolean first, boolean second) {
        if (first && second) {
            view.setNextButtonVisible(true);
        }
    }

    @Override
    public void nextButtonClicked() {
        view.requestNextPage();
    }

    @Override
    public void destroy() {
        view = null;
    }
}
