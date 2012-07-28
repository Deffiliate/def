package def.slayer.tasks;

import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.concurrent.Task;

import def.slayer.DefSlayer;
import def.slayer.banking.BankItem;
import def.slayer.equipment.TaskEquipment;
import def.slayer.masters.SlayerMaster;
import def.slayer.walking.WalkingStrategy;
import def.powerbot.*;

public class TaskManager extends Strategy implements Task {
	// Variables
	public static SlayerTask currentTask = null;
	// Declare BankItems
	public static final BankItem glory = new BankItem(new int[] { 1712, 1710, 1708, 1706 }, 1);
	public static BankItem gamesNecklace = new BankItem(new int[] { 3853, 3855, 3857,
			3859, 3861, 3863, 3865}, 1);
	public static final BankItem ringOfDuelling = new BankItem(new int[] { 2552, 2554, 2556,
			2558, 2560, 2562, 2564, 2566}, 1);
	public static final BankItem superStrength = new BankItem (new int[] { 2440, 157, 159,
			161}, 2);
	public static final BankItem superAttack = new BankItem (new int[] { 2436, 145, 147,
			149}, 2);
	public static final BankItem prayerPot = new BankItem (new int[] { 2434,139,141, 
			143}, 12);
	public static final BankItem antifirePot = new BankItem (new int[] { 2452,2454,2456, 
			2458}, 4);
	public static final BankItem astralRune = new BankItem (new int[] {9075}, 1);
	public static final BankItem cosmicRune = new BankItem (new int[] {564}, 1);
	public static final BankItem airRune = new BankItem (new int[] {556}, 2);
	public static final BankItem ringOfKinship = new BankItem (new int[] {15707}, 1);
	public static BankItem [] npcContactRunes = new BankItem[]{astralRune,cosmicRune,airRune};
	public static BankItem [] optionalItems = new BankItem[]{superStrength,superAttack,ringOfDuelling};
	// Declare Equipment sets
	private static final TaskEquipment defaultSet = new TaskEquipment(0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0);
	private static final TaskEquipment antidragonShield = new TaskEquipment(0, 0, 0, 0, 0,
			1540, 0, 0, 0, 0, 0);
	// Declare Tasks
	public static final SlayerTask greaterDemons = new SlayerTask("Greater Demons",
			WalkingStrategy.toGreaterDemons, new int[] { 1103212794 },
			new int[] { 0 }, new BankItem[] {}, new BankItem[] { glory },defaultSet, 0, 0, "",30, 99);
	public static final SlayerTask bloodvelds = new SlayerTask("Bloodvelds",
			WalkingStrategy.toBloodvelds, new int[] { 1099018490 }, new int[] {
					1618, 1619 }, new BankItem[] {}, new BankItem[] {}, defaultSet, 2, 0, "",30, 99);
	public static final SlayerTask waterfiends = new SlayerTask("Waterfiends",
			WalkingStrategy.toWaterfiends, new int[] { 1117892858 }, new int[] {
					5361 }, new BankItem[] {prayerPot}, new BankItem[] {gamesNecklace}, antidragonShield, 2, 0, "",10, 1);
	public static final SlayerTask steelDragons = new SlayerTask("Steel Dragons",
			WalkingStrategy.toSteelDragons, new int[] { 1096921338 }, new int[] {
					1592 }, new BankItem[] {prayerPot,antifirePot}, new BankItem[] {glory}, antidragonShield, 0, 0, "",35, 0);
	private static final SlayerTask[] allTasks = { greaterDemons, bloodvelds, waterfiends, steelDragons };
	// Declare Masters
	public static final SlayerMaster Vanneka = new SlayerMaster("Vanneka", 0, 7);

	// Methods
	public static int getKillsLeft() {
		return Settings.get(394);
	}

	public static int getCurrentTaskId() {
		return Settings.get(1232);
	}

	public static SlayerTask getCurrentTask() {
		if (getKillsLeft() == 0) {
			return null;
		}
		int currentTaskId = getCurrentTaskId();
		for (SlayerTask t : allTasks) {
			for (int taskId : t.getTaskIds()) {
				if (taskId == currentTaskId) {
					currentTask = t;
					return t;
				}
			}
		}
		System.out.println("Undefined task: " + currentTaskId);
		return null;
	}

