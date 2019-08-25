package myproject.tracerstudy.activity;

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
import myproject.tracerstudy.Kelas.Kuesioner;
import myproject.tracerstudy.Kelas.Lowongan;
import myproject.tracerstudy.Kelas.SharedVariable;
import myproject.tracerstudy.R;
import myproject.tracerstudy.adapter.AdapterKuesioner;
import myproject.tracerstudy.adapter.AdapterLowongan;

public class ListKuesioner extends AppCompatActivity {

    RecyclerView rvKuesioner;
    AdapterKuesioner adapter;
    private List<Kuesioner> kuesionerList;
    private SweetAlertDialog pDialogLoading,pDialodInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kuesioner);

        rvKuesioner = findViewById(R.id.rvKuesioner);

        kuesionerList = new ArrayList<>();
        adapter = new AdapterKuesioner(ListKuesioner.this,kuesionerList);

        rvKuesioner.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvKuesioner.setHasFixedSize(true);
        rvKuesioner.setItemAnimator(new DefaultItemAnimator());
        rvKuesioner.setAdapter(adapter);

        pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);
        pDialogLoading.show();

       getDataKuesioner();

    }

    public void getDataKuesioner(){
        String url = SharedVariable.ipServer+"/listKuesioner/";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialogLoading.dismiss();
                try {

                    JSONArray jsonArray2 = new JSONArray(response);
                    Log.d("jmlLowongan",""+jsonArray2.length());

                    kuesionerList.clear();

                    for (int d=0;d<jsonArray2.length();d++){
                        JSONObject jojo = jsonArray2.getJSONObject(d);
                        Log.d("arrayNya:",""+jojo.toString());

                        String id_kuesioner = jojo.getString("id_kuesioner");
                        String nama_kuesioner = jojo.getString("nama_kuesioner");
                        String created = jojo.getString("created");
                        String id_admin = jojo.getString("id_admin");

                        Kuesioner kuesioner = new Kuesioner(id_kuesioner,nama_kuesioner,created,id_admin);

                        kuesionerList.add(kuesioner);
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
