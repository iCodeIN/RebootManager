// ! rx1310 <rx1310@inbox.ru> | Copyright (c) rx1310, 2020 | MIT License
 
package ru.rx1310.app.rebootmanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

public class RebootManager {
	
	public static String CMD_REBOOT_RECOVERY = "su -c svc power reboot recovery"; // команда для перехода в recovery
	public static String CMD_REBOOT_BOOTLOADER = "su -c svc power reboot bootloader"; // команда для перехода в bootloader
	public static String CMD_REBOOT_SYS_SOFT = "setprop ctl.restart zygote"; // команда для soft-reboot
	public static String CMD_SAFE_MODE = "setprop persist.sys.safemode 1 && setprop ctl.restart zygote"; // команда для перехода в безопасный режим
	
	// версия приложения
	public static String appVersion(Context c) {
		
		// переменные
		String s, a;
		int v;
		
		// регистрируем PackageManager
		PackageManager m = c.getPackageManager();
		
		try {
			// регистрируем PackageInfo
			PackageInfo i = m.getPackageInfo(c.getPackageName(), 0);
			s = i.versionName; // Получаем название версии
			v = i.versionCode; // Получаем код версии
			a = s + "." + v;   // Объединяем название и код версии для "вида"
		} catch(PackageManager.NameNotFoundException e) {
			// в случае ошибки вернем "error(String.appVersion)"
			e.printStackTrace();
			a = "error(String.appVersion)";
		}
		
		return a; // вернем версию в формате НАЗВАНИЕ.КОД (напр.: 1.200915)
		
	}
	
	// вызов Toast
	public static void showToast(String s, Context c) {
		Toast.makeText(c, s, Toast.LENGTH_LONG).show();
	}
	
	// переход по ссылкам
	public static void openUrl(String url, Context c) {
		c.startActivity(new Intent (Intent.ACTION_VIEW, Uri.parse(url)));
	}
	
}
