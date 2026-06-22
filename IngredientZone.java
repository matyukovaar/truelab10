import greenfoot.*;

public class IngredientZone extends Actor {
    double COEF = Kuhnya.COEF;
    // неизменный ингредиент тайп для зоны
    private String ingredientType;
    private GreenfootImage zoneImage;
    
    public IngredientZone(String type, String imageName, int width, int height) {
    this.ingredientType = type;
    zoneImage = new GreenfootImage(c(width), c(height));
    zoneImage.setColor(new Color(0, 0, 0, 0));
    zoneImage.fill();
    
    setImage(zoneImage);
}
    
    public void act() {
        if (Greenfoot.mousePressed(this)) {
            spawnIngredient();
        }
    }
    
    // реальное создание ингредиента
    private void spawnIngredient() {
        Ingredient ing = createIngredient();
        if (ing != null) {
            ing.setHomeZone(this);
            getWorld().addObject(ing, getX(), getY());
        }
    }
    
    // создание ингредиента именно new
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
    //пиксельное смещение
    public int c(int x) {
        return (int)(x * COEF);
    }
    // возврат типо ингредиента который спавнится в этой зоне
    public String getType() {
        return ingredientType;
    }
}