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
import android.content.Intent;
import android.net.Uri;
import eu.chainfire.libsuperuser.Shell;
import android.preference.SwitchPreference;

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
		
		SwitchPreference proMode = new SwitchPreference(this);
		proMode.setKey("");
		proMode.setTitle(R.string.setting_pro_mode);
		proMode.setSummary(R.string.setting_pro_mode_desc);
		
		Preference uninstallApp = new Preference(this);
		uninstallApp.setKey("SETTING_UNINSTALL_APP");
		uninstallApp.setTitle(R.string.setting_uninstall_app);
		
		Preference appVersion = new Preference(this);
		appVersion.setKey("");
		appVersion.setTitle(R.string.info_app_version);
		appVersion.setSummary(RebootManager.appVersion(this));
		
		Preference appAuthor = new Preference(this);
		appAuthor.setKey("");
		appAuthor.setTitle(R.string.info_app_author);
		appAuthor.setSummary(R.string.app_author);
		
		Preference appTranslator = new Preference(this);
		appTranslator.setKey("ABOUT_APP_TRANSLATOR_URL");
		appTranslator.setTitle(R.string.info_app_translator);
		appTranslator.setSummary(R.string.app_translator);
		
		Preference appUrl = new Preference(this);
		appUrl.setKey("ABOUT_APP_URL");
		appUrl.setTitle(R.string.info_app_url);
		appUrl.setSummary(R.string.info_app_url_desc);
		
		Preference sysSuInfo = new Preference(this);
		sysSuInfo.setTitle(R.string.info_su);
		sysSuInfo.setSummary(suInfo());
		sysSuInfo.setEnabled(false);
		
		p.addPreference(ctgSettings);
		if (Build.VERSION.SDK_INT >= 25) {
			p.addPreference(hideIcon);
		}
		p.addPreference(proMode);
		p.addPreference(uninstallApp);
		
		p.addPreference(ctgAbout);
		p.addPreference(appVersion);
		p.addPreference(appAuthor);
		p.addPreference(appTranslator);
		p.addPreference(appUrl);
		p.addPreference(sysSuInfo);
		
	}

	// обработка нажатий на пункты
	public boolean onPreferenceTreeClick(PreferenceScreen s, Preference p) {

		switch (p.getKey()) {

			case "SETTING_HIDE_ICON":
				hideIconDlg();
				break;
				
			case "SETTING_UNINSTALL_APP":
				Intent i = new Intent(Intent.ACTION_DELETE);
				i.setData(Uri.parse("package:" + getPackageName()));
				startActivity(i);
				break;
				
			case "ABOUT_APP_TRANSLATOR_URL":
				RebootManager.openUrl(getString(R.string.app_translator_url), this);
				break;
				
			case "ABOUT_APP_URL":
				RebootManager.openUrl("https://vk.com", this);
				break;

		}

		return super.onPreferenceTreeClick(s, p);

	}
	
	// скрытие иконки
	void hideIconDlg(){
		
		// создаем диалог
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		
		b.setTitle(R.string.setting_hide_icon); // заголовок
		b.setMessage(Html.fromHtml(getString(R.string.msg_hide_icon))); // сообщение (+ задействованы теги html)
		b.setCancelable(false); // отключаем скрытие окна по нажатию за границы окна
		b.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // обработка нажатия кнопки "Да"
			public void onClick(DialogInterface d, int i) {
				ComponentName n = new ComponentName(AdditionallyActivity.this, MainActivity.class); // скрываем иконку
				PackageManager m = getPackageManager();
				m.setComponentEnabledSetting(n, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
				RebootManager.showToast(getString(R.string.msg_restart_for_apply), getApplicationContext()); // отображаем toast уведомление
			}
		});
		b.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() { // обработка нажатия кнопки "Нет"
			public void onClick(DialogInterface d, int i) {
				d.cancel(); // закрывает диалог
			}
		});
		
		AlertDialog a = b.create();
	
		a.show(); // отображаем диалог
		
	}
	
	// информация о SU
	String suInfo() {
		return "SU Available: " + Shell.SU.available() + 
			   "\nSELinuxEnforcing: " + Shell.SU.isSELinuxEnforcing() +
			   "\nVersion: " + Shell.SU.version(true) + " (" + Shell.SU.version(false) + ")";
	}
	
}
