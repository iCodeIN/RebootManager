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

public class MainActivity extends PreferenceActivity {
	
	protected void onCreate(Bundle s) {
		super.onCreate(s);
		
		PreferenceScreen p = getPreferenceManager().createPreferenceScreen(this);
		setPreferenceScreen(p);
		
		Preference rebootSystem = new Preference(this);
		rebootSystem.setKey("rebootSystem");
		rebootSystem.setTitle(R.string.reboot_system);
		rebootSystem.setSummary(R.string.reboot_system_desc);
		
		Preference rebootRecovery = new Preference(this);
		rebootRecovery.setKey("rebootRecovery");
		rebootRecovery.setTitle(R.string.reboot_recovery);
		rebootRecovery.setSummary(R.string.reboot_recovery_desc);
		
		Preference rebootBootloader = new Preference(this);
		rebootBootloader.setKey("rebootBootloader");
		rebootBootloader.setTitle(R.string.reboot_bootloader);
		rebootBootloader.setSummary(R.string.reboot_bootloader_desc);
		
		
		// добавление пунктов на экран
		p.addPreference(rebootSystem);
		p.addPreference(rebootRecovery);
		p.addPreference(rebootBootloader);
		
	}
	
	// обработка нажатий на пункты
	public boolean onPreferenceTreeClick(PreferenceScreen s, Preference p) {
		
		switch (p.getKey()) {
			
			case "rebootSystem":
				Shell.SU.run(RebootManager.CMD_REBOOT_SYS);
				break;
				
			case "rebootRecovery":
				Shell.SU.run(RebootManager.CMD_REBOOT_RECOVERY);
				break;
				
			case "rebootBootloader":
				Shell.SU.run(RebootManager.CMD_REBOOT_BOOTLOADER);
				break;
			
		}
		
		return super.onPreferenceTreeClick(s, p);
		
	}
	
}
