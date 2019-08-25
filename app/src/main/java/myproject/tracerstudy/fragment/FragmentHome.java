package myproject.tracerstudy.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.tracerstudy.Kelas.SharedVariable;
import myproject.tracerstudy.R;
import myproject.tracerstudy.activity.ListKuesioner;
import myproject.tracerstudy.activity.ListLowongan;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {


    public FragmentHome() {
        // Required empty public constructor
    }

    CarouselView carouselView;
    TextView tvDataKuesiner,tvDataLowongan;

    private SweetAlertDialog pDialogLoading,pDialodInfo;
    LinearLayout lineLowongan,lineKuesioner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvDataKuesiner = view.findViewById(R.id.tvDataKuesioner);
        tvDataLowongan = view.findViewById(R.id.tvDataLowongan);
        lineLowongan = view.findViewById(R.id.lineLowongan);
        lineKuesioner = view.findViewById(R.id.lineKuesioner);


        pDialogLoading = new SweetAlertDialog(this.getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Menampilkan data..");
        pDialogLoading.setCancelable(false);

        lineLowongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListLowongan.class);
                startActivity(intent);
            }
        });
        lineKuesioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListKuesioner.class);
                startActivity(intent);
            }
        });

        getJumlahKuesioner();
        getJumlahLowongan();


        return view;
    }


    public void getJumlahLowongan(){
        String url = SharedVariable.ipServer+"/listLowongan/";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialogLoading.dismiss();
                try {

                    JSONArray jsonArray2 = new JSONArray(response);
                    Log.d("jmlLowongan",""+jsonArray2.length());
                    tvDataLowongan.setText(jsonArray2.length()+ " Lowongan");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialogLoading.dismiss();
                Log.d("getlaporan:","eror "+error.getMessage().toString());
                Toast.makeText(getActivity(),"Terjadi kesalahan, coba lagi nanti",Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void getJumlahKuesioner(){
        String url = SharedVariable.ipServer+"/listKuesioner/";

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialogLoading.dismiss();
                try {

                    JSONArray jsonArray2 = new JSONArray(response);
                    Log.d("jmlKuesioner",""+jsonArray2.length());
                    tvDataKuesiner.setText(jsonArray2.length()+" Kuesioner");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialogLoading.dismiss();
                Log.d("getlaporan:","eror "+error.getMessage().toString());
                Toast.makeText(getActivity(),"Terjadi kesalahan, coba lagi nanti",Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }






}
