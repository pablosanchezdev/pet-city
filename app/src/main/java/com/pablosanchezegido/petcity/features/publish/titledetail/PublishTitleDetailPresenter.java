package com.pablosanchezegido.petcity.features.publish.titledetail;

public interface PublishTitleDetailPresenter {

    void titleTextChanged(String text);
    void detailTextChanged(String text);
    void nextButtonClicked();
    void destroy();
}
