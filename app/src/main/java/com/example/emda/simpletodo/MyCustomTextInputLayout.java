package com.example.emda.simpletodo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by emda on 2/9/2018.
 */


public class MyCustomTextInputLayout extends TextInputLayout {
    private CharSequence mHint;
    private boolean mishintset;

    public MyCustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyCustomTextInputLayout(Context context) {
        super(context);
    }


    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof EditText) {
            mHint = ((EditText) child).getHint();
        }
        super.addView(child, index, params);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mishintset && ViewCompat.isLaidOut(this)) {
            setHint(null);

            CharSequence currentEditTextHint = getEditText().getHint();

            if (currentEditTextHint != null && currentEditTextHint.length() > 0) {
                mHint = currentEditTextHint;
            }
            setHint(mHint);
            mishintset = true;
        }
    }

}