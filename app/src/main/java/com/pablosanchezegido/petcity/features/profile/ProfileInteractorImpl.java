package com.pablosanchezegido.petcity.features.profile;

import com.pablosanchezegido.petcity.features.registration.UserInteractor;
import com.pablosanchezegido.petcity.features.registration.UserInteractorImpl;
import com.pablosanchezegido.petcity.models.User;

public class ProfileInteractorImpl implements ProfileInteractor {

    private UserInteractorImpl userInteractor;

    ProfileInteractorImpl() {
        userInteractor = new UserInteractorImpl();
    }

    @Override
    public void fetchUserProfile(OnUserProfileFetchedListener listener) {
        userInteractor.fetchUserProfile(new UserInteractor.OnUserFetchedListener() {
            @Override
            public void onSuccess(User user) {
                listener.onSuccess(user);
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
    }

    @Override
    public void detachRealtimeListener() {
        userInteractor.detachUserProfileRealtimeListener();
    }
}
