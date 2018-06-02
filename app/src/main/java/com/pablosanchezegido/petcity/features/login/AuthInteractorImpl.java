package com.pablosanchezegido.petcity.features.login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pablosanchezegido.petcity.features.registration.UserInteractor;
import com.pablosanchezegido.petcity.features.registration.UserInteractorImpl;

public class AuthInteractorImpl implements AuthInteractor {

    private final FirebaseAuth auth;
    private UserInteractor userInteractor;

    public AuthInteractorImpl() {
        this.auth = FirebaseAuth.getInstance();
        this.userInteractor = new UserInteractorImpl();
    }

    @Override
    public boolean isUserLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    @Override
    public void loginUser(String email, String password, OnAuthFinishedListener listener) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess();
                    } else {
                        if (task.getException() != null) {
                            listener.onError(task.getException().getMessage());
                        } else {
                            listener.onError("Unknown error");
                        }
                    }
                });
    }

    @Override
    public void registerUser(String email, String password, String fullName, String phoneNumber, OnAuthFinishedListener listener) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userInteractor.createUser(getCurrentUser().getUid(), email, fullName, phoneNumber,
                                new UserInteractor.OnUserCreatedListener() {
                            @Override
                            public void onSuccess() {
                                listener.onSuccess();
                            }

                            @Override
                            public void onError(String error) {
                                listener.onError(error);
                            }
                        });
                    } else {
                        if (task.getException() != null) {
                            listener.onError(task.getException().getMessage());
                        } else {
                            listener.onError("Unknown error");
                        }
                    }
                });
    }

    @Override
    public void logoutUser() {
        auth.signOut();
    }

    public static String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return (user != null) ? user.getUid() : null;
    }

    private FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }
}
