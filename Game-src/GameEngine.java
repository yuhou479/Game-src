import java.util.*;

public class GameEngine {
	private GameMap map; // 地图
	private Player player1;
	private Player player2;
	private Referee referee; // 裁判，负责规则判定

	/**
	 * 初始化游戏的一局
	 */
	public void init(Scanner input) {
		map = new GameMap();
		map.createMap(input); // 生成地图
		player1 = new Player(); // 设置玩家1起始位置
		player2 = new Player(); // 设置玩家2起始位置
		referee = new Referee(map, player1, player2); // 规则交给裁判
	}

	/**
	 * 开始游戏
	 */
	public void start() {
		// 初始化
		Scanner input = new Scanner(System.in, "GBK");
		init(input);
		System.out.println("**************************************************************");
		System.out.println("*                                                            *");
		System.out.println("*                                                            *");
		System.out.println("*                         飞行棋游戏                         *");
		System.out.println("*                                                            *");
		System.out.println("*                                                            *");
		System.out.println("**************************************************************\n");
		//map.choseMap(input);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~两  人  对  战~~~~~~~~~~~~~~~~~~~~~~~~\n");
		System.out.println("请选择角色: 1. 关羽  2. 张飞  3. 吕布  4. 赵云");

		// 通过键盘输入接收用户选择的角色，注意，不允许角色重复
		System.out.print("请玩家1选择角色:  ");
		int role1 = player1.choseCharactor(input, 0, null);
		System.out.print("请玩家2选择角色:  ");
		int role2 = player2.choseCharactor(input, 0, player1);
		while (role2 == role1) {
			System.out.println();
			System.out.print("角色" + (role1+1) + "已被选择，请重新选择：");
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
		while (referee.isGameOver() == false) {
			// 玩家1掷骰子
			gameRound(player1, input);

			if (referee.isGameOver() == false) {
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

	/**
	 * 单个玩家的一个回合：掷骰子 → 处理事件 → 判定胜负 → 显示
	 */
	private void gameRound(Player player, Scanner input) {
		int step;
		step = player.throwShifter(input);
		if (step == -1) {
			return;
		}
		System.out.println("-----------------"); // 显示结果信息
		System.out.println(player.getName() + "刚才得到的骰子数： " + step + "\n");
		referee.handleMapEvent(player, input); // 处理当前位置的事件
		System.out.println();
		System.out.println(player1.getName() + "(A)当前位置：  " + player1.curPos);
		System.out.println(player2.getName() + "(B)当前位置：  " + player2.curPos);
		System.out.println("-----------------");
		referee.judgeWin(player); // 判定胜负
		if (referee.isGameOver() == true) {
			return;
		}
		// 显示对战地图
		System.out.println("图例说明：○ 起点; ▓ 终点; ■ 暂停; ¤ 幸运轮盘; ★ 地雷; 〓 时空隧道; － 普通; 数字 当前位置序号\n地图如下：");
		map.showMap(player1.curPos, player2.curPos); // 显示当前地图
		System.out.println("\n");
	}
}
