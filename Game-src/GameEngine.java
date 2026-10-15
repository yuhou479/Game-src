import java.util.*;

public class GameEngine {
	GameMap map; // 地图
	int playerPos1; // 对战中玩家1的当前位置
	int playerPos2; // 对战中玩家2的当前位置
	String[] goAndStop = new String[2]; // 走或停标识设置
	String[] playerName = new String[2]; // 对战角色

	/**
	 * 初始化游戏的一局
	 */
	public void init() {
		map = new GameMap();
		map.createMap(); // 生成地图
		playerPos1 = 0; // 设置玩家1起始位置
		playerPos2 = 0; // 设置玩家2起始位置
		goAndStop[0] = "on"; // 记录玩家1下一次走或停
		goAndStop[1] = "on"; // 设置玩家2下一次走或停
	}

	private int getInputOfChosingPlayer(Scanner input) {
		String temp = input.next().trim();
		if (temp.equals("1. 关羽") || temp.equals("关羽") || temp.equals("1.关羽")) {
			temp = "1";
		} else if (temp.equals("2. 张飞") || temp.equals("张飞") || temp.equals("2.张飞")) {
			temp = "2";
		} else if (temp.equals("3. 吕布") || temp.equals("吕布") || temp.equals("3.吕布")) {
			temp = "3";
		} else if (temp.equals("4. 赵云") || temp.equals("赵云") || temp.equals("4.赵云")) {
			temp = "4";
		}
		int ans = temp.charAt(0) - '0';
		if (temp.length() > 1 || (ans != 1 && ans != 2 && ans != 3 && ans != 4)) {
			System.out.println();
			System.out.print("输入不合法，请重新输入：");
			ans = getInputOfChosingPlayer(input);
		}
		return ans;
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

		/*********** 待补充的代码区域 *************/
		// 通过键盘输入接收用户选择的角色，注意，不允许角色重复
		System.out.print("请玩家1选择角色:  ");
		int role1 = getInputOfChosingPlayer(input);
		System.out.print("请玩家2选择角色:  ");
		int role2 = getInputOfChosingPlayer(input);
		while (role2 == role1) {
			System.out.println();
			System.out.print("角色" + role1 + "已被选择，请重新选择：");
			role2 = getInputOfChosingPlayer(input);
		}
		/*********** 待补充的代码区域 *************/

		setRole(1, role1); // 设置玩家1代表的角色
		setRole(2, role2); // 设置玩家2代表的角色

		play(input); // 开始两人对战
		input.close();
	}

	/**
	 * 设置对战角色
	 * 
	 * @param no
	 *             玩家次序 1：玩家1 2：玩家2
	 * @param role
	 *             角色代号
	 */
	public void setRole(int no, int role) {
		switch (role) {
			case 1:
				playerName[no - 1] = "关羽";
				break;
			case 2:
				playerName[no - 1] = "张飞";
				break;
			case 3:
				playerName[no - 1] = "吕布";
				break;
			case 4:
				playerName[no - 1] = "赵云";
				break;
			default:

				break;
		}
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
		System.out.println("^_^" + playerName[0] + "的士兵：Ａ");
		System.out.println("^_^" + playerName[1] + "的士兵：Ｂ\n");

		// 显示对战地图
		System.out.println("图例说明：○ 起点; ▓ 终点; ■ 暂停; ¤ 幸运轮盘; ★ 地雷; 〓 时空隧道; － 普通; 数字 当前位置序号\n地图如下：");
		map.showMap(playerPos1, playerPos2);
		System.out.println("\n");

		// 游戏开始
		int step; // 存储骰子数目
		while (playerPos1 < 99 && playerPos2 < 99) { // 有任何一方走到终点，跳出循环

			// 轮流掷骰子
			if (goAndStop[0].equals("on")) {
				// 玩家1掷骰子
				step = throwShifter(1, input); // 掷骰子
				System.out.println("-----------------"); // 显示结果信息
				System.out.println(playerName[0] + "刚才得到的骰子数： " + step + "\n");
				playerPos1 = getCurPos(1, playerPos1, step, input); // 计算这一次移动后的当前位置
				System.out.println();
				System.out.println(playerName[0] + "(A)当前位置：  " + playerPos1);
				System.out.println(playerName[1] + "(B)当前位置：  " + playerPos2);
				System.out.println("-----------------");

				// 显示对战地图
				System.out.println("图例说明：○ 起点; ▓ 终点; ■ 暂停; ¤ 幸运轮盘; ★ 地雷; 〓 时空隧道; － 普通; 数字 当前位置序号\n地图如下：");

				map.showMap(playerPos1, playerPos2); // 显示当前地图

				System.out.println("\n");

			} else {
				System.out.println("\n" + playerName[0] + "停掷一次！\n"); // 显示此次暂停信息
				goAndStop[0] = "on"; // 设置下次可掷状态
			}

			// System.out.println("\n");

			if (goAndStop[1].equals("on")) {
				// 玩家2掷骰子
				step = throwShifter(2, input); // 掷骰子
				System.out.println("-----------------"); // 显示结果信息
				System.out.println(playerName[1] + "刚才得到的骰子数： " + step + "\n");
				playerPos2 = getCurPos(2, playerPos2, step, input); // 计算这一次移动后的当前位置
				System.out.println();
				System.out.println(playerName[0] + "(A)当前位置：  " + playerPos1);
				System.out.println(playerName[1] + "(B)当前位置：  " + playerPos2);
				System.out.println("-----------------");

				// 显示对战地图
				System.out.println("图例说明：○ 起点; ▓ 终点; ■ 暂停; ¤ 幸运轮盘; ★ 地雷; 〓 时空隧道; － 普通; 数字 当前位置序号\n地图如下：");
				map.showMap(playerPos1, playerPos2); // 显示当前地图

				System.out.println("\n");

			} else {
				System.out.println("\n" + playerName[1] + "停掷一次！\n"); // 显示此次暂停信息
				goAndStop[1] = "on"; // 设置下次可掷状态
			}

			// System.out.println("\n");
		}

		// 游戏结束
		System.out.println("\n\n\n\n");
		System.out.print("****************************************************\n");
		System.out.print("                      Game  Over                    \n");
		System.out.print("****************************************************\n\n");
		judge();
	}

