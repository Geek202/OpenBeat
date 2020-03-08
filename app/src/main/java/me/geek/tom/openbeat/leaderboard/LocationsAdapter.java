package me.geek.tom.openbeat.leaderboard;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import me.geek.tom.openbeat.Logger;
import me.geek.tom.openbeat.MainActivity;
import me.geek.tom.openbeat.R;
import me.geek.tom.openbeat.Utils;
import me.geek.tom.openbeat.btsapi.APIClient;
import me.geek.tom.openbeat.btsapi.ApiInterface;
import me.geek.tom.openbeat.btsapi.pagedata.Edge;
import me.geek.tom.openbeat.btsapi.Location;
import me.geek.tom.openbeat.btsapi.pagedata.PageData;
import me.geek.tom.openbeat.btsapi.schemeinfo.SchemeInfo;
import me.geek.tom.openbeat.btsapi.schemeinfo.SchemeResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.Container> {

    private List<Location> locations = new ArrayList<>();

    private AtomicInteger itemCount;

    public static final String SCHEME_ID = "me.geek.tom.openbeat.SchemeId";
    public static final String SCHEME = "me.geek.tom.openbeat.Scheme";
    private SwipeRefreshLayout refreshLayout;

    public LocationsAdapter(final Activity activity, SwipeRefreshLayout refresher) {

        Utils.updateAndroidSecurityProvider(activity);

        this.refreshLayout = refresher;
        refresh(activity);
    }

    public void refresh(final Activity activity) {
        locations = new ArrayList<>();
        ApiInterface apiInterface = APIClient.getPagedataClient().create(ApiInterface.class);
        Call<PageData> call = apiInterface.getLoginPageData();
        call.enqueue(new Callback<PageData>() {
            @Override
            public void onResponse(Call<PageData> call, Response<PageData> response) {
                populatePageData(response.body(), activity);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<PageData> call, Throwable t) {
                Logger.info("Oof, failed: " + t.getMessage());
                call.cancel();
                Snackbar.make(activity.findViewById(R.id.mainview), "Failed to load!", BaseTransientBottomBar.LENGTH_LONG).show();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void populatePageData(PageData data, final Activity activity) {
        ApiInterface api = APIClient.getApiClient().create(ApiInterface.class);

        final List<Edge> edges = data.result.data.allSanityLocality.edges;
        List<Edge> es = new ArrayList<>(edges);
        itemCount = new AtomicInteger(0);
        for (final Edge edge : edges) {
            Call<SchemeInfo> call = api.getSchemeInfo(edge.node.urlSlug);
            call.enqueue(new Callback<SchemeInfo>() {
                @Override
                public void onResponse(Call<SchemeInfo> call, Response<SchemeInfo> response) {
                    SchemeResult result = response.body().result;

                    Location l = new Location();
                    l.schemeId = result.iD;
                    l.name = result.name;
                    l.internalName = edge.node.urlSlug;
                    locations.add(l);
                    if (itemCount.addAndGet(1) >= edges.size()) {
                        LocationsAdapter.this.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<SchemeInfo> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(activity, "Failed to load regions: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @NonNull
    @Override
    public Container onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location, parent, false);
        return new Container(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Container holder, final int position) {
        holder.regionName.setText(locations.get(position).name);
        holder.currentLocation = locations.get(position);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.btn.getContext(), MainActivity.class);

                intent.putExtra(SCHEME_ID, locations.get(position).schemeId);
                intent.putExtra(SCHEME, holder.currentLocation.internalName);

                holder.btn.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @SuppressWarnings("WeakerAccess")
    public static class Container extends RecyclerView.ViewHolder {
        private View view;
        private TextView regionName;
        private Button btn;
        private Location currentLocation;

        public Container(View view) {
            super(view);
            this.view = view;
            this.regionName = view.findViewById(R.id.location);
            this.btn = view.findViewById(R.id.go_to_leaders);
        }
    }
}
