package me.geek.tom.openbeat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import me.geek.tom.openbeat.leaderboard.LocationsAdapter;
import me.geek.tom.openbeat.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int schemeId = getIntent().getIntExtra(LocationsAdapter.SCHEME_ID, -1);
        String scheme = getIntent().getStringExtra(LocationsAdapter.SCHEME);

        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), schemeId, scheme);
        ViewPager viewPager = findViewById(R.id.view_pager);

        if (schemeId == -1) {
            Snackbar.make(viewPager, "Invalid scheme!", BaseTransientBottomBar.LENGTH_LONG);
        }

        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}