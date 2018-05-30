package com.pablosanchezegido.petcity.features.profile;

import com.pablosanchezegido.petcity.models.UserView;

public interface ProfileView {

    void setLoadingVisible(boolean visible);
    void layoutData(UserView user);
    void setError(String error);
}
