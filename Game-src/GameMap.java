import java.util.*;

public class GameMap {
	int[] map = new int[100]; // 对战地图，元素值 0~4 表示该位置的关卡代号

	/**
	 * 生成地图：随机生成逻辑已抽取到 MapGenerator 中，这里只负责调用
	 * 关卡代号为：1：幸运轮盘 2：地雷 3：暂停 4：时空隧道 0：普通
	 */
	public void createMap(Scanner input) {
		String ans;
		int LuckyCount = 6, MineCount = 9, PauseCount = 4, TunnelCount = 7;
		System.out.println("当前特殊位置数量：幸运轮盘 " + LuckyCount + "个，地雷 " + MineCount
				+ "个，暂停 " + PauseCount + "个，时空隧道 " + TunnelCount + "个"
				+ "\n(上限均为10，超过上限的输入将设为10)\n请选择是否改变特殊位置数量【Y/N】");
		ans = input.nextLine().trim().toUpperCase();
		if (ans.equals("Y")) {

			System.out.print("\n请输入幸运轮盘数量：");
			LuckyCount = input.nextInt();
			LuckyCount = LuckyCount > 10 ? 10 : LuckyCount;
			System.out.print("\n请输入地雷数量：");
			MineCount = input.nextInt();
			MineCount = MineCount > 10 ? 10 : MineCount;
			System.out.print("\n请输入暂停数量：");
			PauseCount = input.nextInt();
			PauseCount = PauseCount > 10 ? 10 : PauseCount;
			System.out.print("\n请输入时空隧道数量：");
			TunnelCount = input.nextInt();
			TunnelCount = TunnelCount > 10 ? 10 : TunnelCount;
			input.nextLine(); // 吃掉 nextInt 留下的换行符，防止污染后续的 nextLine
		}
		map = new MapGenerator(LuckyCount, MineCount, PauseCount, TunnelCount).generate();
	}

	/**
	 * 显示地图关卡对应的图形
	 * 
	 * @param i          地图当前位置的关卡代号,即图例符号的代号
	 * @param index      当前地图位置编号
	 * @param playerPos1 玩家1的当前位置
	 * @param playerPos2 玩家2的当前位置
	 * @return 地图当前位置的对应图片
	 */
	public String getGraph(int i, int index, int playerPos1, int playerPos2) {
		String graph = "";

		/*********** 待补充的代码区域****** *******/
		// 设置给定的地图位置index的正确的图形符号graph,graph的可能值包括：
		// AB（2个玩家恰好同在位置index）
		// Ａ（玩家1恰好在位置index）
		// Ｂ（玩家2恰好在位置index）
		// ¤、★、■、〓、－（2个玩家均不在位置index，则按关卡代号i相应设置图形符号）
		// ○ 地图的起点
		// ▓ 地图的终点
		// ５ 地图位置5时显示全角的５,在其它位置（10,15...）时显示半角数字字符
		if (index == playerPos1 || index == playerPos2) {
			if (index == playerPos1) {
				graph += 'A';
			}
			if (index == playerPos2) {
				graph += 'B';
			}
		} else if (index == 0) {
			graph += "○";

		} else if (index == 99) {
			graph += "▓";

		} else if (index % 5 == 0) {
			graph += (index < 10) ? "５" + ' ' : String.valueOf(index) + ' ';
			return graph;
		} else {
			switch (i) {
				case 0:
					graph += '-';
					break;
				case 2:
					graph += "★";
					break;
				case 1:
					graph += "¤";
					break;
				case 3:
					graph += "■";
					break;
				case 4:
					graph += "〓 ";
					return graph;
				default:
					graph += '－';
					break;
			}
		}
		while (graph.length() < 3) {
			graph += ' ';
		}

		/*********** 待补充的代码区域****** *******/
		return graph;
	}

	/***
	 * 输出地图的奇数行（第1、3行）
	 * 
	 * @param start      输出的起始点在地图上的位置
	 * @param end        输出的结束点在地图上的位置
	 * @param playerPos1 玩家1的当前位置
	 * @param playerPos2 玩家2的当前位置
	 */
	public void showLine1(int start, int end, int playerPos1, int playerPos2) {
		for (int i = start; i < end; i++) {
			System.out.print(getGraph(map[i], i, playerPos1, playerPos2));
		}
	}

	/**
	 * 输出地图的偶数行（第2行）
	 * 
	 * @param start      输出的起始点在地图上的位置
	 * @param end        输出的结束点在地图上的位置
	 * @param playerPos1 玩家1的当前位置
	 * @param playerPos2 玩家2的当前位置
	 */
	public void showLine2(int start, int end, int playerPos1, int playerPos2) {
		/*********** 待补充的代码区域****** *******/
		for (int i = end - 1; i >= start; i--) {
			System.out.print(getGraph(map[i], i, playerPos1, playerPos2));
		}
		/*********** 待补充的代码区域****** *******/
	}

	/**
	 * 输出地图的右竖列
	 * 
	 * @param start      输出的起始点在地图上的位置
	 * @param end        输出的结束点在地图上的位置
	 * @param playerPos1 玩家1的当前位置
	 * @param playerPos2 玩家2的当前位置
	 */
	public void showRLine(int start, int end, int playerPos1, int playerPos2) {
		/*********** 待补充的代码区域****** *******/
		for (int i = start; i < end; i++) {
			System.out.println(" ".repeat(90) + getGraph(map[i], i, playerPos1, playerPos2));
		}
		/*********** 待补充的代码区域****** *******/
	}

	/**
	 * 输出地图的左竖列
	 * 
	 * @param start      输出的起始点在地图上的位置
	 * @param end        输出的结束点在地图上的位置
	 * @param playerPos1 玩家1的当前位置
	 * @param playerPos2 玩家2的当前位置
	 */
	public void showLLine(int start, int end, int playerPos1, int playerPos2) {
		for (int i = start; i < end; i++) {
			System.out.println(getGraph(map[i], i, playerPos1, playerPos2));
		}
	}

	/**
	 * 显示对战地图
	 * 
	 * @param playerPos1 玩家1的当前位置
	 * @param playerPos2 玩家2的当前位置
	 */
	public void showMap(int playerPos1, int playerPos2) {
		showLine1(0, 31, playerPos1, playerPos2); // 显示地图第一行
		System.out.println(); // 换行
		showRLine(31, 35, playerPos1, playerPos2); ///右竖行
		showLine2(35, 66, playerPos1, playerPos2);///第二行
		System.out.println(); // 换行
		showLLine(66, 69, playerPos1, playerPos2); ///左竖行
		showLine1(69, 100, playerPos1, playerPos2); ///第3行
	}
}