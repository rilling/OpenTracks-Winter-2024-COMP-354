package de.dennisguse.opentracks;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import de.dennisguse.opentracks.databinding.ActivitySeasonalBinding;
import de.dennisguse.opentracks.databinding.ActivitySeasonalPerSeasonBinding;

public class SeasonalActivityPerSeason extends AbstractActivity
{
    private ActivitySeasonalPerSeasonBinding viewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seasonal_per_season);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle data = getIntent().getExtras();
        if (data != null)
        {
            changeTitle(data.getString("seasonTitle"));
        }
    }
    private void MakeYesNoButton(Button button)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Heading to " + button.getText()).setMessage("Is this okay?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    // Do stuff
                })
                .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel());
    }
    private void changeTitle(String newTitle)
    {
        newTitle = newTitle.replaceAll("_", " ");
        setContentView(R.layout.activity_seasonal_per_season);
        TextView tv = (TextView)findViewById(R.id.pageTitle);
        tv.setText(newTitle);
        tv.setTextSize(32);
    }

    @Override
    protected View getRootView()
    {
        viewBinding = ActivitySeasonalPerSeasonBinding.inflate(getLayoutInflater());
        return viewBinding.getRoot();
    }
}