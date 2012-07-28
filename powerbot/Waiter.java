package def.powerbot;

import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;

import def.slayer.killing.KillStrategy;


public class Waiter {

	public static void waitFor(final WaitCondition waitCondition, final int timeOut) {
		waitFor(waitCondition, timeOut, 100);
	}
	
	public static void waitFor(final WaitCondition waitCondition, final int timeOut, final int sleep) {
		Timer timer = new Timer(timeOut);
		while (timer.isRunning()) {
			Time.sleep(sleep);
			KillStrategy.eat();
			if (waitCondition.isValid())
				return;
		}
	}
}
