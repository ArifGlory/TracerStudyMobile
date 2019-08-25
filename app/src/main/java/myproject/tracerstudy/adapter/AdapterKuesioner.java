package myproject.tracerstudy.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import myproject.tracerstudy.Kelas.Kuesioner;
import myproject.tracerstudy.R;
import myproject.tracerstudy.activity.DetailKuesionerActivity;
import myproject.tracerstudy.activity.DetailLowonganActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AdapterKuesioner extends RecyclerView.Adapter<AdapterKuesioner.MyViewHolder> {

    private Context mContext;
    private List<Kuesioner> kuesionerList;
    private SweetAlertDialog pDialogLoading,pDialodInfo;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNamaKuesioner;
        public LinearLayout lineKuesioner;

        public MyViewHolder(View view) {
            super(view);
            tvNamaKuesioner = (TextView) view.findViewById(R.id.tvNamaKuesioner);
            lineKuesioner = view.findViewById(R.id.lineKuesioner);

        }
    }

    public AdapterKuesioner(Context mContext, List<Kuesioner> kuesionerList) {
        this.mContext = mContext;
        this.kuesionerList = kuesionerList;

        pDialogLoading = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kuesioner, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (kuesionerList.isEmpty()){

            Log.d("isiKuesioner: ",""+kuesionerList.size());
        }else {


            final Kuesioner kuesioner  = kuesionerList.get(position);

            holder.tvNamaKuesioner.setText(kuesioner.getNamaKuesioner());
            holder.lineKuesioner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailKuesionerActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("idKuesioner",kuesioner.getIdKuesioner());
                    mContext.startActivity(intent);
                }
            });

        }

    }


    @Override
    public int getItemCount() {
        //return namaWisata.length;
        return kuesionerList.size();
    }
}
