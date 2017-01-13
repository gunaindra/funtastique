package com.example.sandi.funtastiq.Data;

/**
 * Created by SandI on 12/14/2016.
 */

public class Class_Get_Idea {
    private static Class_Get_Idea instance;
    private String Etittle;
    private String Eisi_pertama;
    private String Eisi_kedua;
    private String Eisi_ketiga;
    private String Egambar_satu;
    private String Egambar_dua;
    private String Egambar_tiga;

    private Class_Get_Idea() {
    }

    public String getEtittle() {
        return this.Etittle;
    }
    public void setEtittle(String etittle) {
        this.Etittle = etittle;
    }

    public String getEisi_pertama(){
        return this.Eisi_pertama;
    }
    public void setEisi_pertama(String eisi_pertama) {this.Eisi_pertama = eisi_pertama;}

    public String getEisi_kedua(){
        return this.Eisi_kedua;
    }
    public void setEisi_kedua(String eisi_kedua) {this.Eisi_kedua = eisi_kedua;}

    public String getEisi_ketiga(){
        return this.Eisi_ketiga;
    }
    public void setEisi_ketiga(String eisi_ketiga) {this.Eisi_ketiga = eisi_ketiga;}

    public String getEgambar_satu(){
        return this.Egambar_satu;
    }
    public void setEgambar_satu(String egambar_satu) {this.Egambar_satu = egambar_satu;}

    public String getEgambar_dua(){
        return this.Egambar_dua;
    }
    public void setEgambar_dua(String egambar_dua) {this.Egambar_dua = egambar_dua;}

    public String getEgambar_tiga(){
        return this.Egambar_tiga;
    }
    public void setEgambar_tiga(String egambar_tiga) {this.Egambar_tiga = egambar_tiga;}

    public static synchronized Class_Get_Idea getInstance(){
        if (instance == null){
            instance=new Class_Get_Idea();
        }
        return instance;
    }
}
