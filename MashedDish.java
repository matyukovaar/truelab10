import greenfoot.*;
import java.util.ArrayList;

public class MashedDish extends Food {
    private ArrayList<Ingredient> ingredients;
    private int sizeY;
    private Ingredient base;

    public MashedDish() {
        ingredients = new ArrayList<>();
        size = 150;
        sizeY = size * 2;
        updateImage();
    }
    
    public boolean addIngredient(Ingredient ing) {
        if (ing instanceof MashedPotatoes) {
            base = ing;
            updateImage();
            return true;
        }
        if (ing instanceof Rice || ing instanceof MashedPotatoes || ing instanceof Bread) {
            return false;
        }
        if (ing instanceof Tomato || ing instanceof Lettuce || ing instanceof Sauce) {
            ingredients.add(ing);
            updateImage();
            return true;
        }
        if (ing instanceof Egg || ing instanceof Cutlet) {
            if (ing.getHomeContainer() instanceof Pan) {
                Pan pan = (Pan) ing.getHomeContainer();
                if (pan.isCooked()) {
                    ingredients.add(ing);
                    updateImage();
                    return true;
                }
            }
        }
        return false;
    }
    
    public void removeIngredient(Ingredient ing) {
        ingredients.remove(ing);
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
        
        if (base != null) {
            GreenfootImage baseImage = new GreenfootImage(base.getPicture());
            baseImage.scale(c(base.size), c(base.sizeY));
            int x = (imgW - baseImage.getWidth()) / 2;
            int y = imgH - baseImage.getHeight() - 10;
            g2.drawImage(baseImage.getAwtImage(), x, y, null);
        }
        
        for (Ingredient ing : ingredients) {
            GreenfootImage ingImage = new GreenfootImage(ing.getPicture());
            ingImage.scale(c(ing.size), c(ing.sizeY));
            
            int maxW = imgW - ingImage.getWidth();
            int x = maxW > 0 ? Greenfoot.getRandomNumber(maxW) : 0;
            
            int yOffset = 20 + Greenfoot.getRandomNumber(21);
            int y = imgH - yOffset - ingImage.getHeight();
            
            g2.drawImage(ingImage.getAwtImage(), x, y, null);
        }
        
        g2.dispose();
        setImage(foodImage);
    }
    
    public int ySpawnOffset() {
    return (int)(c(sizeY) / 2.0) + 70;
}
}