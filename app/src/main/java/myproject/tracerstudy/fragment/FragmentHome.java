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
import android.widget.RelativeLayout;
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
import myproject.tracerstudy.activity.ListAlumni;
import myproject.tracerstudy.activity.ListKuesioner;
import myproject.tracerstudy.activity.ListLowongan;
import myproject.tracerstudy.activity.MyProfilActivity;


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
    RelativeLayout rlAlumni,rlProfil,rlTracer,rlLowongan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);


        pDialogLoading = new SweetAlertDialog(this.getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Menampilkan data..");
        pDialogLoading.setCancelable(false);


        rlAlumni = view.findViewById(R.id.rlAlumni);
        rlTracer = view.findViewById(R.id.rlTracer);
        rlProfil = view.findViewById(R.id.rlProfil);
        rlLowongan = view.findViewById(R.id.rlLowongan);

        rlAlumni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListAlumni.class);
                startActivity(intent);
            }
        });
        rlLowongan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ListLowongan.class);
                startActivity(intent);
            }
        });
        rlTracer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ListKuesioner.class);
                startActivity(intent);
            }
        });
        rlProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyProfilActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }








}
