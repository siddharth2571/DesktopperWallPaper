package com.pemikir.desktopper.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.pemikir.desktopper.R;
import com.pemikir.desktopper.Utility.PreferencesUtility;


/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatActivity {

    String action;

    @Override
    public void onCreate(Bundle savedInstanceState) {

      /*  if (PreferencesUtility.getInstance(this).getTheme().equals("dark"))
            setTheme(R.style.AppThemeNormalDark);
        else if (PreferencesUtility.getInstance(this).getTheme().equals("black"))
            setTheme(R.style.AppThemeNormalBlack);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        action = getIntent().getAction();

        getSupportActionBar().setTitle(R.string.app_name);
        PreferenceFragment fragment = new SettingsFragment();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commit();



    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Utils.Adloaded(SettingsActivity.this);
                startActivity(new Intent(SettingsActivity.this, MainDrawerActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String EXAMPLE_SWITCH = "example_switch";
    private static final String EXAMPLE_TEXT = "FOLDER_NAME";
    private static final String EXAMPLE_LIST = "selected_row";


    SwitchPreference example_switch;
    EditTextPreference example_text;
    ListPreference example_list;

    PreferencesUtility mPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        setHasOptionsMenu(true);

        mPreferences = PreferencesUtility.getInstance(getActivity());

        example_switch = (SwitchPreference) findPreference(EXAMPLE_SWITCH);
        example_text = (EditTextPreference) findPreference(EXAMPLE_TEXT);
        example_list = (ListPreference) findPreference(EXAMPLE_LIST);

        bindPreferenceSummaryToValue(findPreference(EXAMPLE_TEXT));
        bindPreferenceSummaryToValue(findPreference(EXAMPLE_LIST));


//        setPrefernceCickListeners();
//        PreferencesUtility.getInstance(getActivity()).setOnSharedPreferenceChangeListener(this);
//        mPreferences
    }

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };


    public static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences preference,
                                          String key) {

        String stringValue = key.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list.
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            // Set the summary to reflect the new value.
            listPreference.setSummary(
                    index >= 0
                            ? listPreference.getEntries()[index]
                            : null);
            ((ListPreference) preference).setSummary(index);
            mPreferences.setNo_of_rows(index);

        } else if (preference instanceof EditTextPreference) {

            ((EditTextPreference) preference).setSummary(stringValue);
            mPreferences.setFolderName(((EditTextPreference) preference).getSummary().toString());
        }
    }

    private void setPrefernceCickListeners() {

        example_list.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
               /* Intent i = getActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
//                mPreferences.setNo_of_rows(Integer.parseInt(preference.getSummary().toString()));
                Toast.makeText(getActivity(), preference.getSummary(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        example_text.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                mPreferences.setFolderName(preference.getSummary().toString());
                return true;
            }
        });

    }


}








/*public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Display the fragment as the main content
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new GeneralPreferenceFragment())
                .commit();
    }

    *//**
 * Set up the {@link android.app.ActionBar}, if the API is available.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 *
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 * <p/>
 * {@inheritDoc}
 * <p/>
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 * <p/>
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 * @see #sBindPreferenceSummaryToValueListener
 * <p/>
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 * <p/>
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 * <p/>
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 *//*


    *//**
 * {@inheritDoc}
 *//*
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    *//**
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 *//*
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    *//**
 * {@inheritDoc}
 *//*


    *//**
 * A preference value change listener that updates the preference's summary
 * to reflect its new value.
 *//*
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                Log.i("Selectvlaue", "=>" + stringValue + "===" + index);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
                        preference.setSummary(null);
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    *//**
 * Binds a preference's summary to its value. More specifically, when the
 * preference's value is changed, its summary (line of text below the
 * preference title) is updated to reflect the value. The summary is also
 * immediately updated upon calling this method. The exact display format is
 * dependent on the type of preference.
 *
 * @see #sBindPreferenceSummaryToValueListener
 *//*
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    *//**
 * This method stops fragment injection in malicious applications.
 * Make sure to deny any unknown fragments here.
 *//*
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName);

    }

    *//**
 * This fragment shows general preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 *//*
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

            // Bind the summaries of EditText/List/Dialog/Ringtone preferences
            // to their values. When their values change, their summaries are
            // updated to reflect the new value, per the Android Design
            // guidelines.
            bindPreferenceSummaryToValue(findPreference("example_text"));
            bindPreferenceSummaryToValue(findPreference("example_list"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    *//**
 * This fragment shows notification preferences only. It is used when the
 * activity is showing a two-pane settings UI.
 *//*

}*/
