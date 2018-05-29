package com.pablosanchezegido.petcity.features.publish.titledetail;

public class PublishTitleDetailPresenterImpl implements PublishTitleDetailPresenter {

    private PublishTitleDetailView view;

    private String title, detail;

    public PublishTitleDetailPresenterImpl(PublishTitleDetailView view) {
        this.view = view;
        title = detail = "";
    }

    private boolean isInfoCorrect() {
        return title.length() > 0 && detail.length() > 0;
    }

    @Override
    public void titleTextChanged(String text) {
        title = text;
        view.setNextButtonVisible(isInfoCorrect());
    }

    @Override
    public void detailTextChanged(String text) {
        detail = text;
        view.setNextButtonVisible(isInfoCorrect());
    }

    @Override
    public void nextButtonClicked() {
        view.requestNextPage();
    }

    @Override
    public void destroy() {
        view = null;
    }
}
