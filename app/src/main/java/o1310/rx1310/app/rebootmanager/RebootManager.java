/*!
 * @author      rx1310 <rx1310@inbox.ru>
 * @copyright   Copyright (c) o1310, 2020
 * @license     MIT License
 */

package o1310.rx1310.app.rebootmanager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

public class RebootManager {
	
	public static String CMD_REBOOT_RECOVERY = "su -c svc power reboot recovery";
	public static String CMD_REBOOT_BOOTLOADER = "su -c svc power reboot bootloader";
	public static String CMD_SHUTDOWN = "su -c svc power shutdown";
	public static String CMD_REBOOT_SYS = "su -c svc power reboot";
	public static String CMD_REBOOT_SYS_SOFT = "setprop ctl.stop zygote";
	public static String TEST_CMD_FAKE_BATTERY_LOW = "am broadcast -a android.intent.action.BATTERY_LOW";
	
	public static String appVersionInfo(Context c) {
		
		String s, a;
		int v;
		
		PackageManager m = c.getPackageManager();
		
		try {
			PackageInfo i = m.getPackageInfo(c.getPackageName(), 0);
			s = i.versionName;
			v = i.versionCode;
			a = s + "." + v;
		} catch(PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			a = "error";
		}
		
		return a;
		
	}
	
	public static void showToast(String s, Context c) {
		Toast.makeText(c, s, Toast.LENGTH_LONG).show();
	}
	
	public static void openUrl(String url, Context c) {
		c.startActivity(new Intent (Intent.ACTION_VIEW, Uri.parse(url)));
	}
	
}
