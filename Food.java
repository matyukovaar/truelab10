import greenfoot.*; 
import java.util.ArrayList;

public abstract class Food extends Ingredient 
{
    // домик где хранится данная еда
    public Container home;
    // смещение для спавна
    public abstract int ySpawnOffset();
    // Добавление ингредиента
    public abstract boolean addIngredient(Ingredient ing);
}
