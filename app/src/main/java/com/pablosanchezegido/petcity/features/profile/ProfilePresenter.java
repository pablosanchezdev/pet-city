package com.pablosanchezegido.petcity.features.profile;

public interface ProfilePresenter {

    void changePictureRequested();
    void cameraItemClicked();
    void galleryItemClicked();
    void photoUploadRequested(String imageUri);
    void fetchData(int maxRecentActivity);
    void destroy();
}
