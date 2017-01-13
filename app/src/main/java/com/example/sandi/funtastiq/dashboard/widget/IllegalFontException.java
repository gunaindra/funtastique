package com.example.sandi.funtastiq.dashboard.widget;

/**
 * Created by SandI on 12/4/2016.
 */

public class IllegalFontException extends IllegalArgumentException {

    public IllegalFontException(String fontName) {
        super("Supplied font name " + fontName + " must match file name in assets/fonts/ directory");
    }
}
