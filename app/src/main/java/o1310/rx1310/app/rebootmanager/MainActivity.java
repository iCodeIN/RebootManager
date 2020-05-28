/*!
 * @author      rx1310 <rx1310@inbox.ru>
 * @copyright   Copyright (c) o1310, 2020
 * @license     MIT License
 */

package o1310.rx1310.app.rebootmanager;

// простите. я говнокодер.

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.Html;
import eu.chainfire.libsuperuser.Shell;
import o1310.rx1310.app.rebootmanager.R;
import o1310.rx1310.app.rebootmanager.RebootManager;
import android.content.Intent;

public class MainActivity extends PreferenceActivity {
	
	SharedPreferences s;
	
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		
		// указываем заголовок активности
		setTitle(R.string.activity_main);
		
		// сохранение настроек
		s = PreferenceManager.getDefaultSharedPreferences(this);
		
		// булево значение настройки "Режим Pro"
		Boolean proMode = s.getBoolean("SETTING_PRO_MODE", false);
		
		// создаем экран
		PreferenceScreen p = getPreferenceManager().createPreferenceScreen(this);
		setPreferenceScreen(p);
		
		// пункт на случай отсутствия root на девайсе
		Preference rootNotAvailableMsg = new Preference(this);
		rootNotAvailableMsg.setEnabled(false);
		rootNotAvailableMsg.setSelectable(false);
		rootNotAvailableMsg.setSummary(R.string.msg_root_not_available);
		
		// пункт для перехода в "Настройки"
		Preference goToAdditionallyActivity = new Preference(this);
		goToAdditionallyActivity.setKey("goToAdditionallyActivity");
		goToAdditionallyActivity.setSummary(R.string.activity_additionally);
		
		// перезагрузка в режим recovery
		Preference rebootIntoRecovery = new Preference(this);
		rebootIntoRecovery.setKey("rebootIntoRecovery");
		rebootIntoRecovery.setTitle(R.string.mng_reboot_into_recovery);
		rebootIntoRecovery.setSummary(R.string.mng_reboot_into_recovery_desc);
		
		// перезагрузка в bootloader
		Preference rebootIntoBootloader = new Preference(this);
		rebootIntoBootloader.setKey("rebootIntoBootloader");
		rebootIntoBootloader.setTitle(R.string.mng_reboot_into_bootloader);
		rebootIntoBootloader.setSummary(R.string.mng_reboot_into_bootloader_desc);
		
		// проверка наличия root
		if (!Shell.SU.available()) {
			// если рут доступа нет
			setTitle(R.string.app_name);
			p.addPreference(rootNotAvailableMsg);
		} else {
			// если рут доступ есть
			p.addPreference(rebootIntoRecovery);
			p.addPreference(rebootIntoBootloader);
		}
		
		p.addPreference(goToAdditionallyActivity); // добавляем в конце пункт для перехода в меню "Дополнительно"
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		// принудительно выгружаем процесс из памяти
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	// обработка нажатий на пункты
	public boolean onPreferenceTreeClick(PreferenceScreen s, Preference p) {
		
		switch (p.getKey()) {
			
			// переход в меню "Дополнительное"
			case "goToAdditionallyActivity": 
				startActivity(new Intent(this, AdditionallyActivity.class)); // вызов активности
				break;
			
			// переход в режим recovery
			case "rebootIntoRecovery":
				Shell.SU.run(RebootManager.CMD_REBOOT_RECOVERY); // выполнение команды
				break;

			// переход в bootloader
			case "rebootIntoBootloader":
				Shell.SU.run(RebootManager.CMD_REBOOT_BOOTLOADER); // выполнение команды
				break;
			
		}
		
		return super.onPreferenceTreeClick(s, p);
		
	}
	
}
