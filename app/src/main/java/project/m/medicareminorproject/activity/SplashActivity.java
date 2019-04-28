package project.m.medicareminorproject.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import project.m.medicareminorproject.R;
import project.m.medicareminorproject.database.DatabaseHelper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DatabaseHelper databaseHelper= new DatabaseHelper(SplashActivity.this);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main=new Intent(SplashActivity.this,MenuActivity.class);
                startActivity(main);
                finish();
            }
        },5000);

    }


}
