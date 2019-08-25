package myproject.tracerstudy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.tracerstudy.Kelas.SharedVariable;
import myproject.tracerstudy.Kelas.UserPreference;
import myproject.tracerstudy.R;

public class UbahPasswordActivity extends AppCompatActivity {

    ImageButton btnSimpan;
    EditText etOldPass,etNewPass;
    UserPreference mUserPref;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    private HttpResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        mUserPref = new UserPreference(this);

        btnSimpan = findViewById(R.id.btnSimpan);
        etOldPass = findViewById(R.id.etOldPass);
        etNewPass = findViewById(R.id.etNewPass);

        pDialogLoading = new SweetAlertDialog(UbahPasswordActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    public void checkValidation(){
        String getOldPass = etOldPass.getText().toString();
        String getNewPass  = etNewPass.getText().toString();

        if (getOldPass.equals("") || getOldPass.length() == 0
                || getNewPass.equals("") || getNewPass.length() == 0){

            new SweetAlertDialog(UbahPasswordActivity.this,SweetAlertDialog.ERROR_TYPE)
                    .setContentText("Semua data harus diisi")
                    .setTitleText("Oops..")
                    .setConfirmText("OK")
                    .show();
        }else if (!mUserPref.getPassword().equals(getOldPass)){
            new SweetAlertDialog(UbahPasswordActivity.this,SweetAlertDialog.ERROR_TYPE)
                    .setContentText("Password lama salah")
                    .setTitleText("Oops..")
                    .setConfirmText("OK")
                    .show();
        }
        else {
            pDialogLoading.show();
            updatePassword(getNewPass);
        }
    }

    private void updatePassword(final String newPassword){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {


            @Override
            protected String doInBackground(String... strings) {

                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("id_alumni", mUserPref.getIdUser()));
                nameValuePairs.add(new BasicNameValuePair("password", newPassword));

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

                    mUserPref.setPassword(newPassword);
                    Toast.makeText(getApplicationContext(),"Ubah Password Berhasil !",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                    startActivity(intent);
                }else {
                    new SweetAlertDialog(UbahPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Terjadi kesalahan, coba lagi nanti")
                            .setTitleText("Gagal ubah")
                            .show();
                }



            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(newPassword);
    }
}
