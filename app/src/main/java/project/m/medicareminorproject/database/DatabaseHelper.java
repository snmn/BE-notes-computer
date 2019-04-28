package project.m.medicareminorproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import project.m.medicareminorproject.helper.MedicineHelper;

/**
 * Created by Lenovo on 7/23/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MedicareDatabase.db";

    private static final String MEDICINE_TABLE = "medicine_table";

    private static final String ID = "ID";
    private static final String MEDICINE_NAME = "MEDICINE_NAME";
    private static final String RECOMMENDED_BY = "RECOMMENDED_BY";
    private static final String ALARM_TIME = "ALARM_TIME";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + MEDICINE_TABLE + " ( " + makeMedicineTable() + " );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP DATABASE " + DATABASE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + MEDICINE_TABLE);
        onCreate(db);
    }

    public String makeMedicineTable() {
        return ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + MEDICINE_NAME + " TEXT, " + RECOMMENDED_BY + " TEXT, " + ALARM_TIME + " TEXT ";
    }

    public boolean insertIntoDatabase(MedicineHelper medicineHelper) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(MEDICINE_NAME, medicineHelper.getMedicineName());
        contentValues.put(RECOMMENDED_BY, medicineHelper.getRecommendedBy());
        contentValues.put(ALARM_TIME, medicineHelper.getAlarmTime());

        // insert() method returns the row ID of the newly inserted row, or -1 if an error occurred.
        long result = sqLiteDatabase.insert(MEDICINE_TABLE, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<MedicineHelper> selectFromDatabase() { // yaha bata chai database ma bhako sap data taneko
        ArrayList<MedicineHelper> savedDataList = new ArrayList<>();

        String selectQuery = "SELECT " + "*" + " FROM " + MEDICINE_TABLE + " WHERE 1;";// + IS_ALARMED + " = " + "'" + FALSE.trim() + "'" + " ORDER BY " + TIME + "," + DATE + " ASC ;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                MedicineHelper medicineHelper = new MedicineHelper();
                medicineHelper.setId(cursor.getString(0)); // o bhaneko id
                medicineHelper.setMedicineName(cursor.getString(1)); //1 bhaneko Medicine name
                medicineHelper.setRecommendedBy(cursor.getString(2));//2 bhaneko Recomm
                medicineHelper.setAlarmTime(cursor.getString(3));//3 bhaneko alarm time

                //yo chai mathi table banauda kasari rakheko xa tesarinai hunxa

                savedDataList.add(medicineHelper);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return savedDataList;
    }

    public boolean editSavedMedicine(MedicineHelper medicineHelper, String id) { // yesle database edit garxa tara kun data edit garne ho bhanne chai
        //id aanusar garxa (String id) and update garne data chai medicinehepler ma aauxa
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MEDICINE_NAME, medicineHelper.getMedicineName());
        values.put(RECOMMENDED_BY, medicineHelper.getRecommendedBy());
        values.put(ALARM_TIME, medicineHelper.getAlarmTime());

        int ret = sqLiteDatabase.update(MEDICINE_TABLE, values, ID + "='" + id + " ' ", null); //tyo aako data yehi id ma hal bhaneko
        if (ret == -1) {
            return false;
        } else {
            return true;
        }
    }

}
