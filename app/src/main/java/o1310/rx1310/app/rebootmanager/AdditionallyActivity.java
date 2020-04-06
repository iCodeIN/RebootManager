/*!
 * @author      rx1310 <rx1310@inbox.ru>
 * @copyright   Copyright (c) o1310, 2020
 * @license     MIT License
 */

package o1310.rx1310.app.rebootmanager;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.text.Html;
import o1310.rx1310.app.rebootmanager.R;
import o1310.rx1310.app.rebootmanager.RebootManager;

public class AdditionallyActivity extends PreferenceActivity {

	protected void onCreate(Bundle b) {
		super.onCreate(b);

		setTitle(R.string.activity_additionally);

		// создаем экран
		PreferenceScreen p = getPreferenceManager().createPreferenceScreen(this);
		setPreferenceScreen(p);

		PreferenceCategory ctgSettings = new PreferenceCategory(this);
		ctgSettings.setTitle(R.string.settings);
		
		PreferenceCategory ctgAbout = new PreferenceCategory(this);
		ctgAbout.setTitle(R.string.about);
		
		Preference hideIcon = new Preference(this);
		hideIcon.setKey("SETTING_HIDE_ICON");
		hideIcon.setTitle(R.string.setting_hide_icon);
		hideIcon.setSummary(R.string.setting_hide_icon_desc);
		
		Preference appVersion = new Preference(this);
		appVersion.setKey("");
		appVersion.setTitle(R.string.info_app_version);
		appVersion.setSummary(RebootManager.appVersion(this));
		
		Preference appAuthor = new Preference(this);
		appAuthor.setKey("");
		appAuthor.setTitle(R.string.info_app_author);
		appAuthor.setSummary(R.string.app_author);
		
		p.addPreference(ctgSettings);
		if (Build.VERSION.SDK_INT >= 25) {
			p.addPreference(hideIcon);
		}
		
		p.addPreference(ctgAbout);
		p.addPreference(appVersion);
		p.addPreference(appAuthor);
		
	}

	// обработка нажатий на пункты
	public boolean onPreferenceTreeClick(PreferenceScreen s, Preference p) {

		switch (p.getKey()) {

			case "SETTING_HIDE_ICON":
				hideIconDlg();
				break;

		}

		return super.onPreferenceTreeClick(s, p);

	}
	
	void hideIconDlg(){
		
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		
		b.setTitle(R.string.setting_hide_icon);
		b.setMessage(Html.fromHtml(getString(R.string.msg_hide_icon)));
		b.setCancelable(false);
		b.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface d, int i) {
				ComponentName n = new ComponentName(AdditionallyActivity.this, MainActivity.class);
				PackageManager m = getPackageManager();
				m.setComponentEnabledSetting(n, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
				RebootManager.showToast(getString(R.string.msg_restart_for_apply), getApplicationContext());
			}
		});
		b.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface d, int i) {
				d.cancel();
			}
		});
		
		AlertDialog a = b.create();
	
		a.show();
		
	}
	
}
