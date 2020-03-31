/*!
 * @author      rx1310 <rx1310@inbox.ru>
 * @copyright   Copyright (c) o1310, 2020
 * @license     MIT License
 */

package o1310.rx1310.app.rebootmanager;

public class RebootManager {
	
	public static String CMD_REBOOT_SYS = "su -c svc power reboot";
	public static String CMD_REBOOT_RECOVERY = "su -c svc power reboot recovery";
	public static String CMD_REBOOT_BOOTLOADER = "su -c svc power reboot bootloader";
	public static String CMD_SHUTDOWN = "su -c svc power shutdown";
	
}
