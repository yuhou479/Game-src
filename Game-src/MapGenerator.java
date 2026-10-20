import java.util.*;

/**
 * 地图生成器：随机生成地图上的特殊格子（幸运轮盘、地雷、暂停、时空隧道）。
 *
 * 生成规则：
 * 1. 所有特殊位置互不重叠 —— 用一次洗牌统一抽出 26 个位置再切成四类；
 * 2. 地雷不能形成连续 6 个 —— 否则玩家最多走 6 步，永远跳不过这道墙，
 *    会卡死在墙前无法获胜，因此不满足条件就重新生成。
 */
public class MapGenerator {

	/** 地图总长度，起点 0 和终点 99 必须保持普通格 */
	private static final int Size = 100;
	/** 骰子最大步数，连续 6 个地雷就是过不去的墙 */
	private static final int MaxRoll = 6;

	/** 幸运轮盘数量 */
	private static int LuckyCount = 6;
	/** 地雷数量 */
	private static int MineCount = 9;
	/** 暂停数量 */
	private static int PauseCount = 4;
	/** 时空隧道数量 */
	private static int TunnelCount = 7;

	private Random random = new Random(System.currentTimeMillis());

    public MapGenerator(int LuckyCount,int MineCount,int PauseCount,int TunnelCount){
        MapGenerator.LuckyCount = LuckyCount;
        MapGenerator.MineCount = MineCount;
        MapGenerator.PauseCount = PauseCount;
        MapGenerator.TunnelCount = TunnelCount;
    }

	/**
	 * 生成一张地图。
	 *
	 * @return 长度 100 的数组，元素值表示该位置的关卡代号：
	 *         0 普通、1 幸运轮盘、2 地雷、3 暂停、4 时空隧道
	 */
	public int[] generate() {
		int[] luckyTurn, landMine, pause, timeTunnel;
		do {
			// 一次洗牌抽出全部特殊位置，天然互不重叠
			int specialCount = LuckyCount + MineCount + PauseCount + TunnelCount;
			List<Integer> special = pickDistinct(1, Size - 2, specialCount);

			// 按顺序切成四段，分别作为四类格子
			int cursor = 0;
			luckyTurn = slice(special, cursor, LuckyCount);
			cursor += LuckyCount;
			landMine = slice(special, cursor, MineCount);
			cursor += MineCount;
			pause = slice(special, cursor, PauseCount);
			cursor += PauseCount;
			timeTunnel = slice(special, cursor, TunnelCount);
		} while (hasSixInARow(landMine)); // 地雷出现 6 连墙就重新生成

		// 组装成地图数组
		int[] map = new int[Size];
		fill(map, luckyTurn, 1);
		fill(map, landMine, 2);
		fill(map, pause, 3);
		fill(map, timeTunnel, 4);
		return map;
	}

	/**
	 * 从 [from, to]（含两端）中洗牌抽取 count 个互不重复的数。
	 */
	private List<Integer> pickDistinct(int from, int to, int count) {
		List<Integer> candidates = new ArrayList<>();
		for (int i = from; i <= to; i++) {
			candidates.add(i);
		}
		Collections.shuffle(candidates, random);
		return new ArrayList<>(candidates.subList(0, count));
	}

	/**
	 * 从 list 的第 start 个元素起，连续取 count 个，转成 int 数组。
	 */
	private int[] slice(List<Integer> list, int start, int count) {
		int[] result = new int[count];
		for (int i = 0; i < count; i++) {
			result[i] = list.get(start + i);
		}
		return result;
	}

	/**
	 * 判断地雷里是否存在连续 MAX_ROLL（6）个的墙。
	 */
	private boolean hasSixInARow(int[] mines) {
		boolean[] isMine = new boolean[Size];
		for (int p : mines) {
			isMine[p] = true;
		}
		for (int i = 0; i + MaxRoll <= Size; i++) {
			boolean allMine = true;
			for (int j = i; j < i + MaxRoll; j++) {
				if (!isMine[j]) {
					allMine = false;
					break;
				}
			}
			if (allMine) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 把指定位置的关卡代号写入地图数组。
	 */
	private void fill(int[] map, int[] positions, int type) {
		for (int p : positions) {
			map[p] = type;
		}
	}
}
