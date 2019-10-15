package myproject.tracerstudy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import myproject.tracerstudy.Kelas.Alumni;
import myproject.tracerstudy.R;

public class DetailAlumniActivity extends AppCompatActivity {

    Alumni alumni;
    Intent intent;
    CircleImageView ivProfPict;
    TextView tvName,tvNIS,tvAgama,tvTahunLulus,tvAlamat,tvPekerjaan,tvNama2,tvJenisKelamin,tvTtl;
    Date dateTL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_alumni);

        intent = getIntent();
        alumni = (Alumni) intent.getSerializableExtra("Alumni");

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
                .load(alumni.getFoto())
                .into(ivProfPict);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat format2 =  new SimpleDateFormat("dd-F-yyyy");
        String tanggalLahir = alumni.getTanggal_lahir();
        try {
            dateTL = format.parse(tanggalLahir);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvTtl.setText(alumni.getTempat_lahir()+", "+new SimpleDateFormat("dd-MM-yyyy").format(dateTL));
        tvAlamat.setText(alumni.getAlamat());
        tvNIS.setText("NIS :"+alumni.getNis());
        tvName.setText(alumni.getNama());
        tvNama2.setText(alumni.getNama());
        tvAgama.setText(alumni.getAgama());
        tvTahunLulus.setText("Kelulusan "+alumni.getTahun_lulus());
        tvPekerjaan.setText(alumni.getPekerjaan());
        tvJenisKelamin.setText(alumni.getJenis_kelamin());
    }
}
