package de.dennisguse.opentracks.ui.leaderboard.leaderboardList;

import android.content.Context;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.dennisguse.opentracks.R;

import de.dennisguse.opentracks.data.models.Rankings;
import de.dennisguse.opentracks.databinding.FragmentLeaderboardListBinding;
import de.dennisguse.opentracks.ui.leaderboard.leaderboardList.LeaderboardListViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class LeaderboardListFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private LeaderboardListViewModel leaderboardListViewModel;
    private FragmentLeaderboardListBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LeaderboardListFragment() {
    }

    public static LeaderboardListFragment newInstance(int columnCount) {
        LeaderboardListFragment fragment = new LeaderboardListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, columnCount);
        bundle.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        leaderboardListViewModel = new ViewModelProvider(this).get(LeaderboardListViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        leaderboardListViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard_list_item, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new LeaderboardListAdapter(Rankings.RANKINGS));
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}