package project.m.medicareminorproject.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import project.m.medicareminorproject.helper.AmbulanceHelper;
import project.m.medicareminorproject.adapter.AmbulanceListAdapter;
import project.m.medicareminorproject.R;

public class AmbulanceActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    TextView response ;
    ArrayList<AmbulanceHelper> ambulanceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(AmbulanceActivity.this)); // yo chai recyclerview lai vertical banako (3 type ko manager hunxa )
        recyclerView.hasFixedSize();
        LoadData loadData= new LoadData() ;
        loadData.execute() ;
    }

    public class LoadData extends AsyncTask<String,String,String> {
        @Override
        protected  void onPreExecute() {
            super.onPreExecute();

//            pDialog.show();

            //Toast.makeText(AmbulanceActivity.this,"On pre Execute", Toast.LENGTH_SHORT).show();



        }

        @Override
        protected String doInBackground(String... params) {
            String result= GET("http://www.tamanneupane.com.np/medicare/getambulance.php") ;
            parseJsonData(result);
            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter = new AmbulanceListAdapter(ambulanceList, AmbulanceActivity.this);
            recyclerView.setAdapter(adapter);
//            pDialog.cancel();
//
//            Toast.makeText(AmbulanceActivity.this, ambulanceList.size()+"", Toast.LENGTH_SHORT).show();


        }

        public void parseJsonData(String result){
            try {
                JSONArray jsonArray= new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {
                    AmbulanceHelper ambulanceHelper = new AmbulanceHelper();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ambulanceHelper.setId(jsonObject.getString("id"));
                    ambulanceHelper.setName(jsonObject.getString("name"));
                    ambulanceHelper.setAddress(jsonObject.getString("address"));
                    ambulanceHelper.setPhone_number(jsonObject.getString("phone_number"));
                    ambulanceList.add(ambulanceHelper);
                }


            }catch (JSONException e){
                e.printStackTrace();

            }
        }

        public String GET(String urll) {
            InputStream inputStream = null;
            String result = "";
            URL url;

            try {
                url = new URL(urll);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        result += line;
                    }
                } else {
                    result = "";
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }


    }
}
