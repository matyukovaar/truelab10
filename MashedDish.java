import greenfoot.*;

public class MashedDish extends Food {
    private Ingredient ing1, ing2;
    private int sizeY;

    public MashedDish() {
        ing1 = null;
        ing2 = null;
        size = 100;
        sizeY = size;
        updateImage();
    }
    
    public boolean addIngredient(Ingredient ing) {
        if (ing instanceof Rice || ing instanceof MashedPotatoes || ing instanceof Bread) {
            return false;
        }
        if (ing instanceof pieceIngredient || ing instanceof cookableIngredient) {
            if (ing1 == null) {
                ing1 = ing;
            } else if (ing2 == null) {
                ing2 = ing;
            } else {
                return false;
            }
            updateImage();
            return true;
        }
        return false;
    }
    
    public void removeIngredient(Ingredient ing) {
        if (ing1 == ing) {
            ing1 = null;
        } else if (ing2 == ing) {
            ing2 = null;
        }
        updateImage();
    }
    
    private void updateImage() {
    int imgW = c(size);
    int imgH = c(sizeY);
    GreenfootImage foodImage = new GreenfootImage(imgW, imgH);
    
    if (Kuhnya.TESTZONES) {
        foodImage.setColor(new Color(Greenfoot.getRandomNumber(256), 
        Greenfoot.getRandomNumber(256), Greenfoot.getRandomNumber(256), 100));
        foodImage.fill();
    }
    
    java.awt.Graphics2D g2 = foodImage.getAwtImage().createGraphics();
    
    GreenfootImage riceImage = new GreenfootImage("mashed.png");
    riceImage.scale(imgW, imgH);
    g2.drawImage(riceImage.getAwtImage(), 0, 0, null);
    
    int scale = (int)(imgH*0.6);
    int offset = (int)(imgH/2.5);
    
    if (ing1 != null) {
        GreenfootImage ingImage = new GreenfootImage(ing1.getPicture());
        // Убрали c() — scale уже содержит коэффициент через imgH
        ingImage.scale(ing1.size * scale / 100, ing1.sizeY * scale / 100);
        int x = offset;
        int y = imgH - offset - ingImage.getHeight();
        g2.drawImage(ingImage.getAwtImage(), x, y, null);
    }
    
    if (ing2 != null) {
        GreenfootImage ingImage = new GreenfootImage(ing2.getPicture());
        // Убрали c() — scale уже содержит коэффициент через imgH
        ingImage.scale(ing2.size * scale / 100, ing2.sizeY * scale / 100);
        int x = imgW - ingImage.getWidth() - offset;
        int y = imgH - offset - ingImage.getHeight() - c(30);
        g2.drawImage(ingImage.getAwtImage(), x, y, null);
    }
    
    g2.dispose();
    setImage(foodImage);
}
    
    public int ySpawnOffset() {
        return (int)(sizeY*4/9);
    }
}