package com.caldremch.widget.round;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import androidx.annotation.Nullable;

/**
 * @author Caldremch
 * @date 2019-07-06 19:34
 * @email caldremch@163.com
 * @describe
 **/
public class RoundScrollView extends ScrollView {
    public RoundScrollView(Context context) {
        this(context, null);
    }
    public RoundScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public RoundScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        new RoundWidgetDelegate(this, context, attrs);
    }
}
