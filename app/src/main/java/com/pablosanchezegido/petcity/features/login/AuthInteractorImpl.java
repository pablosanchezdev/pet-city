package com.pablosanchezegido.petcity.features.login;

import com.google.firebase.auth.FirebaseAuth;

public class AuthInteractorImpl implements AuthInteractor {

    private final FirebaseAuth auth;

    AuthInteractorImpl() {
        this.auth = FirebaseAuth.getInstance();
    }

    private void login(String email, String password, OnAuthFinishedListener listener) {
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

    private void signup(String email, String password, OnAuthFinishedListener listener) {
        auth.createUserWithEmailAndPassword(email, password)
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
    public boolean isUserLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    @Override
    public void authUser(AuthType type, String email, String password, OnAuthFinishedListener listener) {
        if (type == AuthType.LOGIN) {
            login(email, password, listener);
        } else {
            signup(email, password, listener);
        }
    }
}
