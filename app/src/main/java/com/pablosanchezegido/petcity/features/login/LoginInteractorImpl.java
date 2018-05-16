package com.pablosanchezegido.petcity.features.login;

import com.google.firebase.auth.FirebaseAuth;

public class LoginInteractorImpl implements LoginInteractor {

    private final FirebaseAuth auth;

    LoginInteractorImpl() {
        this.auth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean isUserLoggedIn() {
        return auth.getCurrentUser() != null;
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
    public void authUser(AuthType type, String email, String password, OnAuthFinishedListener listener) {
        if (type == AuthType.LOGIN) {
            login(email, password, listener);
        } else {
            signup(email, password, listener);
        }
    }
}
