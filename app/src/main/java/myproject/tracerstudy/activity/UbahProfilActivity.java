package myproject.tracerstudy.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import myproject.tracerstudy.Kelas.SharedVariable;
import myproject.tracerstudy.Kelas.UserPreference;
import myproject.tracerstudy.MainActivity;
import myproject.tracerstudy.R;

public class UbahProfilActivity extends AppCompatActivity {

    LinearLayout lineUbah;
    CircleImageView ivProfPict;
    EditText etPhone,etAlamat,etPekerjaan;
    UserPreference mUserPref;
    ImageButton btnSimpan;

    private int PLACE_PICKER_REQUEST = 1;
    static final int RC_PERMISSION_READ_EXTERNAL_STORAGE = 1;
    static final int RC_IMAGE_GALLERY = 2;
    Uri uri,file;
    FirebaseUser fbUser;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    private HttpResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);
        Firebase.setAndroidContext(this);
        FirebaseApp.initializeApp(UbahProfilActivity.this);

        mUserPref = new UserPreference(this);

        ivProfPict = findViewById(R.id.ivProfPict);
        etPhone = findViewById(R.id.etPhone);
        etAlamat = findViewById(R.id.etAlamat);
        etPekerjaan = findViewById(R.id.etPekerjaan);
        btnSimpan = findViewById(R.id.btnSimpan);

        pDialogLoading = new SweetAlertDialog(UbahProfilActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

        String urlGambar = SharedVariable.IMAGE_URL+mUserPref.getFoto();
        Glide.with(this)
                .load(urlGambar)
                .into(ivProfPict);

        etAlamat.setText(mUserPref.getAlamat());
        etPekerjaan.setText(mUserPref.getPekerjaan());
        etPhone.setText(mUserPref.getNope());

        ivProfPict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(UbahProfilActivity.this, new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE}, RC_PERMISSION_READ_EXTERNAL_STORAGE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, RC_IMAGE_GALLERY);
                }
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView

        if (requestCode == RC_IMAGE_GALLERY && resultCode == RESULT_OK) {
            uri = data.getData();
            ivProfPict.setImageURI(uri);
        }
        else if (requestCode == 100 && resultCode == RESULT_OK){
            uri = file;
            ivProfPict.setImageURI(uri);
        }
    }

    private void uploadGambar(Uri uri){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imagesRef = storageRef.child("images");
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = timeStamp;
        StorageReference fileRef = imagesRef.child(filename);

        UploadTask uploadTask = fileRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(UbahProfilActivity.this, "Upload failed!\n" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                pDialogLoading.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Toast.makeText(UbahProfilActivity.this, "Upload finished!", Toast.LENGTH_SHORT).show();

                // save image to database
                String urlGambar = downloadUrl.toString();
                updateProfile(mUserPref.getIdUser(),
                        etPekerjaan.getText().toString(),
                        etPhone.getText().toString(),
                        etAlamat.getText().toString(),
                        urlGambar);


            }
        });
    }

    public void checkValidation(){
        String getPekerjaan      = etPekerjaan.getText().toString();
        String getPhone  = etPhone.getText().toString();
        String getAlamat  = etAlamat.getText().toString();

        if (getPekerjaan.equals("") || getPekerjaan.length() == 0
                || getPhone.equals("") || getPhone.length() == 0
                || getAlamat.equals("") || getAlamat.length() == 0
                || uri == null){

            new SweetAlertDialog(UbahProfilActivity.this,SweetAlertDialog.ERROR_TYPE)
                    .setContentText("Semua data harus diisi")
                    .setTitleText("Oops..")
                    .setConfirmText("OK")
                    .show();
        }else {
            pDialogLoading.show();
            uploadGambar(uri);
        }
    }

    private void updateProfile(final String idUser, final String pekerjaan, final String phone, final String alamat, final String urlGambar){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {


            @Override
            protected String doInBackground(String... strings) {

                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("id_alumni", idUser));
                nameValuePairs.add(new BasicNameValuePair("pekerjaan", pekerjaan));
                nameValuePairs.add(new BasicNameValuePair("no_hape", phone));
                nameValuePairs.add(new BasicNameValuePair("alamat", alamat));
                nameValuePairs.add(new BasicNameValuePair("foto", urlGambar));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            SharedVariable.ipServer+"/updateProfile/");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();



                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                    Log.d("errorUpdate:",e.getMessage());

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("errorUpdate:",e.getMessage());
                }


                //look at this
                return "success";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                pDialogLoading.dismiss();
                int statusCode = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();


                String responData = null;
                try {

                    responData = EntityUtils.toString(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.d("responRest:",""+statusCode);
                Log.d("responData:",""+responData);

                if (statusCode == 200){
                    responData =  responData.replace("\"", "");
                    Log.d("responData:",""+responData);

                    mUserPref.setPekerjaan(pekerjaan);
                    mUserPref.setNope(phone);
                    mUserPref.setAlamat(alamat);
                    mUserPref.setFoto(urlGambar);

                    Toast.makeText(getApplicationContext(),"Ubah Profil Berhasil !",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                    startActivity(intent);
                }else {
                    new SweetAlertDialog(UbahProfilActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Terjadi kesalahan, coba lagi nanti")
                            .setTitleText("Gagal ubah")
                            .show();
                }



            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(idUser,pekerjaan,phone,alamat,urlGambar);
    }


}
