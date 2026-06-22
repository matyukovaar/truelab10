import greenfoot.*;
import java.util.List;
 
public class Client extends Actor {
    double COEF = Kuhnya.COEF; 
    private int patience = 900;
    private int maxPatience = 900;
    private Order orderCloud;
    private int slotNum;
    
    public Client(int num) {
        GreenfootImage img = new GreenfootImage("client1.png");
        img.scale((int)(150*COEF), (int)(COEF*100));
        setImage(img);
        slotNum = num;
    }
 
    protected void addedToWorld(World world) {
        orderCloud = new Order();
        world.addObject(orderCloud, getX(), getY() - (int)(190 * COEF)); 
    }
 
    public void act() {
        if (GameManager.isPaused) return;
        losePatience();
    }
 
    private void losePatience() {
        if (patience > 0) {
            patience--;
 
            double percent = (double) patience / maxPatience;
 
            if (orderCloud != null) {
                orderCloud.updateMood(percent);
            }
        } else {
            leaveAngry();
        }
    }
 
 
    public boolean receiveFood(Food food) {
        if (orderCloud == null) {
            return false;
        }
 
        boolean delivered = orderCloud.tryDeliver(food);
 
        if (delivered) {
            if (orderCloud.isComplete()) {
                rewardAndLeave();
            }
        } else {
            getWorld().showText("Это не мой заказ!", getX(), getY() - 150);
        }
 
        return delivered;
    }
 
    private void rewardAndLeave() {
        int pointsReward = 100;
        double percent = (double) patience / maxPatience;
        if (percent > 0.6) pointsReward = 150;
        else if (percent <= 0.25) pointsReward = 50;
 
        List<GameManager> managers = getWorld().getObjects(GameManager.class);
        if (!managers.isEmpty()) {
            managers.get(0).addScore(pointsReward);
        }
        removeSelf();
    }
 
    private void leaveAngry() {
        removeSelf();
    }
 
    private void removeSelf() {
        Kuhnya world = (Kuhnya)(getWorld());
        if (orderCloud != null && orderCloud.getWorld() != null) {
            getWorld().removeObject(orderCloud);
        }
        if (world != null) {
            getWorld().removeObject(this);
            world.getSpawner().freeSlot(slotNum);
        }
    }
}