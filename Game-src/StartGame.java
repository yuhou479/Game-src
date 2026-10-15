import java.util.NoSuchElementException;

public class StartGame {

	/**
	 * 启动游戏
	 * @param args
	 */
	public static void main(String[] args) {
		try{
		GameEngine game = new GameEngine();   //创建游戏类
  	    game.start();//开始游戏
		}catch(NoSuchElementException e){
			System.out.println("\n检测到结束输入，游戏已退出。");
		}
	}
}
