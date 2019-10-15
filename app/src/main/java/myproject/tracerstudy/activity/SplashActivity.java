package myproject.tracerstudy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.tracerstudy.Kelas.SharedVariable;
import myproject.tracerstudy.Kelas.UserPreference;
import myproject.tracerstudy.MainActivity;
import myproject.tracerstudy.R;

public class SplashActivity extends AppCompatActivity {

    Intent i;
    int delay =  3000;
    private static final long time = 3;
    private CountDownTimer mCountDownTimer;
    private long mTimeRemaining;
    private String now;
    String activeDeviceKirim;
    String bagian;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    String versiApp = "1";
    UserPreference mUserpref;
    String urlGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mUserpref = new UserPreference(this);

        pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

        if (mUserpref.getIsLoggedIn() != null){

            //get data user berdasarkan userprefence id user nya
            getUserData(mUserpref.getIdUser());

        }else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    private void getUserData(String idUser){
        String url = SharedVariable.ipServer+"/userProfile/"+idUser;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialogLoading.dismiss();
                try {
                    showPaket(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialogLoading.dismiss();
                Log.d("getlaporan:","eror "+error.getMessage());
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan, coba lagi nanti",Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void showPaket(String response) throws JSONException {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();


        JSONArray jsonArray2 = new JSONArray(response);
        Log.d("ukuranJarray",""+jsonArray2.length());

        if (jsonArray2.length() == 0){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }


        for (int d=0;d<jsonArray2.length();d++){
            JSONObject jojo = jsonArray2.getJSONObject(d);
            Log.d("arrayNya:",""+jojo.toString());

            String nama_alumni = jojo.getString("nama_alumni");
            String nis = jojo.getString("nis");
            String nisn = jojo.getString("nisn");
            String no_hape = jojo.getString("no_hape");
            String email = jojo.getString("email");
            String alamat = jojo.getString("alamat");
            String pekerjaan = jojo.getString("pekerjaan");
            String foto = jojo.getString("foto");
            String idJurusan = jojo.getString("id_jurusan");
            String password = jojo.getString("password");
            String jenis_kelamin = jojo.getString("jenis_kelamin");
            String tempat_lahir = jojo.getString("tempat_lahir");
            String tanggal_lahir = jojo.getString("tanggal_lahir");
            String agama = jojo.getString("agama");
            String tahun_lulus = jojo.getString("tahun_lulus");
            Log.d("nama alumni : ",nama_alumni);

            if (foto.substring(0,4).equals("http")){
                urlGambar = foto;
            }else{
                urlGambar = SharedVariable.IMAGE_URL+foto;
            }

            mUserpref.setNis(nis);
            mUserpref.setNama(nama_alumni);
            mUserpref.setNisn(nisn);
            mUserpref.setNope(no_hape);
            mUserpref.setEmail(email);
            mUserpref.setAlamat(alamat);
            mUserpref.setPekerjaan(pekerjaan);
            mUserpref.setFoto(urlGambar);
            mUserpref.setIdJurusan(idJurusan);
            mUserpref.setPassword(password);
            mUserpref.setJenisKelamin(jenis_kelamin);
            mUserpref.setTempatLahir(tempat_lahir);
            mUserpref.setTanggalLahir(tanggal_lahir);
            mUserpref.setAgama(agama);
            mUserpref.setTahunLulus(tahun_lulus);

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);

        }

    }

}
