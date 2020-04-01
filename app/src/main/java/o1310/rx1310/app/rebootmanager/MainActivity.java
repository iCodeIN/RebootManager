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
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import eu.chainfire.libsuperuser.Shell;
import o1310.rx1310.app.rebootmanager.R;

public class MainActivity extends PreferenceActivity {
	
	protected void onCreate(Bundle s) {
		super.onCreate(s);
		
		if (!Shell.SU.available()) {
			rootNotAviable();
		}
		
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
	
	void rootNotAviable() {
		AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
		ab.setMessage(R.string.root_not_aviable);
		ab.setTitle(R.string.app_name);
		ab.setIcon(R.mipmap.ic_launcher);
		ab.setCancelable(false);
		ab.setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface du, int id) {
					hideAppIcon();
				}
			});
		AlertDialog ad = ab.create();
		ad.show();
	}
	
	void hideAppIcon() {
		ComponentName componentToDisable = new ComponentName(getPackageName(), "o1310.rx1310.app.rebootmanager.MainActivity");
		getPackageManager().setComponentEnabledSetting(componentToDisable, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
	}
	
}
