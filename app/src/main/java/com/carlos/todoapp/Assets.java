package com.carlos.todoapp;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by carlos on 3/15/2015.
 */
public class Assets {
    public static void setFontRoboto(TextView textView, Context context){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        textView.setTypeface(font);
    }
}
