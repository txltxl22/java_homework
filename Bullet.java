import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.image.BufferedImage;
/**
 * 准星类
 */
public class Bullet extends flying {
    private BufferedImage[] images={};//准星图片
    private int index = 0;//准星不同状态下图片切换索引
    private int num = 50;//子弹数
    //初始化
    public  Bullet(){
        images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1};
        image = ShootGame.hero0;
        width = image.getWidth();
        height = image.getHeight();
        x=150;
        y=400;
    }
    //增加子弹
    public void addBullet(){
        num+=5;
    }
    //当前物体移动了一下，相对距离，x,y鼠标位置
    public void moveTo(int x, int y){
        this.x=x-width/2;
        this.y=y-height/2;
    }
    //越界处理
    @Override
    public boolean outOfBounds(){
        return false;
    }

    public boolean shoot(){

    }

}
