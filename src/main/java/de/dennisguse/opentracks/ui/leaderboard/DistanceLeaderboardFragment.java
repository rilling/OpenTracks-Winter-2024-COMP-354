package de.dennisguse.opentracks.ui.leaderboard;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import de.dennisguse.opentracks.R;

public class DistanceLeaderboardFragment extends Fragment {

    private RecyclerView leaderboardRecyclerView;
    private LeaderboardAdapter leaderboardAdapter;
    private List<User> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_distance_leaderboard, container, false);

        userList = UserData.getUsers(); // Get the list of users

        leaderboardRecyclerView = view.findViewById(R.id.leaderboardListDistance);
        leaderboardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        leaderboardAdapter = new LeaderboardAdapter(userList, false); // false to indicate distance data
        leaderboardRecyclerView.setAdapter(leaderboardAdapter);

        return view;
    }
}
