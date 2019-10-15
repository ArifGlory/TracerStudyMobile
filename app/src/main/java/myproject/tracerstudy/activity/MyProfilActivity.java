package myproject.tracerstudy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import myproject.tracerstudy.Kelas.SharedVariable;
import myproject.tracerstudy.Kelas.UserPreference;
import myproject.tracerstudy.MainActivity;
import myproject.tracerstudy.R;

public class MyProfilActivity extends AppCompatActivity {

    LinearLayout lineUbah,lineKeluar,linePassword;
    CircleImageView ivProfPict;
    TextView tvName,tvNIS,tvAgama,tvTahunLulus,tvAlamat,tvPekerjaan,tvNama2,tvJenisKelamin,tvTtl;
    UserPreference mUserPref;
    String urlGambar;
    Date dateTL;

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
        tvNama2 = findViewById(R.id.tvNama2);
        tvNIS = findViewById(R.id.tvNIS);
        tvAgama = findViewById(R.id.tvAgama);
        tvJenisKelamin = findViewById(R.id.tvJenisKelamin);
        tvTahunLulus = findViewById(R.id.tvTahunLulus);
        tvAlamat = findViewById(R.id.tvAlamat);
        tvPekerjaan = findViewById(R.id.tvPekerjaan);
        tvTtl = findViewById(R.id.tvTtl);



        Glide.with(this)
                .load(mUserPref.getFoto())
                .into(ivProfPict);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat format2 =  new SimpleDateFormat("dd-F-yyyy");
        String tanggalLahir = mUserPref.getTanggalLahir();
        try {
            dateTL = format.parse(tanggalLahir);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvTtl.setText(mUserPref.getTempatLahir()+", "+new SimpleDateFormat("dd-MM-yyyy").format(dateTL));
        tvAlamat.setText(mUserPref.getAlamat());
        tvNIS.setText("NIS :"+mUserPref.getNis());
        tvName.setText(mUserPref.getNama());
        tvNama2.setText(mUserPref.getNama());
        tvAgama.setText(mUserPref.getAgama());
        tvTahunLulus.setText("Kelulusan "+mUserPref.getTahunLulus());
        tvPekerjaan.setText(mUserPref.getPekerjaan());
        tvJenisKelamin.setText(mUserPref.getJenisKelamin());

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
                mUserPref.setIsLoggedIn(null);
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
