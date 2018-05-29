package com.pablosanchezegido.petcity.features.publish.dates;

public interface PublishDatesPresenter {

    void dateInputRequested();
    void validateDates(long firstDateTimestamp, boolean firstDate, long secondDateTimestamp, boolean secondDate);
    void nextPageRequested();
    void destroy();
}
