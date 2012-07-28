package def.slayer.banking;

import org.powerbot.game.api.methods.tab.Inventory;

public class BankItem {

	private int [] itemIds;
	private int amount;
	public int [] getId() {
		return itemIds;
	}
	public void setId(int [] itemIds) {
		this.itemIds = itemIds;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public BankItem (int [] itemIds,int amount){
		this.itemIds = itemIds;
		this.amount = amount;
	}
	
	public static boolean inventoryContains(BankItem [] items){
		for (BankItem i : items){
			if (Inventory.getCount(true,i.getId())< i.getAmount())
				return false;
		}
		return true;
	}
	
	public static boolean inventoryContains(BankItem item){
		return (inventoryContains(new BankItem[]{item}));
	}
	
}
