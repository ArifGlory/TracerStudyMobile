package myproject.tracerstudy.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.tracerstudy.Kelas.Alumni;
import myproject.tracerstudy.Kelas.Lowongan;
import myproject.tracerstudy.Kelas.SharedVariable;
import myproject.tracerstudy.R;
import myproject.tracerstudy.adapter.AdapterAlumni;

public class ListAlumni extends AppCompatActivity {

    Spinner spJurusan,spTahunLulus;
    RecyclerView rvAlumni;
    AdapterAlumni adapter;
    private List<Alumni> alumniList;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    List<String> listJurusan =  new ArrayList<String>();
    List<String> listIdJurusan =  new ArrayList<String>();
    List<String> listTahun =  new ArrayList<String>();
    ArrayAdapter<String> adapterJurusan;
    ArrayAdapter<String> adapterTahun;
    ImageButton btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alumni);

        spJurusan = findViewById(R.id.spJurusan);
        spTahunLulus = findViewById(R.id.spTahunLulus);
        rvAlumni = findViewById(R.id.rvLowongan);
        btnRefresh = findViewById(R.id.btnRefresh);

        alumniList = new ArrayList<>();
        adapter = new AdapterAlumni(ListAlumni.this,alumniList);

        adapterJurusan = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, listJurusan);
        adapterJurusan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJurusan.setAdapter(adapterJurusan);

        adapterTahun = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, listTahun);
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTahunLulus.setAdapter(adapterTahun);



        rvAlumni.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvAlumni.setHasFixedSize(true);
        rvAlumni.setItemAnimator(new DefaultItemAnimator());
        rvAlumni.setAdapter(adapter);

        pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);
        pDialogLoading.show();

        getDataAlumni();
        getDataJurusan();
        getDataTahun();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialogLoading.show();
                getDataAlumni();
            }
        });
    }

    private void getDataAlumni(){
        String url = SharedVariable.ipServer+"/listAlumni/";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialogLoading.dismiss();
                try {

                    JSONArray jsonArray2 = new JSONArray(response);
                    Log.d("jmlAlumni",""+jsonArray2.length());
                    Toast.makeText(getApplicationContext(),"Jumlah alumni = "+jsonArray2.length(),Toast.LENGTH_SHORT).show();

                    alumniList.clear();

                    for (int d=0;d<jsonArray2.length();d++){
                        JSONObject jojo = jsonArray2.getJSONObject(d);
                        Log.d("arrayNya:",""+jojo.toString());

                        String id_alumni = jojo.getString("id_alumni");
                        String nis = jojo.getString("nis");
                        String nisn = jojo.getString("nisn");
                        String nama_alumni = jojo.getString("nama_alumni");
                        String no_hape = jojo.getString("no_hape");
                        String email = jojo.getString("email");
                        String alamat = jojo.getString("alamat");
                        String tahun_lulus = jojo.getString("tahun_lulus");
                        String pekerjaan = jojo.getString("pekerjaan");
                        String id_jurusan = jojo.getString("id_jurusan");
                        String foto = jojo.getString("foto");

                        Alumni alumni = new Alumni(id_alumni,
                                nama_alumni,
                                tahun_lulus,
                                id_jurusan,
                                nis,
                                nisn,
                                no_hape,
                                email,
                                alamat,
                                pekerjaan,
                                foto);
                        alumniList.add(alumni);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialogLoading.dismiss();
                Log.d("getlaporan:","eror "+error.getMessage().toString());
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan, coba lagi nanti",Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getDataJurusan(){
        String url = SharedVariable.ipServer+"/listJurusan/";
        listIdJurusan.clear();
        listJurusan.clear();
        adapterJurusan.notifyDataSetChanged();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialogLoading.dismiss();
                try {

                    JSONArray jsonArray2 = new JSONArray(response);
                    Log.d("jmlLowongan",""+jsonArray2.length());

                    alumniList.clear();

                    for (int d=0;d<jsonArray2.length();d++){
                        JSONObject jojo = jsonArray2.getJSONObject(d);
                        Log.d("arrayNya:",""+jojo.toString());

                        String id_jurusan = jojo.getString("id_jurusan");
                        String nama_jurusan = jojo.getString("nama_jurusan");

                        listIdJurusan.add(id_jurusan);
                        listJurusan.add(nama_jurusan);
                    }
                    adapterJurusan.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialogLoading.dismiss();
                Log.d("gagalJurusa:","eror "+error.getMessage().toString());
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan, coba lagi nanti",Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getDataTahun(){
        String url = SharedVariable.ipServer+"/listTahun/";
        listTahun.clear();
        adapterTahun.notifyDataSetChanged();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialogLoading.dismiss();
                try {

                    JSONArray jsonArray2 = new JSONArray(response);
                    Log.d("jmlLowongan",""+jsonArray2.length());

                    alumniList.clear();

                    for (int d=0;d<jsonArray2.length();d++){
                        JSONObject jojo = jsonArray2.getJSONObject(d);
                        Log.d("arrayNya:",""+jojo.toString());

                        String tahun_lulus = jojo.getString("tahun_lulus");

                        listTahun.add(tahun_lulus);
                    }
                    adapterTahun.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialogLoading.dismiss();
                Log.d("gagalTahun:","eror "+error.getMessage().toString());
                Toast.makeText(getApplicationContext(),"Terjadi kesalahan, coba lagi nanti",Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
