package myproject.tracerstudy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import myproject.tracerstudy.Kelas.SharedVariable;
import myproject.tracerstudy.Kelas.UserPreference;
import myproject.tracerstudy.MainActivity;
import myproject.tracerstudy.R;

public class MyProfilActivity extends AppCompatActivity {

    LinearLayout lineUbah,lineKeluar,linePassword;
    CircleImageView ivProfPict;
    TextView tvName,tvNISN,tvEmail,tvPhone,tvAlamat,tvPekerjaan;
    UserPreference mUserPref;
    String urlGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profil);

        mUserPref = new UserPreference(this);

        lineUbah = findViewById(R.id.lineUbah);
        lineKeluar = findViewById(R.id.lineKeluar);
        linePassword = findViewById(R.id.linePassword);
        ivProfPict = findViewById(R.id.ivProfPict);
        tvName = findViewById(R.id.tvName);
        tvNISN = findViewById(R.id.tvNISN);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAlamat = findViewById(R.id.tvAlamat);
        tvPekerjaan = findViewById(R.id.tvPekerjaan);



        Glide.with(this)
                .load(mUserPref.getFoto())
                .into(ivProfPict);

        tvAlamat.setText(mUserPref.getAlamat());
        tvNISN.setText("NISN :"+mUserPref.getNisn());
        tvName.setText(mUserPref.getNama());
        tvEmail.setText(mUserPref.getEmail());
        tvPhone.setText(mUserPref.getNope());
        tvPekerjaan.setText(mUserPref.getPekerjaan());

        lineUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UbahProfilActivity.class);
                startActivity(intent);
            }
        });

        lineKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        linePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UbahPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
