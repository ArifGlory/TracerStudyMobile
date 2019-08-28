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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.tracerstudy.Kelas.Alumni;
import myproject.tracerstudy.Kelas.Lowongan;
import myproject.tracerstudy.Kelas.SharedVariable;
import myproject.tracerstudy.R;
import myproject.tracerstudy.adapter.AdapterLowongan;

public class ListLowongan extends AppCompatActivity {

    RecyclerView rvLowongan;
    AdapterLowongan adapter;
    private List<Lowongan> lowonganList;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    List<String> listJurusan =  new ArrayList<String>();
    List<String> listIdJurusan =  new ArrayList<String>();
    ArrayAdapter<String> adapterJurusan;
    Spinner spJurusan;
    private HttpResponse response;
    ImageButton btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lowongan);

        rvLowongan = findViewById(R.id.rvLowongan);
        spJurusan = findViewById(R.id.spJurusan);
        btnRefresh = findViewById(R.id.btnRefresh);

        lowonganList = new ArrayList<>();
        adapter = new AdapterLowongan(ListLowongan.this,lowonganList);

        adapterJurusan = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, listJurusan);
        adapterJurusan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJurusan.setAdapter(adapterJurusan);


        rvLowongan.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvLowongan.setHasFixedSize(true);
        rvLowongan.setItemAnimator(new DefaultItemAnimator());
        rvLowongan.setAdapter(adapter);

        pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);
        pDialogLoading.show();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialogLoading.show();
                getDataLowongan();
            }
        });

        spJurusan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    String idJurusan    = listIdJurusan.get(position).toString();
                    Log.d("idJurusan :",""+idJurusan);
                    filterData(idJurusan);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getDataLowongan();
        getDataJurusan();
    }

    private void filterData(final String idJurusan){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {

                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("id_jurusan", idJurusan));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            SharedVariable.ipServer+"/filterLowongan/");
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

                        lowonganList.clear();
                        //populate the adapter
                        for (int d=0;d<jsonArray2.length();d++) {
                            JSONObject jojo = jsonArray2.getJSONObject(d);
                            Log.d("arrayNya:", "" + jojo.toString());

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






                    Toast.makeText(getApplicationContext(),"Filtered",Toast.LENGTH_LONG).show();

                }else {
                    new SweetAlertDialog(ListLowongan.this, SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Terjadi kesalahan, coba lagi nanti")
                            .setTitleText("Gagal ubah")
                            .show();
                }

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(idJurusan);
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
}
