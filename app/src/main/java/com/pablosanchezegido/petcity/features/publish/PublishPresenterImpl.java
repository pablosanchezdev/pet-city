package com.pablosanchezegido.petcity.features.publish;

public class PublishPresenterImpl implements PublishPresenter {

    private PublishView view;

    public PublishPresenterImpl(PublishView view) {
        this.view = view;
    }

    @Override
    public void publishOfferClicked() {
        view.startPublishProcess();
    }

    @Override
    public void destroy() {
        view = null;
    }
}
