package myproject.tracerstudy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
import java.util.Map;

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
    private HttpResponse response;
    private RequestQueue requestQueue;

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
        spTahunLulus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0){
                    String tahun        = adapterTahun.getItem(position).toString();
                    String idJurusan    = listIdJurusan.get(spJurusan.getSelectedItemPosition()).toString();

                    Log.d("idJurusan :",""+idJurusan);
                    Log.d("tahun:",tahun);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spJurusan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0){
                        String idJurusan    = listIdJurusan.get(position).toString();
                    String tahun        = listTahun.get(spTahunLulus.getSelectedItemPosition());
                    Log.d("idJurusan :",""+idJurusan);
                    Log.d("tahun:",tahun);

                    pDialogLoading.show();
                    //filterData(tahun,idJurusan);
                    try {
                        filterAlumni(tahun,idJurusan);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void filterData(final String tahun, final String idJurusan){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {

                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("id_jurusan", idJurusan));
                nameValuePairs.add(new BasicNameValuePair("tahun_lulus", tahun));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            SharedVariable.ipServer+"/filterAlumni/");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();



                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                    Log.d("errorFilter:",e.getMessage());

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("errorFilter:",e.getMessage());
                }

                //look at this
                return "success";
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                pDialogLoading.dismiss();
                int statusCode = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();


                String responData = null;
                try {

                    responData = EntityUtils.toString(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d("responRest:",""+statusCode);
                Log.d("responData:",""+responData);


                if (statusCode == 200){
                    JSONArray jsonArray2 = null;
                    try {
                        jsonArray2 = new JSONArray(responData);
                        Log.d("jmlFiltered",""+jsonArray2.length());

                        alumniList.clear();
                        //populate the adapter
                        for (int d=0;d<jsonArray2.length();d++) {
                            JSONObject jojo = jsonArray2.getJSONObject(d);
                            Log.d("arrayNya:", "" + jojo.toString());

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
                            String jenis_kelamin = jojo.getString("jenis_kelamin");
                            String tempat_lahir = jojo.getString("tempat_lahir");
                            String tanggal_lahir = jojo.getString("tanggal_lahir");
                            String agama = jojo.getString("agama");
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
                                    foto,
                                    jenis_kelamin,
                                    tempat_lahir,
                                    tanggal_lahir,
                                    agama);
                            alumniList.add(alumni);
                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }






                    Toast.makeText(getApplicationContext(),"Filtered",Toast.LENGTH_LONG).show();

                }else {
                    new SweetAlertDialog(ListAlumni.this, SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Terjadi kesalahan, coba lagi nanti")
                            .setTitleText("Gagal ubah")
                            .show();
                }

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(tahun,idJurusan);
    }

    private void filterAlumni(final String tahun,final String idJurusan) throws JSONException {
        String url = SharedVariable.ipServer+"/filterAlumni/";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialogLoading.dismiss();
                        Log.d("MYLaporan", response.toString());

                        int prevSize = alumniList.size();

                        alumniList.clear();
                        adapter.notifyItemRangeRemoved(0,prevSize);

                        JSONArray jsonArray2 = null;
                        try {
                            jsonArray2 = new JSONArray(response);
                            Log.d("jmltanggapan",""+jsonArray2.length());

                            for (int d=0; d < jsonArray2.length(); d++){
                                JSONObject jojo = jsonArray2.getJSONObject(d);

                                Log.d("arrayNya:", "" + jojo.toString());

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
                                String jenis_kelamin = jojo.getString("jenis_kelamin");
                                String tempat_lahir = jojo.getString("tempat_lahir");
                                String tanggal_lahir = jojo.getString("tanggal_lahir");
                                String agama = jojo.getString("agama");
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
                                        foto,
                                        jenis_kelamin,
                                        tempat_lahir,
                                        tanggal_lahir,
                                        agama);
                                alumniList.add(alumni);
                            }
                            adapter.notifyDataSetChanged();
                           // adapter.notifyItemRangeRemoved(0,prevSize);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialogLoading.dismiss();
                        //Log.d("ErrTanggapan", error.toString());
                        Toast.makeText(getApplicationContext(),"terjadi kesalahan, coba lagi nanti",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tahun_lulus", tahun);
                params.put("id_jurusann", idJurusan);
                return params;
            }
        };


        addToRequestQueue(stringRequest,"filterRequest");
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
                        String jenis_kelamin = jojo.getString("jenis_kelamin");
                        String tempat_lahir = jojo.getString("tempat_lahir");
                        String tanggal_lahir = jojo.getString("tanggal_lahir");
                        String agama = jojo.getString("agama");
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
                                foto,
                                jenis_kelamin,
                                tempat_lahir,
                                tanggal_lahir,
                                agama);
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

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }


    public void addToRequestQueue(Request request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }
}
