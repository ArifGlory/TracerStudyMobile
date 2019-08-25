package myproject.tracerstudy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class ResultActivity extends AppCompatActivity {

    Intent intent;
    ArrayList<String> listJawaban = new ArrayList();
    String jawaban = "";
    String idKuesioner;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    private HttpResponse response;
    UserPreference mUserPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        intent = getIntent();
        pDialogLoading = new SweetAlertDialog(ResultActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);
        pDialogLoading.show();

        mUserPref = new UserPreference(this);

        listJawaban = intent.getStringArrayListExtra("listJawaban");
        idKuesioner = intent.getStringExtra("idKuesioner");

        for (int c=0;c < listJawaban.size();c++){
            jawaban = jawaban + listJawaban.get(c) + ",";
        }
        Log.d("stringbiasa:",jawaban);

        sendJawaban(jawaban);

    }

    public void keHome(View view) {
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void sendJawaban(final String jawaban){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {


            @Override
            protected String doInBackground(String... strings) {

                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("id_kuesioner", idKuesioner));
                nameValuePairs.add(new BasicNameValuePair("id_alumni", mUserPref.getIdUser()));
                nameValuePairs.add(new BasicNameValuePair("jawaban", jawaban));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            SharedVariable.ipServer+"/simpanJawaban/");
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

                }else {
                    new SweetAlertDialog(ResultActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Terjadi kesalahan, coba lagi nanti")
                            .setTitleText("Simpan gagal")
                            .show();
                }



            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(jawaban);
    }
}
