package com.pablosanchezegido.petcity.views.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BigCardItemDecoration extends RecyclerView.ItemDecoration {

    private final float heightRatio;

    public BigCardItemDecoration(float heightRatio) {
        this.heightRatio = heightRatio;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        view.getLayoutParams().height = Math.round(parent.getMeasuredWidth() * heightRatio);
    }
}
