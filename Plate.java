import greenfoot.*; 

public class Plate extends Container
{
    // еда на тарелке
    Food food;
    public void act() {
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
    
    public Plate(int x1, int x2, int y1, int y2) {
        super(x1, x2, y1, y2); 
    }
    
    // пустая ли тарелка
    public boolean isEmpty() {
        if (food == null) return true;
        return false;
    }
    
    // добавить кусок еды (положить огрызок) на тарелку
    public boolean addIngredient(Ingredient ing) {
        // если тарелка пуста
        if (isEmpty()) {
            // если кладем в пустую какое-то собранное Блюдо
            if (ing instanceof Food) {
                // запоминаем это блюдо как наше, и говорим ему
                ((Plate)(((Food)(ing)).home)).food = null; // старая тарелка забывает это блюдо 
                ((Food)(ing)).home = this;
                food = (Food)ing;
                // кладем еду куда надо
                int centerX = x1 + (x2 - x1) / 2;
                food.setLocation(c(centerX), c(y2-food.ySpawnOffset()));
                return true;
            }
            // если кладем в пустую хлеб
            if (ing instanceof Bread) {
                // создаем новое блюдо бутерброд и сразу кладем туда хлеб, рождаем его
                food = new Sandwich();
                food.addIngredient(ing);
                int centerX = x1 + (x2 - x1) / 2;
                getWorld().addObject(food, c(centerX), c(y2-food.ySpawnOffset()));
                // бутерброд запоминает свой дом
                food.home = this;
                return true;
            }
            // если кладем не базу
            return false;
        }
        // если не пусто
        if (food instanceof Sandwich) {
            return food.addIngredient(ing);
        }
        return false;
    }
    
    // освобождение тарелки
    public boolean makeFree() {
        if (food != null) {
            food.home = null;
            food = null;
            return true;
        }
        return false;
    }
}
