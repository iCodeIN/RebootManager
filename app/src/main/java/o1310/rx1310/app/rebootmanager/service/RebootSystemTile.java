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

@RequiresApi(api = Build.VERSION_CODES.N)
public class RebootSystemTile extends TileService {

	public void onTileAdded() {
		Shell.SU.available();
	}
    
    @Override
    public void onStartListening() {

        Tile tile = getQsTile();
        
    }

    @Override
    public void onClick() {
        
        if (!isSecure()) {

            showDialog();

        } else {

            unlockAndRun(new Runnable() {
					@Override
					public void run() {

						showDialog();
					}
				});
        }
    }

    private void showDialog() {

        //showDialog(TileDialog.getDialog(this));
    }
}


