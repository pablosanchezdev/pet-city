package com.pablosanchezegido.petcity.features.profile;

import com.pablosanchezegido.petcity.models.User;

public interface ProfileInteractor {

    interface OnUserProfileFetchedListener {
        void onSuccess(User user);
        void onError(String error);
    }

    void fetchUserProfile(OnUserProfileFetchedListener listener);
    void detachRealtimeListener();
}
