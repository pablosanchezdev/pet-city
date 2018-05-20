package com.pablosanchezegido.petcity.views.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";

    public static DatePickerFragment newInstance(int year, int month, int day) {
        DatePickerFragment dialog = new DatePickerFragment();

        Bundle args = new Bundle();
        args.putInt(YEAR, year);
        args.putInt(MONTH, month);
        args.putInt(DAY, day);
        dialog.setArguments(args);

        return dialog;
    }

    public DatePickerFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof DateSetListener) {
            listener = (DateSetListener) context;
        } else {
            throw new ClassCastException(context.getClass().getSimpleName() + " must implement DateSetListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();

        int year = args.getInt(YEAR);
        int month = args.getInt(MONTH);
        int day = args.getInt(DAY);

        return new DatePickerDialog(getContext(), this, year, month, day);
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // We can be sure that listener is not null (checked in onAttach)
        listener.onDateSet(year, month, dayOfMonth);
    }

    public interface DateSetListener {
        void onDateSet(int year, int month, int day);
    }

    private DateSetListener listener;
}
