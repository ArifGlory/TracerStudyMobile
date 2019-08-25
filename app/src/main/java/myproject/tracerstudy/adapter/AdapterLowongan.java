package myproject.tracerstudy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.tracerstudy.Kelas.Lowongan;
import myproject.tracerstudy.R;
import myproject.tracerstudy.activity.DetailLowonganActivity;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AdapterLowongan extends RecyclerView.Adapter<AdapterLowongan.MyViewHolder> {

    private Context mContext;
    private List<Lowongan> lowonganList;
    private SweetAlertDialog pDialogLoading,pDialodInfo;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJudul,tvDeskripsi;
        public ImageView ivLowongan;
        public LinearLayout lineLowongan;

        public MyViewHolder(View view) {
            super(view);
            tvJudul = (TextView) view.findViewById(R.id.tvJudul);
            tvDeskripsi =  view.findViewById(R.id.tvDeskripsi);
            ivLowongan =  view.findViewById(R.id.ivLowongan);
            lineLowongan = view.findViewById(R.id.lineLowongan);

        }
    }

    public AdapterLowongan(Context mContext, List<Lowongan> lowonganList) {
        this.mContext = mContext;
        this.lowonganList = lowonganList;

        pDialogLoading = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lowongan, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (lowonganList.isEmpty()){

            Log.d("isiLowongan: ",""+lowonganList.size());
        }else {


            final Lowongan lowongan  = lowonganList.get(position);

            holder.tvJudul.setText(lowongan.getJudul());
            holder.tvDeskripsi.setText(lowongan.getDeskripsi());
            Glide.with(mContext)
                    .load(lowongan.getFoto())
                    .into(holder.ivLowongan);
            holder.lineLowongan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailLowonganActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("lowongan",lowongan);
                    mContext.startActivity(intent);
                }
            });




        }

    }


    @Override
    public int getItemCount() {
        //return namaWisata.length;
        return lowonganList.size();
    }
}
