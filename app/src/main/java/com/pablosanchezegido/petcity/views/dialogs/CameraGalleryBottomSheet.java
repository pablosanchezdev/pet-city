package com.pablosanchezegido.petcity.views.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.pablosanchezegido.petcity.R;

public class CameraGalleryBottomSheet extends BottomSheetDialogFragment {

    public interface OnItemClickListener {
        boolean onItemClick(MenuItem item);
    }

    private OnItemClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnItemClickListener) {
            listener = (OnItemClickListener) context;
        } else {
            throw new ClassCastException(context + " must implement OnItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NavigationView nv = (NavigationView) inflater.inflate(R.layout.bottom_sheet_camera_gallery, container, false);
        nv.setNavigationItemSelectedListener(item -> {
            dismiss();
            return listener.onItemClick(item);
        });
        return nv;
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }
}
