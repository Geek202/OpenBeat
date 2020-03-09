package me.geek.tom.openbeat.btsapi.leaderboard;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Individual {

    @SerializedName("UserName")
    @Expose
    public String userName = "__blank__";
    @SerializedName("Points")
    @Expose
    public Integer points;
    @SerializedName("Rank")
    @Expose
    public Integer rank;

}
