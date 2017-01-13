package com.example.sandi.funtastiq;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
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
import com.example.sandi.funtastiq.dashboard.entry.Prototyping;
import com.example.sandi.funtastiq.dashboard.entry.Test;
import com.example.sandi.funtastiq.dashboard.entry.define;
import com.example.sandi.funtastiq.dashboard.entry.empati;
import com.example.sandi.funtastiq.dashboard.entry.idea;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fragmentManager;

    Toolbar toolbar;
    int id,idk;
    TextView namakel;
    ProgressDialog loading;
    String User;

    Class_Global CG;
    Class_Get_Empati CGE;
    Class_Get_Define CGD;
    Class_Get_Idea CGI;
    Class_Get_Proto CGP;
    Class_Get_Test CGT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        idk = (i.getIntExtra("idk",0));
        String kelnama = (i.getStringExtra(Login.USER_NAME));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("MENU");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        namakel = (TextView)hView.findViewById(R.id.namakel);
        CG = Class_Global.getInstance();
        User = CG.getUser();
        namakel.setText(User);
        navigationView.setNavigationItemSelectedListener(this);

        Home mhome = new Home();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mainFrame,mhome,mhome.getTag()).commit();
        getDataEmpati();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_dashboard) {
            fragment = new Dashboard();
            toolbar.setTitle("DASHBOARD");
            toolbar.setBackground(getResources().getDrawable(R.color.colorDark));
            // Handle the camera action
        } else if (id == R.id.nav_rof) {
            fragment = new Rulesofgames();
            toolbar.setTitle("RULES OF GAMES");
        } else if (id == R.id.nav_mod) {
            fragment = new Manonduty();
            toolbar.setTitle("MAN ON DUTY");
        } else if (id == R.id.nav_help) {
            fragment = new Help();
            toolbar.setTitle("HELP AND EMERGENCY");
        } else if (id == R.id.nav_about) {
            fragment = new About();
            toolbar.setTitle("ABOUT US");
        } else if (id == R.id.nav_logout) {
            fragment = new Logout();
            toolbar.setTitle("LOG OUT");
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.mainFrame, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getDataEmpati() {
        String katagori = User+"Empati";
        loading = ProgressDialog.show(this,"Sinkron Data...","Empati...",false,false);

        String url = Config.DATA_URL+katagori;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSONEmpati(response);
                getDataDefine();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Connection Error")
                                .setMessage("Reload")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getDataEmpati();
                                    }

                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    private void showJSONEmpati(String response){
        CGE = Class_Get_Empati.getInstance();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            CGE.setEtittle(collegeData.getString(Config.KEY_GET_JUDUL));
            CGE.setEisi_pertama(collegeData.getString(Config.KEY_GET_ISI_PERTAMA));
            CGE.setEisi_kedua(collegeData.getString(Config.KEY_GET_ISI_KEDUA));
            CGE.setEisi_ketiga(collegeData.getString(Config.KEY_GET_ISI_KETIGA));
            CGE.setEgambar_satu(collegeData.getString(Config.KEY_GET_GAMBAR_SATU));
            CGE.setEgambar_dua(collegeData.getString(Config.KEY_GET_GAMBAR_DUA));
            CGE.setEgambar_tiga(collegeData.getString(Config.KEY_GET_GAMBAR_TIGA));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
    }

    private void getDataDefine() {
        String katagori = User+"Define";
        loading = ProgressDialog.show(this,"Sinkron Data...","Define...",false,false);

        String url = Config.DATA_URL+katagori;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSONDefine(response);
                getDataIdea();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Connection Error")
                                .setMessage("Reload")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getDataDefine();
                                    }

                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    private void showJSONDefine(String response){
        CGD = Class_Get_Define.getInstance();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            CGD.setEtittle(collegeData.getString(Config.KEY_GET_JUDUL));
            CGD.setEisi_pertama(collegeData.getString(Config.KEY_GET_ISI_PERTAMA));
            CGD.setEisi_kedua(collegeData.getString(Config.KEY_GET_ISI_KEDUA));
            CGD.setEisi_ketiga(collegeData.getString(Config.KEY_GET_ISI_KETIGA));
            CGD.setEgambar_satu(collegeData.getString(Config.KEY_GET_GAMBAR_SATU));
            CGD.setEgambar_dua(collegeData.getString(Config.KEY_GET_GAMBAR_DUA));
            CGD.setEgambar_tiga(collegeData.getString(Config.KEY_GET_GAMBAR_TIGA));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
    }

    private void getDataIdea() {
        String katagori = User+"Idea";
        loading = ProgressDialog.show(this,"Sinkron Data...","Idea...",false,false);

        String url = Config.DATA_URL+katagori;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSONIdea(response);
                getDataProto();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Connection Error")
                                .setMessage("Reload")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getDataIdea();
                                    }

                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    private void showJSONIdea(String response){
        CGI = Class_Get_Idea.getInstance();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            CGI.setEtittle(collegeData.getString(Config.KEY_GET_JUDUL));
            CGI.setEisi_pertama(collegeData.getString(Config.KEY_GET_ISI_PERTAMA));
            CGI.setEisi_kedua(collegeData.getString(Config.KEY_GET_ISI_KEDUA));
            CGI.setEisi_ketiga(collegeData.getString(Config.KEY_GET_ISI_KETIGA));
            CGI.setEgambar_satu(collegeData.getString(Config.KEY_GET_GAMBAR_SATU));
            CGI.setEgambar_dua(collegeData.getString(Config.KEY_GET_GAMBAR_DUA));
            CGI.setEgambar_tiga(collegeData.getString(Config.KEY_GET_GAMBAR_TIGA));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
    }

    private void getDataProto() {
        String katagori = User+"Prototyping";
        loading = ProgressDialog.show(this,"Sinkron Data...","Prototyping...",false,false);

        String url = Config.DATA_URL+katagori;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSONProto(response);
                getDataTest();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Connection Error")
                                .setMessage("Reload")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getDataProto();
                                    }

                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    private void showJSONProto(String response){
        CGP = Class_Get_Proto.getInstance();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            CGP.setEtittle(collegeData.getString(Config.KEY_GET_JUDUL));
            CGP.setEisi_pertama(collegeData.getString(Config.KEY_GET_ISI_PERTAMA));
            CGP.setEisi_kedua(collegeData.getString(Config.KEY_GET_ISI_KEDUA));
            CGP.setEisi_ketiga(collegeData.getString(Config.KEY_GET_ISI_KETIGA));
            CGP.setEgambar_satu(collegeData.getString(Config.KEY_GET_GAMBAR_SATU));
            CGP.setEgambar_dua(collegeData.getString(Config.KEY_GET_GAMBAR_DUA));
            CGP.setEgambar_tiga(collegeData.getString(Config.KEY_GET_GAMBAR_TIGA));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
    }

    private void getDataTest() {
        String katagori = User+"Test";
        loading = ProgressDialog.show(this,"Sinkron Data...","Test...",false,false);

        String url = Config.DATA_URL+katagori;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSONTest(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Connection Error")
                                .setMessage("Reload")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getDataTest();
                                    }

                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }




    private void showJSONTest(String response){
        CGT = Class_Get_Test.getInstance();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            CGT.setEtittle(collegeData.getString(Config.KEY_GET_JUDUL));
            CGT.setEisi_pertama(collegeData.getString(Config.KEY_GET_ISI_PERTAMA));
            CGT.setEisi_kedua(collegeData.getString(Config.KEY_GET_ISI_KEDUA));
            CGT.setEisi_ketiga(collegeData.getString(Config.KEY_GET_ISI_KETIGA));
            CGT.setEgambar_satu(collegeData.getString(Config.KEY_GET_GAMBAR_SATU));
            CGT.setEgambar_dua(collegeData.getString(Config.KEY_GET_GAMBAR_DUA));
            CGT.setEgambar_tiga(collegeData.getString(Config.KEY_GET_GAMBAR_TIGA));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (idk == 1){
            Dashboard mdash = new Dashboard();
            fragmentManager = getSupportFragmentManager();
            toolbar.setTitle("DASHBOARD");
            toolbar.setBackground(getResources().getDrawable(R.color.colorDark));
            fragmentManager.beginTransaction().replace(R.id.mainFrame,mdash,mdash.getTag()).commit();
        }
    }
}
