package me.geek.tom.openbeat.leaderboard;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.geek.tom.openbeat.Logger;
import me.geek.tom.openbeat.R;
import me.geek.tom.openbeat.btsapi.APIClient;
import me.geek.tom.openbeat.btsapi.leaderboard.Individual;
import me.geek.tom.openbeat.btsapi.leaderboard.LeaderboardCount;
import me.geek.tom.openbeat.btsapi.leaderboard.LeaderboardEntry;
import me.geek.tom.openbeat.btsapi.ApiInterface;
import me.geek.tom.openbeat.btsapi.leaderboard.LeaderboardIndiv;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.Container> {

    private List<LeaderboardEntry> mDataset;
    private List<Individual> mIndivDataset;

    private SwipeRefreshLayout refreshLayout;

    private boolean isIndiv;
    private String scheme;
    private int schemeId;

    public LeaderboardAdapter(int schemeId, boolean isIndiviual, String scheme, Activity activity, SwipeRefreshLayout refreshLayout) {
        mDataset = Collections.emptyList();
        mIndivDataset = Collections.emptyList();
        this.isIndiv = isIndiviual;
        this.scheme = scheme;
        this.schemeId = schemeId;
        this.refreshLayout = refreshLayout;

        refresh(activity);
    }

    public void refresh(Activity activity) {
        ApiInterface leaderboardInterface = APIClient.getApiClient().create(ApiInterface.class);

        if (!isIndiv) {
            populateTeams(schemeId, leaderboardInterface, activity);
        } else {
            populateIndividuals(scheme, leaderboardInterface, activity);
        }
    }

    private void populateIndividuals(final String scheme, final ApiInterface leaderboardInterface, final Activity activity) {
        Call<LeaderboardCount> call = leaderboardInterface.getLeaderboardCount(scheme);
        this.refreshLayout.setRefreshing(true);
        call.enqueue(new Callback<LeaderboardCount>() {
            @Override
            public void onResponse(Call<LeaderboardCount> call, Response<LeaderboardCount> response) {
                int count = response.body().result;
                Logger.info("Load indiv count for: " + scheme + " = " + count);

                Call<LeaderboardIndiv> indivCall = leaderboardInterface.getIndividualLeaderboard(scheme, "1", String.valueOf(count));
                indivCall.enqueue(new Callback<LeaderboardIndiv>() {
                    @Override
                    public void onResponse(Call<LeaderboardIndiv> call, Response<LeaderboardIndiv> response) {
                        List<Individual> leaderboard = response.body().result;
                        mIndivDataset = new ArrayList<>(leaderboard);
                        mIndivDataset.add(0, new Individual());
                        LeaderboardAdapter.this.notifyDataSetChanged();
                        LeaderboardAdapter.this.refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<LeaderboardIndiv> call, Throwable t) {
                        call.cancel();
                        Snackbar.make(activity.findViewById(R.id.view_pager), "Failed to load!", BaseTransientBottomBar.LENGTH_SHORT);
                        Logger.info("Failed to load indiv lead: " + t.getMessage());
                        LeaderboardAdapter.this.refreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(Call<LeaderboardCount> call, Throwable t) {
                call.cancel();
                Snackbar.make(activity.findViewById(R.id.view_pager), "Failed to load!", BaseTransientBottomBar.LENGTH_SHORT);
                Logger.info("Failed to load indiv lead size: " + t.getMessage());
                LeaderboardAdapter.this.refreshLayout.setRefreshing(false);
            }
        });
    }

    private void populateTeams(int schemeId, ApiInterface leaderboardInterface, final Activity activity) {
        this.refreshLayout.setRefreshing(true);
        Call<List<LeaderboardEntry>> call = leaderboardInterface.getLeaderboard(String.valueOf(schemeId), "schools");
        call.enqueue(new Callback<List<LeaderboardEntry>>() {
            @Override
            public void onResponse(Call<List<LeaderboardEntry>> call, Response<List<LeaderboardEntry>> response) {
                mDataset = new ArrayList<>(response.body());
                mDataset.add(0, new LeaderboardEntry());
                LeaderboardAdapter.this.notifyDataSetChanged();
                LeaderboardAdapter.this.refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<LeaderboardEntry>> call, Throwable t) {
                call.cancel();
                Snackbar.make(activity.findViewById(R.id.view_pager), "Failed to load!", BaseTransientBottomBar.LENGTH_SHORT);
                Logger.info("Failed to load teams: " + t.getMessage());
                LeaderboardAdapter.this.refreshLayout.setRefreshing(false);
            }
        });
    }

    @NonNull
    @Override
    public Container onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboard_entry, parent, false);
        return new Container(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Container holder, int position) {
        LeaderboardEntry entry;

        if (!isIndiv)
            entry = mDataset.get(position);
        else {
            entry = new LeaderboardEntry();
            Individual indiv = mIndivDataset.get(position);
            entry.teamName = indiv.userName;
            entry.totalPoints = indiv.points;
        }

        if (entry.teamName.equals("__blank__")) {

            holder.teamName.setText(R.string.team_name);
            holder.teamPoints.setText(R.string.team_score);
            holder.teamRank.setText(R.string.rank);

            if (!isIndiv) {
                holder.teamAverage.setText(R.string.team_average);
                holder.teamPlayers.setText(R.string.team_cards);
                holder.teamColour.setBackgroundColor(0xffffff);
            } else {
                holder.teamAverage.setText("");
                holder.teamPlayers.setText("");
            }

            return;
        }

        holder.teamName.setText(entry.teamName);
        holder.teamPoints.setText(String.valueOf(entry.totalPoints));
        holder.teamRank.setText(String.valueOf(position));

        if (!isIndiv) {
            holder.teamAverage.setText(String.valueOf(entry.averagePoints));
            holder.teamPlayers.setText(String.valueOf(entry.numberMembers));
            holder.teamColour.setBackgroundColor(Integer.parseInt(entry.teamColour.substring(1), 16));
        } else {
            holder.teamAverage.setText("");
            holder.teamPlayers.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return isIndiv ? mIndivDataset.size() : mDataset.size();
    }

    @SuppressWarnings("WeakerAccess")
    public static class Container extends RecyclerView.ViewHolder {
        private View view;
        private TextView teamName;
        private TextView teamPoints;
        private TextView teamAverage;
        private TextView teamPlayers;
        private TextView teamRank;
        private ImageView teamColour;

        public Container(View view) {
            super(view);
            this.view = view;
            this.teamName = view.findViewById(R.id.team_name);
            this.teamPoints = view.findViewById(R.id.team_points);
            this.teamAverage = view.findViewById(R.id.team_average);
            this.teamPlayers = view.findViewById(R.id.team_players);
            this.teamColour = view.findViewById(R.id.team_colour);
            this.teamRank = view.findViewById(R.id.rank);
        }
    }
}
