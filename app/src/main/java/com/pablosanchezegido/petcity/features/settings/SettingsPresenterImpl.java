package com.pablosanchezegido.petcity.features.settings;

class SettingsPresenterImpl implements SettingsPresenter {

    private SettingsView view;

    SettingsPresenterImpl(SettingsView view) {
        this.view = view;
        view.loadSettings();
    }

    @Override
    public void enterColorRequested() {
        view.openColorDialog();
    }

    @Override
    public void saveSettingsRequested() {
        view.commitSettings();
    }

    @Override
    public void destroy() {
        view = null;
    }
}
