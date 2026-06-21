import greenfoot.*; 
import java.util.ArrayList;

public abstract class Food extends Ingredient 
{
    // смещение для спавна
    public abstract int ySpawnOffset();
    // Добавление ингредиента
    public abstract boolean addIngredient(Ingredient ing);
}
