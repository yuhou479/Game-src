import java.util.*;

public class GameEngine {
	private GameMap map; // 地图
	private Player player1;
	private Player player2;
	private static boolean gameOver = false;

	/**
	 * 初始化游戏的一局
	 */
	public void init() {
		map = new GameMap();
		map.createMap(); // 生成地图
		player1 = new Player(); // 设置玩家1起始位置
		player2 = new Player(); // 设置玩家2起始位置
	}

	/**
	 * 开始游戏
	 */
	public void start() {
		// 初始化
		init();
		System.out.println("**************************************************************");
		System.out.println("*                                                            *");
		System.out.println("*                                                            *");
		System.out.println("*                         飞行棋游戏                         *");
		System.out.println("*                                                            *");
		System.out.println("*                                                            *");
		System.out.println("**************************************************************\n");

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~两  人  对  战~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.println("请选择角色: 1. 关羽  2. 张飞  3. 吕布  4. 赵云");
		Scanner input = new Scanner(System.in, "GBK");

		// 通过键盘输入接收用户选择的角色，注意，不允许角色重复
		System.out.print("请玩家1选择角色:  ");
		int role1 = player1.choseCharactor(input, 0, null);
		System.out.print("请玩家2选择角色:  ");
		int role2 = player2.choseCharactor(input, 0, player1);
		while (role2 == role1) {
			System.out.println();
			System.out.print("角色" + role1 + "已被选择，请重新选择：");
			role2 = player2.choseCharactor(input, 0, player1);
		}

		play(input); // 开始两人对战
		input.close();
	}

	/**
	 * 两人对战玩法
	 */
	public void play(Scanner input) {
		System.out.println("\n");

		System.out.print("**************************************************************\n");
		System.out.print("*                        Game Start                          *\n");
		System.out.print("**************************************************************\n");

		// 显示对战双方士兵样式
		System.out.println("^_^" + player1.getName() + "的士兵：Ａ");
		System.out.println("^_^" + player2.getName() + "的士兵：Ｂ\n");

		// 显示对战地图
		System.out.println("图例说明：○ 起点; ▓ 终点; ■ 暂停; ¤ 幸运轮盘; ★ 地雷; 〓 时空隧道; － 普通; 数字 当前位置序号\n地图如下：");
		map.showMap(player1.curPos, player2.curPos);
		System.out.println("\n");

		// 游戏开始
		// 轮流掷骰子直到有人达到终点
		while (gameOver == false) {
			// 玩家1掷骰子
			gameRound(player1, input);

			if (gameOver == false) {
				// 玩家2掷骰子
				gameRound(player2, input);
			}
		}
		// 游戏结束
		System.out.println("\n\n\n\n");
		System.out.print("****************************************************\n");
		System.out.print("                      Game  Over                    \n");
		System.out.print("****************************************************\n\n");
	}

	private void gameRound(Player player, Scanner input) {
		int step;
		step = player.throwShifter(input);
		if (step == -1) {
			return;
		}
		System.out.println("-----------------"); // 显示结果信息
		System.out.println(player.getName() + "刚才得到的骰子数： " + step + "\n");
		handleMapEvent(player, input); // 处理当前位置的事件
		System.out.println();
		System.out.println(player1.getName() + "(A)当前位置：  " + player1.curPos);
		System.out.println(player2.getName() + "(B)当前位置：  " + player2.curPos);
		System.out.println("-----------------");
		// 显示对战地图
		System.out.println("图例说明：○ 起点; ▓ 终点; ■ 暂停; ¤ 幸运轮盘; ★ 地雷; 〓 时空隧道; － 普通; 数字 当前位置序号\n地图如下：");
		map.showMap(player1.curPos, player2.curPos); // 显示当前地图
		System.out.println("\n");
	}

	public void handleMapEvent(Player player, Scanner input) {
		Player enemy = player == player1 ? player2 : player1;
		switch (map.map[player.curPos]) { // 根据地图中的关卡代号进行判断
			case 0: // 走到普通格
				if (player1.curPos == player2.curPos) {
					(enemy).moveToStart();
					System.out.print(player.getName() + ":后来者总有机会.");
				} else
					System.out.println("什么事也没发生");
				break;
			case 1: // 幸运轮盘
				System.out.println("请选择你的策略：");
				System.out.println("策略1：交换位置");
				System.out.println("策略2：轰炸（对方位置-6，如果对方当前位置小于6，回起点）");
				int choice = player.getTheChoiceOfLuckyWheel(input);
				if (choice == 1) {
					swapPos(player, enemy);
				} else if (choice == 2) {
					enemy.handleToBomb();
				}
				break;
			case 2: // 踩到地雷，回到起点同时从准备好的句子中随机抽一句输出
				player.moveToStart();
				printARandomSentence();
				break;
			case 3: // 下一次暂停一次
				System.out.println(enemy.getName() + "：就搁着呆着吧！");
				player.handleToStop();
				break;
			case 4: // 时空隧道
				player.handleToShiKongSuiDao();
				break;
		}
	}

	private void printARandomSentence() {
		int numsOfSetence = 10;
		String[] sentences = {
				"砰！踩雷了吧，走路不长眼睛？",
				"地雷都被你踩中了，今天运气不错嘛～",
				"哈哈，脚底生花，恭喜中奖！",
				"这一脚下去，怕是踩出了个寂寞。",
				"雷区漫步，你是真的勇士！",
				"哎哟，这一炸，魂都飞了吧？",
				"别人是走路，你是排雷，致敬英雄！",
				"踩雷高手，非你莫属！",
				"这一脚，踩出了新年鞭炮的感觉。",
				"兄弟，地雷是让着你踩的吗？"
		};
		Random r = new Random(System.currentTimeMillis());
		System.out.println(sentences[r.nextInt(numsOfSetence)]);
	}

	private void swapPos(Player player01, Player player02) {
		int tempPos = player01.curPos;
		player01.curPos = player02.curPos;
		player02.curPos = tempPos;
	}

	/**
	 * 显示胜利
	 */
	public static void win(Player player) {
		System.out.println("\n恭喜" + player.getName() + "将军! 您获胜了！");
		gameOver = true;
	}
}
