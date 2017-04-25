package com.think360.cmg.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.think360.cmg.R;

public class RobotoBoldTextView extends android.support.v7.widget.AppCompatTextView {

    public RobotoBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RobotoBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoBoldTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/robotobold.ttf");
        setTypeface(tf);


    }
}