package def.slayer.walking;

import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.map.TilePath;
import org.powerbot.concurrent.Task;

import def.slayer.DefSlayer;
import def.slayer.banking.BankItem;
import def.slayer.equipment.EquipmentViewer;
import def.slayer.killing.KillStrategy;
import def.slayer.tasks.TaskManager;
import def.powerbot.*;

public class WalkingStrategy extends Strategy implements Task {

	//Variabls
	public static int runAmmount = Random.nextInt(50, 90);
	
	// Declare Tilepaths

	// Banks

	public static TilePath toCastleWarsChestp = new TilePath(new Tile[] {
			new Tile(2442, 3088, 0), new Tile(2444, 3083, 0), });

	public static TilePath toDaemonheimBankp = new TilePath(new Tile[] {
			new Tile(3449, 3700, 0), new Tile(3449, 3707, 0),
			new Tile(3449, 3712, 0), new Tile(3449, 3716, 0),
			new Tile(3449, 3720, 0) });
	// Monsters

	// Brimhaven Dungeon
	public final static TilePath toBrimhavenGatesp = new TilePath(new Tile[] {
			new Tile(2918, 3176, 0), new Tile(2916, 3176, 0),
			new Tile(2913, 3176, 0), new Tile(2913, 3173, 0),
			new Tile(2910, 3173, 0), new Tile(2905, 3174, 0),
			new Tile(2902, 3173, 0), new Tile(2900, 3170, 0),
			new Tile(2898, 3167, 0), new Tile(2895, 3165, 0),
			new Tile(2892, 3164, 0), new Tile(2889, 3163, 0),
			new Tile(2886, 3163, 0), new Tile(2883, 3163, 0),
			new Tile(2881, 3162, 0), new Tile(2878, 3162, 0),
			new Tile(2874, 3163, 0), new Tile(2870, 3163, 0),
			new Tile(2867, 3163, 0), new Tile(2864, 3162, 0),
			new Tile(2861, 3163, 0), new Tile(2858, 3163, 0),
			new Tile(2855, 3161, 0), new Tile(2852, 3159, 0),
			new Tile(2849, 3159, 0), new Tile(2846, 3158, 0),
			new Tile(2843, 3157, 0), new Tile(2840, 3157, 0),
			new Tile(2837, 3156, 0), new Tile(2834, 3156, 0),
			new Tile(2831, 3156, 0), new Tile(2828, 3158, 0),
			new Tile(2827, 3161, 0), new Tile(2824, 3163, 0),
			new Tile(2823, 3166, 0), new Tile(2822, 3169, 0),
			new Tile(2819, 3171, 0), new Tile(2818, 3174, 0),
			new Tile(2817, 3177, 0), new Tile(2816, 3180, 0),
			new Tile(2817, 3183, 0) });

	public final static TilePath toSanibochp = new TilePath(new Tile[] {
			new Tile(2814, 3183, 0), new Tile(2811, 3183, 0),
			new Tile(2808, 3183, 0), new Tile(2805, 3183, 0),
			new Tile(2802, 3182, 0), new Tile(2799, 3180, 0),
			new Tile(2796, 3180, 0), new Tile(2793, 3179, 0),
			new Tile(2790, 3179, 0), new Tile(2787, 3180, 0),
			new Tile(2785, 3183, 0), new Tile(2782, 3185, 0),
			new Tile(2779, 3186, 0), new Tile(2776, 3186, 0),
			new Tile(2772, 3186, 0), new Tile(2769, 3186, 0),
			new Tile(2766, 3186, 0), new Tile(2765, 3184, 0),
			new Tile(2764, 3181, 0), new Tile(2760, 3180, 0),
			new Tile(2757, 3179, 0), new Tile(2756, 3176, 0),
			new Tile(2754, 3173, 0), new Tile(2751, 3172, 0),
			new Tile(2749, 3169, 0), new Tile(2751, 3166, 0),
			new Tile(2751, 3163, 0), new Tile(2751, 3160, 0),
			new Tile(2747, 3157, 0), new Tile(2747, 3154, 0),
			new Tile(2745, 3152, 0) });

	public final static TilePath toBrimhavenVinep = new TilePath(new Tile[] {
			new Tile(2710, 9564, 0), new Tile(2707, 9564, 0),
			new Tile(2704, 9564, 0), new Tile(2701, 9564, 0),
			new Tile(2698, 9564, 0), new Tile(2695, 9564, 0),
			new Tile(2692, 9564, 0), new Tile(2691, 9564, 0) });

