import java.util.Random;
import java.util.Scanner;

public class Player {
    private static String[] names = { "关羽", "张飞", "吕布", "赵云" };
    private String name;
    private boolean canMove = true;
    public int curPos = 0; // 角色当前位置

    /**
     * 选择角色
     * 
     * @a 迭代次数
     **/
    public int choseCharactor(Scanner input, int a, Player enemy) {
        if (a >= 30) {
            System.out.println("错误次数过多，将自动为你选择");
            if (enemy == null) {
                Random r = new Random(System.currentTimeMillis());
                int choice = r.nextInt(4);
                name = names[choice];
                return choice;
            } else {
                for (int i = 0; i < 4; i++) {
                    if (enemy.getName().equals(names[i]) == false) {
                        name = names[i];
                        return i;
                    }
                }
            }
        }
        String temp = input.nextLine().trim();
        if (temp.isEmpty()) {
            System.out.println();
            System.out.print("输入不合法，请重新输入：");
            return choseCharactor(input, a + 1, enemy);
        }
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
            return choseCharactor(input, a + 1, enemy);
        }
        name = names[ans - 1];
        return ans - 1;
    }

    public String getName() {
        return name;
    }

    /**
     * 掷骰子
     * 
     * 
     * @return step 掷出的骰子数目
     */
    public int throwShifter(Scanner input) {
        if (canMove == false) {
            System.out.println("\n" + name + "停掷一次！\n"); // 显示此次暂停信息
            canMove = true;
            return -1;
        }
        int step = 0;
        System.out.print(name + ", 请您按任意字母键后回车启动掷骰子： ");
        String answer = input.next();

        Random r = new Random(System.currentTimeMillis());
        int seed = 0;
        for (int i = 0; i < answer.length(); i++) {
            seed += answer.charAt(i);
        }
        Random r2 = new Random(r.nextLong() * seed);
        step = r2.nextInt(6);
        moveStep(step + 1);
        return step + 1;
    }

    // 前进step步
    private void moveStep(int step) {
        curPos = curPos + step > 99 ? 99 : curPos + step;
    }

    public void moveToStart() {
        curPos = 0;
    }

    public void handleToShiKongSuiDao() {
        System.out.println(name + "：遁入虚空！");
        moveStep(10);
    }

    public void handleToStop() {
        canMove = false;
    }

    public void handleToBomb() {
        curPos = curPos - 6 < 0 ? 0 : curPos - 6;
        System.out.println(name + "：你给我等着！！！");
    }

    public int getTheChoiceOfLuckyWheel(Scanner input) {
        String temp = input.next();
        if (temp.equals("策略1") || temp.equals("交换位置") || temp.equals("策略1：交换位置")) {
            temp = "1";
        } else if (temp.equals("策略2") || temp.equals("轰炸") || temp.equals("轰炸（对方位置-6，如果对方当前位置小于6，回起点）")
                || temp.equals("策略2：轰炸（对方位置-6，如果对方当前位置小于6，回起点）")) {
            temp = "2";
        }
        if (temp.length() > 1) {
            System.out.print("输入错误请重新输入：");
            return getTheChoiceOfLuckyWheel(input);
        }
        if (temp.charAt(0) != '1' && temp.charAt(0) != '2') {
            System.out.print("输入错误请重新输入：");
            return getTheChoiceOfLuckyWheel(input);
        }
        return temp.charAt(0) - '0';
    }
}
