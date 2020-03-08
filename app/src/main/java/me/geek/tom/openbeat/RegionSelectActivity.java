package me.geek.tom.openbeat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import me.geek.tom.openbeat.leaderboard.LeaderboardAdapter;
import me.geek.tom.openbeat.leaderboard.LocationsAdapter;
import me.geek.tom.openbeat.ui.main.SectionsPagerAdapter;

public class RegionSelectActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager layoutManager;
    private LocationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_select);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final SwipeRefreshLayout refresher = findViewById(R.id.refresher);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new LocationsAdapter(this, refresher);
        recyclerView.setAdapter(adapter);

        refresher.setColorSchemeColors(0xFF0000, 0x009100, 0x00C6FF, 0x9400D3);
        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh(RegionSelectActivity.this);
            }
        });
    }
}
