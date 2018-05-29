package com.pablosanchezegido.petcity.features.publish.images;

public interface PublishImagesPresenter {

    void imageButtonClicked();
    void cameraItemClicked();
    void galleryItemClicked();
    void checkBothImages(boolean first, boolean second);
    void nextButtonClicked();
    void destroy();
}
