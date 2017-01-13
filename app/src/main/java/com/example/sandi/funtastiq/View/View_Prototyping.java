package com.example.sandi.funtastiq.View;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sandi.funtastiq.Data.Class_Get_Define;
import com.example.sandi.funtastiq.Data.Class_Get_Proto;
import com.example.sandi.funtastiq.Data.Class_Global;
import com.example.sandi.funtastiq.Fungsi.Config;
import com.example.sandi.funtastiq.Fungsi.ImageLoader;
import com.example.sandi.funtastiq.Fungsi.RequestHandler;
import com.example.sandi.funtastiq.MainActivity;
import com.example.sandi.funtastiq.R;
import com.example.sandi.funtastiq.dashboard.MissionDefine;
import com.example.sandi.funtastiq.dashboard.MissionProto;
import com.example.sandi.funtastiq.dashboard.entry.Prototyping;
import com.example.sandi.funtastiq.dashboard.entry.define;
import com.example.sandi.funtastiq.dashboard.entry.empati;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class View_Prototyping extends AppCompatActivity {

    public static final String UPLOAD_URL = "http://sandihamzah92.esy.es/upload_data.php";
    public static final String KEY_KATAGORI = "katagori";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_JUDUL = "judul";
    public static final String KEY_ISI_PERTAMA = "isi_pertama";
    public static final String KEY_ISI_KEDUA = "isi_kedua";
    public static final String KEY_ISI_KETIGA = "isi_ketiga";
    public static final String KEY_IMAGE_SATU = "image_satu";
    public static final String KEY_IMAGE_DUA = "image_dua";
    public static final String KEY_IMAGE_TIGA = "image_tiga";

    TextView idview,tvisi1,tvisi2,tvisi3,tvtittle;
    ImageView entry,mission,bgheader,photo1,photo2,photo3,iv_dash;
    String itittle,iisi1,iisi2,iisi3,User,image1,image2,image3;

    Bitmap bmp1,bmp2,bmp3;
    ProgressDialog loading;
    Class_Get_Proto CGP;
    byte [] byte1,byte2,byte3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__prototyping);

        int loader = R.drawable.icon;

        Class_Global CG = Class_Global.getInstance();
        User = CG.getUser();
        idview = (TextView)findViewById(R.id.idview);
        tvisi1 = (TextView)findViewById(R.id.tvisi1);
        tvisi2 = (TextView)findViewById(R.id.tvisi2);
        tvisi3 = (TextView)findViewById(R.id.tvisi3);
        tvtittle = (TextView)findViewById(R.id.tvtittle) ;
        entry = (ImageView)findViewById(R.id.iv_entry);
        mission = (ImageView)findViewById(R.id.iv_mission);
        bgheader = (ImageView)findViewById(R.id.bgheader);
        photo1 = (ImageView)findViewById(R.id.photo1);
        photo2 = (ImageView)findViewById(R.id.photo2);
        photo3 = (ImageView)findViewById(R.id.photo3);
        iv_dash = (ImageView)findViewById(R.id.iv_dash);


        Intent i = getIntent();
        itittle = i.getStringExtra("tittle");
        iisi1 = i.getStringExtra("isi1");
        iisi2 = i.getStringExtra("isi2");
        iisi3 = i.getStringExtra("isi3");
        byte1 = i.getByteArrayExtra("bitmap1");
        byte2 = i.getByteArrayExtra("bitmap2");
        byte3 = i.getByteArrayExtra("bitmap3");
        if (null!=byte1) {
            bmp1 = BitmapFactory.decodeByteArray(byte1, 0, byte1.length);
            if (null!=byte2) {
                bmp2 = BitmapFactory.decodeByteArray(byte2, 0, byte2.length);
                if (null!=byte3) {
                    bmp3 = BitmapFactory.decodeByteArray(byte3, 0, byte3.length);
                }
            }

        }
        CGP = Class_Get_Proto.getInstance();
        if (itittle==null) {
            if (CGP.getEtittle().equals("")){
                Toast.makeText(getApplicationContext(), "Data Anda Masih Kosing", Toast.LENGTH_LONG).show();
            } else {
                tvtittle.setText(CGP.getEtittle());
                tvisi1.setText(CGP.getEisi_pertama());
                tvisi2.setText(CGP.getEisi_kedua());
                tvisi3.setText(CGP.getEisi_ketiga());
                if (!CGP.getEgambar_satu().equals("")) {
                    String image_url1 = CGP.getEgambar_satu();
                    ImageLoader imgLoader1 = new ImageLoader(getApplicationContext());
                    imgLoader1.DisplayImage(image_url1, loader, photo1);
                    imgLoader1.DisplayImage(image_url1, loader, bgheader);
                }
                if (!CGP.getEgambar_dua().equals("")) {
                    String image_url2 = CGP.getEgambar_dua();
                    ImageLoader imgLoader2 = new ImageLoader(getApplicationContext());
                    imgLoader2.DisplayImage(image_url2, loader, photo2);
                }
                if (!CGP.getEgambar_tiga().equals("")) {
                    String image_url3 = CGP.getEgambar_tiga();
                    ImageLoader imgLoader3 = new ImageLoader(getApplicationContext());
                    imgLoader3.DisplayImage(image_url3, loader, photo3);
                }
            }
        } else {
            tvtittle.setText(itittle);
            tvisi1.setText(iisi1);
            tvisi2.setText(iisi2);
            tvisi3.setText(iisi3);
            bgheader.setImageBitmap(bmp1);
            photo1.setImageBitmap(bmp1);
            photo2.setImageBitmap(bmp2);
            photo3.setImageBitmap(bmp3);
        }

        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Prototyping.class);
                i.putExtra("tittle",itittle);
                i.putExtra("isi1",iisi1);
                i.putExtra("isi2",iisi2);
                i.putExtra("isi3",iisi3);
                if (null!=photo1.getDrawable()){
                    Bitmap img1 = ((BitmapDrawable)photo1.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                    img1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
                    byte1 = stream1.toByteArray();
                    i.putExtra("bitmap1",byte1);
                    stream1.reset();
                    if (null!=photo2.getDrawable()) {
                        Bitmap img2 = ((BitmapDrawable) photo2.getDrawable()).getBitmap();
                        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                        img2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                        byte2 = stream2.toByteArray();
                        i.putExtra("bitmap2", byte2);
                        stream2.reset();
                        if (null!=photo3.getDrawable()) {
                            Bitmap img3 = ((BitmapDrawable) photo3.getDrawable()).getBitmap();
                            ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
                            img3.compress(Bitmap.CompressFormat.PNG, 100, stream3);
                            byte3 = stream3.toByteArray();
                            i.putExtra("bitmap3", byte3);
                            stream3.reset();
                        }
                    }
                }
                startActivity(i);
                finish();
            }
        });

        iv_dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData();

            }
        });

        mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MissionProto.class);
                startActivity(i);
                finish();
            }
        });

    }

    public String getStringImage1(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public String getStringImage2(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    public String getStringImage3(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void uploadData(){
        final String username = User;
        final String katagori = User+"Prototyping";
        final String judul = tvtittle.getText().toString().trim();
        final String isii1 = tvisi1.getText().toString().trim();
        final String isii2 = tvisi2.getText().toString().trim();
        final String isii3 = tvisi3.getText().toString().trim();
        if (null!=bmp1) {
            image1 = getStringImage1(bmp1);
            if (null!=bmp3) {
                image2 = getStringImage2(bmp2);
                if (null!=bmp3) {
                    image3 = getStringImage3(bmp3);
                }
            }
        }

        class uploadData extends AsyncTask<Void,Void,String> {
            RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getApplicationContext(),"Upload","Data dikirim....",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equals("")){
                    new AlertDialog.Builder(View_Prototyping.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Connection Error")
                            .setMessage("Reload")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    uploadData();
                                }

                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .show();
                }else {
                    Toast.makeText(View_Prototyping.this,s,Toast.LENGTH_LONG).show();
                    int idk = 1;
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("idk", idk);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            protected String doInBackground(Void... params) {

                HashMap<String,String> param = new HashMap<String,String>();
                param.put(KEY_USERNAME,username);
                param.put(KEY_KATAGORI,katagori);
                param.put(KEY_JUDUL,judul);
                param.put(KEY_ISI_PERTAMA,isii1);
                param.put(KEY_ISI_KEDUA,isii2);
                param.put(KEY_ISI_KETIGA,isii3);
                if (null!=image1){
                    param.put(KEY_IMAGE_SATU,image1);
                }
                if (null!=image2){
                    param.put(KEY_IMAGE_DUA,image2);
                }
                if (null!=image3){
                    param.put(KEY_IMAGE_TIGA,image3);
                }
                String result = rh.sendPostRequest(UPLOAD_URL, param);



                return result;
            }
        }
        uploadData u = new uploadData();
        u.execute();
    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Confirm")
                .setMessage("Anda Yakin Akan keluar dari aplikasi?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
