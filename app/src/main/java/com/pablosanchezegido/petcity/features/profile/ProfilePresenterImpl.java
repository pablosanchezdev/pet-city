package com.pablosanchezegido.petcity.features.profile;

import com.pablosanchezegido.petcity.models.User;
import com.pablosanchezegido.petcity.utils.ModelMapperKt;

public class ProfilePresenterImpl implements ProfilePresenter {

    private ProfileView view;
    private ProfileInteractor interactor;

    ProfilePresenterImpl(ProfileView view, ProfileInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void fetchData() {
        view.setLoadingVisible(true);
        interactor.fetchUserProfile(listener);
    }

    @Override
    public void destroy() {
        view = null;
        interactor.detachRealtimeListener();
        interactor = null;
    }

    private ProfileInteractor.OnUserProfileFetchedListener listener =
            new ProfileInteractor.OnUserProfileFetchedListener() {
        @Override
        public void onSuccess(User user) {
            view.setLoadingVisible(false);
            view.layoutData(ModelMapperKt.userToUserView(user));
        }

        @Override
        public void onError(String error) {
            view.setLoadingVisible(false);
        }
    };
}
