package def.slayer.killing;


import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.bot.event.MessageEvent;
import org.powerbot.game.bot.event.listener.MessageListener;
import org.powerbot.concurrent.Task;

import def.slayer.DefSlayer;
import def.slayer.tasks.TaskManager;
import def.slayer.walking.WalkingStrategy;
import def.powerbot.*;

public class KillStrategy extends Strategy implements Task, MessageListener {

	//Methods
	public static int getCurrentHealth () { 
		return Integer.parseInt(Widgets.get(748).getChild(8).getText());
	}
	public static int getCurrentPrayerPoints() { 
		return Integer.parseInt(Widgets.get(749).getChild(6).getText());
	}
	public static int getMaxHealth() {
		return Skills.getRealLevel(Skills.CONSTITUTION);
	}
	public static int getHpPercent() {
		return getCurrentHealth()/getMaxHealth()*10;
	}
	public static void eat(){
		while (getHpPercent()<DefSlayer.eatPercent && Inventory.getCount(DefSlayer.foodId)>0){
			if (!Tabs.getCurrent().equals(Tabs.INVENTORY)){
				Tabs.INVENTORY.open();
				Waiter.waitFor(new WaitCondition() { @Override
					public boolean isValid() {
					return (Tabs.getCurrent().equals(Tabs.INVENTORY));
				}},5000);
			}
			System.out.println("Time to eat.");
			Inventory.getItem(DefSlayer.foodId).getWidgetChild().interact("Eat");
			Time.sleep(1900,2100);
			return;
		}
	}
	public void potionHandler(){
		if (Inventory.getCount(229)>0){
			for(Item i : Inventory.getItems()){
				if (i.getId()==229){
					i.getWidgetChild().interact("Drop");
					Time.sleep(600,800);
				}
			}
		}
		if (Skills.getLevel(Skills.STRENGTH)<Skills.getRealLevel(Skills.STRENGTH)+3){
			Inventory.getItem(TaskManager.superStrength.getId()).getWidgetChild().click(true);
			Waiter.waitFor(new WaitCondition() { @Override
				public boolean isValid() {
				return (Skills.getLevel(Skills.STRENGTH)>Skills.getRealLevel(Skills.STRENGTH)+3);
			}},5000);
		}
		if (Skills.getLevel(Skills.ATTACK)<Skills.getRealLevel(Skills.ATTACK)+3){
			Inventory.getItem(TaskManager.superAttack.getId()).getWidgetChild().click(true);
			Waiter.waitFor(new WaitCondition() { @Override
				public boolean isValid() {
				return (Skills.getLevel(Skills.ATTACK)>Skills.getRealLevel(Skills.ATTACK)+3);
			}},5000);
		}
		if (getCurrentPrayerPoints()<100){
			Inventory.getItem(TaskManager.prayerPot.getId()).getWidgetChild().click(true);
			Waiter.waitFor(new WaitCondition() { @Override
				public boolean isValid() {
				return (getCurrentPrayerPoints()>10);
			}},5000);
		}
		
	}
	
	public static void prayerHandler(){
		if (TaskManager.currentTask.getProtectionPrayer()!=99 && getCurrentPrayerPoints()>50 && 
				Players.getLocal().getPrayerIcon()!=TaskManager.currentTask.getProtectionPrayer()){
			if (!Tabs.getCurrent().equals(Tabs.PRAYER)){
				Tabs.PRAYER.open();
				Waiter.waitFor(new WaitCondition() { @Override
					public boolean isValid() {
					return (Tabs.getCurrent().equals(Tabs.PRAYER));
				}},5000);
			}
			int widgetChild = 19 - TaskManager.currentTask.getProtectionPrayer();
			System.out.println("Time to pray.");
			Widgets.get(271).getChild(7).getChild(widgetChild).click(true);
			Waiter.waitFor(new WaitCondition() { @Override
				public boolean isValid() {
				return (Players.getLocal().getPrayerIcon()==TaskManager.currentTask.getProtectionPrayer());
			}},5000);
			Tabs.INVENTORY.open();
			Waiter.waitFor(new WaitCondition() { @Override
				public boolean isValid() {
				return (Tabs.getCurrent().equals(Tabs.INVENTORY));
			}},5000);
		}
	}
	
	private boolean monsterInRadius(NPC n){
		return Calculations.distance(n, TaskManager.currentTask.getPathToMonster().getFinalDestination())<TaskManager.currentTask.getRadius();
	}

