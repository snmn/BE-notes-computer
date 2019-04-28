package project.m.medicareminorproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import project.m.medicareminorproject.R;

public class DiseaseDetailsActivity extends AppCompatActivity {

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_disease_details);
            String prevention_str = getIntent().getStringExtra("prevention"); // uta double cote ma j rakhya xa tehi rakhnu parxa case sensative
            String symptoms_str = getIntent().getStringExtra("symptom"); // yesari receive garxa uta bata pathako

            TextView symptoms = (TextView) findViewById(R.id.symptoms);
            symptoms.setText(symptoms_str);
            TextView preventions = (TextView) findViewById(R.id.preventions);
            preventions.setText(prevention_str);
    }
}
