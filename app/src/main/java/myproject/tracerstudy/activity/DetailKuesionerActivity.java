package myproject.tracerstudy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.tracerstudy.Kelas.Pertanyaan;
import myproject.tracerstudy.Kelas.SharedVariable;
import myproject.tracerstudy.Kelas.UserPreference;
import myproject.tracerstudy.R;

public class DetailKuesionerActivity extends AppCompatActivity {

    EditText etJawaban;
    TextView tvPertanyaan;
    Button btnNext;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    UserPreference mUserpref;
    private List<Pertanyaan> pertanyaanList;

    Intent intent;
    String idKuesioner;
    private int currentPertanyaan = 0;
    ArrayList<String> jawabanList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kuesioner);

        mUserpref = new UserPreference(this);
        pDialogLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

        pertanyaanList = new ArrayList<>();

        intent = getIntent();
        idKuesioner = intent.getStringExtra("idKuesioner");

        btnNext = findViewById(R.id.btnNext);
        etJawaban = findViewById(R.id.etJawaban);
        tvPertanyaan = findViewById(R.id.tvPertanyaan);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etJawaban.getText().toString().equals("") || etJawaban.getText().toString().length() == 0){
                    new SweetAlertDialog(DetailKuesionerActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Jawaban harus diisi")
                            .setTitleText("oops")
                            .show();
                }else{
                    nextPertanyaan();
                }

            }
        });

        getPertanyaan(idKuesioner);
    }

    private void getPertanyaan(String idKuesioner){
        String url = SharedVariable.ipServer+"/detailKuesioner/"+idKuesioner;

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
        pertanyaanList.clear();

        for (int d=0;d<jsonArray2.length();d++){
            JSONObject jojo = jsonArray2.getJSONObject(d);
            Log.d("arrayNya:",""+jojo.toString());

            String id_pertanyaan = jojo.getString("id_pertanyaan");
            String pertanyaan = jojo.getString("pertanyaan");
            String id_kuesioner = jojo.getString("id_kuesioner");
            Log.d("pertanyaan : ",pertanyaan);

            Pertanyaan pertanyaanClass = new Pertanyaan(id_pertanyaan,pertanyaan,id_kuesioner);
            pertanyaanList.add(pertanyaanClass);

        }
        setupPertanyaan();
    }

    private void setupPertanyaan(){

        Pertanyaan myPertanyan = pertanyaanList.get(currentPertanyaan);
        tvPertanyaan.setText(myPertanyan.getPertanyaan());

        currentPertanyaan++;
    }

    private void nextPertanyaan(){
        String jawaban = etJawaban.getText().toString();
        jawabanList.add(jawaban);
        etJawaban.setText("");

        if (currentPertanyaan < pertanyaanList.size()){
            setupPertanyaan();
        }else{
            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
            intent.putExtra("listJawaban",jawabanList);
            intent.putExtra("idKuesioner",idKuesioner);
            startActivity(intent);
            finish();
        }
    }

}
