package com.example.sandi.funtastiq;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sandi.funtastiq.Data.Class_Get_Define;
import com.example.sandi.funtastiq.Data.Class_Get_Empati;
import com.example.sandi.funtastiq.Data.Class_Get_Idea;
import com.example.sandi.funtastiq.Data.Class_Get_Proto;
import com.example.sandi.funtastiq.Data.Class_Get_Test;
import com.example.sandi.funtastiq.Data.Class_Global;
import com.example.sandi.funtastiq.Fungsi.Config;
import com.example.sandi.funtastiq.dashboard.MissionDefine;
import com.example.sandi.funtastiq.dashboard.MissionEmpaty;
import com.example.sandi.funtastiq.dashboard.MissionIdea;
import com.example.sandi.funtastiq.dashboard.MissionProto;
import com.example.sandi.funtastiq.dashboard.MissionTest;
import com.example.sandi.funtastiq.dashboard.entry.empati;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by SandI on 11/30/2016.
 */

public class Dashboard extends Fragment {

    ImageView ivempati,ivdefine,ividea,ivproto,ivtest;

    TextView tvempaty,tvdefine,tvidea,tvprotyping,tvtest;

    Class_Get_Empati CGE;
    Class_Get_Define CGD;
    Class_Get_Idea CGI;
    Class_Get_Proto CGP;
    Class_Get_Test CGT;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
            View view = inflater.inflate(R.layout.dashboard_layout, null);

        CGE = Class_Get_Empati.getInstance();
        CGD = Class_Get_Define.getInstance();
        CGI = Class_Get_Idea.getInstance();
        CGP = Class_Get_Proto.getInstance();
        CGT = Class_Get_Test.getInstance();
        String TittleEmp = CGE.getEtittle();
        String TittleDef = CGD.getEtittle();
        String TittleIdea = CGI.getEtittle();
        String TittleProto = CGP.getEtittle();
        String TittleTest = CGT.getEtittle();
        ivempati = (ImageView)view.findViewById(R.id.ivempati);
        ivdefine = (ImageView)view.findViewById(R.id.ivdefine);
        ividea = (ImageView)view.findViewById(R.id.ividea);
        ivproto = (ImageView)view.findViewById(R.id.ivproto);
        ivtest = (ImageView)view.findViewById(R.id.ivtest);
        tvempaty = (TextView)view.findViewById(R.id.tvempati);
        tvdefine = (TextView)view.findViewById(R.id.tvdefine);
        tvidea = (TextView)view.findViewById(R.id.tvidea);
        tvprotyping = (TextView)view.findViewById(R.id.tvprototype);
        tvtest = (TextView)view.findViewById(R.id.tvtesting);
        if (CGE.getEtittle().equals("")) {
            tvempaty.setText("");
        } else {
            tvempaty.setText(TittleEmp);
        }

        if (CGD.getEtittle().equals("")) {
            tvdefine.setText("");
        } else {
            tvdefine.setText(TittleDef);
        }

        if (CGI.getEtittle().equals("")) {
            tvidea.setText("");
        } else {
            tvidea.setText(TittleIdea);
        }

        if (CGP.getEtittle().equals("")) {
            tvprotyping.setText("");
        } else {
            tvprotyping.setText(TittleProto);
        }

        if (CGT.getEtittle().equals("")) {
            tvtest.setText("");
        } else {
            tvtest.setText(TittleTest);
        }


        ivempati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this.getActivity(), MissionEmpaty.class);
                startActivity(i);
                getActivity().finish();
            }
        });
        ivdefine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(Dashboard.this.getActivity(), MissionDefine.class);
                    startActivity(i);
                    getActivity().finish();

            }
        });
        ividea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(Dashboard.this.getActivity(), MissionIdea.class);
                    startActivity(i);
                    getActivity().finish();

            }
        });
        ivproto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(Dashboard.this.getActivity(), MissionProto.class);
                    startActivity(i);
                    getActivity().finish();

            }
        });
        ivtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(Dashboard.this.getActivity(), MissionTest.class);
                    startActivity(i);
                    getActivity().finish();

            }
        });

        return view;

    }

}