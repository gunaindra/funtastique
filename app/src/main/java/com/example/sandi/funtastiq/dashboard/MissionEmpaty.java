package com.example.sandi.funtastiq.dashboard;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sandi.funtastiq.Data.Class_Get_Empati;
import com.example.sandi.funtastiq.Data.Class_Global;
import com.example.sandi.funtastiq.Fungsi.Config;
import com.example.sandi.funtastiq.MainActivity;
import com.example.sandi.funtastiq.R;
import com.example.sandi.funtastiq.View.MapsActivity;
import com.example.sandi.funtastiq.View.View_Empati;
import com.example.sandi.funtastiq.dashboard.entry.empati;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MissionEmpaty extends AppCompatActivity {

    ImageView ivmisentry,ivmispreview,ivmisplace,ivmissurvey,ivmislink;
    TextView back;
    ProgressDialog loading;
    String User;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);
        Class_Global CG = Class_Global.getInstance();
        User = CG.getUser();
        ivmisentry = (ImageView)findViewById(R.id.ivmisentry);
        ivmispreview = (ImageView)findViewById(R.id.ivmispreview);
        ivmisplace = (ImageView)findViewById(R.id.ivmisplace);
        ivmissurvey = (ImageView)findViewById(R.id.ivmissurvey);
        ivmislink = (ImageView)findViewById(R.id.ivmislink);
        //back = (TextView)findViewById(R.id.tv_menu) ;


        ivmisentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MissionEmpaty.this,empati.class);
                startActivity(i);
                finish();
                //getData();
            }
        });

        ivmispreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MissionEmpaty.this,View_Empati.class);
                //getData();
                startActivity(i);
                finish();
            }
        });

        ivmisplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MissionEmpaty.this, MapsActivity.class);
                startActivity(i);
                finish();
            }
        });

        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MissionEmpaty.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });*/


    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Confirm")
                .setMessage("Kembali ke Menu?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(MissionEmpaty.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
