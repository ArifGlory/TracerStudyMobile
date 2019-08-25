package myproject.tracerstudy.Kelas;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    private String KEY_BAGIAN = "bagian";
    private String KEY_EMAIL = "email";
    private String KEY_NIS = "nis";
    private String KEY_NISN = "nisn";
    private String KEY_ALAMAT = "alamat";
    private String KEY_PEKERJAAN = "pekerjaan";
    private String KEY_FOTO = "foto";
    private String KEY_ID_JURUSAN = "idJurusan";
    private String KEY_TOKEN = "token";
    private String KEY_ID_USER = "username";
    private String KEY_IS_LOGGED_IN = "islogin";
    private String KEY_NAMA = "nama";
    private String KEY_NO_HP = "nope";
    private String KEY_PASSWORD = "password";
    private SharedPreferences preferences;

    public UserPreference(Context context){
        String PREFS_NAME = "UserPref";
        preferences = context.getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
    }

    public void setBagian(String bagian){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_BAGIAN,bagian);
        editor.apply();
    }


    public void setEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_EMAIL,email);
        editor.apply();
    }

    public void setPassword(String password){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_PASSWORD,password);
        editor.apply();
    }

    public void setNis(String nis){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NIS,nis);
        editor.apply();
    }

    public void setNisn(String nisn){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NISN,nisn);
        editor.apply();
    }

    public void setNama(String nama){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NAMA,nama);
        editor.apply();
    }

    public void setAlamat(String alamat){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ALAMAT,alamat);
        editor.apply();
    }

    public void setPekerjaan(String pekerjaan){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_PEKERJAAN,pekerjaan);
        editor.apply();
    }

    public void setFoto(String foto){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_FOTO,foto);
        editor.apply();
    }

    public void setIdJurusan(String idJurusan){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ID_JURUSAN,idJurusan);
        editor.apply();
    }

    public void setNope(String nope){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NO_HP,nope);
        editor.apply();
    }

    public void setToken(String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_TOKEN,token);
        editor.apply();
    }

    public void setIdUser(String idUser){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_ID_USER,idUser);
        editor.apply();
    }

    public void setIsLoggedIn(String isLoggedIn){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_IS_LOGGED_IN,isLoggedIn);
        editor.apply();
    }

    public String getIsLoggedIn(){
        return preferences.getString(KEY_IS_LOGGED_IN,null);
    }

    public String getBagian(){
        return preferences.getString(KEY_BAGIAN,null);
    }

    public String getEmail(){
        return preferences.getString(KEY_EMAIL,null);
    }

    public String getPassword(){
        return preferences.getString(KEY_PASSWORD,null);
    }

    public String getNis(){
        return preferences.getString(KEY_NIS,null);
    }

    public String getNisn(){
        return preferences.getString(KEY_NISN,null);
    }

    public String getAlamat(){
        return preferences.getString(KEY_ALAMAT,null);
    }

    public String getPekerjaan(){
        return preferences.getString(KEY_PEKERJAAN,null);
    }

    public String getFoto(){
        return preferences.getString(KEY_FOTO,null);
    }

    public String getidJurusan(){
        return preferences.getString(KEY_ID_JURUSAN,null);
    }

    public String getIdUser(){
        return preferences.getString(KEY_ID_USER,null);
    }

    public String getToken(){
        return preferences.getString(KEY_TOKEN,null);
    }

    public String getNama(){
        return preferences.getString(KEY_NAMA,null);
    }

    public String getNope(){
        return preferences.getString(KEY_NO_HP,null);
    }
}