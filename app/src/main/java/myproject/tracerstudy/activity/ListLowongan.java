package myproject.tracerstudy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import myproject.tracerstudy.Kelas.Lowongan;
import myproject.tracerstudy.Kelas.SharedVariable;
import myproject.tracerstudy.R;
import myproject.tracerstudy.adapter.AdapterLowongan;

public class ListLowongan extends AppCompatActivity {

    RecyclerView rvLowongan;
    AdapterLowongan adapter;
    private List<Lowongan> lowonganList;
    private SweetAlertDialog pDialogLoading,pDialodInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lowongan);

        rvLowongan = findViewById(R.id.rvLowongan);

        lowonganList = new ArrayList<>();
        adapter = new AdapterLowongan(ListLowongan.this,lowonganList);

        rvLowongan.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvLowongan.setHasFixedSize(true);
        rvLowongan.setItemAnimator(new DefaultItemAnimator());
        rvLowongan.setAdapter(adapter);

        pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);
        pDialogLoading.show();

        getDataLowongan();
    }

    public void getDataLowongan(){
        String url = SharedVariable.ipServer+"/listLowongan/";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialogLoading.dismiss();
                try {

                    JSONArray jsonArray2 = new JSONArray(response);
                    Log.d("jmlLowongan",""+jsonArray2.length());

                    lowonganList.clear();

                    for (int d=0;d<jsonArray2.length();d++){
                        JSONObject jojo = jsonArray2.getJSONObject(d);
                        Log.d("arrayNya:",""+jojo.toString());

                        String id_lowongan = jojo.getString("id_lowongan");
                        String judul = jojo.getString("judul");
                        String deskripsi = jojo.getString("deskripsi");
                        String foto = jojo.getString("foto");
                        String urlFoto = SharedVariable.IMAGE_URL+"lowongan/"+foto;

                        Lowongan lowongan = new Lowongan(id_lowongan,
                                judul,
                                deskripsi,
                                urlFoto);
                        lowonganList.add(lowongan);
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
}
