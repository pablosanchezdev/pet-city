package com.pablosanchezegido.petcity.features.profile;

import com.pablosanchezegido.petcity.models.UserView;

public interface ProfileView {

    void setLoadingVisible(boolean visible);
    void openDialog();
    void launchCamera();
    void launchGallery();
    void layoutData(UserView user);
    void setNoRecentActivityVisible(boolean visible);
    void changeImage(String url);
    void setError(String error);
}
