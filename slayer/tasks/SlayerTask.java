package def.slayer.tasks;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.wrappers.interactive.NPC;

import def.slayer.DefSlayer;
import def.slayer.banking.BankItem;
import def.slayer.equipment.TaskEquipment;
import def.slayer.walking.PathSet;

public class SlayerTask {
	private String taskName;
	private PathSet pathToMonster;
	private int [] taskIds;
	private int [] monsterIds;
	private BankItem [] neededInventoryItems;
	private BankItem [] neededTeleportItems;
	private TaskEquipment neededEquipment;
	private int style; //{1=Melee, 2=Range, 3=Mage}
	private int finishingItem; //0 for none
	private String finishingString;
	private TaskEquipment generatedSet;
	private int radius;
	private int protectionPrayer; //0=Melee,1=Range,2=Mage,99=none
	
	public String getTaskName() {
		return taskName ;
	}
	public PathSet getPathToMonster() {
		return pathToMonster ;
	}
	public int [] getTaskIds() {
		return taskIds ;
	}
	public int [] getMonsterIds() {
		return monsterIds;
	}
	public BankItem[] getNeededInventoryItems() {
		return neededInventoryItems;
	}
	public BankItem[] getNeededTeleportItems() {
		return neededTeleportItems;
	}
	public TaskEquipment getNeededEquipment() {
		return neededEquipment;
	}
	public TaskEquipment getGeneratedSet() {
		return generatedSet;
	}
	public int getStyle() {
		return style;
	}
	public int getFinishingItem() {
		return finishingItem;
	}
	public String getFinishingString() {
		return finishingString;
	}
	public int getRadius() {
		return radius;
	}
	public int getProtectionPrayer() {
		return protectionPrayer;
	}
	public boolean atMonster(){
		return Calculations.distanceTo(pathToMonster.getFinalDestination())<radius && pathToMonster.getFinalDestination().canReach();
	}
	
	public SlayerTask (String taskName,PathSet pathToMonster, int [] taskIds, int [] monsterIds, BankItem [] neededInventoryItems, BankItem [] neededTeleportItems, TaskEquipment neededEquipment, int style, int finishingItem, String finishingString, int radius, int protectionPrayer){
		this.taskName = taskName;
		this.pathToMonster = pathToMonster;
		this.monsterIds = monsterIds;
		this.neededInventoryItems = neededInventoryItems;
		this.neededTeleportItems = neededTeleportItems;
		this.neededEquipment = neededEquipment;
		this.taskIds = taskIds;
		this.style = style;
		this.finishingItem = finishingItem;
		this.finishingString = finishingString;
		this.radius = radius;
		this.protectionPrayer = protectionPrayer;
		this.generatedSet = TaskEquipment.generateSet(DefSlayer.mainGear[style], neededEquipment);
	}
}
