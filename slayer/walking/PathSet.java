package def.slayer.walking;

import org.powerbot.game.api.wrappers.Tile;


public class PathSet {
	private PathLeg [] pathToDest;
	private String destinationString;
	private Tile finalDestination;
	public PathLeg[] getPathToDest() {
		return pathToDest;
	}
	public void setPathToDest(PathLeg[] pathToDest) {
		this.pathToDest = pathToDest;
	}
	public String getDestinationString() {
		return destinationString;
	}
	public void setDestinationString(String destinationString) {
		this.destinationString = destinationString;
	}
	public Tile getFinalDestination(){
		return finalDestination;
	}
	public void getToStart(){
	};
	public PathLeg getSpotInPath(){
		int i = 0;
		for (PathLeg cp: pathToDest){
			if (cp.atStart() && !cp.obstacleComplete()){
				return cp;
			}
			if (cp.atObstacleStart()){
				return cp;
			}
			i++;
		}
		return null;
	}
	public PathLeg getMidwayPath(){
		int i = 0;
		for (PathLeg cp: pathToDest){
			if (WalkingStrategy.canReachPointInPath(cp.getPath())){
				return cp;
			}
			i++;
		}
		return null;
	}
	
	public boolean atStart(){
		return getSpotInPath()!=null;
	}
	public PathSet(PathLeg [] pathToDest, String destinationString, Tile finalDestination){
		this.pathToDest = pathToDest;
		this.destinationString = destinationString;
		this.finalDestination = finalDestination;
	}
}
