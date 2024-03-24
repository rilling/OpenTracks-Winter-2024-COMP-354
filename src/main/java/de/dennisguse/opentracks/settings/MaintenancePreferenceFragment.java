package de.dennisguse.opentracks.settings;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import de.dennisguse.opentracks.R; // Make sure to import your R class.

public class MaintenancePreferenceFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.activity_maintenance_infor, rootKey);
    }
}
