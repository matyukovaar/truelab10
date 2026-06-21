import greenfoot.*;

public class CupSpawner extends Actor {
    double COEF = Kuhnya.COEF;
    private GreenfootImage zoneImage;
    
    public CupSpawner(int width, int height) {
        zoneImage = new GreenfootImage(c(width), c(height));
        GreenfootImage cupImage = new GreenfootImage("cup.png");
        cupImage.scale(c(50), c(70));
        
        zoneImage.setColor(new Color(200, 200, 200, 100));
        zoneImage.fillRect(0, 0, c(width), c(height));
        zoneImage.drawImage(cupImage, (c(width) - c(50))/2, (c(height) - c(70))/2);
        
        setImage(zoneImage);
    }
    
    public void act() {
        if (Greenfoot.mousePressed(this)) {
            spawnCup();
        }
    }
    
    private void spawnCup() {
        Cup cup = new Cup();
        cup.setHomeZone(null);
        getWorld().addObject(cup, getX(), getY());
    }
    
    public int c(int x) {
        return (int)(x * COEF);
    }
}