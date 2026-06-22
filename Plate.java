import greenfoot.*;

public class Plate extends Container {
    Food food = null;
    int id = Greenfoot.getRandomNumber(555);
    public void act() {
        if(GameManager.isPaused) return;
        // отладочно
        if (TESTZONES) {
            if (isEmpty()) {
                GreenfootImage photo = getImage();
                photo.setColor(new Color(0, 255, 0, 100)); 
                photo.fill();
                setImage(photo);
                }
            else {
                GreenfootImage photo = getImage();
                photo.setColor(new Color(255, 0, 0, 100)); 
                photo.fill();
                setImage(photo);
            }
        }
    }
    
    public Plate(int x1, int x2, int y1, int y2) {
        super(x1, x2, y1, y2); 
    }
    
    public boolean isEmpty() {
        if (food == null) return true;
        return false;
    }
    
    public boolean addIngredient(Ingredient ing) {
        if (isEmpty()) {
            if (ing instanceof Food) {
                if (((Food)(ing)).home != null && ((Food)(ing)).home instanceof Plate) {
                    // makefree
                    ((Plate)(((Food)(ing)).home)).food = null;
                }
                ((Food)(ing)).home = this;
                food = (Food)ing;
                int centerX = x1 + (x2 - x1) / 2;
                food.setLocation(c(centerX), c(y2-food.ySpawnOffset()));
                return true;
            }
            // Хлеб = Sandwich
            if (ing instanceof Bread) {
                food = new Sandwich();
                food.addIngredient(ing);
                int centerX = x1 + (x2 - x1) / 2;
                getWorld().addObject(food, c(centerX), c(y2-food.ySpawnOffset()));
                food.home = this;
                getWorld().removeObject(ing);
                return true;
            }
            // Рис = RiceDish
            if (ing instanceof Rice) {
                food = new RiceDish();
                food.addIngredient(ing);
                int centerX = x1 + (x2 - x1) / 2;
                getWorld().addObject(food, c(centerX), c(y2-food.ySpawnOffset()));
                food.home = this;
                getWorld().removeObject(ing);
                return true;
            }
            // Пюре = MashedDish
            if (ing instanceof MashedPotatoes) {
                food = new MashedDish();
                food.addIngredient(ing);
                int centerX = x1 + (x2 - x1) / 2;
                getWorld().addObject(food, c(centerX), c(y2-food.ySpawnOffset()));
                food.home = this;
                getWorld().removeObject(ing);
                return true;
            }
            return false;
        }
        // если не пуста
        if (food.addIngredient(ing)) {
            getWorld().removeObject(ing);
            return true;
        }
        return false;
    }
    
    public boolean makeFree() {
        if (food != null) {
            food.home = null;
            food = null;
            return true;
        }
        return false;
    }
    
    public void returnIngredient(Ingredient ing) {
        //System.out.println(""+food.ySpawnOffset());
        if (food != null) {
            int centerX = x1 + (x2 - x1) / 2;
            ing.setLocation(c(centerX), c(y2 - food.ySpawnOffset()));
        }
    }
    
    // public void removeIngredient(Ingredient ing) {
        // if (food != null) {
            // food.removeIngredient(ing);
        // }
    // }
    
    public Food getFood() {
    return food;
}
}