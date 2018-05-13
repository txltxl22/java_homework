import java.util.Random;
//奖励鸟
public class HappyBird extends flying implements Award {
    private int xSpeed = 1;//x坐标移动速度
    private int ySpeed = 2;//y坐标移动速度
    private  int awardType; //奖励类型

    //初始化数据
    public HappyBird(){
        this.image = ShootGame.HappyBird;
        width=image.getWidth();
        height=image.getHeight();
        x = -width;
        Random rand = new Random();
        y=rand.nextInt(ShootGame.HEIGHT - height);
        awardType = rand.nextInt(2);
    }

    //获得奖励类型
    public int getType(){
        return awardType;
    }

    //越界处理
    @Override
    public boolean outOfBounds(){
        return x>ShootGame.WIDTH;
    }
    //移动（待修改）
    @Override
    public void step(){
        x +=xSpeed;
        y+=ySpeed;
        if(y>ShootGame.HEIGHT-height){
            ySpeed = -2;
        }
        if(y<0){
            ySpeed = 2;
        }
    }
}
