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
		Scanner input = new Scanner(System.in);

		/*********** 待补充的代码区域 *************/
		// 通过键盘输入接收用户选择的角色，注意，不允许角色重复
		System.out.print("请玩家1选择角色:  ");
		int role1 = input.nextInt();
		while (role1 <= 0 || role1 > 4) {
			System.out.println();
			System.out.println("输入不合法，请重新输入");
			role1 = input.nextInt();
		}
		System.out.print("请玩家2选择角色:  ");
		int role2 = input.nextInt();
		while (role2 <= 0 || role2 > 4) {
			System.out.println();
			System.out.println("输入不合法，请重新输入");
			role2 = input.nextInt();
		}
		while(role2 == role1){
			System.out.println();
			System.out.println("角色"+role1+"已被选择，请重新选择：");
			role2 = input.nextInt();
		}
		/*********** 待补充的代码区域 *************/

		setRole(1, role1); // 设置玩家1代表的角色
		setRole(2, role2); // 设置玩家2代表的角色

		play(); // 开始两人对战
		input.close();
	}

	/**
	 * 设置对战角色
	 * 
	 * @param no
	 *            玩家次序 1：玩家1 2：玩家2
	 * @param role
	 *            角色代号
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
	public void play() {
		System.out.println("\n");

		System.out.print("**************************************************************\n");
		System.out.print("*                        Game Start                          *\n");
		System.out.print("**************************************************************\n");

		// 显示对战双方士兵样式
		System.out.println("^_^" + playerName[0] + "的士兵：Ａ");
		System.out.println("^_^" + playerName[1] + "的士兵：Ｂ\n");

		// 显示对战地图
		System.out.println("图例说明：○ 起点; ▓ 终点; ■ 暂停; ¤ 幸运轮盘; ★ 地雷; 〓 时空隧道; － 普通; 数字 当前位置序号\n地图如下：");

		System.out.println("起点→");
		map.showMap(playerPos1, playerPos2);
		System.out.println("\n");
		for (int j = 28; j > 0; j--) {
			System.out.print("　");
		}
		System.out.println("→终点");

		// 游戏开始
		int step; // 存储骰子数目
		while (playerPos1 < 99 && playerPos2 < 99) { // 有任何一方走到终点，跳出循环

			// 轮流掷骰子
			if (goAndStop[0].equals("on")) {
				// 玩家1掷骰子
				step = throwShifter(1); // 掷骰子
				System.out.println("-----------------"); // 显示结果信息
				System.out.println(playerName[0] + "刚才得到的骰子数： " + step + "\n");
				playerPos1 = getCurPos(1, playerPos1, step); // 计算这一次移动后的当前位置
				System.out.println(playerName[0] + "(A)当前位置：  " + playerPos1);
				System.out.println(playerName[1] + "(B)当前位置：" + playerPos2);
				System.out.println("-----------------");

				// 显示对战地图
				System.out.println("图例说明：○ 起点; ▓ 终点; ■ 暂停; ¤ 幸运轮盘; ★ 地雷; 〓 时空隧道; － 普通; 数字 当前位置序号\n地图如下：");

				System.out.println("起点→");

				map.showMap(playerPos1, playerPos2); // 显示当前地图

				System.out.println("\n");
				for (int j = 28; j > 0; j--) {
					System.out.print("　");
				}
				System.out.println("→终点");

			} else {
				System.out.println("\n" + playerName[0] + "停掷一次！\n"); // 显示此次暂停信息
				goAndStop[0] = "on"; // 设置下次可掷状态
			}

			// System.out.println("\n");

			if (goAndStop[1].equals("on")) {
				// 玩家2掷骰子
				step = throwShifter(2); // 掷骰子
				System.out.println("-----------------"); // 显示结果信息
				System.out.println(playerName[1] + "刚才得到的骰子数： " + step + "\n");
				playerPos2 = getCurPos(2, playerPos2, step); // 计算这一次移动后的当前位置
				System.out.println(playerName[0] + "(A)当前位置：  " + playerPos1);
				System.out.println(playerName[1] + "(B)当前位置：" + playerPos2);
				System.out.println("-----------------");

				// 显示对战地图
				System.out.println("图例说明：○ 起点; ▓ 终点; ■ 暂停; ¤ 幸运轮盘; ★ 地雷; 〓 时空隧道; － 普通; 数字 当前位置序号\n地图如下：");

				System.out.println("起点→");

				map.showMap(playerPos1, playerPos2); // 显示当前地图

				System.out.println("\n");
				for (int j = 28; j > 0; j--) {
					System.out.print("　");
				}
				System.out.println("→终点");
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
	 *            玩家次序
	 * @return step 掷出的骰子数目
	 */
	public int throwShifter(int no) {
		int step = 0;
		System.out.print(playerName[no - 1] + ", 请您按任意字母键后回车启动掷骰子： ");
		Scanner input = new Scanner(System.in);
		String answer = input.next();

		/*********** 待补充的代码区域 *************/ // 产生一个1~6的数字,即掷的骰子数目
		input.close();
		return 2;
		/*********** 待补充的代码区域 *************/
	}

	/**
	 * 计算玩家此次移动后的当前位置
	 * 
	 * @param no
	 *            玩家次序
	 * @param position
	 *            移动前位置
	 * @param step
	 *            掷的骰子数目
	 * @return position 移动后的位置
	 */
	public int getCurPos(int no, int position, int step) {
		position = position + step; // 第一次移动后的位置
		if (position >= 99) {
			return 99;
		}
		Scanner input = new Scanner(System.in);
		input.close();
		switch (map.map[position]) { // 根据地图中的关卡代号进行判断
		case 0: // 走到普通格
			/*********** 待补充的代码区域 *************/
			;
			/*********** 待补充的代码区域 *************/
		case 1: // 幸运轮盘
			/*********** 待补充的代码区域 *************/
			;
			/*********** 待补充的代码区域 *************/
		case 2: // 踩到地雷
			/*********** 待补充的代码区域 *************/
			;
			/*********** 待补充的代码区域 *************/
		case 3: // 下一次暂停一次
			/*********** 待补充的代码区域 *************/
			;
			/*********** 待补充的代码区域 *************/
		case 4: // 时空隧道
			/*********** 待补充的代码区域 *************/
			;
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