	public static int[] getEquipIds() {
		if (currentTask == null) {
			return new int[] { DefSlayer.mainGear[0].getBootsSlot(),
					DefSlayer.mainGear[0].getCapeSlot(),
					DefSlayer.mainGear[0].getHeadSlot(),
					DefSlayer.mainGear[0].getGlovesSlot(),
					DefSlayer.mainGear[0].getLegsSlot(),
					DefSlayer.mainGear[0].getNeckSlot(),
					DefSlayer.mainGear[0].getQuiverSlot(),
					DefSlayer.mainGear[0].getShieldSlot(),
					DefSlayer.mainGear[0].getTorsoSlot(),
					DefSlayer.mainGear[0].getWeaponSlot(),
					DefSlayer.mainGear[0].getRingSlot() };
		}
		return new int[] {
				TaskManager.currentTask.getGeneratedSet().getBootsSlot(),
				TaskManager.currentTask.getGeneratedSet().getCapeSlot(),
				TaskManager.currentTask.getGeneratedSet().getHeadSlot(),
				TaskManager.currentTask.getGeneratedSet().getGlovesSlot(),
				TaskManager.currentTask.getGeneratedSet().getLegsSlot(),
				TaskManager.currentTask.getGeneratedSet().getNeckSlot(),
				TaskManager.currentTask.getGeneratedSet().getQuiverSlot(),
				TaskManager.currentTask.getGeneratedSet().getShieldSlot(),
				TaskManager.currentTask.getGeneratedSet().getTorsoSlot(),
				TaskManager.currentTask.getGeneratedSet().getWeaponSlot(),
				TaskManager.currentTask.getGeneratedSet().getRingSlot() };
	}

	// Strategy
	@Override
	public void run() {
		if (getCurrentTask() != null) {
			currentTask = getCurrentTask();
			return;
		}
		if (DefSlayer.usingNPCContact) {
			if (Tabs.getCurrent().equals(Tabs.INVENTORY)) {
				Tabs.MAGIC.open();
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (Tabs.getCurrent().equals(Tabs.MAGIC));
					}
				}, 7000);
				Widgets.get(430).getChild(26).click(true);
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (Widgets.get(88).validate());
					}
				}, 10000);
				Widgets.scroll(Widgets.get(88).getChild(5).getChild(DefSlayer.selectedMaster.getWidgetChild()), Widgets.get(88).getChild(7));
				Widgets.get(88).getChild(5).getChild(DefSlayer.selectedMaster.getWidgetChild()).click(true);
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (Widgets.get(1191).validate());
					}
				}, 16000);
				for (int i = 0; i<4; i++){
					Widgets.getContinue().click(true);
					if (Widgets.get(1191).validate()){
						Waiter.waitFor(new WaitCondition() {
							@Override
							public boolean isValid() {
								return (Widgets.get(1184).validate());
							}
						}, 9000);
					}
					else{
						Waiter.waitFor(new WaitCondition() {
							@Override
							public boolean isValid() {
								return (Widgets.get(1191).validate() || Widgets.get(1188).validate());
							}
						}, 9000);
					}
				}
				Keyboard.sendText("1", true);	
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (Widgets.get(1191).validate());
					}
				}, 10000);
				for (int i = 0; i<3; i++){
					Widgets.getContinue().click(true);
					if (Widgets.get(1191).validate()){
						Waiter.waitFor(new WaitCondition() {
							@Override
							public boolean isValid() {
								return (Widgets.get(1184).validate());
							}
						}, 9000);
					}
					else{
						Waiter.waitFor(new WaitCondition() {
							@Override
							public boolean isValid() {
								return (Widgets.get(1191).validate() || Widgets.get(1188).validate());
							}
						}, 9000);
					}
				}
			}

		} else {
			if (SceneEntities
					.getNearest(DefSlayer.selectedMaster.getMasterId())
					.isOnScreen()) {

			} else {

			}
		}

	}

	@Override
	public boolean validate() {
		return (TaskManager.getCurrentTask() == null && (DefSlayer.usingNPCContact && WalkingStrategy.atBank() &&  BankItem.inventoryContains(npcContactRunes) || (NPCs
				.getNearest(DefSlayer.selectedMaster.getMasterId()) != null && NPCs
				.getNearest(DefSlayer.selectedMaster.getMasterId())
				.isOnScreen())));
	}

}
