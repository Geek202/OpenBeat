package me.geek.tom.openbeat.btsapi.schemeinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SchemeResult {

    @SerializedName("ID")
    @Expose
    public Integer iD;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Status")
    @Expose
    public Double status;
    @SerializedName("Unit")
    @Expose
    public Boolean unit;
    @SerializedName("NumberOfUsers")
    @Expose
    public Double numberOfUsers;
    @SerializedName("NumberOfMiles")
    @Expose
    public Double numberOfMiles;
    @SerializedName("NumberOfMilesToday")
    @Expose
    public Double numberOfMilesToday;
    @SerializedName("Anticipation")
    @Expose
    public String anticipation;
    @SerializedName("Registration")
    @Expose
    public String registration;
    @SerializedName("Project")
    @Expose
    public String project;
    @SerializedName("Legacy")
    @Expose
    public String legacy;
    @SerializedName("AnticipationCountdown")
    @Expose
    public Double anticipationCountdown;
    @SerializedName("RegistrationCountdown")
    @Expose
    public Double registrationCountdown;
    @SerializedName("ProjectCountdown")
    @Expose
    public Double projectCountdown;
    @SerializedName("LegacyCountdown")
    @Expose
    public Double legacyCountdown;

}
