import greenfoot.*; 

public class Plate extends Container
{
    // еда на тарелке
    Food food;
    // координаты тарелочной зоны тарелочного гейства
    int x1; int x2; int y1; int y2;
    
    public Plate(int x1, int x2, int y1, int y2) {
        super(x1, x2, y1, y2); 
        this.x1 = x1; this.x2 = x2; this.y1 = y1; this.y2 = y2;
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
            // если кладем в пустую хлеб
            if (ing instanceof Bread) {
                food = new Sandwich();
                food.addIngredient(ing);
                int centerX = x1 + (x2 - x1) / 2;
                getWorld().addObject(food, c(centerX), c(y2-food.ySpawnOffset()));
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
}
