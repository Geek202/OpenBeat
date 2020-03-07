package me.geek.tom.openbeat.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import me.geek.tom.openbeat.R;
import me.geek.tom.openbeat.leaderboard.LeaderboardAdapter;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String SCHEMEID = "scheme_id";
    private static final String SCHEME = "scheme_name";

    private PageViewModel pageViewModel;

    private RecyclerView.LayoutManager layoutManager;
    private LeaderboardAdapter adapter;
    private int schemeId;
    private String scheme;
    private int index;

    @SuppressWarnings("WeakerAccess")
    public static PlaceholderFragment newInstance(int index, int schemeId, String scheme) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putInt(SCHEMEID, schemeId);
        bundle.putString(SCHEME, scheme);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            schemeId = getArguments().getInt(SCHEMEID);
            scheme = getArguments().getString(SCHEME);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        final RecyclerView recycler = root.findViewById(R.id.leaders);
        final SwipeRefreshLayout refreshLayout = root.findViewById(R.id.refresh_layout);

        layoutManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(layoutManager);

        adapter = new LeaderboardAdapter(schemeId, index == 2, scheme, this.getActivity(), refreshLayout);
        recycler.setAdapter(adapter);

        refreshLayout.setColorSchemeColors(0xFF0000, 0x009100, 0x00C6FF, 0x9400D3);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh(PlaceholderFragment.this.getActivity());
            }
        });

        return root;
    }
}