package myproject.tracerstudy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import myproject.tracerstudy.Kelas.Alumni;
import myproject.tracerstudy.R;

public class DetailAlumniActivity extends AppCompatActivity {

    Alumni alumni;
    Intent intent;
    CircleImageView ivProfPict;
    TextView tvName,tvNISN,tvEmail,tvPhone,tvAlamat,tvPekerjaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_alumni);

        intent = getIntent();
        alumni = (Alumni) intent.getSerializableExtra("Alumni");

        ivProfPict = findViewById(R.id.ivProfPict);
        tvName = findViewById(R.id.tvName);
        tvNISN = findViewById(R.id.tvNISN);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAlamat = findViewById(R.id.tvAlamat);
        tvPekerjaan = findViewById(R.id.tvPekerjaan);



        Glide.with(this)
                .load(alumni.getFoto())
                .into(ivProfPict);

        tvAlamat.setText(alumni.getAlamat());
        tvNISN.setText("NISN :"+alumni.getNisn());
        tvName.setText(alumni.getNama());
        tvEmail.setText(alumni.getEmail());
        tvPhone.setText(alumni.getPhone());
        tvPekerjaan.setText(alumni.getPekerjaan());
    }
}
