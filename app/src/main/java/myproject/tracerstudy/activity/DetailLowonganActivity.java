package myproject.tracerstudy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import myproject.tracerstudy.Kelas.Lowongan;
import myproject.tracerstudy.R;

public class DetailLowonganActivity extends AppCompatActivity {

    TextView tvJudul,tvDeskripsi;
    ImageView ivLowongan;
    Intent intent;
    Lowongan lowongan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lowongan);

        intent = getIntent();
        lowongan = (Lowongan) intent.getSerializableExtra("lowongan");

        tvJudul = findViewById(R.id.tvJudul);
        tvDeskripsi = findViewById(R.id.tvDeskripsi);
        ivLowongan = findViewById(R.id.ivLowongan);

        tvDeskripsi.setText(lowongan.getDeskripsi());
        tvJudul.setText(lowongan.getJudul());
        Glide.with(this)
                .load(lowongan.getFoto())
                .into(ivLowongan);
    }
}
