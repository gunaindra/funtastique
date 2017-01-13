package com.example.sandi.funtastiq;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by SandI on 11/30/2016.
 */

public class Home extends Fragment {

    @Nullable
    @Override

    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.home_layout, null);
        /*LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.home_layout);
        linearLayout.setBackground(getActivity().getResources().getDrawable(R.drawable.home));*/
        return view;
    }
}