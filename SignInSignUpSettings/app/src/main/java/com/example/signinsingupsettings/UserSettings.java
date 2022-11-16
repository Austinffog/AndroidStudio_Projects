package com.example.signinsingupsettings;


public class UserSettings {
    //DECLARING MY VARIABLES
    private String MeasureSystem;
    private String PrefLandMarks;

    public String getMeasureSystem() {
        return MeasureSystem;
    }

    public void setMeasureSystem(String measureSystem) {
        MeasureSystem = measureSystem;
    }


    public String getPrefLandMarks() {
        return PrefLandMarks;
    }

    public void setPrefLandMarks(String prefLandMarks) {
        PrefLandMarks = prefLandMarks;
    }

    //CREATING MY CONSTRUCTOR
    public UserSettings(){}
}
