package project.m.medicareminorproject.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.util.Calendar;

import project.m.medicareminorproject.R;
import project.m.medicareminorproject.alarm.AlarmReceiver;
import project.m.medicareminorproject.database.DatabaseHelper;
import project.m.medicareminorproject.helper.MedicineHelper;

public class EditMedicineActivity extends AppCompatActivity  implements
        TimePickerDialog.OnTimeSetListener {

    EditText medicine_name,recommended_by,alarm_time;
    Button save;
    DatabaseHelper databaseHelper;
    String id="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

        medicine_name= (EditText) findViewById(R.id.medicine_name_et);
        recommended_by= (EditText) findViewById(R.id.recommended_by_et);
        alarm_time= (EditText) findViewById(R.id.medicine_time);
        save= (Button) findViewById(R.id.save);

        MedicineHelper medicineHelper= (MedicineHelper) getIntent().getSerializableExtra("medicinedetail"); // SavedListAdapter ma doublecote ma
        // bhako ra yesma bhako same huna parxa hai

       id= medicineHelper.getId();
        medicine_name.setText(medicineHelper.getMedicineName());
        recommended_by.setText(medicineHelper.getRecommendedBy());
        alarm_time.setText(medicineHelper.getAlarmTime());

        databaseHelper= new DatabaseHelper(EditMedicineActivity.this);

        alarm_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        EditMedicineActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setThemeDark(false);
                tpd.vibrate(true);
                tpd.dismissOnPause(false);
                tpd.enableSeconds(false);
                tpd.setTitle("Select Time");
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicineHelper medicineHelper= new MedicineHelper();
                medicineHelper.setMedicineName(medicine_name.getText().toString().trim());
                medicineHelper.setRecommendedBy(recommended_by.getText().toString().trim());
                medicineHelper.setAlarmTime(alarm_time.getText().toString().trim());

                boolean result= databaseHelper.editSavedMedicine(medicineHelper,id);

                if(result){
//                    AlarmReceiver alarmReceiver= new AlarmReceiver();
//                    try {
//                        alarmReceiver.setAlarm(MedicineReminderActivity.this, medicineHelper);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
                    Intent intent = new Intent(EditMedicineActivity.this, SavedMedicineActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    Toast.makeText(EditMedicineActivity.this, "Not inserted to database. please try again", Toast.LENGTH_SHORT).show();
                    // natra tehi save garne page ma basera yo toast aauna paro
                }
            }
        });
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
//        String secondString = second < 10 ? "0" + second : "" + second;(nabhayeni hunxa)
        String time_str = hourString + ":" + minuteString;
        alarm_time.setText(time_str);
    }
}
