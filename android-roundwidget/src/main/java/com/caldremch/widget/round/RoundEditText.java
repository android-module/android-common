package com.caldremch.widget.round;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author Caldremch
 * @date 2019-07-07 00:44
 * @email caldremch@163.com
 * @describe
 **/
public class RoundEditText extends androidx.appcompat.widget.AppCompatEditText {
    public RoundEditText(Context context) {
        this(context, null);
    }
    public RoundEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public RoundEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        new RoundWidgetDelegate(this, context, attrs);
    }
}
