import greenfoot.*;

public class DrinkStation extends Container {
    private Cup cup;
    private int state = 0;
    private int cookingTime = 0;
    private static final int COOK_TIME = 300;
    private String drinkType;
    private String drinkPicture;
    
    public DrinkStation(int x1, int x2, int y1, int y2, String type) {
        super(x1, x2, y1, y2);
        this.drinkType = type;
        
        if (type.equals("coffee1")) {
            drinkPicture = "coffee1.png";
        } else if (type.equals("coffee2")) {
            drinkPicture = "coffee2.png";
        } else if (type.equals("coffee3")) {
            drinkPicture = "coffee3.png";
        }
    }
    
    public void act() {
        if(GameManager.isPaused) return;
        if (getWorld() != null) {
            updateCooking();
            updateImage();
        }
    }
    
    private void updateCooking() {
        if (cup != null && state == 1) {
            cookingTime++;
            if (cookingTime >= COOK_TIME) {
                state = 2;
                if (cup != null && cup.getWorld() != null) {
                    cup.setPicture(drinkPicture);
                }
            }
        }
    }
    
    
    private void updateImage() {
        GreenfootImage photo = new GreenfootImage(c(x2 - x1), c(y2 - y1));
        
        // отладочно
        if (TESTZONES) {
            if (state == 0) {
                photo.setColor(new Color(0, 255, 0, 100));
            } else if (state == 1) {
                photo.setColor(new Color(255, 165, 0, 150));
            } else if (state == 2) {
                photo.setColor(new Color(0, 255, 0, 200));
            }
            photo.fill();
            setImage(photo);
        }
        }
    
    public boolean isEmpty() {
            return cup == null;
        }    
    public boolean addIngredient(Ingredient ing) {
    if (ing instanceof Cup && isEmpty()) {
        cup = (Cup) ing;
        state = 1;
        cookingTime = 0;
        ((Cup)(ing)).setHomeContainer(this);
        
        int centerX = x1 + (x2 - x1) / 2;
        int centerY = y1 + (y2 - y1) / 2;
        ing.setLocation(c(centerX), c(centerY));
        return true;
        }
        return false;
    }
    
    public void returnIngredient(Ingredient ing) {
        if (cup == ing && ing.getWorld() != null) {
            int centerX = x1 + (x2 - x1) / 2;
            int centerY = y1 + (y2 - y1) / 2;
            ing.setLocation(c(centerX), c(centerY));
        }
    }
    
    public void removeIngredient(Ingredient ing) {
        if (cup == ing) {
            cup = null;
            state = 0;
            cookingTime = 0;
        }
    }
    
    public boolean makeFree() {
        cup = null;
        state = 0;
        cookingTime = 0;
        return true;
    }
    
    public int getState() {
        return state;
    }
    
    public Cup getCup() {
        return cup;
    }
}