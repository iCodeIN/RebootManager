/*!
 * @author      rx1310 <rx1310@inbox.ru>
 * @copyright   Copyright (c) o1310, 2020
 * @license     MIT License
 */

package o1310.rx1310.app.rebootmanager;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import eu.chainfire.libsuperuser.Shell;
import o1310.rx1310.app.rebootmanager.R;
import o1310.rx1310.app.rebootmanager.RebootManager;

public class MainActivity extends PreferenceActivity {
	
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		
		setTitle(R.string.activity_main);
		
		// создаем экран
		PreferenceScreen p = getPreferenceManager().createPreferenceScreen(this);
		setPreferenceScreen(p);
		
		// пункт на случай отсутствия root на девайсе
		Preference rootNotAviableMsg = new Preference(this);
		rootNotAviableMsg.setEnabled(false);
		rootNotAviableMsg.setSelectable(false);
		rootNotAviableMsg.setSummary(R.string.msg_root_not_aviable);
		
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
		
		// проверка наличия root
		if (Shell.SU.available()) {
			p.addPreference(rebootSystem);
			p.addPreference(rebootIntoRecovery);
			p.addPreference(rebootIntoBootloader);
		} else {
			setTitle(R.string.app_name);
			p.addPreference(rootNotAviableMsg);
		}
		
	}
	
	// обработка нажатий на пункты
	public boolean onPreferenceTreeClick(PreferenceScreen s, Preference p) {
		
		switch (p.getKey()) {
			
			case "rebootSystem":
				Shell.SU.run(RebootManager.CMD_REBOOT_SYS);
				break;
				
			case "rebootIntoRecovery":
				Shell.SU.run(RebootManager.CMD_REBOOT_RECOVERY);
				break;
				
			case "rebootIntoBootloader":
				Shell.SU.run(RebootManager.CMD_REBOOT_BOOTLOADER);
				break;
			
		}
		
		return super.onPreferenceTreeClick(s, p);
		
	}
	
}
