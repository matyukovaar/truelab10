import greenfoot.*;

public class Pan extends Container {
    cookableIngredient cooking = null;
    
    //int id = Greenfoot.getRandomNumber(555);
    
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
        // если не пусто = жарить
        if (!isEmpty()) {
            cooking.cook();
        }
    }
    
    public Pan(int x1, int x2, int y1, int y2) {
        super(x1, x2, y1, y2); 
    }
    
    public boolean isEmpty() {
        if (cooking  == null) return true;
        return false;
    }
    
    public boolean addCookable(Ingredient ing) {
        if (isEmpty()) {
            // если сковорода пуста то добавить туда котлетко или яйцо
            if (ing instanceof cookableIngredient) {
                // берем ингредиент и запоминаем его
                cooking = (cookableIngredient)ing;
                // если где-то лежало = теперь не лежит
                if (cooking.pan != null) {
                    ((Pan)(cooking.pan)).makeFree();
                }
                // и говорим что мы его дом
                cooking.pan = this;
                
                int centerX = x1 + (x2 - x1) / 2;
                cooking.setLocation(c(centerX), c(y2-cooking.ySpawnOffset()));
                return true;
            }
        }
        return false;
    }
    public boolean makeFree() {
        if (cooking != null) {
            cooking.pan = null;
            cooking  = null;
            return true;
        }
        return false;
    }
    
    public void returnIngredient(Ingredient ing) {
        if (cooking != null) {
            int centerX = x1 + (x2 - x1) / 2;
            ing.setLocation(c(centerX), c(y2 - cooking.ySpawnOffset()));
        }
    }
    
    public cookableIngredient getFood() {
        return cooking;
    }
}