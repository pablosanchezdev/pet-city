package com.pablosanchezegido.petcity.utils;

import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.pablosanchezegido.petcity.models.Size;

public class SizeCalculator {

    private WindowManager windowManager;
    private float heightRelativeToWidthRatio;

    public SizeCalculator(WindowManager windowManager, float heightRelativeToWidthRatio) {
        this.windowManager = windowManager;
        this.heightRelativeToWidthRatio = heightRelativeToWidthRatio;
    }

    public Size calculateSize() {
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        return new Size(point.x, Math.round(point.x * heightRelativeToWidthRatio));
    }
}
