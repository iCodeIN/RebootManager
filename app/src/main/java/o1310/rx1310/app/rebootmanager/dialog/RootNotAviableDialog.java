/*!
 * @author      rx1310 <rx1310@inbox.ru>
 * @copyright   Copyright (c) o1310, 2020
 * @license     MIT License
 */

package o1310.rx1310.app.rebootmanager.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import java.util.Random;
import o1310.rx1310.app.rebootmanager.R;

public class RootNotAviableDialog {
	
	public static AlertDialog getDialog(Context c) {
		
		AlertDialog.Builder ab = new AlertDialog.Builder(c);
		ab.setMessage(R.string.root_not_aviable);
		ab.setTitle(R.string.app_name);
		ab.setIcon(R.mipmap.ic_launcher);
		ab.setCancelable(false);
		ab.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface di, int id) {
					
				}
			});
		
		return ab.create();
		
	}
	
}
