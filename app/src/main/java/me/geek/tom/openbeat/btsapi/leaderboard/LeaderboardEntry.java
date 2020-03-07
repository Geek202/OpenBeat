package me.geek.tom.openbeat.btsapi.leaderboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderboardEntry {
    @SerializedName("Rank")
    @Expose
    public Integer rank;
    @SerializedName("TotalPoints")
    @Expose
    public Integer totalPoints;
    @SerializedName("AveragePoints")
    @Expose
    public Integer averagePoints;
    @SerializedName("NumberMembers")
    @Expose
    public Integer numberMembers;
    @SerializedName("ActiveCards")
    @Expose
    public Integer activeCards;
    @SerializedName("SchemeTeamID")
    @Expose
    public Integer schemeTeamID;
    @SerializedName("TeamName")
    @Expose
    public String teamName = "__blank__";
    @SerializedName("TeamID")
    @Expose
    public Integer teamID;
    @SerializedName("TeamColour")
    @Expose
    public String teamColour = "__blank__";
    @SerializedName("SchemeID")
    @Expose
    public Integer schemeID;
}