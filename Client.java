import greenfoot.*;

public class Client extends Actor {
    private int patience = 900; 
    private int maxPatience = 900;
    private Order orderCloud; 

    public Client() {
        int skinNumber = Greenfoot.getRandomNumber(3) + 1;
        GreenfootImage img = new GreenfootImage("client" + skinNumber + ".png");
        img.scale(90, 120); 
        setImage(img);
    }

    protected void addedToWorld(World world) {
        orderCloud = new Order(); 
        world.addObject(orderCloud, getX(), getY() - 90); 
    }

    public void act() {
        if (GameManager.isPaused) return;
        losePatience();
    }

    private void losePatience() {
        if (patience > 0) {
            patience--;
            
            // Считаем процент оставшегося времени (от 1.0 до 0.0)
            double percent = (double) patience / maxPatience;
            
            // Передаем этот процент в облачко заказа, чтобы оно меняло картинку смайлика
            if (orderCloud != null) {
                orderCloud.updateMood(percent);
            }
        } else {
            leaveAngry();
        }
    }
/*    public void receivePlate(Plate plate) {
        if (orderCloud != null && orderCloud.checkPlate(plate)) {
            int pointsReward = 100;
            double percent = (double) patience / maxPatience;

            if (percent > 0.6) pointsReward = 150; 
            else if (percent <= 0.25) pointsReward = 50; 

            java.util.List<GameManager> managers = getWorld().getObjects(GameManager.class);
            if (!managers.isEmpty()) {
                managers.get(0).addScore(pointsReward);
            }

            plate.clearPlate(); 
            removeSelf();
        } else {
            getWorld().showText("Это не мой заказ!", getX(), getY() - 150);
        }
    }*/

    private void leaveAngry() {
        removeSelf();
    }

    private void removeSelf() {
        if (orderCloud != null && orderCloud.getWorld() != null) {
            getWorld().removeObject(orderCloud);
        }
        getWorld().removeObject(this);
    }
}
