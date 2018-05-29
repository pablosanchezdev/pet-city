package com.pablosanchezegido.petcity.features.publish.dates;

public class PublishDatesPresenterImpl implements PublishDatesPresenter {

    private PublishDatesView view;

    public PublishDatesPresenterImpl(PublishDatesView view) {
        this.view = view;
    }

    @Override
    public void dateInputRequested() {
        view.showDateDialog();
    }

    @Override
    public void validateDates(long firstDateTimestamp, boolean firstDate, long secondDateTimestamp, boolean secondDate) {
        if (firstDate && secondDate) {
            if (firstDateTimestamp >= secondDateTimestamp) {
                view.setErrorVisible(true);
            } else {
                view.setErrorVisible(false);
                view.setNextButtonVisible(true);
            }
        }
    }

    @Override
    public void nextPageRequested() {
        view.requestNextPage();
    }

    @Override
    public void destroy() {
        view = null;
    }
}
