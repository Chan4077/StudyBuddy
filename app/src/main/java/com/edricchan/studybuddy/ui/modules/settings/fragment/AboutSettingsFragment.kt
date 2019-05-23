package com.edricchan.studybuddy.ui.modules.settings.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.edricchan.studybuddy.BuildConfig
import com.edricchan.studybuddy.R
import com.edricchan.studybuddy.constants.Constants
import com.edricchan.studybuddy.ui.modules.updates.UpdatesActivity
import com.edricchan.studybuddy.utils.SharedUtils
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.takisoft.preferencex.PreferenceFragmentCompat

class AboutSettingsFragment : PreferenceFragmentCompat() {

	private lateinit var preferences: SharedPreferences

	override fun onCreatePreferencesFix(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.pref_about, rootKey)
		val context = activity
		preferences = PreferenceManager.getDefaultSharedPreferences(context!!)
		val appAuthorUrl = Uri.parse("https://edricchan03.github.io")
		findPreference<Preference>(Constants.prefUpdates)?.intent = Intent(activity, UpdatesActivity::class.java)
		val appAuthor = findPreference<Preference>(Constants.prefAboutAppAuthor)
		appAuthor?.setOnPreferenceClickListener {
			SharedUtils.launchUri(context, appAuthorUrl, preferences.getBoolean(Constants.prefUseCustomTabs, true))
			true
		}
		val appSrc = findPreference<Preference>(Constants.prefAboutSourceCode)
		appSrc?.setOnPreferenceClickListener {
			SharedUtils.launchUri(context, Constants.uriSrcCode, preferences.getBoolean(Constants.prefUseCustomTabs, true))
			true
		}
		val appVersion = findPreference<Preference>(Constants.prefAboutAppVersion)
		appVersion?.summary = BuildConfig.VERSION_NAME

		val appInfo = findPreference<Preference>(Constants.prefAboutAppInfo)
		appInfo?.intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID))

		findPreference<Preference>(Constants.prefAboutLicenses)?.intent = Intent(activity, OssLicensesMenuActivity::class.java)
	}
}
