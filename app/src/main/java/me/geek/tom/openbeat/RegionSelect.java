package me.geek.tom.openbeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import me.geek.tom.openbeat.leaderboard.LeaderboardAdapter;
import me.geek.tom.openbeat.leaderboard.LocationsAdapter;
import me.geek.tom.openbeat.ui.main.SectionsPagerAdapter;

public class RegionSelect extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private LocationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_select);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new LocationsAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}
