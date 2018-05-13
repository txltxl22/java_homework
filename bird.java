import java.util.Random;
public class bird extends flying implements Enemy{
    private int speed = 3;//移动速度

    //初始化数据
    public bird(){
        this.image = ShootGame.bird;
        width = image.getWidth();
        height = image.getHeight();
        x= -width;
        Random rand = new Random();
        y = rand.nextInt(ShootGame.HEIGHT - height);
    }
    //获取分数
    @Override
    public int getScore(){
        return 5;
    }

    //越界处理
    @Override
    public boolean outOfBounds(){
        return x>ShootGame.WIDTH;
    }
    //移动
    @Override
    public void step(){
        x+= speed;
    }
}
