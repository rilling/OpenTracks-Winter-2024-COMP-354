package de.dennisguse.opentracks.ui.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import de.dennisguse.opentracks.R;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private final List<User> userList;
    private boolean showVertical;

    public LeaderboardAdapter(List<User> userList, boolean showVertical) {
        this.userList = userList;
        this.showVertical = showVertical;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.usernameText.setText(user.getName());
        holder.locationText.setText(user.getLocation());
        holder.rankText.setText(String.valueOf(user.getRank()));
        if(showVertical) {
            holder.valueText.setText(user.getVerticalMeters());
        } else {
            holder.valueText.setText(user.getDistance());
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView usernameText;
        TextView locationText;
        TextView rankText;
        TextView valueText;

        ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            usernameText = itemView.findViewById(R.id.usernameText);
            locationText = itemView.findViewById(R.id.locationText);
            rankText = itemView.findViewById(R.id.rankText);
            valueText = itemView.findViewById(R.id.valueText);
        }
    }
}