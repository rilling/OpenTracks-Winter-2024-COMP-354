package de.dennisguse.opentracks.ui.leaderboard.leaderboardList;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import de.dennisguse.opentracks.data.models.Rankings.Ranking;
import de.dennisguse.opentracks.ui.leaderboard.leaderboardList.placeholder.PlaceholderContent.PlaceholderItem;
import de.dennisguse.opentracks.databinding.FragmentLeaderboardBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class RankingsRecyclerViewAdapter extends RecyclerView.Adapter<RankingsRecyclerViewAdapter.ViewHolder> {

    private final List<Ranking> mValues;

    public RankingsRecyclerViewAdapter(List<Ranking> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentLeaderboardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).rank));
        holder.mContentView.setText(mValues.get(position).username);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public Ranking mItem;

        public ViewHolder(FragmentLeaderboardBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}