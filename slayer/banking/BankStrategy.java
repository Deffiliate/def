package def.slayer.banking;

import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.util.Random;
import org.powerbot.concurrent.Task;

import def.slayer.DefSlayer;
import def.slayer.banking.BankItem;
import def.slayer.equipment.EquipmentViewer;
import def.slayer.killing.KillStrategy;
import def.slayer.tasks.TaskManager;
import def.slayer.walking.WalkingStrategy;
import def.powerbot.WaitCondition;
import def.powerbot.Waiter;

public class BankStrategy extends Strategy implements Task {

	//Methods
	private static int getIndex(String action) {
		action = action.toLowerCase();
		final String[] items = Menu.getActions();
		for (int i = 0; i < items.length; i++) {
			if (items[i].toLowerCase().contains(action)) {
				return i;
			}
		}
		return -1;
	}

	public boolean containInvItems(){
		for (BankItem i : TaskManager.currentTask.getNeededInventoryItems()){
			if (Inventory.getCount(i.getId())==0){
				return false;
			}
		}
		return true;
	}

	public static void withdrawItem(final int amount,final int ... id){
		for (int item : id){
			if (Bank.getItemCount(item)>0){
				Bank.withdraw(item, amount - Inventory.getCount(true, item));
				break;
			}
		}
		Waiter.waitFor(new WaitCondition() {
			@Override
			public boolean isValid() {
				return (Bank.getItemCount(true,id)>=amount);
			}
		}, 10000);
	}

	// Strategy
	@Override
	public void run() {
		if (!Bank.isOpen()){
			Bank.open();
			Waiter.waitFor(new WaitCondition() {
				@Override
				public boolean isValid() {
					return (Bank.isOpen());
				}
			}, 10000);
		}
		if (Bank.isOpen()){
			Bank.depositInventory();
			Waiter.waitFor(new WaitCondition() {
				@Override
				public boolean isValid() {
					return (Inventory.getCount()== 0);
				}
			}, 10000);
			if (TaskManager.currentTask==null){
				for (BankItem i : TaskManager.npcContactRunes){
					if (Inventory.getCount(i.getId())<=i.getAmount()){
						withdrawItem(i.getAmount(),i.getId());
					}
				}
				Bank.close();
				return;
			}
			for (int i : EquipmentViewer.getNeededEquipItems()){
				System.out.println("Withdrawing item: " + i);
				final int f = i;
				withdrawItem(1,i);
				String [] actions = {"Wield","Wear","Equip"};
				Inventory.getItem(i).getWidgetChild().click(false);
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (Menu.isOpen());
					}
				}, 8000);
				int index = -1;
				for (String s : actions){
					if (getIndex(s)!=-1){
						index = getIndex(s);
					}
				}
				final int yOff = 21 + 16 * index + Random.nextInt(0, 12);
				Mouse.move(Menu.getLocation().x + Random.nextInt(0, 15), Menu.getLocation().y
						+ yOff);
				Mouse.click(true);
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (EquipmentViewer.appearanceContains(f));
					}
				}, 10000);
			}
			Bank.depositInventory();
			Waiter.waitFor(new WaitCondition() {
				@Override
				public boolean isValid() {
					return (Inventory.getCount()== 0);
				}
			}, 10000);
			if (TaskManager.currentTask.getNeededInventoryItems().length>0){
				for (BankItem i : TaskManager.currentTask.getNeededInventoryItems()){
					if (Inventory.getCount(i.getId())<=i.getAmount()){
						withdrawItem(i.getAmount(),i.getId());
					}
				}
			}
			if (TaskManager.currentTask.getNeededTeleportItems().length>0){
				for (BankItem i : TaskManager.currentTask.getNeededTeleportItems()){
					if (Inventory.getCount(i.getId())<=i.getAmount()){
						withdrawItem(i.getAmount(),i.getId());
					}
				}
			}
			for (BankItem i : TaskManager.optionalItems){
				if (Inventory.getCount(true,i.getId())<=i.getAmount()){
					withdrawItem(i.getAmount(),i.getId());
				}
			}
			Bank.withdraw(DefSlayer.foodId, 0);
			Waiter.waitFor(new WaitCondition() {
				@Override
				public boolean isValid() {
					return (Inventory.getCount()==28);
				}
			}, 10000);
			KillStrategy.eat();
			if (Inventory.getCount()<28){
				Bank.withdraw(DefSlayer.foodId, 0);
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (Inventory.getCount()==28);
					}
				}, 10000);
			}
			Bank.close();
			Waiter.waitFor(new WaitCondition() {
				@Override
				public boolean isValid() {
					return (!Bank.isOpen());
				}
			}, 10000);
		}
	}


	@Override
	public boolean validate() {
		return (WalkingStrategy.atBank() && ((TaskManager.getCurrentTask()!= null && WalkingStrategy.needBank()) || (TaskManager.getCurrentTask()== null && DefSlayer.usingNPCContact && !BankItem.inventoryContains(TaskManager.npcContactRunes))));
	}

}
