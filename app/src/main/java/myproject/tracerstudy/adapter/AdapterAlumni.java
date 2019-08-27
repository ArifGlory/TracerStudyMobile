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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import myproject.tracerstudy.Kelas.Alumni;
import myproject.tracerstudy.R;
import myproject.tracerstudy.activity.DetailAlumniActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AdapterAlumni extends RecyclerView.Adapter<AdapterAlumni.MyViewHolder> {

    private Context mContext;
    private List<Alumni> alumniList;
    private SweetAlertDialog pDialogLoading,pDialodInfo;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama,tvTahunLulus;
        public ImageView ivProfPict;
        public RelativeLayout rlAlumni;

        public MyViewHolder(View view) {
            super(view);
            tvNama = (TextView) view.findViewById(R.id.tvNama);
            tvTahunLulus =  view.findViewById(R.id.tvTahunLulus);
            ivProfPict =  view.findViewById(R.id.ivProfPict);
            rlAlumni = view.findViewById(R.id.rlAlumni);

        }
    }

    public AdapterAlumni(Context mContext, List<Alumni> alumniList) {
        this.mContext = mContext;
        this.alumniList = alumniList;

        pDialogLoading = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alumni, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (alumniList.isEmpty()){

            Log.d("isiAlumni: ",""+alumniList.size());
        }else {


            final Alumni alumni  = alumniList.get(position);
            Log.d("adapterAluni:",alumni.getNama());

            holder.tvNama.setText(alumni.getNama());
            holder.tvTahunLulus.setText("Kelulusan "+alumni.getTahun_lulus());
            Glide.with(mContext)
                    .load(alumni.getFoto())
                    .into(holder.ivProfPict);
            holder.rlAlumni.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailAlumniActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Alumni",alumni);
                    mContext.startActivity(intent);
                }
            });




        }

    }


    @Override
    public int getItemCount() {
        //return namaWisata.length;
        return alumniList.size();
    }
}
