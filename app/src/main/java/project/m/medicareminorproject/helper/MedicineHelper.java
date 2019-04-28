package project.m.medicareminorproject.helper;

import java.io.Serializable;

/**
 * Created by Lenovo on 7/23/2016.
 */
public class MedicineHelper implements Serializable {
    private String id;
    private String medicineName;
    private String recommendedBy;
    private String alarmTime;

    public MedicineHelper() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getRecommendedBy() {
        return recommendedBy;
    }

    public void setRecommendedBy(String recommendedBy) {
        this.recommendedBy = recommendedBy;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
}
