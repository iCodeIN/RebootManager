/*!
 * @author      rx1310 <rx1310@inbox.ru>
 * @copyright   Copyright (c) o1310, 2020
 * @license     MIT License
 */

package o1310.rx1310.app.rebootmanager;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import eu.chainfire.libsuperuser.Shell;
import o1310.rx1310.app.rebootmanager.R;
import o1310.rx1310.app.rebootmanager.RebootManager;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MainActivity extends PreferenceActivity {
	
	SharedPreferences s;
	
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		
		setTitle(R.string.activity_main);
		
		s = PreferenceManager.getDefaultSharedPreferences(this);
		
		Boolean proMode = s.getBoolean("SETTING_PRO_MODE", false);
		
		// создаем экран
		PreferenceScreen p = getPreferenceManager().createPreferenceScreen(this);
		setPreferenceScreen(p);
		
		// пункт на случай отсутствия root на девайсе
		Preference rootNotAvailableMsg = new Preference(this);
		rootNotAvailableMsg.setEnabled(false);
		rootNotAvailableMsg.setSelectable(false);
		rootNotAvailableMsg.setSummary(R.string.msg_root_not_available);
		
		// перезагрузка системы
		Preference rebootSystem = new Preference(this);
		rebootSystem.setKey("rebootSystem");
		rebootSystem.setTitle(R.string.mng_reboot_system);
		rebootSystem.setSummary(R.string.mng_reboot_system_desc);
		
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
		
		// перезагрузка в bootloader
		Preference sysShutdown = new Preference(this);
		sysShutdown.setKey("sysShutdown");
		sysShutdown.setTitle(R.string.mng_reboot_into_bootloader);
		sysShutdown.setSummary(R.string.mng_reboot_into_bootloader_desc);
		
		// проверка наличия root
		if (!Shell.SU.available()) {
			setTitle(R.string.app_name);
			p.addPreference(rootNotAvailableMsg);
		} else {
			p.addPreference(rebootIntoRecovery);
			p.addPreference(rebootIntoBootloader);
			// если включен режим "Pro"
			if (proMode) {
				p.addPreference(rebootSystem);
				p.addPreference(sysShutdown);
			}
		}
		
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
			
			case "rebootIntoRecovery":
				Shell.SU.run(RebootManager.CMD_REBOOT_RECOVERY);
				break;

			case "rebootIntoBootloader":
				Shell.SU.run(RebootManager.CMD_REBOOT_BOOTLOADER);
				break;
			
			case "rebootSystem":
				Shell.SU.run(RebootManager.CMD_REBOOT_SYS);
				break;
				
			case "sysShutdown":
				Shell.SU.run(RebootManager.CMD_SHUTDOWN);
				break;

			/*case "rebootIntoBootloader":
				Shell.SU.run(RebootManager.CMD_REBOOT_BOOTLOADER);
				break;*/
			
		}
		
		return super.onPreferenceTreeClick(s, p);
		
	}
	
}
