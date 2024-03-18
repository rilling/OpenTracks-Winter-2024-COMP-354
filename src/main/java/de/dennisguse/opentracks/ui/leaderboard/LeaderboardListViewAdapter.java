package de.dennisguse.opentracks.ui.leaderboard;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import de.dennisguse.opentracks.data.models.Rankings.Ranking;
import de.dennisguse.opentracks.databinding.FragmentLeaderboardListBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Ranking}.
 * TODO: Replace the implementation with code for your data type.
 */
public class LeaderboardListViewAdapter extends RecyclerView.Adapter<LeaderboardListViewAdapter.ViewHolder> {

    private final List<Ranking> mValues;

    public LeaderboardListViewAdapter(List<Ranking> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentLeaderboardListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).rank);
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

        public ViewHolder(FragmentLeaderboardListBinding binding) {
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