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

public class MainActivity extends PreferenceActivity {
	
	protected void onCreate(Bundle s) {
		super.onCreate(s);
		
		PreferenceScreen p = getPreferenceManager().createPreferenceScreen(this);
		setPreferenceScreen(p);
		
		Preference chb1 = new Preference(this);
		chb1.setKey("id1");
		chb1.setTitle("Item title");
		chb1.setSummary("Item description");
		
		p.addPreference(chb1);
		
	}
	
	// обработка нажатий на пункты
	public boolean onPreferenceTreeClick(PreferenceScreen s, Preference p) {
		
		switch (p.getKey()) {
			
			case "id1":
				// функция
				break;
			
		}
		
		return super.onPreferenceTreeClick(s, p);
		
	}
	
}
