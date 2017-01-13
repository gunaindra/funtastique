package com.example.sandi.funtastiq.dashboard.entry;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.example.sandi.funtastiq.Data.Class_Get_Define;
import com.example.sandi.funtastiq.Data.Class_Get_Idea;
import com.example.sandi.funtastiq.Data.Class_Global;
import com.example.sandi.funtastiq.Fungsi.Config;
import com.example.sandi.funtastiq.Fungsi.ImageLoader;
import com.example.sandi.funtastiq.Fungsi.RequestHandler;
import com.example.sandi.funtastiq.MainActivity;
import com.example.sandi.funtastiq.R;
import com.example.sandi.funtastiq.View.View_Define;
import com.example.sandi.funtastiq.View.View_Empati;
import com.example.sandi.funtastiq.View.View_Idea;
import com.example.sandi.funtastiq.dashboard.MissionDefine;
import com.example.sandi.funtastiq.dashboard.MissionIdea;
import com.example.sandi.funtastiq.dashboard.widget.LineEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by SandI on 12/14/2016.
 */

public class idea extends AppCompatActivity {
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

    int loader = R.drawable.icon;
    public static final String TAG = "MY MESSAGE";
    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_REQUEST = 1;
    private static final int REQUEST_ACESS_STORAGE=3;
    private static final int REQUEST_ACESS_CAMERA=2;
    private Uri uri;

