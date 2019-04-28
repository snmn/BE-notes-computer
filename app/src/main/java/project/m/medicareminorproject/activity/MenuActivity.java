package project.m.medicareminorproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import project.m.medicareminorproject.R;

public class MenuActivity extends AppCompatActivity {

    Button ambulance_btn, disease_btn, savedMedicine_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        disease_btn = (Button) findViewById(R.id.disease_btn);
        ambulance_btn = (Button) findViewById(R.id.ambulance_btn);
        savedMedicine_btn = (Button) findViewById(R.id.medicine_btn);


        disease_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent disease = new Intent(MenuActivity.this, DiseaseActivity.class);
                startActivity(disease);
            }
        });
        ambulance_btn.setOnClickListener(new View.OnClickListener(
        ) {
            @Override
            public void onClick(View v) {
                Intent ambulance = new Intent(MenuActivity.this, AmbulanceActivity.class);
                startActivity(ambulance);
            }
        });

        savedMedicine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent medicine = new Intent(MenuActivity.this, SavedMedicineActivity.class);
                startActivity(medicine);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(new Intent(MenuActivity.this, MedicineReminderActivity.class));
            }
        });
    }

}
