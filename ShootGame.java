import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


//游戏启动主类
public class ShootGame extends JPanel{
    public static final int WIDTH = 800;//面板宽
    public static final int HEIGHT = 1200;//面板高
    //游戏的当前状态：开始start，运行running,暂停pause，结束gameover
    private int state;
    private static final int START = 0;
    private static final int RUNNING = 1;
    private static final int PAUSE = 2;
    private static final int GAME_OVER = 3;

    private int score = 0;//分数
    private Timer timer;//计时器
    private int interval = 1000/100;//时间间隔（ms)

    public static BufferedImage background;
    public static BufferedImage start;
    public static BufferedImage bird;
    public static BufferedImage HappyBird;
    public static BufferedImage Bullet;
    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage pause;
    public static BufferedImage gameover;

    private flying[] flyings = {};//飞鸟数组
    private Bullet[] bullets ={};//子弹数组

    //初始化图片资源

}
