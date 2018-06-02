package com.pablosanchezegido.petcity.views.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.pablosanchezegido.petcity.utils.SizeCalculator;

public class BigCardItemDecoration extends RecyclerView.ItemDecoration {

    private WindowManager windowManager;
    private final float heightRatio;

    public BigCardItemDecoration(WindowManager windowManager, float heightRatio) {
        this.windowManager = windowManager;
        this.heightRatio = heightRatio;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        SizeCalculator sizeCalculator = new SizeCalculator(windowManager, heightRatio);
        view.getLayoutParams().height = sizeCalculator.calculateSize().getHeight();
    }
}
