import greenfoot.*;

public class Pan extends Container {
    private Ingredient cookingItem;
    private int cookingState = 0;
    private int cookingTime = 0;
    private static final int COOKED_TIME = 600;
    private static final int BURNT_TIME = 1200;
    
    public Pan(int x1, int x2, int y1, int y2) {
        super(x1, x2, y1, y2);
    }
    
    public void act() {
         if(GameManager.isPaused) {
            return;
        }
        updateCooking();
        updateImage();
        
    }
    
    private void updateCooking() {
        if (cookingItem != null && (cookingState == 1 || cookingState == 2)) {
            cookingTime++;
            if (cookingTime >= BURNT_TIME) {
                cookingState = 3;
            } else if (cookingTime >= COOKED_TIME && cookingState == 1) {
                cookingState = 2;
            }
        }
    }
    
    private void updateImage() {
        GreenfootImage photo = new GreenfootImage(c(x2 - x1), c(y2 - y1));
        
        if (cookingState == 0) {
            photo.setColor(new Color(0, 255, 0, 100));
        } else if (cookingState == 1) {
            photo.setColor(new Color(255, 165, 0, 150));
        } else if (cookingState == 2) {
            photo.setColor(new Color(0, 255, 0, 200));
        } else if (cookingState == 3) {
            photo.setColor(new Color(50, 50, 50, 200));
        }
        photo.fill();
        setImage(photo);
    }
    
    public boolean isEmpty() {
        return cookingItem == null;
    }
    
    public boolean addIngredient(Ingredient ing) {
        if ((ing instanceof Egg || ing instanceof Cutlet) && isEmpty()) {
            cookingItem = ing;
            cookingState = 1;
            cookingTime = 0;
            ing.setHomeContainer(this);
            
            int centerX = x1 + (x2 - x1) / 2;
            int centerY = y1 + (y2 - y1) / 2;
            ing.setLocation(c(centerX), c(centerY));
            return true;
        }
        return false;
    }
    
    public void returnIngredient(Ingredient ing) {
        if (cookingItem == ing) {
            int centerX = x1 + (x2 - x1) / 2;
            int centerY = y1 + (y2 - y1) / 2;
            ing.setLocation(c(centerX), c(centerY));
        }
    }
    
    public void removeIngredient(Ingredient ing) {
        if (cookingItem == ing) {
            cookingItem = null;
            cookingState = 0;
            cookingTime = 0;
        }
    }
    
    public boolean makeFree() {
        cookingItem = null;
        cookingState = 0;
        cookingTime = 0;
        return true;
    }
    
    public boolean isCooked() {
        return cookingState == 2;
    }
    
    public boolean isBurnt() {
        return cookingState == 3;
    }
    
    public int getCookingState() {
        return cookingState;
    }
    
    public Ingredient getCookingItem() {
        return cookingItem;
    }
}