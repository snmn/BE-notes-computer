package project.m.medicareminorproject.activity;

import android.content.DialogInterface;
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

public class MedicineReminderActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener{

    EditText medicine_name,recommended_by,alarm_time;
    Button save;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_reminder);

        medicine_name= (EditText) findViewById(R.id.medicine_name_et);
        recommended_by= (EditText) findViewById(R.id.recommended_by_et);

        alarm_time= (EditText) findViewById(R.id.medicine_time);

        save= (Button) findViewById(R.id.save);

        databaseHelper= new DatabaseHelper(MedicineReminderActivity.this);

        alarm_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        MedicineReminderActivity.this,
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        false
                );
                tpd.setThemeDark(false);
                tpd.vibrate(true);
                tpd.dismissOnPause(false);
                tpd.enableSeconds(false);
                tpd.setTitle("Select Medicine Time");
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

               boolean result= databaseHelper.insertIntoDatabase(medicineHelper);

                if(result){
                    AlarmReceiver alarmReceiver= new AlarmReceiver();
                    try {
                        alarmReceiver.setAlarm(MedicineReminderActivity.this);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    onBackPressed();// successfully insert bho bhane 1st wala page khunla paro
                }else {
                    Toast.makeText(MedicineReminderActivity.this, "Not inserted to database. please try again", Toast.LENGTH_SHORT).show();
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