	public final static TilePath toBrimhavenSteppingStonep = new TilePath(
			new Tile[] { new Tile(2686, 9564, 0), new Tile(2683, 9564, 0),
					new Tile(2680, 9564, 0), new Tile(2677, 9564, 0),
					new Tile(2675, 9565, 0), new Tile(2674, 9567, 0),
					new Tile(2673, 9569, 0), new Tile(2672, 9571, 0),
					new Tile(2670, 9572, 0), new Tile(2667, 9572, 0),
					new Tile(2665, 9570, 0), new Tile(2663, 9569, 0),
					new Tile(2661, 9568, 0), new Tile(2659, 9567, 0),
					new Tile(2657, 9566, 0), new Tile(2654, 9566, 0),
					new Tile(2652, 9564, 0), new Tile(2651, 9562, 0),
					new Tile(2649, 9562, 0), });
	public final static TilePath toBrimhavenVine2p = new TilePath(new Tile[] {
			new Tile(2647, 9556, 0), new Tile(2647, 9553, 0),
			new Tile(2647, 9550, 0), new Tile(2647, 9547, 0),
			new Tile(2647, 9544, 0), new Tile(2647, 9541, 0),
			new Tile(2647, 9538, 0), new Tile(2646, 9535, 0),
			new Tile(2644, 9533, 0), new Tile(2642, 9531, 0),
			new Tile(2641, 9528, 0), new Tile(2641, 9525, 0),
			new Tile(2641, 9522, 0), new Tile(2641, 9519, 0),
			new Tile(2642, 9517, 0), new Tile(2643, 9515, 0),
			new Tile(2645, 9513, 0), new Tile(2646, 9511, 0),
			new Tile(2648, 9510, 0), new Tile(2649, 9508, 0),
			new Tile(2650, 9506, 0), new Tile(2652, 9504, 0),
			new Tile(2654, 9503, 0), new Tile(2656, 9501, 0),
			new Tile(2658, 9499, 0), new Tile(2660, 9497, 0),
			new Tile(2661, 9495, 0), new Tile(2662, 9493, 0),
			new Tile(2664, 9492, 0), new Tile(2688, 9451, 0),
			new Tile(2665, 9490, 0), new Tile(2665, 9487, 0),
			new Tile(2666, 9484, 0), new Tile(2668, 9482, 0),
			new Tile(2670, 9481, 0), new Tile(2672, 9480, 0),
			new Tile(2674, 9479, 0) });
	public final static TilePath toBrimhavenVine3p = new TilePath(new Tile[] {
			new Tile(2676, 9479, 0), new Tile(2679, 9479, 0),
			new Tile(2681, 9478, 0), new Tile(2683, 9477, 0),
			new Tile(2686, 9477, 0), new Tile(2689, 9478, 0),
			new Tile(2691, 9480, 0), new Tile(2692, 9482, 0),
			new Tile(2693, 9482, 0) });
	public final static TilePath toBrimhavenDragonsp = new TilePath(new Tile[] {
			new Tile(2695, 9482, 0), new Tile(2698, 9482, 0),
			new Tile(2701, 9482, 0), new Tile(2742, 9482, 0),
			new Tile(2703, 9482, 0), new Tile(2705, 9481, 0),
			new Tile(2707, 9479, 0), new Tile(2708, 9477, 0),
			new Tile(2708, 9474, 0), new Tile(2708, 9471, 0),
			new Tile(2708, 9468, 0), new Tile(2708, 9465, 0),
			new Tile(2708, 9462, 0), new Tile(2708, 9459, 0),
			new Tile(2708, 9456, 0), new Tile(2708, 9453, 0),
			new Tile(2708, 9412, 0), new Tile(2708, 9451, 0),
			new Tile(2708, 9448, 0), new Tile(2710, 9446, 0),
			new Tile(2713, 9446, 0), new Tile(2716, 9445, 0),
			new Tile(2718, 9443, 0), new Tile(2720, 9441, 0),
			new Tile(2717, 9441, 0), new Tile(2716, 9441, 0) });

