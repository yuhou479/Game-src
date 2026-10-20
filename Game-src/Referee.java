import java.util.*;

/**
 * 裁判：负责游戏规则，包括
 * 1. 处理玩家落在某格后触发的事件（交换位置、轰炸、地雷、暂停、时空隧道）；
 * 2. 判定胜负（谁先到达终点 99）。
 * <p>
 * 不含「谁先走、走几轮、怎么显示」这些流程逻辑——那由 GameEngine 负责。
 */
public class Referee {

	private GameMap map;
	private Player player1;
	private Player player2;
	private boolean gameOver = false;

	public Referee(GameMap map, Player player1, Player player2) {
		this.map = map;
		this.player1 = player1;
		this.player2 = player2;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * 处理玩家落在当前位置后触发的事件
	 */
	public void handleMapEvent(Player player, Scanner input) {
		Player enemy = player == player1 ? player2 : player1;
		switch (map.map[player.curPos]) { // 根据地图中的关卡代号进行判断
			case 0: // 走到普通格
				if (player1.curPos == player2.curPos) {
					enemy.moveToStart();
					System.out.print(player.getName() + ":后来者总有机会.");
				} else {
					System.out.println("什么事也没发生");
				}
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

	/**
	 * 判定胜负：玩家到达终点（99）则胜利，游戏结束
	 */
	public void judgeWin(Player player) {
		if (player.curPos >= 99) {
			System.out.println("\n恭喜" + player.getName() + "将军! 您获胜了！");
			gameOver = true;
		}
	}

	private void swapPos(Player a, Player b) {
		int tempPos = a.curPos;
		a.curPos = b.curPos;
		b.curPos = tempPos;
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
}
