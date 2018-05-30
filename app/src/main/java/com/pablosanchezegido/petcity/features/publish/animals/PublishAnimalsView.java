package com.pablosanchezegido.petcity.features.publish.animals;

public interface PublishAnimalsView {

    void setNumPets(int numPets);
    void setPublishButtonVisible(boolean visible);
    void setPublishButtonProgressVisible(boolean visible);
    void setPublishButtonEnabled(boolean enabled);
    void onPublishSuccess();
    void onPublishError(String error);
}
