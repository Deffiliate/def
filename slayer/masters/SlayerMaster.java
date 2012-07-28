package def.slayer.masters;

public class SlayerMaster {
	private String masterName;
	private int masterId;
	private int widgetChild;
	public String getMasterName() {
		return masterName;
	}
	public int getMasterId() {
		return masterId;
	}
	public int getWidgetChild() {
		return widgetChild;
	}
	public SlayerMaster(String masterName, int masterId, int widgetChild){
		this.masterName = masterName;
		this.masterId = masterId;
		this.widgetChild = widgetChild;
	}
}
