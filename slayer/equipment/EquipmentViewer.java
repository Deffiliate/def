package def.slayer.equipment;
import java.util.ArrayList;
import java.util.List;

import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.interactive.Player;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

import def.slayer.tasks.TaskManager;

/**
 * Uses appearance and widgets to get ids of the currently worn equipment
 * @author Strikeskids
 *
 */
public enum EquipmentViewer {
	HEAD(0, 6), CAPE(1, 9), NECK(2, 12), WEAPON(3, 15), TORSO(4, 18), SHIELD(5, 21), LEGS(7, 24), GLOVES(9, 27), BOOTS(
			10, 30), RING(-1, 33), ARROWS(-1, 36), AURA(14, 45);

	private final int appear, child;

	private EquipmentViewer(int app, int child) {
		this.appear = app;
		this.child = child;
	}

	public int getItemId() {
		if (appear >= 0) {
			int[] clothes = getAppearance();
			if (clothes != null && appear < clothes.length)
				return clothes[appear];
		}
		Widget equipWidget = getWidget();
		if (equipWidget != null) {
			WidgetChild curChild = equipWidget.getChild(child);
			if (curChild != null && curChild.validate())
				return curChild.getChildId();
		}
		return -1;
	}

	public WidgetChild getWidgetChild() {
		Widget equipWidget = getWidget();
		if (equipWidget != null) {
			WidgetChild curChild = equipWidget.getChild(child);
			if (curChild != null)
				return curChild;
		}
		return null;
	}

	// STATIC

	private static final int WIDGET_ID = 387;

	public static int[] getAppearance() {
		Player me = Players.getLocal();
		if (me == null)
			return null;
		return me.getAppearance();
	}

	public static Widget getWidget() {
		Widget w = Widgets.get(WIDGET_ID);
		if (w == null || !w.validate())
			return null;
		return w;
	}

	public static boolean equipmentContains(int... ids) {
		for (int id : ids) {
			for (EquipmentViewer cur : values()) {
				if (cur.getItemId() == id)
					return true;
			}
		}
		return false;
	}
	
	public static int [] getNeededEquipItems() {
		List<Integer> x = new ArrayList<Integer>();
		for (int id : TaskManager.getEquipIds()) {
			boolean contain = false;
			for (int cur : getAppearance()) {
				if (cur == id){
					contain = true;
				}
			}
			if (!contain && id!=0){
				x.add(id);
			}
		}
		int [] list = new int [x.size()];
		for (int i = 0; i<list.length; i++){
			list [i] = x.get(i);
		}
		return list;
	}

	public static boolean appearanceContains(int... ids) {
		int[] appear = getAppearance();
		if (appear == null)
			return false;
		for (int id : ids) {
			if (arrayContains(appear, id))
				return true;
		}
		return false;
	}

	
	public static boolean arrayContainsString(String[] arr, String in, boolean caseInsensitive) {
		String look = in;
		if (caseInsensitive)
			look = look.toUpperCase();
		for (String s : arr) {
			if (s == null)
				continue;
			if (caseInsensitive)
				s = s.toUpperCase();
			if (stringInString(s, look))
				return true;
		}
		return false;
	}
	
	public static boolean stringInString(String a, String b) {
		if (isEmptyString(a)||isEmptyString(b)) return a==b;
		return a.contains(b)||b.contains(a);
	}
	
	public static boolean isEmptyString(String s) {
		return s==null||s.length()==0;
	}

	public static boolean arrayContains(int[] array, int look) {
		for (int i : array) {
			if (i == look)
				return true;
		}
		return false;
	}
}