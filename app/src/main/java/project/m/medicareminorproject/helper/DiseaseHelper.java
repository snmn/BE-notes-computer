package project.m.medicareminorproject.helper;

/**
 * Created by Lenovo on 6/25/2016.
 */
public class DiseaseHelper {

    private String id;
    private String disease_name;
    private String symptoms;
    private String preventions;

    public DiseaseHelper() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms){ this.symptoms = symptoms;}

    public String getPreventions() {
        return preventions;
    }

    public void setPreventions(String preventions) {
        this.preventions = preventions;
    }
}
