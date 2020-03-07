package me.geek.tom.openbeat.btsapi;

import java.util.List;

import me.geek.tom.openbeat.btsapi.leaderboard.LeaderboardCount;
import me.geek.tom.openbeat.btsapi.leaderboard.LeaderboardEntry;
import me.geek.tom.openbeat.btsapi.leaderboard.LeaderboardIndiv;
import me.geek.tom.openbeat.btsapi.pagedata.PageData;
import me.geek.tom.openbeat.btsapi.schemeinfo.SchemeInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("LeaderboardWidget/getHighestPointScoreData/?")
    Call<List<LeaderboardEntry>> getLeaderboard(@Query("schemeId") String scheme, @Query("city") String city);

    @GET("LeaderBoard/CountLeaderBoard/?")
    Call<LeaderboardCount> getLeaderboardCount(@Query("schemeURLName") String scheme);

    @GET("LeaderBoard/?")
    Call<LeaderboardIndiv> getIndividualLeaderboard(@Query("schemeURLName") String schemeId, @Query("rankFrom") String startRank, @Query("rankTo") String endRank);

    @GET("Information/?")
    Call<SchemeInfo> getSchemeInfo(@Query("SchemeURLName") String schemeName);

    @GET("login/page-data.json")
    Call<PageData> getLoginPageData();
}
