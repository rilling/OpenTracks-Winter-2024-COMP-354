package de.dennisguse.opentracks.settings;
import androidx.preference.Preference;
import androidx.preference.ListPreference;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import de.dennisguse.opentracks.R;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.preference.PreferenceFragmentCompat; // Assuming you're using this
import androidx.fragment.app.DialogFragment;

public class SkiProfileFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_ski_profile);

        Preference sharingPref =(ListPreference) findPreference("sharing_location");

       if(sharingPref != null){
         sharingPref.setOnPreferenceClickListener(preference -> {
             showEditSharingPreferencesDialog();
             return true;
         });
       }
    }

    private void showEditSharingPreferencesDialog() {
        // Inflate the layout.
        View formView = LayoutInflater.from(getContext()).inflate(R.layout.sharing_preferences_form, null);

        RadioGroup radioGroup = formView.findViewById(R.id.radioGroup); // Assuming your RadioGroup has the ID 'radioGroup'

        String currentSharingOption = PreferencesUtils.getString(R.string.sharing_location_key, "no_one"); // Get this from your settings
        switch (currentSharingOption) {
            case "friends":
                radioGroup.check(R.id.radio_friends);
                break;
            case "share with":
                radioGroup.check(R.id.radio_share_with);
                break;
            case "public":
                radioGroup.check(R.id.radio_public);
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }
}