	// Slayer Tower
	public final static TilePath toTempleGatesp = new TilePath(new Tile[] {
			new Tile(3212, 3377, 0), new Tile(3211, 3381, 0),
			new Tile(3210, 3385, 0), new Tile(3210, 3388, 0),
			new Tile(3211, 3393, 0), new Tile(3211, 3397, 0),
			new Tile(3211, 3402, 0), new Tile(3210, 3406, 0),
			new Tile(3210, 3410, 0), new Tile(3210, 3413, 0),
			new Tile(3210, 3417, 0), new Tile(3211, 3421, 0),
			new Tile(3214, 3423, 0), new Tile(3217, 3424, 0),
			new Tile(3220, 3425, 0), new Tile(3223, 3426, 0),
			new Tile(3228, 3429, 0), new Tile(3232, 3429, 0),
			new Tile(3236, 3430, 0), new Tile(3239, 3429, 0),
			new Tile(3243, 3429, 0), new Tile(3246, 3429, 0),
			new Tile(3249, 3429, 0), new Tile(3252, 3429, 0),
			new Tile(3255, 3428, 0), new Tile(3259, 3428, 0),
			new Tile(3262, 3428, 0), new Tile(3266, 3428, 0),
			new Tile(3269, 3428, 0), new Tile(3271, 3429, 0),
			new Tile(3275, 3428, 0), new Tile(3278, 3428, 0),
			new Tile(3281, 3428, 0), new Tile(3284, 3431, 0),
			new Tile(3285, 3435, 0), new Tile(3286, 3440, 0),
			new Tile(3286, 3444, 0), new Tile(3287, 3448, 0),
			new Tile(3287, 3452, 0), new Tile(3286, 3456, 0),
			new Tile(3289, 3459, 0), new Tile(3292, 3460, 0),
			new Tile(3298, 3462, 0), new Tile(3301, 3462, 0),
			new Tile(3304, 3462, 0), new Tile(3307, 3463, 0),
			new Tile(3310, 3464, 0), new Tile(3313, 3466, 0),
			new Tile(3315, 3467, 0), new Tile(3318, 3467, 0) });
	public final static TilePath toTempleDungeonp = new TilePath(new Tile[] {
			new Tile(3320, 3468, 0), new Tile(3322, 3468, 0),
			new Tile(3326, 3469, 0), new Tile(3329, 3470, 0),
			new Tile(3330, 3474, 0), new Tile(3331, 3477, 0),
			new Tile(3334, 3479, 0), new Tile(3338, 3479, 0),
			new Tile(3342, 3481, 0), new Tile(3346, 3483, 0),
			new Tile(3347, 3486, 0), new Tile(3348, 3491, 0),
			new Tile(3351, 3492, 0), new Tile(3355, 3489, 0),
			new Tile(3358, 3485, 0), new Tile(3361, 3482, 0),
			new Tile(3364, 3483, 0), new Tile(3368, 3484, 0),
			new Tile(3371, 3486, 0), new Tile(3374, 3487, 0),
			new Tile(3378, 3484, 0), new Tile(3382, 3482, 0),
			new Tile(3384, 3484, 0), new Tile(3388, 3485, 0),
			new Tile(3392, 3485, 0), new Tile(3395, 3485, 0),
			new Tile(3398, 3486, 0), new Tile(3401, 3486, 0),
			new Tile(3403, 3487, 0), new Tile(3404, 3490, 0),
			new Tile(3404, 3494, 0), new Tile(3404, 3498, 0),
			new Tile(3404, 3501, 0), new Tile(3405, 3506, 0) });
	public final static TilePath toTempleDoor1p = new TilePath(new Tile[] {
			new Tile(3405, 9906, 0), new Tile(3405, 9900, 0),
			new Tile(3405, 9895, 0) });
	public final static TilePath toTempleDoor2p = new TilePath(new Tile[] {
			new Tile(3404, 9889, 0), new Tile(3417, 9883, 0),
			new Tile(3419, 9892, 0), new Tile(3431, 9897, 0) });
	public final static TilePath toCanifisPortalp = new TilePath(new Tile[] {
			new Tile(3440, 9897, 0), new Tile(3440, 9887, 0) });
	public final static TilePath toSlayerTowerp = new TilePath(new Tile[] {
			new Tile(3427, 3484, 0), new Tile(3431, 3484, 0),
			new Tile(3435, 3484, 0), new Tile(3439, 3485, 0),
			new Tile(3443, 3485, 0), new Tile(3447, 3487, 0),
			new Tile(3451, 3488, 0), new Tile(3451, 3492, 0),
			new Tile(3448, 3495, 0), new Tile(3445, 3498, 0),
			new Tile(3443, 3502, 0), new Tile(3442, 3506, 0),
			new Tile(3439, 3509, 0), new Tile(3436, 3512, 0),
			new Tile(3435, 3515, 0), new Tile(3432, 3518, 0),
			new Tile(3430, 3521, 0), new Tile(3429, 3524, 0),
			new Tile(3429, 3528, 0), new Tile(3429, 3531, 0),
			new Tile(3429, 3535, 0) });
	public final static TilePath toSlayerTowerFloor2p = new TilePath(
			new Tile[] { new Tile(3428, 3537, 0), new Tile(3424, 3537, 0),
					new Tile(3420, 3537, 0), new Tile(3416, 3537, 0),
					new Tile(3412, 3537, 0), new Tile(3411, 3540, 0),
					new Tile(3411, 3546, 0), new Tile(3414, 3546, 0),
					new Tile(3418, 3546, 0), new Tile(3422, 3545, 0),
					new Tile(3426, 3545, 0), new Tile(3427, 3551, 0),
					new Tile(3427, 3554, 0), new Tile(3423, 3556, 0),
					new Tile(3419, 3557, 0), new Tile(3415, 3557, 0),
					new Tile(3413, 3561, 0), new Tile(3412, 3565, 0),
					new Tile(3411, 3570, 0), new Tile(3415, 3571, 0),
					new Tile(3419, 3573, 0), new Tile(3423, 3573, 0),
					new Tile(3428, 3573, 0), new Tile(3431, 3573, 0),
					new Tile(3435, 3573, 0), new Tile(3439, 3573, 0),
					new Tile(3443, 3573, 0), new Tile(3447, 3574, 0),
					new Tile(3446, 3569, 0), new Tile(3446, 3565, 0),
					new Tile(3446, 3561, 0), new Tile(3443, 3558, 0),
					new Tile(3439, 3558, 0), new Tile(3435, 3558, 0),
					new Tile(3433, 3554, 0), new Tile(3433, 3550, 0),
					new Tile(3437, 3548, 0), new Tile(3438, 3544, 0),
					new Tile(3438, 3540, 0), new Tile(3438, 3538, 0) });
	// Slayer Tower Rooms
	public final static TilePath toSlayerTowerBloodveldsp = new TilePath(
			new Tile[] { new Tile(3433, 3537, 1), new Tile(3440, 3536, 1),
					new Tile(3447, 3536, 1), new Tile(3447, 3544, 1),
					new Tile(3447, 3552, 1), new Tile(3447, 3558, 1),
					new Tile(3444, 3565, 1), new Tile(3440, 3570, 1),
					new Tile(3435, 3571, 1), new Tile(3431, 3573, 1),
					new Tile(3426, 3573, 1), new Tile(3420, 3573, 1),
					new Tile(3412, 3571, 1), new Tile(3412, 3561, 1),
					new Tile(3419, 3561, 1) });
	// Ancient Caverns
	public static TilePath toWhirlpoolp = new TilePath(new Tile[] {
			new Tile(2519, 3570, 0), new Tile(2519, 3567, 0),
			new Tile(2522, 3565, 0), new Tile(2522, 3562, 0),
			new Tile(2524, 3559, 0), new Tile(2525, 3556, 0),
			new Tile(2526, 3553, 0), new Tile(2526, 3550, 0),
			new Tile(2525, 3547, 0), new Tile(2525, 3544, 0),
			new Tile(2525, 3541, 0), new Tile(2523, 3538, 0),
			new Tile(2522, 3535, 0), new Tile(2520, 3532, 0),
			new Tile(2518, 3529, 0), new Tile(2515, 3526, 0),
			new Tile(2514, 3523, 0), new Tile(2512, 3520, 0),
			new Tile(2512, 3517, 0), new Tile(2512, 3514, 0),
			new Tile(2512, 3511, 0) });
	public static TilePath toRoughStepsp = new TilePath(new Tile[] {
			new Tile(1763, 5365, 1), new Tile(1766, 5365, 1),
			new Tile(1767, 5366, 1), new Tile(1768, 5366, 1) });
	public static TilePath toWaterfiendsp = new TilePath(new Tile[] {
			new Tile(1772, 5366, 0), new Tile(1774, 5366, 0),
			new Tile(1774, 5362, 0), new Tile(1775, 5359, 0),
			new Tile(1775, 5354, 0), new Tile(1775, 5349, 0),
			new Tile(1775, 5344, 0), new Tile(1775, 5338, 0),
			new Tile(1770, 5338, 0), new Tile(1765, 5338, 0),
			new Tile(1760, 5338, 0), new Tile(1755, 5338, 0),
			new Tile(1750, 5338, 0), new Tile(1755, 5338, 0),
			new Tile(1750, 5340, 0), new Tile(1747, 5342, 0),
			new Tile(1742, 5342, 0), new Tile(1739, 5343, 0),
			new Tile(1738, 5348, 0), new Tile(1738, 5352, 0) });

