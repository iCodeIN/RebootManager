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
import o1310.rx1310.app.rebootmanager.MainActivity;
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
		
		// булево значение пункта hideIcon
		Boolean appIconHideState = s.getBoolean("SETTING_HIDE_ICON", false);
		
		// создаем экран
		PreferenceScreen p = getPreferenceManager().createPreferenceScreen(this);
		setPreferenceScreen(p);

		// категория "Настройки"
		PreferenceCategory ctgSettings = new PreferenceCategory(this);
		ctgSettings.setTitle(R.string.settings);
		
		// категория "О программе"
		PreferenceCategory ctgAbout = new PreferenceCategory(this);
		ctgAbout.setTitle(R.string.about);
		
		// переключатель Pro-режима
		SwitchPreference proMode = new SwitchPreference(this);
		proMode.setKey("SETTING_PRO_MODE");
		proMode.setTitle(R.string.setting_pro_mode);
		proMode.setSummary(R.string.setting_pro_mode_desc);
			
		// пункт для настройки AssistantMode
		Preference setAssistantMode = new Preference(this);
		setAssistantMode.setKey("SETTING_ASSISTANTMODE");
		setAssistantMode.setTitle(R.string.setting_assistantmode);
		setAssistantMode.setSummary(R.string.setting_assistantmode_desc);

		// пункт для настройки A2IGA
		Preference installInA2IGA = new Preference(this);
		installInA2IGA.setKey("SETTING_INSTALL_IN_A2IGA");
		installInA2IGA.setTitle(R.string.setting_install_in_a2iga);
		
		if (appIconHideState) {
			setAssistantMode.setEnabled(false);
			installInA2IGA.setEnabled(false);
		}
		
		// настройка "Скрыть иконку"
		SwitchPreference hideIcon = new SwitchPreference(this);
		hideIcon.setKey("SETTING_HIDE_ICON");
		hideIcon.setTitle(R.string.setting_hide_icon);
		hideIcon.setSummary(R.string.setting_hide_icon_desc);
		hideIcon.setDefaultValue(false);
		hideIcon.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			
			public boolean onPreferenceChange(Preference p, Object o) {
				
				boolean b = o;
				ComponentName n = new ComponentName(AdditionallyActivity.this, MainActivity.class);
				PackageManager m = getPackageManager();
				
				if (b) {
					m.setComponentEnabledSetting(n, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
					hideIconMsg();
				} else {
					m.setComponentEnabledSetting(n, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
				}
				
				RebootManager.showToast(getString(R.string.msg_restart_for_apply), AdditionallyActivity.this);
				
				return true;
				
			}
			
		});
		
		// пункт для удаления приложения
		Preference uninstallApp = new Preference(this);
		uninstallApp.setKey("SETTING_UNINSTALL_APP");
		uninstallApp.setTitle(R.string.setting_uninstall_app);
		
		// пункт с версией приложения
		Preference appVersion = new Preference(this);
		appVersion.setKey("ABOUT_APP_VERSION");
		appVersion.setTitle(R.string.info_app_version);
		appVersion.setSummary(RebootManager.appVersion(this));
		
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
		
		// добавление настроек
		p.addPreference(ctgSettings);
		
		/* Если у пользователя версия AndroidSDK равна
		 * или больше 25 (Android N), то пункт "Скрыть иконку"
		 * будет отображен. Почему? Потому что QuickTiles, которые заменяют
		 * основной интерфейс, ввели именно в этой версии Android.
		 * Если скрыть иконку в системе, где быстрые тайлы не поддерживаются,
		 * то вызов функций RebootManager становится невозможным.
		 */
		if (Build.VERSION.SDK_INT >= 25) {
			p.addPreference(hideIcon);
		} 
		
		if (Build.VERSION.SDK_INT >= 23) {
			p.addPreference(setAssistantMode);
			if (RebootManager.appIsInstalled(this, "o1310.rx1310.app.a2iga")) {
				p.addPreference(installInA2IGA);
			}
		}
		
		p.addPreference(proMode);
		p.addPreference(uninstallApp);
		
		p.addPreference(ctgAbout);
		p.addPreference(appVersion);
		p.addPreference(appAuthor);
		p.addPreference(appTranslator);
		//p.addPreference(appUrl);
		p.addPreference(sysSuInfo);
		
	}

	// обработка нажатий на пункты
	public boolean onPreferenceTreeClick(PreferenceScreen s, Preference p) {

		switch (p.getKey()) {

			case "SETTING_ASSISTANTMODE":
				startActivity(new Intent(android.provider.Settings.ACTION_VOICE_INPUT_SETTINGS));
				RebootManager.showToast(getString(R.string.setting_assistantmode_guides), this);
				break;
				
			case "SETTING_INSTALL_IN_A2IGA":
				Intent sendPackageName = new Intent();
				sendPackageName.setAction(Intent.ACTION_SEND);
				sendPackageName.putExtra(Intent.EXTRA_TEXT, getPackageName());
				sendPackageName.setType("text/plain");
				startActivity(Intent.createChooser(sendPackageName, getString(R.string.setting_install_in_a2iga_chooser_title)));
				break;
				
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
	
	// Предупреждение при скрытии иконки
	void hideIconMsg() {
		
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setIcon(R.mipmap.ic_launcher_round);
		b.setTitle(R.string.app_name);
		b.setMessage(R.string.msg_hide_icon_tip);
		
		AlertDialog d = b.create();
		d.show();
		
	}
	
	// информация о SU
	String suInfo() {
		return "SU Available: " + Shell.SU.available() +
			   "\nSELinuxEnforcing: " + Shell.SU.isSELinuxEnforcing() +
			   "\nVersion: " + Shell.SU.version(true) + " (" + Shell.SU.version(false) + ")";
	}
	
}
