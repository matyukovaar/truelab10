import greenfoot.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sandwich extends Food {
    private ArrayList<Ingredient> ingredients;
    private int sizeY;
    private Map<String, String> ingredientPictures;

    public Sandwich() {
        ingredients = new ArrayList<>();
        ingredientPictures = new HashMap<>();
        size = 150;
        sizeY = size * 2;
        updateImage();
    }
    
    public void setIngredientPicture(String type, String picture) {
        ingredientPictures.put(type, picture);
    }
    
    public boolean addIngredient(Ingredient ing) {
        if (ing instanceof Rice || ing instanceof MashedPotatoes) {
            return false;
        }
        if (ing instanceof Bread) {
            ingredients.add(ing);
            updateImage();
            return true;
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
        
        int y = imgH - 20;
        
        for (Ingredient ing : ingredients) {
            GreenfootImage ingredientImage = new GreenfootImage(ing.getPicture());
            ingredientImage.scale(c(ing.size), c(ing.sizeY));
            
            int x = (imgW - ingredientImage.getWidth()) / 2;
            
            y = y - ingredientImage.getHeight() + 10;
            
            g2.drawImage(ingredientImage.getAwtImage(), x, y, null);
        }
        g2.dispose();
        setImage(foodImage);
    }
    
    private String getIngredientType(Ingredient ing) {
        if (ing instanceof Tomato) return "tomato";
        if (ing instanceof Lettuce) return "lettuce";
        if (ing instanceof Sauce) return "sauce";
        if (ing instanceof Bread) return "bread";
        if (ing instanceof MashedPotatoes) return "mashed";
        if (ing instanceof Rice) return "rice";
        if (ing instanceof Egg) return "egg";
        if (ing instanceof Cutlet) return "cutlet";
        return "unknown";
    }
    
    public int ySpawnOffset() {
        return (int)(c(sizeY) / 2.0) + 70;
    }
}