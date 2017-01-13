package com.example.sandi.funtastiq;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SandI on 11/30/2016.
 */

public class Rulesofgames extends Fragment {

    @Nullable
    @Override

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.rules_layout, null);
    }
}