	public boolean monsterMatchesId(NPC whatsAttackingMe){
		boolean unknown = true;
		for (int knownMonster : TaskManager.currentTask.getMonsterIds()){
			if (knownMonster == whatsAttackingMe.getId()){
				unknown = false;
			}
		}
		return !unknown;
	}

	public boolean shouldIRun(NPC whatsAttackingMe){
		boolean isItScary = true;
		if (monsterInRadius(whatsAttackingMe)){
			return false;
		}
		for (int knownMonster : TaskManager.currentTask.getMonsterIds()){
			if (knownMonster == whatsAttackingMe.getId()){
				isItScary = false;
			}
		}
		return isItScary;
	}


	//Strategy
	@Override
	public void run() {
		prayerHandler();
		WalkingStrategy.toggleRun();
		eat();
		potionHandler();
		if (Settings.get(300)/10>=DefSlayer.specialNeeded && Settings.get(301)==0 && Players.getLocal().getInteracting()!=null && Players.getLocal().getInteracting().getHpPercent()>50){
			Tabs.ATTACK.open();
			Waiter.waitFor(new WaitCondition() { @Override
				public boolean isValid() {
				return (Tabs.getCurrent().equals(Tabs.ATTACK));
			}},5000);
			Widgets.get(884).getChild(34).click(true);
			Waiter.waitFor(new WaitCondition() { @Override
				public boolean isValid() {
				return (Settings.get(301)==1);
			}},5000);
			Tabs.INVENTORY.open();
			Waiter.waitFor(new WaitCondition() { @Override
				public boolean isValid() {
				return (Tabs.getCurrent().equals(Tabs.INVENTORY));
			}},5000);
		}
		if (Players.getLocal().getInteracting()!=null){
			if (TaskManager.currentTask.getFinishingItem()!=0 && Players.getLocal().getInteracting().getHpPercent()<2){
				Inventory.getItem(TaskManager.currentTask.getFinishingItem()).getWidgetChild().interact(TaskManager.currentTask.getFinishingString());
				Waiter.waitFor(new WaitCondition() { @Override
					public boolean isValid() {
					return (Players.getLocal().getInteracting()==null);
				}},10000);
			}else{
				Time.sleep(600,700);
			}
		}
		else{
			System.out.println("Time to find a target!");
			NPC choosing = null;
			int lastDist = 100;
			for (NPC test : NPCs.getLoaded()){
				if (monsterMatchesId(test) && test.getInteracting()==null && Calculations.distanceTo(test)<lastDist && test.getHpPercent()>90 && monsterInRadius(test)){
					choosing = test;
					lastDist = (int) Calculations.distanceTo(test);
				}
				if (test.getInteracting()!=null && test.getInteracting().equals(Players.getLocal())){
					System.out.println("Hot damn im under attack!");
					if (shouldIRun(test)){
						System.out.println("I don't know what this monster is, im out of here!");
						Walking.findPath(TaskManager.currentTask.getPathToMonster().getFinalDestination()).getNext().clickOnMap();
						Time.sleep(1000,1500);
					}
					else if (monsterInRadius(test)){
						System.out.println("I know what this is, Ima fuck it up!");
						choosing = test;
						break;
					}
				}
			}
			final NPC target = choosing;
			if (target==null){
				Walking.findPath(TaskManager.currentTask.getPathToMonster().getFinalDestination()).getNext().clickOnMap();
				Time.sleep(100);
			}
			else{
				if (!target.isOnScreen()){
					Walking.findPath(target).getNext().clickOnMap();
					Waiter.waitFor(new WaitCondition() { @Override
						public boolean isValid() {
						return (target.isOnScreen() || !Players.getLocal().isMoving());
					}},6000);
				}
				if(target.interact("Attack")){
					Waiter.waitFor(new WaitCondition() { @Override
						public boolean isValid() {
						return (Players.getLocal().getInteracting()!=null);
					}},6000);
				}
			}
		}			
	}

	@Override
	public boolean validate() {
		return (TaskManager.getCurrentTask()!=null && !WalkingStrategy.needBank() && TaskManager.currentTask.atMonster());
	}
	@Override
	public void messageReceived(MessageEvent me) {
		if (me.getMessage().contains("resistance to dragonfire is about to run out")){
			Inventory.getItem(TaskManager.antifirePot.getId()).getWidgetChild().click(true);
		}
		if (me.getMessage().contains("resistance to dragonfire has run out")){
			Inventory.getItem(TaskManager.antifirePot.getId()).getWidgetChild().click(true);
		}
		if (me.getMessage().contains("absorbs most of the dragon")){
			Inventory.getItem(TaskManager.antifirePot.getId()).getWidgetChild().click(true);
		}
		
	}

}
