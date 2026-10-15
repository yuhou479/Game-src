public class GameMap {
	int[] map = new int[100]; // 对战地图,注意，以下数组中的index从0开始，故第1个地雷实际出现在地图上的第5个位置
	int[] luckyTurn = { 6, 23, 39, 54, 69, 83 }; // 幸运轮盘
	int[] landMine = { 4, 13, 17, 33, 38, 49, 64, 79, 94 }; // 地雷位置，第1个地雷实际出现在地图上的第5个位置
	int[] pause = { 9, 27, 59, 93 }; // 暂停
	int[] timeTunnel = { 19, 24, 44, 63, 72, 87, 89 }; // 时空隧道

	/**
	 * 生成地图:
	 * 关卡代号为：1：幸运轮盘 2：地雷 3: 暂停 4：时空 ：普通
	 */
	public void createMap() {
		int i = 0;

		// 在对战地图上设置幸运轮盘
		for (i = 0; i < luckyTurn.length; i++) {
			map[luckyTurn[i]] = 1;
		}

		// 在对战地图上设置地雷
		for (i = 0; i < landMine.length; i++) {
			map[landMine[i]] = 2;
		}

		// 在对战地图上设置暂停
		for (i = 0; i < pause.length; i++) {
			map[pause[i]] = 3;
		}

		// 在对战地图上设置时空隧道
		for (i = 0; i < timeTunnel.length; i++) {
			map[timeTunnel[i]] = 4;
		}
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
					graph += "〓";
					break;
				default:
					graph += '-';
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
			System.out.println(" ".repeat(92) + getGraph(map[i], i, playerPos1, playerPos2));
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
		showLine2(35,66,playerPos1,playerPos2);///第二行
		System.out.println(); // 换行
		showLLine(66, 69, playerPos1, playerPos2); ///左竖行
		showLine1(69, 100, playerPos1, playerPos2); ///第3行
	}
}