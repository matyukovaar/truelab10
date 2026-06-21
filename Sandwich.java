import greenfoot.*;  
import java.util.ArrayList;

public class Sandwich extends Food
{
    // состав сендвича
    ArrayList<Ingredient> ingredients;
    // смещение каждого инга
    private static final int YOFF = 17;
    // дополнительный размер который больше обычного для высоких блюд
    int sizeY;

    public Sandwich() {
        ingredients = new ArrayList<>();
        setPicture();
        size = 150;
        sizeY = size*2;
    }
    // добавление в блюлюдо ингре инг чунг чанг чон вон хочу отт тебя 7 детей
    public boolean addIngredient(Ingredient ing){
        if (ing instanceof Tomato || ing instanceof Bread) {
            ingredients.add(ing);
            updateImage();
            return true;
        }
        return false;
    }
    // обновление спрайта еды
    //private void updateImage() {setImage("bomj.png");}; // отладочный
    private void updateImage() {
        // новое имаге
        GreenfootImage foodImage = new GreenfootImage(c(size), c(sizeY));
        
        // отладочная отрисовка зоны еды
        if (Kuhnya.TESTZONES) {
        foodImage.setColor(new Color(Greenfoot.getRandomNumber(256), 
        Greenfoot.getRandomNumber(256), Greenfoot.getRandomNumber(256), 100));
        foodImage.fill();
        }
        
        // графические штучки
        java.awt.Graphics2D g2 = foodImage.getAwtImage().createGraphics();
        // рисуем каждый ингредиент
        int y = sizeY-90;
        for (Ingredient ing : ingredients) {
            GreenfootImage ingredientImage = new GreenfootImage(ing.getPicture());
            ingredientImage.scale(c(ing.size), c(ing.size));
            // центрирование
            int x = (foodImage.getWidth() - ingredientImage.getWidth()) / 2 + 8;
            // вертикальная позиция + смещение
            g2.drawImage(ingredientImage.getAwtImage(), c(x), c(y), null);
            y -= YOFF;
        }
        g2.dispose();
        setImage(foodImage);
    }
    // смещение для корректного спавна нового спрайта еды
    public int ySpawnOffset() {
        return (int)(sizeY/2.0);
    }
}
