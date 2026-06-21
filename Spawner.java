import greenfoot.*;

public class Spawner extends Actor {
    double COEF = Kuhnya.COEF;
    private String ingredientType;
    private GreenfootImage originalImage;
    
    public Spawner(String type, String imageName) {
        this.ingredientType = type;
        GreenfootImage img = new GreenfootImage(imageName);
        img.scale(c(80), c(80));
        setImage(img);
        originalImage = img;
    }
    
    public void act() {
        if (Greenfoot.mousePressed(this)) {
            spawnIngredient();
        }
        // Подсветка при наведении
        if (Greenfoot.mouseMoved(null) && Greenfoot.getMouseInfo() != null) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            Actor actor = mouse.getActor();
            if (actor == this) {
                GreenfootImage highlighted = new GreenfootImage(originalImage);
                highlighted.setColor(new Color(255, 255, 0, 100));
                highlighted.fill();
                highlighted.drawImage(originalImage, 0, 0);
                setImage(highlighted);
            } else {
                setImage(originalImage);
            }
        }
    }
    
    private void spawnIngredient() {
        Ingredient ing = createIngredient();
        if (ing != null) {
            getWorld().addObject(ing, getX(), getY());
            ing.setLocation(getX() + c(20), getY() + c(20));
        }
    }
    
    private Ingredient createIngredient() {
        switch(ingredientType) {
            case "bread": return new Bread();
            case "tomato": return new Tomato();
            case "mashed": return new MashedPotatoes();
            case "rice": return new Rice();
            case "sauce": return new Sauce();
            case "lettuce": return new Lettuce();
            case "egg": return new Egg();
            case "cutlet": return new Cutlet();
            default: return null;
        }
    }
    
    public int c(int x) {
        return (int)(x * COEF);
    }
}