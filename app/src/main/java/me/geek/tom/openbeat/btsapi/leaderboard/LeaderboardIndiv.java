package me.geek.tom.openbeat.btsapi.leaderboard;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderboardIndiv {

    @SerializedName("Success")
    @Expose
    public Boolean success;
    @SerializedName("ErrorCode")
    @Expose
    public Integer errorCode;
    @SerializedName("ErrorMessage")
    @Expose
    public Object errorMessage;
    @SerializedName("Result")
    @Expose
    public List<Individual> result = null;

}
