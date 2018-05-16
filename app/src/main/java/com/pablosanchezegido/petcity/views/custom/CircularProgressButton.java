package com.pablosanchezegido.petcity.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircularProgressButton extends FrameLayout {

    @BindView(R.id.progress_button)
    Button progressButton;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    String buttonText;

    private OnButtonClickListener listener;

    public CircularProgressButton(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CircularProgressButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircularProgressButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircularProgressButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        listener = null;
        super.onDetachedFromWindow();
    }

    private void init(Context context, AttributeSet attrs) {
        View view = ExtensionsKt.inflate(this, R.layout.circular_progress_button, true);

        initComponents(view, context, attrs);
    }

    private void initComponents(View view, Context context, AttributeSet attrs) {
        ButterKnife.bind(this, view);

        buttonText = "";
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressButton);
        if (array != null) {
            buttonText = array.getString(R.styleable.CircularProgressButton_buttonText);
            array.recycle();
        }

        progressButton.setText(buttonText);
        progressButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onButtonClick(v);
            }
        });
    }

    public void setProgressVisible(boolean show) {
        if (show) {
            progressButton.setText("");
            progressBar.setVisibility(VISIBLE);
        } else {
            progressBar.setVisibility(GONE);
            progressButton.setText(buttonText);
        }
    }

    public void setButtonEnabled(boolean enabled) {
        progressButton.setEnabled(enabled);
    }

    // Container activity/fragment must implement this interface
    public interface OnButtonClickListener {
        void onButtonClick(View view);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }
}
