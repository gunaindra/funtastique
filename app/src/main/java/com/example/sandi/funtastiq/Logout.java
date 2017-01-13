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

public class Logout extends Fragment {

    @Nullable
    @Override

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.logout_layout, null);
        return view;

    }
}