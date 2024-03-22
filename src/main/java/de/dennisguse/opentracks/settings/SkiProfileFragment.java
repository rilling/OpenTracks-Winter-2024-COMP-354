package de.dennisguse.opentracks.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import de.dennisguse.opentracks.R;

public class SkiProfileFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_ski_profile);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
