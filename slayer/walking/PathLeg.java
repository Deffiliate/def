package def.slayer.walking;

import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class PathLeg {
	private TilePath path;
	private int obstacleId;
	private String obstacleAction;
	private String destinationString;
	private Tile destination;
	private int obstacleTimeout;
	public TilePath getPath() {
		return path;
	}
	public int getObstacleId() {
		return obstacleId;
	}
	public String getObstacleAction() {
		return obstacleAction;
	}
	public int getObstacleTimeout() {
		return obstacleTimeout;
	}
	public String getDestinationString() {
		return destinationString;
	}
	public Tile getDestination() {
		return destination;
	}
	public boolean atStart(){
		return path.toArray()[0].isOnMap() &&  path.toArray()[0].getPlane() == Players.getLocal().getPlane();
	}
	public boolean atObstacleStart(){
		SceneObject obs = SceneEntities.getNearest(obstacleId);
		return !obstacleComplete() && obs!= null && obs.isOnScreen() && Calculations.distanceTo(obs)<6;
	}
	public boolean obstacleComplete(){
		return Players.getLocal().getLocation().equals(destination);
	}
	
	public boolean doObstacle(){
		SceneObject objo = SceneEntities.getNearest(obstacleId);
		if (objo.isOnScreen()){
			objo.interact(obstacleAction);
			waitFor(new waitCondition() { @Override
				public boolean isValid() {
				return (obstacleComplete());
			}},10000);
			return true;
		}
		else{
			if (objo.getLocation().isOnMap()){
				objo.getLocation().clickOnMap();
			}
			Camera.turnTo(objo);
		}
		return false;
	}
	
	public static abstract interface waitCondition {
		public boolean isValid();
	}
	public static boolean waitFor(final waitCondition waitCondition, final long timeOut) {
		Timer timer = new Timer(timeOut);
		while (timer.isRunning()) {
			Time.sleep(100);
			if (waitCondition.isValid())return true;
		}
		return false;
	}
	
	public PathLeg (TilePath path, int obstacleId, String obstacleAction, String destinationString, int obstacleTimeout, Tile destination){
		this.path = path;
		this.obstacleId = obstacleId;
		this.obstacleAction = obstacleAction;
		this.destinationString = destinationString;
		this.obstacleTimeout = obstacleTimeout;
		this.destination = destination;
	}
	
}
