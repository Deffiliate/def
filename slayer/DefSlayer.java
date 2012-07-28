package def.slayer;

import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;

import def.slayer.banking.BankStrategy;
import def.slayer.equipment.TaskEquipment;
import def.slayer.killing.KillStrategy;
import def.slayer.masters.SlayerMaster;
import def.slayer.tasks.TaskManager;
import def.slayer.walking.WalkingStrategy;

@Manifest(authors = { "Deffiliate" }, name = "DefSlayer", description = "Kills assigned monsters", version = 1.0)
public class DefSlayer extends ActiveScript{
	public static boolean usingNPCContact = true;
	//0=Melee,1=Range,2=Mage
	public static TaskEquipment [] mainGear = new TaskEquipment [] {
		new TaskEquipment(10828, 9754, 1712, 20671, 1127, 6524, 4087, 11133, 11732, 0, 0),
		new TaskEquipment(10828, 9754, 1712, 20671, 1127, 6524, 4087, 11133, 11732, 0, 0),
		new TaskEquipment(10828, 9754, 1712, 20671, 24382, 6524, 24379, 11133, 11732, 0, 0)
	};
	public static SlayerMaster selectedMaster = TaskManager.Vanneka;
	public static int foodId = 7946;
	public static int eatPercent = 65;
	public static int specialNeeded = 75;
	@Override
	protected void setup() {
		provide (new BankStrategy());
		provide (new WalkingStrategy());
		provide (new KillStrategy());
		provide (new TaskManager());
	}
}