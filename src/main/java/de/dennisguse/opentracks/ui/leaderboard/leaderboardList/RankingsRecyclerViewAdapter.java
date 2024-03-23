package de.dennisguse.opentracks.ui.leaderboard.leaderboardList;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import de.dennisguse.opentracks.data.models.Ranking;
import de.dennisguse.opentracks.databinding.FragmentLeaderboardBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a list of {@link Ranking} instances.
 * TODO: Replace the implementation with code for your data type.
 */
public class RankingsRecyclerViewAdapter extends RecyclerView.Adapter<RankingsRecyclerViewAdapter.ViewHolder> {

    private final List<Ranking> mValues;

    public RankingsRecyclerViewAdapter(List<Ranking> items) {
        mValues = items;
    }

    // TODO: Delete this method once refresh works.
    public static List<Ranking> makePlaceholderRankingList() {
        List<Ranking> rankings = new ArrayList<>();
        rankings.add(new Ranking(1, "Da bes", "Steamboat Springs",  25));
        rankings.add(new Ranking(2, "Second place", "North California CA",  24));
        rankings.add(new Ranking(3, "Tertiary", "Steamboat Springs, Color Red",  23));
        rankings.add(new Ranking(4,  "Quad Runner", "Montreal",  22));
        rankings.add(new Ranking(5,  "Quintuple champ", "Steamboat Springs",  21));
        rankings.add(new Ranking(6,  "Gang of Six", "Montreal",  20));
        rankings.add(new Ranking(7,  "Seven", "Steamboat Springs",  19));
        rankings.add(new Ranking(8,  "Eight", "Montreal",  18));
        rankings.add(new Ranking(9,  "Ninth", "Montreal",  17));
        rankings.add(new Ranking(10,  "DoubleDigits", "Steamboat Springs",  16));
        rankings.add(new Ranking(11,  "El e Ven", "Montreal",  15));
        rankings.add(new Ranking(12,  "Twelve", "Montreal",  14));
        rankings.add(new Ranking(13,  "XIII", "Montreal",  13));
        rankings.add(new Ranking(14,  "Four Teen", "Steamboat Springs",  12));
        rankings.add(new Ranking(15, "Fif Teen", "Montreal",  11));
        rankings.add(new Ranking(16, "Six Teen", "Steamboat Springs",  10));
        rankings.add(new Ranking(17,  "Seven+10", "Montreal",  9));
        rankings.add(new Ranking(18,  "Eight een", "Montreal",  8));
        rankings.add(new Ranking(19,  "Nineteen", "Steamboat Springs",  7));
        rankings.add(new Ranking(20,  "10+10", "Steamboat Springs",  6));
        rankings.add(new Ranking(21,  "Twenty-first", "Montreal",  5));
        rankings.add(new Ranking(22,  "TwoTwo", "Montreal",  4));
        rankings.add(new Ranking(23,  "TwoThree", "Steamboat Springs",  3));
        rankings.add(new Ranking(24,  "The day", "Montreal",  2));
        rankings.add(new Ranking(25,  "The saved day", "Montreal",  1));
        return rankings;
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