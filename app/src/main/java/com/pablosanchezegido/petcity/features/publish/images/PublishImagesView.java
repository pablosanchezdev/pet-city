package com.pablosanchezegido.petcity.features.publish.images;

public interface PublishImagesView {

    void openDialog();
    void launchCamera();
    void launchGallery();
    void setNextButtonVisible(boolean visible);
    void requestNextPage();
}
