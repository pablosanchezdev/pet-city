package com.pablosanchezegido.petcity.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    public interface OnAlertDialogClickListener {
        void onPositiveButtonClick();
        void onNegativeButtonClick();
    }

    private OnAlertDialogClickListener listener;

    private static final String TITLE = "title";
    private static final String DETAIL = "detail";

    public static AlertDialogFragment newInstance(@StringRes int titleRes, @StringRes int detailRes) {
        AlertDialogFragment dialog = new AlertDialogFragment();

        Bundle args = new Bundle();
        args.putInt(TITLE, titleRes);
        args.putInt(DETAIL, detailRes);
        dialog.setArguments(args);

        return dialog;
    }

    public AlertDialogFragment() { }

    public void setOnAlertDialogClickListener(OnAlertDialogClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        int titleRes = args.getInt(TITLE);
        int detailRes = args.getInt(DETAIL);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(titleRes)
                .setMessage(detailRes)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (listener != null) {
                    listener.onPositiveButtonClick();
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                if (listener != null) {
                    listener.onNegativeButtonClick();
                }
                break;
        }
    }
}
