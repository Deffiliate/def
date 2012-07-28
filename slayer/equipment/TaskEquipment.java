package def.slayer.equipment;

public class TaskEquipment {
	private int headSlot;
	private int capeSlot;
	private int neckSlot;
	private int weaponSlot;
	private int torsoSlot;
	private int shieldSlot;
	private int legsSlot;
	private int glovesSlot;
	private int bootsSlot;
	private int quiverSlot;
	private int ringSlot;
	public int getHeadSlot() {
		return headSlot;
	}
	public void setHeadSlot(int headSlot) {
		this.headSlot = headSlot;
	}
	public int getCapeSlot() {
		return capeSlot;
	}
	public void setCapeSlot(int capeSlot) {
		this.capeSlot = capeSlot;
	}
	public int getNeckSlot() {
		return neckSlot;
	}
	public void setNeckSlot(int neckSlot) {
		this.neckSlot = neckSlot;
	}
	public int getWeaponSlot() {
		return weaponSlot;
	}
	public void setWeaponSlot(int weaponSlot) {
		this.weaponSlot = weaponSlot;
	}
	public int getTorsoSlot() {
		return torsoSlot;
	}
	public void setTorsoSlot(int torsoSlot) {
		this.torsoSlot = torsoSlot;
	}
	public int getShieldSlot() {
		return shieldSlot;
	}
	public void setShieldSlot(int shieldSlot) {
		this.shieldSlot = shieldSlot;
	}
	public int getLegsSlot() {
		return legsSlot;
	}
	public void setLegsSlot(int legsSlot) {
		this.legsSlot = legsSlot;
	}
	public int getGlovesSlot() {
		return glovesSlot;
	}
	public void setGlovesSlot(int glovesSlot) {
		this.glovesSlot = glovesSlot;
	}
	public int getBootsSlot() {
		return bootsSlot;
	}
	public void setBootsSlot(int bootsSlot) {
		this.bootsSlot = bootsSlot;
	}
	public int getQuiverSlot() {
		return quiverSlot;
	}
	public void setQuiverSlot(int quiverSlot) {
		this.quiverSlot = quiverSlot;
	}
	public int getRingSlot() {
		return ringSlot;
	}
	public void setRingSlot(int ringSlot) {
		this.ringSlot = ringSlot;
	}
	public static TaskEquipment generateSet (TaskEquipment mainGear, TaskEquipment taskGear){
		TaskEquipment generatedSet = mainGear;
		if (taskGear.getBootsSlot() != 0){
			generatedSet.setBootsSlot(taskGear.getBootsSlot());
		}
		if (taskGear.getCapeSlot()  != 0){
			generatedSet.setCapeSlot(taskGear.getCapeSlot());
		}
		if (taskGear.getGlovesSlot()!= 0){
			generatedSet.setGlovesSlot(taskGear.getGlovesSlot());
		}
		if (taskGear.getHeadSlot() != 0){
			generatedSet.setHeadSlot(taskGear.getHeadSlot());
		}
		if (taskGear.getLegsSlot() != 0){
			generatedSet.setLegsSlot(taskGear.getLegsSlot());
		}
		if (taskGear.getNeckSlot() != 0){
			generatedSet.setNeckSlot(taskGear.getNeckSlot());
		}
		if (taskGear.getQuiverSlot() != 0){
			generatedSet.setQuiverSlot(taskGear.getQuiverSlot());
		}
		if (taskGear.getShieldSlot() != 0){
			generatedSet.setShieldSlot(taskGear.getShieldSlot());
		}
		if (taskGear.getTorsoSlot() != 0){
			generatedSet.setTorsoSlot(taskGear.getTorsoSlot());
		}
		if (taskGear.getWeaponSlot() != 0){
			generatedSet.setWeaponSlot(taskGear.getWeaponSlot());
		}
		if (taskGear.getRingSlot() != 0){
			generatedSet.setRingSlot(taskGear.getRingSlot());
		}
		return generatedSet;
	}
	
	
	public TaskEquipment (int headSlot, int capeSlot, int neckSlot, int weaponSlot, int torsoSlot, int shieldSlot, int legsSlot, int glovesSlot, int bootsSlot, int quiverSlot, int ringSlot){
		this.headSlot = headSlot;
		this.capeSlot = capeSlot;
		this.neckSlot = neckSlot;
		this.weaponSlot = weaponSlot;
		this.torsoSlot = torsoSlot;
		this.shieldSlot = shieldSlot;
		this.legsSlot = legsSlot;
		this.glovesSlot = glovesSlot;
		this.bootsSlot = bootsSlot;
		this.quiverSlot = quiverSlot;
		this.ringSlot = ringSlot;
	}
	
}
