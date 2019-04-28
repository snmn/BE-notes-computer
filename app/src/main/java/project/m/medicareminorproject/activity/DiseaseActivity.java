package project.m.medicareminorproject.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import project.m.medicareminorproject.helper.DiseaseHelper;
import project.m.medicareminorproject.adapter.DiseaseListAdapter;
import project.m.medicareminorproject.R;



public class DiseaseActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;


    TextView response;
    ArrayList<DiseaseHelper> diseaseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(DiseaseActivity.this)); // yo chai recyclerview lai vertical banako (3 type ko manager hunxa )
        recyclerView.hasFixedSize();
        pDialog = new ProgressDialog(DiseaseActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(DiseaseActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //click garene bitikai yo method afai call hunxa ani yo method le position dinxa kun position ko item click bho bhanera
                //tesai bata hamilai taha hunxa k click garya ho.
                Toast.makeText(DiseaseActivity.this, diseaseList.get(position).getDisease_name(), Toast.LENGTH_SHORT).show();
                // aaba yaha bata tyo agi banako diseasedetail activity call garne(Kholne) click bhaye pachi khulne haina tah ?
                Intent intent = new Intent(DiseaseActivity.this, DiseaseDetailsActivity.class);
                intent.putExtra("prevention", diseaseList.get(position).getPreventions()); // prevention and cure yaha bata utako activity ma pathako display garna
                intent.putExtra("symptom", diseaseList.get(position).getSymptoms());       //communication between two activities bhanum
                startActivity(intent);
            }
        }));
        LoadData loadData = new LoadData();
        loadData.execute();
    }

    public class LoadData extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.show();


//            Toast.makeText(DiseaseActivity.this, "On pre Execute", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {

            String result = GET("http://tamanneupane.com.np/medicare/getdisease.php");
            parseJsonData(result);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter = new DiseaseListAdapter(diseaseList, DiseaseActivity.this); //adapter call gareko yaha bata
            recyclerView.setAdapter(adapter); // adapter le return gareko adapter recyclerview ma set gareko
            pDialog.cancel();
        }
    }

    public void parseJsonData(String result) {
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                DiseaseHelper diseaseHelper = new DiseaseHelper();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                diseaseHelper.setId(jsonObject.getString("id"));
                diseaseHelper.setDisease_name(jsonObject.getString("disease_name"));
                diseaseHelper.setPreventions(jsonObject.getString("preventions"));
                diseaseHelper.setSymptoms(jsonObject.getString("symptoms"));
                diseaseList.add(diseaseHelper);
            }


        } catch (JSONException e) {
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