	// Declare Pathlegs

	// Banks

	public final static PathLeg toCastleWarsChest = new PathLeg(
			toCastleWarsChestp, 0, "Climb", "Castle Wars chest", 10000,
			new Tile(2444, 3083, 0)) {
		@Override
		public boolean atObstacleStart() {
			return SceneEntities.getNearest(4483) != null
					&& SceneEntities.getNearest(4483).isOnScreen();
		}

		@Override
		public boolean obstacleComplete() {
			return SceneEntities.getNearest(4483) != null
					&& SceneEntities.getNearest(4483).isOnScreen();
		}
	};

	public final static PathLeg toDaemonheimBanker = new PathLeg(
			toDaemonheimBankp, 0, "Climb", "Fremennik banker", 10000, new Tile(
					3419, 3561, 0)) {
		@Override
		public boolean atObstacleStart() {
			return NPCs.getNearest(9710) != null
					&& NPCs.getNearest(9710).isOnScreen();
		}

		@Override
		public boolean obstacleComplete() {
			return NPCs.getNearest(9710) != null
					&& NPCs.getNearest(9710).isOnScreen();
		}
	};
	// Monsters

	// Brimhaven Dungeon
	public final static PathLeg toBrimhavenGates = new PathLeg(
			toBrimhavenGatesp, 24369, "Open", "Karamja gates", 5000, new Tile(
					3429, 3535, 0)) {
		@Override
		public boolean obstacleComplete() {
			return SceneEntities.getNearest(24373) != null
					&& SceneEntities.getNearest(24373).isOnScreen();
		}
	};
	public final static PathLeg toSaniboch = new PathLeg(toSanibochp, 5083,
			"Open", "Saniboch", 5000, new Tile(2713, 9564, 0)) {
		@Override
		public boolean atObstacleStart() {
			return NPCs.getNearest(1595) != null
					&& NPCs.getNearest(1595).isOnScreen();
		}
		@Override
		public boolean doObstacle() {
			if (Settings.get(393) == 0) {
				NPCs.getNearest(1595).interact("Pay");
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (Widgets.get(1191).validate());
					}
				}, 6000);
				Widgets.getContinue().click(true);
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (Settings.get(393) == 1);
					}
				}, 6000);
			}
			SceneEntities.getNearest(5083).interact("Enter");
			return true;
		}
	};
	public final static PathLeg toBrimhavenVines = new PathLeg(toBrimhavenVinep,
			5103, "Chop", "vines", 10000, new Tile(2689, 9564, 0)) {
	};
	public final static PathLeg toBrimhavenSteppingStones = new PathLeg(
			toBrimhavenSteppingStonep, 5110, "Jump", "stepping stones", 20000,
			new Tile(2647, 9557, 0)) {
	};
	public final static PathLeg toBrimhavenVines2 = new PathLeg(
			toBrimhavenVine2p, 5106, "Chop", "vines", 10000,
			new Tile(2676, 9479, 0)) {
	};
	public final static PathLeg toBrimhavenVines3 = new PathLeg(
			toBrimhavenVine3p, 5107, "Chop", "vines", 10000,
			new Tile(2695, 9482, 0)) {
	};
	public final static PathLeg toBrimhavenDragons = new PathLeg(
			toBrimhavenDragonsp, 25338, "Climb", "dragons lair", 7000,
			new Tile(2716, 9441, 0)) {
		@Override
		public boolean atObstacleStart() {
			return TaskManager.currentTask.atMonster();
		}

		@Override
		public boolean obstacleComplete() {
			return TaskManager.currentTask.atMonster();
		}
	};

	// Slayer Tower
	public final static PathLeg toTempleGates = new PathLeg(toTempleGatesp,
			24370, "Open", "temple gates", 3000, new Tile(3319, 3467, 0)) {
		@Override
		public boolean obstacleComplete() {
			return (SceneEntities.getNearest(24370) == null && Calculations
					.distanceTo(new Tile(3319, 3467, 0)) < 5);
		}
	};
	public final static PathLeg toTempleDungeon = new PathLeg(toTempleDungeonp,
			30571, "Open", "temple dungeon", 10000, new Tile(3405, 9906, 0)) {
		@Override
		public boolean atObstacleStart() {
			return SceneEntities.getNearest(30571, 30572) != null
					&& SceneEntities.getNearest(30571, 30572).isOnScreen();
		}

		@Override
		public boolean doObstacle() {
			if (SceneEntities.getNearest(30571) != null) {
				if (!SceneEntities.getNearest(30571).isOnScreen()) {
					Camera.turnTo(SceneEntities.getNearest(30571));
				}
				SceneEntities.getNearest(30571).interact("Open");
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (SceneEntities.getNearest(30572) != null);
					}
				}, 10000);
			}
			if (SceneEntities.getNearest(30572) != null) {
				if (!SceneEntities.getNearest(30572).isOnScreen()) {
					Camera.turnTo(SceneEntities.getNearest(30572));
				}
				SceneEntities.getNearest(30572).interact("down");
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (SceneEntities.getNearest(30572) == null);
					}
				}, 6000);
			}
			return true;
		}
	};
	public final static PathLeg toTempleDoor1 = new PathLeg(toTempleDoor1p,
			3444, "Open", "temple dungeon door 1", 10000, new Tile(3405, 9894,
					0));
	public final static PathLeg toTempleDoor2 = new PathLeg(toTempleDoor2p,
			3445, "Open", "temple dungeon door 2", 10000, new Tile(3432, 9897,
					0));
	public final static PathLeg toCanifisPortal = new PathLeg(toCanifisPortalp,
			3443, "through", "Canifis portal", 10000, new Tile(3424, 3484, 0));
	public final static PathLeg toSlayerTower = new PathLeg(toSlayerTowerp,
			4490, "Open", "Slayer Tower entrance", 10000, new Tile(3429, 3535,
					0)) {
		@Override
		public boolean obstacleComplete() {
			return SceneEntities.getNearest(4492) != null
					&& SceneEntities.getNearest(4492).isOnScreen();
		}
	};
	public final static PathLeg toSlayerTowerFloor2 = new PathLeg(
			toSlayerTowerFloor2p, 4493, "Climb", "Slayer Tower floor 2", 10000,
			new Tile(3433, 3537, 1));
	// Slayer Tower Rooms
	public final static PathLeg toSlayerTowerBloodvelds = new PathLeg(
			toSlayerTowerBloodveldsp, 0, "Climb", "Bloodvelds room", 10000,
			new Tile(3419, 3561, 1)) {
		@Override
		public boolean atObstacleStart() {
			return TaskManager.currentTask.atMonster();
		}

		@Override
		public boolean obstacleComplete() {
			return TaskManager.currentTask.atMonster();
		}
	};
	// Anvient Cavern
	public final static PathLeg toWhirlpool = new PathLeg(toWhirlpoolp, 67966,
			"Dive", "barbarian whirlpool", 24000, new Tile(1763, 5365, 1));
	public final static PathLeg toRoughSteps = new PathLeg(toRoughStepsp,
			25338, "Climb", "rough hewn steps", 13000, new Tile(1772, 5366, 0)) {
		@Override
		public boolean obstacleComplete() {
			return Calculations.distanceTo(new Tile(1772, 5366, 0)) < 4;
		}
	};
	public final static PathLeg toWaterfiendHideout = new PathLeg(
			toWaterfiendsp, 25338, "Climb", "waterfiend hideout", 7000,
			new Tile(1738, 5352, 0)) {
		@Override
		public boolean atObstacleStart() {
			return TaskManager.currentTask.atMonster();
		}

		@Override
		public boolean obstacleComplete() {
			return TaskManager.currentTask.atMonster();
		}
	};

	// Declare PathSets

	// Banks
	public final static PathSet toDaemonheimBank = new PathSet(
			new PathLeg[] { toDaemonheimBanker }, "Daemonheim bank", new Tile(
					3449, 3720, 0)) {
		@Override
		public void getToStart() {
			daemonheimTele();
		}
	};

	public final static PathSet toCastleWarsBank = new PathSet(
			new PathLeg[] { toCastleWarsChest }, "Castlewars bank", new Tile(
					2444, 3083, 0)) {
		@Override
		public void getToStart() {
			duellingRingTele(2);
		}
	};

	// Monsters
	public final static PathSet toGreaterDemons = new PathSet(new PathLeg[] {
			toTempleGates, toTempleDungeon, toTempleDoor1, toTempleDoor2,
			toCanifisPortal, toSlayerTower, toSlayerTowerFloor2,
			toSlayerTowerBloodvelds }, "Slayer Tower - Bloodvelds", new Tile(
			3449, 3720, 0)) {
		@Override
		public void getToStart() {
			lodestoneTele(1);
		}
	};
	public final static PathSet toWaterfiends = new PathSet(new PathLeg[] {
			toWhirlpool, toRoughSteps, toWaterfiendHideout },
			"Ancient Caverns - Waiterfiends", new Tile(1738, 5352, 0)) {
		@Override
		public void getToStart() {
			gamesNecklaceTele(2);
		}
	};
	
	//Brimhaven dungeon
	public final static PathSet toSteelDragons = new PathSet(new PathLeg[] {
			toBrimhavenGates, toSaniboch, toBrimhavenVines, toBrimhavenSteppingStones, toBrimhavenVines2, toBrimhavenVines3, toBrimhavenDragons },
			"Ancient Caverns - Waiterfiends", new Tile(2716, 9441, 0)) {
		@Override
		public void getToStart() {
			gloryNecklaceTele(2);
		}
	};
	
	// Slayer Tower
	public final static PathSet toBloodvelds = new PathSet(new PathLeg[] {
			toTempleGates, toTempleDungeon, toTempleDoor1, toTempleDoor2,
			toCanifisPortal, toSlayerTower, toSlayerTowerFloor2,
			toSlayerTowerBloodvelds }, "Slayer Tower - Bloodvelds", new Tile(
			3419, 3561, 1)) {
		@Override
		public void getToStart() {
			lodestoneTele(1);
		}
	};

	// Methods

	public static void toggleRun(){
		if(Walking.getEnergy()>runAmmount){
			Walking.setRun(true);
		}
		runAmmount = Random.nextInt(50, 90);
	}
	
	public static boolean canReachPointInPath(TilePath tp){
		for (Tile t : tp.toArray()) {
			while(Game.getClientState()==12){
				Time.sleep(100);
				System.out.println("Waiting for Runescape to load area.");
			}
			try{
				if (t!=null && t.validate() && Calculations.distanceTo(t)<20 && t.canReach()){
					return true;
				}
			}
			catch(Exception e){
			}
		}
		return false;
	}
	public static boolean pathIsLocal(final TilePath tp) {
		if (canReachPointInPath(tp)){
			return true;
		}
		System.out.println("For some reason we can't reach a point in our path. Waiting 10 seconds.");
		Waiter.waitFor(new WaitCondition() {
			@Override
			public boolean isValid() {
				return (canReachPointInPath(tp));
			}
		}, 12000);
		if (canReachPointInPath(tp)){
			return true;
		}
		return false;
	}

	public static void gamesNecklaceTele(int option) {
		// 1 = Burthope, 2 = Barb. Outpost, 3 = Gamer's Grotto
		Tile d = null;
		if (option == 2) {
			d = new Tile(2520, 3571, 0);
		}
		final Tile dest = d;
		Tabs.INVENTORY.open();
		Waiter.waitFor(new WaitCondition() {
			@Override
			public boolean isValid() {
				return (Tabs.getCurrent().equals(Tabs.INVENTORY));
			}
		}, 6000);
		Inventory.getItem(TaskManager.gamesNecklace.getId()).getWidgetChild()
				.interact("Rub");
		long timer = System.currentTimeMillis() + 5000;
		while (!Widgets.get(1188).validate() && System.currentTimeMillis()<timer){
			Keyboard.sendText(Integer.toString(option), false);
			Time.sleep(50,100);
		}
		if (Widgets.get(1188).validate()){
			Keyboard.sendText(Integer.toString(option), false);
			Waiter.waitFor(new WaitCondition() {
				@Override
				public boolean isValid() {
					return (Players.getLocal().getAnimation()==9603);
				}
			}, 7000);
			if (Players.getLocal().getAnimation()==9603){
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (Calculations.distanceTo(dest) < 10);
					}
				}, 16000);
			}
		}
	}

	public static void gloryNecklaceTele(int option) {
		// 1 = Edgeville, 2 = Karamja, 3 = Draynor, 4 = Al Kharid
		Tile d = null;
		if (option == 2) {
			d = new Tile(2918, 3176, 0);
		}
		final Tile dest = d;
		Tabs.INVENTORY.open();
		Waiter.waitFor(new WaitCondition() {
			@Override
			public boolean isValid() {
				return (Tabs.getCurrent().equals(Tabs.INVENTORY));
			}
		}, 6000);
		Inventory.getItem(TaskManager.glory.getId()).getWidgetChild()
				.interact("Rub");
		long timer = System.currentTimeMillis() + 5000;
		while (!Widgets.get(1188).validate() && System.currentTimeMillis()<timer){
			Keyboard.sendText(Integer.toString(option), false);
			Time.sleep(50,100);
		}
		if (Widgets.get(1188).validate()){
			Keyboard.sendText(Integer.toString(option), false);
			Waiter.waitFor(new WaitCondition() {
				@Override
				public boolean isValid() {
					return (Players.getLocal().getAnimation()==9603);
				}
			}, 7000);
			if (Players.getLocal().getAnimation()==9603){
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (Calculations.distanceTo(dest) < 10);
					}
				}, 16000);
			}
		}
	}

	public static void duellingRingTele(int option) {
		// 1 = Duel Arena, 2 = Castle Wars, 3 = Mob Armies, 4 = FOG
		Tile dest = null;
		if (option == 2) {
			dest = new Tile(2444, 3083, 0);
		}
		final Tile finalDest = dest;
		Tabs.INVENTORY.open();
		Waiter.waitFor(new WaitCondition() {
			@Override
			public boolean isValid() {
				return (Tabs.getCurrent().equals(Tabs.INVENTORY));
			}
		}, 6000);
		Inventory.getItem(TaskManager.ringOfDuelling.getId()).getWidgetChild()
				.interact("Rub");
		long timer = System.currentTimeMillis() + 5000;
		while (!Widgets.get(1188).validate() && System.currentTimeMillis()<timer){
			Keyboard.sendText(Integer.toString(option), false);
			Time.sleep(50,100);
		}
		if (Widgets.get(1188).validate()){
			Keyboard.sendText(Integer.toString(option), false);
			Waiter.waitFor(new WaitCondition() {
				@Override
				public boolean isValid() {
					return (Players.getLocal().getAnimation()==9603);
				}
			}, 7000);
			if (Players.getLocal().getAnimation()==9603){
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (Calculations.distanceTo(finalDest) < 10);
					}
				}, 16000);
			}
		}
	}

	public static void daemonheimTele() {
		Tabs.INVENTORY.open();
		Waiter.waitFor(new WaitCondition() {
			@Override
			public boolean isValid() {
				return (Tabs.getCurrent().equals(Tabs.INVENTORY));
			}
		}, 6000);
		Inventory.getItem(15707).getWidgetChild().interact("Teleport");
		Waiter.waitFor(new WaitCondition() {
			@Override
			public boolean isValid() {
				return (Calculations.distanceTo(new Tile(3449, 3700, 0)) < 15);
			}
		}, 16000);
	}

	public static void lodestoneTele(int destination) {
		// 1 = Varrock 2 = Taverly
		int widgetChild = 0;
		int lodestoneIdt = 0;
		if (destination == 1) {
			widgetChild = 51;
			lodestoneIdt = 69840;
		} else if (destination == 2) {
			widgetChild = 50;
			lodestoneIdt = 69839;
		}
		final int lodestoneId = lodestoneIdt;
		Tabs.MAGIC.open();
		Waiter.waitFor(new WaitCondition() {
			@Override
			public boolean isValid() {
				return (Tabs.getCurrent().equals(Tabs.MAGIC));
			}
		}, 6000);
		Widgets.get(430).getChild(39).click(true);
		Waiter.waitFor(new WaitCondition() {
			@Override
			public boolean isValid() {
				return (Widgets.get(1092).getChild(0).validate());
			}
		}, 6000);
		Widgets.get(1092).getChild(widgetChild).click(true);
		Waiter.waitFor(new WaitCondition() {
			@Override
			public boolean isValid() {
				return (SceneEntities.getNearest(lodestoneId) != null);
			}
		}, 25000);
	}

	public static boolean containInvItems() {
		for (BankItem i : TaskManager.currentTask.getNeededInventoryItems()) {
			if (Inventory.getCount(i.getId())==0) {
				System.out.println("Missing item: " + i.getId()[0]);
				return false;
			}
		}
		if (!TaskManager.getCurrentTask().atMonster() && TaskManager.getCurrentTask().getPathToMonster().getSpotInPath()==null && TaskManager.getCurrentTask().getPathToMonster().getMidwayPath()==null){
			System.out.println("Still need teleport item.");
			for (BankItem i : TaskManager.currentTask.getNeededTeleportItems()) {
				if (Inventory.getCount(i.getId())==0) {
					System.out.println("Missing item: " + i.getId()[0]);
					return false;
				}
			}
		}
		return true;
	}

	// Strategy
	@Override
	public void run() {
		toggleRun();
		PathLeg currentLeg = null;
		PathSet currentSet = null;
		if (TaskManager.currentTask == null) {
			currentSet = WalkingStrategy.toCastleWarsBank;
		} else {
			if (needBank()) {
				currentSet = WalkingStrategy.toCastleWarsBank;
			} else {
				currentSet = TaskManager.currentTask.getPathToMonster();
			}
		}
		currentLeg = currentSet.getSpotInPath();
		if (currentLeg == null) {
			if (TaskManager.currentTask != null
					&& !needBank()
					&& TaskManager.currentTask.getPathToMonster()
							.getFinalDestination().canReach()) {
				while (TaskManager.currentTask.getPathToMonster()
						.getFinalDestination().canReach()
						&& Calculations.distanceTo(TaskManager.currentTask
								.getPathToMonster().getFinalDestination()) > TaskManager.currentTask
								.getRadius()) {
					Walking.findPath(
							TaskManager.currentTask.getPathToMonster()
									.getFinalDestination()).getNext()
							.clickOnMap();
					Time.sleep(1000, 1500);
				}
				return;
			}
			else if (currentSet.getMidwayPath()!=null){
				currentLeg = currentSet.getMidwayPath();
				System.out.println("Current leg: " +currentLeg.getDestinationString());
			}
			else {
				currentSet.getToStart();
				return;
			}
		}
		while (!currentLeg.atObstacleStart() && !currentLeg.obstacleComplete()
				&& pathIsLocal(currentLeg.getPath())) {
			System.out.println("Traversing path: "
					+ currentLeg.getDestinationString());
			currentLeg.getPath().getNext().clickOnMap();
			Time.sleep(1000, 1500);
			toggleRun();
			KillStrategy.eat();
		}
		if (currentLeg.atObstacleStart()) {
			int i = 0;
			while (!currentLeg.obstacleComplete() && i < 10) {
				System.out.println("Going over obstacle: "
						+ currentLeg.getDestinationString());
				currentLeg.doObstacle();
				final PathLeg myLeg = currentLeg;
				Waiter.waitFor(new WaitCondition() {
					@Override
					public boolean isValid() {
						return (myLeg.obstacleComplete());
					}
				}, currentLeg.getObstacleTimeout());
				i++;
			}
		}
	}

	public static boolean atBank() {
		for (int i : Bank.BANK_NPC_IDS) {
			if (NPCs.getNearest(i) != null && NPCs.getNearest(i).isOnScreen()) {
				return true;
			}
		}
		for (int i : Bank.BANK_BOOTH_IDS) {
			if (SceneEntities.getNearest(i) != null
					&& SceneEntities.getNearest(i).isOnScreen()) {
				return true;
			}
		}
		for (int i : Bank.BANK_COUNTER_IDS) {
			if (SceneEntities.getNearest(i) != null
					&& SceneEntities.getNearest(i).isOnScreen()) {
				return true;
			}
		}
		for (int i : Bank.BANK_CHEST_IDS) {
			if (SceneEntities.getNearest(i) != null
					&& SceneEntities.getNearest(i).isOnScreen()) {
				return true;
			}
		}
		return false;
	}

	public static boolean needBank() {
		return (EquipmentViewer.getNeededEquipItems().length > 0
				|| !containInvItems() || Inventory.getCount(DefSlayer.foodId) == 0);
	}

	@Override
	public boolean validate() {
		return ((TaskManager.getCurrentTask() != null && !atBank() && needBank())
				|| (TaskManager.getCurrentTask() != null && !needBank() && !TaskManager.currentTask.atMonster())
				|| (TaskManager.getCurrentTask() == null && ((DefSlayer.usingNPCContact && !WalkingStrategy
				.atBank()) || (!DefSlayer.usingNPCContact && (NPCs
				.getNearest(DefSlayer.selectedMaster.getMasterId()) == null || !NPCs
				.getNearest(DefSlayer.selectedMaster.getMasterId())
				.isOnScreen())))));
	}

}
