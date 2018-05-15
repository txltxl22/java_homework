import javafx.animation.PauseTransition;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
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
    private int bullets =0;//子弹
    private int birdNum = 0;
    private Bullet bullet = new Bullet();//准星

    //初始化图片资源
    static {
        try {
            background = ImageIO.read(ShootGame.class.getResource("background.png"));
            start = ImageIO.read(ShootGame.class.getResource("start.png"));
            Bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
            bird = ImageIO.read(ShootGame.class.getResource("bird.png"));
            HappyBird = ImageIO.read(ShootGame.class.getResource("HappyBird.png"));
            hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
            pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
            gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //画
    @Override
    public void paint(Graphics g){
        g.drawImage(background,0,0,null);
        paintBullets(g);//画准星
        paintflying(g);//画飞行物
        paintScore(g);//画分数
        paintState(g);//画游戏状态
    }
    public void paintBullets(Graphics g){
        Bullet bullets;
        g.drawImage(bullets.getImage());
    }

    public void paintflying(Graphics g){
        for(int i =0;i<flyings.length;i++){
            flying f = flyings[i];
            g.drawImage(f.getImage(),f.getX(),f.getY(),null);
        }
    }

    public  void paintScore(Graphics g){
        int x = 10;
        int y = 25;
        Font font = new font(Font.SANS_SERIF,Font.BOLD,22);//字体
        g.setColor(new Color(0xFF0000));
        g.setFont(font);//设置字体
        g.drawString("SCORE:"+score,x,y);//画分数
    }
    public void paintState(Graphics g){
        switch (state){
            case START://启动状态
                g.drawImage(start,0,0,null);
                break;
            case PAUSE://暂停
                g.drawImage(pause,0,0,null);
                break;
            case GAME_OVER://游戏结束
                g.drawImage(gameover,0,0,null);
                break;
        }
    }

    public  static void main(String[] args){
        JFrame frame = new JFrame("Fly");
        ShootGame game = new ShootGame();//面板对象
        frame.add(game);//将面板添加到JFrame中
        frame.setSize(WIDTH,HEIGHT);//设置大小
        frame.setAlwaysOnTop(true);//设置总在最上
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭操作
        frame.setIconImage(new ImageIcon("images/icon.jpg").getImage());//设置窗体的图标
        frame.setLocationRelativeTo(null);//设置窗体初始位置
        frame.setVisible(true);//尽快调用paint
        game.action();//启动执行
    }

    public void action(){
        //鼠标监听事件
        MouseAdapter l = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//鼠标点击
                switch (state){
                    case START:
                        state = RUNNING;
                        break;
                    case GAME_OVER://游戏结束，重置现场
                        flyings=new flying[0];
                        bullets = 0;
                        bullet = new Bullet();
                        score = 0;
                        state = START;
                        break;
                    case RUNNING:
                        bullets--;
                        Bullet bs = bullet.shoot();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {//鼠标进入
                if(state == PAUSE){
                    state = RUNNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {//鼠标退出
                if (state == RUNNING){
                    state = PAUSE;
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {//鼠标移动
                if(state == RUNNING){
                    int x = e.getX();
                    int y = e.getY();
                    bullet.moveTo(x,y);
                }
            }
        };
        this.addMouseListener(l);//处理鼠标点击操作
        this.addMouseMotionListener(l);//处理鼠标滑动操作
        timer = new Timer();//主流程控制
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == RUNNING) {//运行状态
                    enterAction();//飞行物入场
                    stepAction();//走一步
                    outOfBoundsAction();//删除越界飞行物及子弹
                    checkGameOverAction();//检查游戏结束
                }
                repaint();//重绘
            }
        },interval,interval);
    }
    int flyEnteredIndex =0;//飞行物计数

    public void enterAction(){
        flyEnteredIndex++;
        if(flyEnteredIndex % 40 ==0) {//400ms生成一个飞行物
            flying obj = nextOne();//随机生成一个飞行物
            flyings = Arrays.copyOf(flyings, flyings.length + 1);
            flyings[flyings.length - 1] = obj;
        }
    }

    public void stepAction(){
        for(int i=0;i<flyings.length;i++){
            flying f = flyings[i];
            f.step();
        }
    }


    public void outOfBoundsAction(){
        int index = 0;//索引
        flying[] flyingLives = new flying[flyings.length];//活着的飞行物
        for(int i = 0;i<flyings.length;i++){
            flying f = flyings[i];
            if(!f.outOfBoujnds()){
                flyingLives[index++] = f;//不越界的留着
            }
        }
        flyings = Arrays.copyOf(flyingLives,index);
    }

    public void checkGameOverAction(){
        if(isGameOver()==true){
            state = GAME_OVER;
        }
    }

    public boolean isGameOver(){
        if(birdNum==0||bullets==0)return true;
        else return false;
    }

    public void bang(Bullet bullet){//击中检查

    }

    //随机生成飞行物
    public static flying nextOne(){
        Random random = new Random();
        int type = random.nextInt();
        int type = random.nextInt(20);
        if(type<4) {
            return new HappyBird();
        }else{
            return new bird();
        }
    }

}
