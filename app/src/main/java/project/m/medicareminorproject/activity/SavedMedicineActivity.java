package project.m.medicareminorproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import project.m.medicareminorproject.R;
import project.m.medicareminorproject.adapter.AmbulanceListAdapter;
import project.m.medicareminorproject.adapter.SavedMedicineListAdapter;
import project.m.medicareminorproject.database.DatabaseHelper;
import project.m.medicareminorproject.helper.MedicineHelper;

public class SavedMedicineActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ArrayList<MedicineHelper> medicineList= new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_medicine);

        databaseHelper= new DatabaseHelper(SavedMedicineActivity.this);
        medicineList= databaseHelper.selectFromDatabase();

        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(SavedMedicineActivity.this)); // yo chai recyclerview lai vertical banako (3 type ko manager hunxa )
        recyclerView.hasFixedSize();

        adapter = new SavedMedicineListAdapter(medicineList, SavedMedicineActivity.this);
        recyclerView.setAdapter(adapter);
    }
}
