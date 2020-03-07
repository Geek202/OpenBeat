package me.geek.tom.openbeat.btsapi.leaderboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderboardCount {

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
    public Integer result;

}