    //LineEditText isi;
    EditText tittle, isi1,isi2,isi3;
    ImageView ivgal1,ivcam1,mIv1,mIv2,mIv3,misentry,mivpreem;
    public String CiDateTime, shari,sbulan,User,image1,image2,image3;
    TextView hari,tgl,bulan,tahun,dash;
    Bitmap img1, img2, img3;
    ProgressDialog loading;
    Class_Get_Idea CGI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idea);

        Intent i = getIntent();
        final String itittle = i.getStringExtra("tittle");
        final String iisi1 = i.getStringExtra("isi1");
        final String iisi2 = i.getStringExtra("isi2");
        final String iisi3 = i.getStringExtra("isi3");
        final byte[] bytes1 = i.getByteArrayExtra("bitmap1");
        final byte[] bytes2 = i.getByteArrayExtra("bitmap2");
        final byte[] bytes3 = i.getByteArrayExtra("bitmap3");
        Class_Global CG = Class_Global.getInstance();
        User = CG.getUser();
        //fungsi kalender
        Calendar ci = Calendar.getInstance();
        CiDateTime = "" + ci.get(Calendar.YEAR) + "-" +
                (ci.get(Calendar.MONTH) + 1) + "-" +
                ci.get(Calendar.DAY_OF_MONTH) + " " +
                ci.get(Calendar.HOUR) + ":" +
                ci.get(Calendar.MINUTE) +  ":" +
                ci.get(Calendar.SECOND);
        shari = ""+ci.get(Calendar.DAY_OF_WEEK);
        sbulan = ""+(ci.get(Calendar.MONTH)+1);

        //inisialisasi
        ivgal1 = (ImageView)findViewById(R.id.ivgalem1);
        ivcam1 = (ImageView)findViewById(R.id.ivcamem1);
        mIv1 = (ImageView)findViewById(R.id.main_img1);
        mIv2 = (ImageView)findViewById(R.id.main_img2);
        mIv3 = (ImageView)findViewById(R.id.main_img3);
        misentry = (ImageView)findViewById(R.id.ivmisentry);
        mivpreem = (ImageView)findViewById(R.id.ivmispreviewem);
        tittle = (EditText)findViewById(R.id.etTittle);
        isi1 = (LineEditText)findViewById(R.id.activity_new_entry_body);
        isi2 = (LineEditText)findViewById(R.id.activity_new_entry_body1);
        isi3 = (LineEditText)findViewById(R.id.activity_new_entry_body2);
        if (null!=itittle){
            tittle.setText(itittle);
            isi1.setText(iisi1);
            isi2.setText(iisi2);
            isi3.setText(iisi3);
            if (null!=bytes1){
                img1 = BitmapFactory.decodeByteArray(bytes1, 0, bytes1.length);
                mIv1.setImageBitmap(img1);
                if (null!=bytes2) {
                    img2 = BitmapFactory.decodeByteArray(bytes2, 0, bytes2.length);
                    mIv2.setImageBitmap(img2);
                    if (null!=bytes3) {
                        img3 = BitmapFactory.decodeByteArray(bytes3, 0, bytes3.length);
                        mIv3.setImageBitmap(img3);
                    }
                }
            }
        } else {
            CGI = Class_Get_Idea.getInstance();
            if (CGI.getEtittle().equals("")) {
                Toast.makeText(getApplicationContext(), "Data Anda Masih Kosing", Toast.LENGTH_LONG).show();
            }else {
                tittle.setText(CGI.getEtittle());
                isi1.setText(CGI.getEisi_pertama());
                isi2.setText(CGI.getEisi_kedua());
                isi3.setText(CGI.getEisi_ketiga());
                if (!CGI.getEgambar_satu().equals("")) {
                    String image_url1 = CGI.getEgambar_satu();
                    ImageLoader imgLoader1 = new ImageLoader(getApplicationContext());
                    imgLoader1.DisplayImage(image_url1, loader, mIv1);
                }
                if (!CGI.getEgambar_dua().equals("")) {
                    String image_url2 = CGI.getEgambar_dua();
                    ImageLoader imgLoader2 = new ImageLoader(getApplicationContext());
                    imgLoader2.DisplayImage(image_url2, loader, mIv2);
                }
                if (!CGI.getEgambar_tiga().equals("")) {
                    String image_url3 = CGI.getEgambar_tiga();
                    ImageLoader imgLoader3 = new ImageLoader(getApplicationContext());
                    imgLoader3.DisplayImage(image_url3, loader, mIv3);
                }
            }
        }



        hari = (TextView)findViewById(R.id.TVday);
        if (shari.equals("1")){
            hari.setText("MINGGU");
        } else if (shari.equals("2")){
            hari.setText("SENIN");
        } else if (shari.equals("3")){
            hari.setText("SELASA");
        } else if (shari.equals("4")){
            hari.setText("RABU");
        } else if (shari.equals("5")){
            hari.setText("KAMIS");
        } else if (shari.equals("6")){
            hari.setText("JUMAT");
        } else if (shari.equals("7")){
            hari.setText("SABTU");
        }
        tgl = (TextView)findViewById(R.id.TVtgl);
        tgl.setText(""+ci.get(Calendar.DAY_OF_MONTH));
        bulan = (TextView)findViewById(R.id.TvBulan);
        if (sbulan.equals("1")){
            bulan.setText("JANUARI");
        } else if (sbulan.equals("2")){
            bulan.setText("FEBRUARI");
        } else if (sbulan.equals("3")){
            bulan.setText("MARET");
        } else if (sbulan.equals("4")){
            bulan.setText("APRIL");
        } else if (sbulan.equals("5")){
            bulan.setText("MEI");
        } else if (sbulan.equals("6")){
            bulan.setText("JUNI");
        } else if (sbulan.equals("7")){
            bulan.setText("JULI");
        } else if (sbulan.equals("8")){
            bulan.setText("AGUSTUS");
        } else if (sbulan.equals("9")){
            bulan.setText("SEPTEMBER");
        } else if (sbulan.equals("10")){
            bulan.setText("OKTOBER");
        } else if (sbulan.equals("11")){
            bulan.setText("NOVEMBER");
        } else if (sbulan.equals("12")){
            bulan.setText("DESEMBER");
        }
        tahun = (TextView)findViewById(R.id.tvtahun);
        tahun.setText(""+ci.get(Calendar.YEAR));

        //button gallery
        ivgal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(idea.this, AlbumSelectActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 3);
                startActivityForResult(intent, Constants.REQUEST_CODE);
            }
        });

        //buttom camera
        ivcam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkPermission(Manifest.permission.CAMERA,idea.this)){
                        openCameraApp();
                    }else{
                        requestPermission(idea.this,new String[]{Manifest.permission.CAMERA},REQUEST_ACESS_CAMERA);
                        openCameraApp();
                    }
                }else{
                    openCameraApp();
                }
            }
        });

        //button save jurnal
        misentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(idea.this,MissionIdea.class);
                startActivity(i);
                finish();
            }
        });

        //button preview
        mivpreem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tittle.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Isikan Tittle Anda", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(getApplicationContext(), View_Idea.class);
                    i.putExtra("tittle", tittle.getText().toString().trim());
                    i.putExtra("isi1", isi1.getText().toString().trim());
                    i.putExtra("isi2", isi2.getText().toString().trim());
                    i.putExtra("isi3", isi3.getText().toString().trim());
                    if (null!=mIv1.getDrawable()){
                        img1 = ((BitmapDrawable)mIv1.getDrawable()).getBitmap();
                        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                        img1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
                        byte[] gam = stream1.toByteArray();
                        i.putExtra("bitmap1",gam);
                        stream1.reset();
                        if (null!=mIv2.getDrawable()){
                            img2 = ((BitmapDrawable)mIv2.getDrawable()).getBitmap();
                            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                            img2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                            byte[] gam2 = stream2.toByteArray();
                            i.putExtra("bitmap2",gam2);
                            stream2.reset();
                            if (null!=mIv3.getDrawable()){
                                img3 = ((BitmapDrawable)mIv3.getDrawable()).getBitmap();
                                ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
                                img3.compress(Bitmap.CompressFormat.PNG, 100, stream3);
                                byte[] gam3 = stream3.toByteArray();
                                i.putExtra("bitmap3",gam3);
                            }
                        }
                    }
                    startActivity(i);
                    finish();
                }
            }
        });

        dash = (TextView)findViewById(R.id.iv_dash);
        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tittle.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Isikan Tittle Anda", Toast.LENGTH_LONG).show();
                }else if (isi1.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "Isikan Cerita Anda", Toast.LENGTH_LONG).show();
                }else {
                    uploadData();
                }
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
        final String katagori = User+"Idea";
        final String judul = tittle.getText().toString().trim();
        final String isii1 = isi1.getText().toString().trim();
        final String isii2 = isi2.getText().toString().trim();
        final String isii3 = isi3.getText().toString().trim();
        if (null!=img1) {
            image1 = getStringImage1(img1);
            if (null!=img2) {
                image2 = getStringImage2(img2);
                if (null!=img3) {
                    image3 = getStringImage3(img3);
                }
            }
        }

        class uploadData extends AsyncTask<Void,Void,String> {
            RequestHandler rh = new RequestHandler();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(idea.this,"Upload","Data dikirim....",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equals("")){
                    new AlertDialog.Builder(idea.this)
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
                    Toast.makeText(idea.this,s,Toast.LENGTH_LONG).show();
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


    //Check permision untuk versi marsmellow
    public static boolean checkPermission(String permission, Context context) {
        int statusCode = ContextCompat.checkSelfPermission(context, permission);
        return statusCode == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(AppCompatActivity activity, String[] permission, int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission[0])) {
            Toast.makeText(activity, "Application need permission", Toast.LENGTH_SHORT).show();
        }
        ActivityCompat.requestPermissions(activity, permission, requestCode);
    }

    public static void requestPermission(Fragment fragment, String[] permission, int requestCode) {
        fragment.requestPermissions(permission, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            for (int i = 0, l = images.size(); i < l; i++) {
                if (i==0) {
                    img1 = BitmapFactory.decodeFile(images.get(i).path);
                    img1 = Bitmap.createScaledBitmap(img1, 300, 150, true);
                    mIv1.setImageBitmap(img1);
                } else if (i==1) {
                    img2 = BitmapFactory.decodeFile(images.get(i).path);
                    img2 = Bitmap.createScaledBitmap(img2, 300, 150, true);
                    mIv2.setImageBitmap(img2);
                } else if (i==2) {
                    img3 = BitmapFactory.decodeFile(images.get(i).path);
                    img3 = Bitmap.createScaledBitmap(img3, 300, 150, true);
                    mIv3.setImageBitmap(img3);
                }
            }
        }
        if (requestCode == GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    uri = data.getData();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    try {
                        BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                        options.inSampleSize =calculateInSampleSize(options, 100, 100);
                        options.inJustDecodeBounds = false;
                        Bitmap image = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
                        mIv1.setImageBitmap(image);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Cancelled",
                            Toast.LENGTH_SHORT).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("data")) {
                    if (null==mIv1.getDrawable()) {
                        img1 = (Bitmap) data.getExtras().get("data");
                        uri = getImageUri(idea.this, img1);
                        File finalFile = new File(getRealPathFromUri(uri));
                        mIv1.setImageBitmap(img1);
                    }else if (null==mIv2.getDrawable()) {
                        img2 = (Bitmap) data.getExtras().get("data");
                        uri = getImageUri(idea.this, img2);
                        File finalFile = new File(getRealPathFromUri(uri));
                        mIv2.setImageBitmap(img2);
                    }else if (null==mIv3.getDrawable())  {
                        img3 = (Bitmap) data.getExtras().get("data");
                        uri = getImageUri(idea.this, img2);
                        File finalFile = new File(getRealPathFromUri(uri));
                        mIv3.setImageBitmap(img2);
                    }
                } else if (data.getExtras() == null) {

                    Toast.makeText(getApplicationContext(),
                            "No extras to retrieve!", Toast.LENGTH_SHORT)
                            .show();

                    BitmapDrawable thumbnail = new BitmapDrawable(
                            getResources(), data.getData().getPath());
                    mIv1.setImageDrawable(thumbnail);

                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    private String getRealPathFromUri(Uri tempUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = this.getContentResolver().query(tempUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    private Uri getImageUri(idea youractivity, Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String path = MediaStore.Images.Media.insertImage(youractivity.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    private void openCameraApp() {
        Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (picIntent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(picIntent, CAMERA_REQUEST);
        }
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