package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        //TODO 10 : Update theme based on value in ListPreference
        val listPreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        listPreference!!.setOnPreferenceChangeListener { preference, newValue ->
            val theme = NightMode.valueOf(newValue.toString().uppercase(Locale.US))
            updateTheme(theme.value)
        }

        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val switchPreference = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        val dailyReminder = DailyReminder()
        switchPreference!!.setOnPreferenceChangeListener { preference, newValue ->
            val value = newValue as Boolean
            if (value) {
                dailyReminder.setDailyReminder(requireContext())
            } else {
                dailyReminder.cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}