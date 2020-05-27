/*!
 * @author      rx1310 <rx1310@inbox.ru>
 * @copyright   Copyright (c) o1310, 2020
 * @license     MIT License
 */

package o1310.rx1310.app.rebootmanager;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.text.Html;
import eu.chainfire.libsuperuser.Shell;
import o1310.rx1310.app.rebootmanager.AdditionallyActivity;
import o1310.rx1310.app.rebootmanager.R;
import o1310.rx1310.app.rebootmanager.RebootManager;

public class AdditionallyActivity extends PreferenceActivity {

	SharedPreferences s;
	
	protected void onCreate(Bundle b) {
		super.onCreate(b);

		// указываем заголовок
		setTitle(R.string.activity_additionally);

		// сохранение настроек
		s = PreferenceManager.getDefaultSharedPreferences(this);
		
		// создаем экран
		PreferenceScreen p = getPreferenceManager().createPreferenceScreen(this);
		setPreferenceScreen(p);

		// пункт для удаления приложения
		Preference uninstallApp = new Preference(this);
		uninstallApp.setKey("SETTING_UNINSTALL_APP");
		uninstallApp.setTitle(R.string.setting_uninstall_app);
		
		// пункт с версией приложения
		Preference appVersion = new Preference(this);
		appVersion.setKey("ABOUT_APP_VERSION");
		appVersion.setTitle(R.string.info_app_version);
		appVersion.setSummary(RebootManager.appVersion(this) + " (lite)");
		
		// пункт с именем автора (rx1310)
		Preference appAuthor = new Preference(this);
		appAuthor.setKey("ABOUT_APP_AUTHOR_URL");
		appAuthor.setTitle(R.string.info_app_author);
		appAuthor.setSummary(R.string.app_author);
		
		// пункт с именем локализатора
		Preference appTranslator = new Preference(this);
		appTranslator.setKey("ABOUT_APP_TRANSLATOR_URL");
		appTranslator.setTitle(R.string.info_app_translator);
		appTranslator.setSummary(R.string.app_translator);
		
		// пункт для перехода на дом. стр. приложения
		Preference appUrl = new Preference(this);
		appUrl.setKey("ABOUT_APP_URL");
		appUrl.setTitle(R.string.info_app_url);
		appUrl.setSummary(R.string.info_app_url_desc);
		
		// информация о рут доступе
		Preference sysSuInfo = new Preference(this);
		sysSuInfo.setTitle(R.string.info_su);
		sysSuInfo.setSummary(suInfo());
		sysSuInfo.setEnabled(false);
		
		p.addPreference(appVersion);
		p.addPreference(appAuthor);
		p.addPreference(appTranslator);
		//p.addPreference(appUrl);
		p.addPreference(sysSuInfo);
		p.addPreference(uninstallApp);
		
	}

	// обработка нажатий на пункты
	public boolean onPreferenceTreeClick(PreferenceScreen s, Preference p) {

		switch (p.getKey()) {

			case "SETTING_UNINSTALL_APP":
				Intent i = new Intent(Intent.ACTION_DELETE);
				i.setData(Uri.parse("package:" + getPackageName()));
				startActivity(i);
				break;
				
			case "ABOUT_APP_VERSION":
				RebootManager.showToast( "made with " + ("♥️") + " by rx1310 (in o1310)", this);
				RebootManager.openUrl("https://github.com/o1310/RebootManager", this);
				break;
				
			case "ABOUT_APP_AUTHOR_URL":
				RebootManager.openUrl(getString(R.string.app_author_url), this);
				break;
				
			case "ABOUT_APP_TRANSLATOR_URL":
				RebootManager.openUrl(getString(R.string.app_translator_url), this);
				break;
				
			case "ABOUT_APP_URL":
				RebootManager.openUrl("https://o1310.github.io", this);
				break;

		}

		return super.onPreferenceTreeClick(s, p);

	}
	
	// информация о SU
	String suInfo() {
		return "SU Available: " + Shell.SU.available() +
			   "\nSELinuxEnforcing: " + Shell.SU.isSELinuxEnforcing() +
			   "\nVersion: " + Shell.SU.version(true) + " (" + Shell.SU.version(false) + ")";
	}
	
}
