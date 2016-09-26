package com.erickmtz.tipcalc;

import android.app.Application;

/**
 * Created by erick on 26/09/16.
 */
public class TipCalcApp extends Application{
    private final static String  ABOUT_URL= "http://www.google.com";

    public String getAboutUrl(){
        return ABOUT_URL;
    }


}
