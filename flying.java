import java.awt.image.BufferedImage;

/**
 *飞行物（飞鸟，奖励品，准星）
 */
public class flying {
    protected int x;//x坐标
    protected int y;//y坐标
    protected int width;//宽
    protected int height;//高
    protected BufferedImage image;//图片

    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x=x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getWidth(){
        return width;
    }
    public void setWidth(int width){
        this.width=width
    }
    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height=height;
    }

    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image){
        this.image=image;
    }
    public abstract boolean outOfBoujnds();//检查是否出界
    public abstract void step();//飞行物移动
    /**
     * 检查当前分行恶是否被击中
     * @return true表示被击中了
     */
    public boolean shootBy(Bullet bullet){
        int x=bullet.x;
        int y=bullet.y;
        return this.x<x&&x<this.x+width && this.y<y && y<this.y+height;
    }
}