	/**
	 * 掷骰子
	 * 
	 * @param no
	 *           玩家次序
	 * @return step 掷出的骰子数目
	 */
	public int throwShifter(int no, Scanner input) {
		int step = 0;
		System.out.print(playerName[no - 1] + ", 请您按任意字母键后回车启动掷骰子： ");
		String answer = input.next();

		/*********** 待补充的代码区域 *************/ // 产生一个1~6的数字,即掷的骰子数目
		Random r = new Random(System.currentTimeMillis());
		int seed = 0;
		for (int i = 0; i < answer.length(); i++) {
			seed += answer.charAt(i);
		}
		Random r2 = new Random(r.nextLong() * seed);
		step = Math.abs(r2.nextInt()) % 6;

		return step + 1;
		/*********** 待补充的代码区域 *************/
	}

	/**
	 * 计算玩家此次移动后的当前位置
	 * 
	 * @param no
	 *                 玩家次序
	 * @param position
	 *                 移动前位置
	 * @param step
	 *                 掷的骰子数目
	 * @return position 移动后的位置
	 */
	public int getCurPos(int no, int position, int step, Scanner input) {
		position = position + step; // 第一次移动后的位置
		if (position >= 99) {
			return 99;
		}
		switch (map.map[position]) { // 根据地图中的关卡代号进行判断
			case 0: // 走到普通格
				/*********** 待补充的代码区域 *************/
				if (no == 1 && position == playerPos2) {
					playerPos2 = 0;
					System.out.print(playerName[0] + ":后来者总有机会.");
				} else if (no == 2 && position == playerPos1) {
					playerPos1 = 0;
					System.out.print(playerName[1] + ":后来者总有机会.");
				} else
					System.out.println("什么事也没发生");
				break;
			/*********** 待补充的代码区域 *************/
			case 1: // 幸运轮盘
				/*********** 待补充的代码区域 *************/
				System.out.println("请选择你的策略：");
				System.out.println("策略1：交换位置");
				System.out.println("策略2：轰炸（对方位置-6，如果对方当前位置小于6，回起点）");
				int choice = getTheChoice(input);
				if (choice == 1) {
					int tempPos = 0;
					if(no == 1){
						tempPos = playerPos2;
						playerPos2 = position;
						position = tempPos;
					}else{
						tempPos = playerPos1;
						playerPos1 = position;
						position = playerPos1;
					}
				} else if (choice == 2) {
					if (no == 1) {
						playerPos2 = playerPos2 - 6 < 0 ? 0 : playerPos2 - 6;
						System.out.println(playerName[1] + "：你给我等着！！！");
					} else {
						playerPos1 = playerPos1 - 6 < 0 ? 0 : playerPos1 - 6;
						System.out.println(playerName[0] + "：你给我等着！！！");
					}
				} else {
					System.out.print("输入错误，请重新输入：");
					getTheChoice(input);
				}
				break;
			/*********** 待补充的代码区域 *************/
			case 2: // 踩到地雷，回到起点同时从准备好的句子中随机抽一句输出
				/*********** 待补充的代码区域 *************/
				position = 0;
				printARandomSentence();
				break;
			/*********** 待补充的代码区域 *************/
			case 3: // 下一次暂停一次
				/*********** 待补充的代码区域 *************/
				System.out.println(playerName[no == 1 ? 1 : 0] + "：就搁着呆着吧！");
				goAndStop[no - 1] = "off";
				break;
			/*********** 待补充的代码区域 *************/
			case 4: // 时空隧道
				/*********** 待补充的代码区域 *************/
				System.out.println(playerName[no - 1] + "：遁入虚空！");
				position += 10;
				break;
			/*********** 待补充的代码区域 *************/
		}

		// 返回此次掷骰子后玩家的位置坐标
		if (position < 0) {
			return 0;
		} else if (position > 99) {
			return 99;
		} else {
			return position;
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
		System.out.println(sentences[Math.abs(r.nextInt()) % numsOfSetence]);
	}

	private int getTheChoice(Scanner input) {
		String temp = input.next();
		if (temp.equals("策略1") || temp.equals("交换位置") || temp.equals("策略1：交换位置")) {
			temp = "1";
		} else if (temp.equals("策略2") || temp.equals("轰炸") || temp.equals("轰炸（对方位置-6，如果对方当前位置小于6，回起点）")
				|| temp.equals("策略2：轰炸（对方位置-6，如果对方当前位置小于6，回起点）")) {
			temp = "2";
		}
		if (temp.length() > 1) {
			System.out.print("输入错误请重新输入：");
			return getTheChoice(input);
		}
		if (temp.charAt(0) != '1' && temp.charAt(0) != '2') {
			System.out.print("输入错误请重新输入：");
			return getTheChoice(input);
		}
		return temp.charAt(0) - '0';
	}

	/**
	 * 显示对战结果
	 */
	public void judge() {
		if (playerPos1 > playerPos2) {
			System.out.println("\n恭喜" + playerName[0] + "将军! 您获胜了！");
		} else {
			System.out.println("\n恭喜" + playerName[1] + "将军! 您获胜了！");
		}
	}
}
