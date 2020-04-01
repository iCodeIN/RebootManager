/*!
 * @author      rx1310 <rx1310@inbox.ru>
 * @copyright   Copyright (c) o1310, 2020
 * @license     MIT License
 */

package o1310.rx1310.app.rebootmanager.service;


import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.support.annotation.RequiresApi;
import eu.chainfire.libsuperuser.Shell;
import o1310.rx1310.app.rebootmanager.dialog.RootNotAviableDialog;
import o1310.rx1310.app.rebootmanager.RebootManager;

@RequiresApi(api = Build.VERSION_CODES.N)
public class RebootSystemTile extends TileService {

	@Override
	public void onClick() {
		
		if (!Shell.SU.available()) {
			showError();
		} else {
			Shell.SU.run(RebootManager.CMD_REBOOT_SYS);
		}
		
	}
	
	private void showError() {
		showDialog(RootNotAviableDialog.getDialog(this));
	}
	
}


