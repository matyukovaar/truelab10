import greenfoot.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Sandwich extends Food {
    private ArrayList<Ingredient> ingredients;
    private int sizeY;
    private Map<String, String> ingredientPictures;

    public Sandwich() {
        ingredients = new ArrayList<>();
        ingredientPictures = new HashMap<>();
        size = 150;
        sizeY = size;
        updateImage();
    }
    
    public void setIngredientPicture(String type, String picture) {
        ingredientPictures.put(type, picture);
    }
    
    // получение списка ингредиентов
    public List<Ingredient> getIngredients() {
    return new ArrayList<>(ingredients); 
    }
    
    public boolean addIngredient(Ingredient ing) {
        // проверка макс размера
        // if (ingredients.size() > 3) {
            // return false;
        // }
        // кусочковый добавляется
        if (ing instanceof pieceIngredient || ing instanceof cookableIngredient) {
            ingredients.add(ing);
            updateImage();
            return true;
        }
        return false;
    }
    
    public void removeIngredient(Ingredient ing) {
        ingredients.remove(ing);
        updateImage();
    }

    private void updateImage() {
        int imgW = c(size);
        int baseHeight = Bread.BREADH;
        
        List<GreenfootImage> tempImages = new ArrayList<>();
        int totalIngredientsHeight = 0;
    
        for (Ingredient ing : ingredients) {
            GreenfootImage tempImg = new GreenfootImage(ing.getPicture());
            tempImg.scale(c(ing.size), c(ing.sizeY));
            tempImages.add(tempImg);
            totalIngredientsHeight += tempImg.getHeight()/2;
        }
        int spacing = 0;//5 * ingredients.size(); 
        int finalHeight = baseHeight + totalIngredientsHeight + spacing;
        //System.out.println(finalHeight);
        GreenfootImage foodImage = new GreenfootImage(imgW, c(finalHeight));
        
        if (Kuhnya.TESTZONES) {
            foodImage.setColor(new Color(Greenfoot.getRandomNumber(256), 
            Greenfoot.getRandomNumber(256), Greenfoot.getRandomNumber(256), 100));
            foodImage.fill();
        }
        
        java.awt.Graphics2D g2 = foodImage.getAwtImage().createGraphics();
        int y = c(finalHeight); // низ спрайта
        y -= Bread.BREADH/2; // подъем на половину хлеба чтобы первый хлеб был не внизу
        for (GreenfootImage img : tempImages) {
            int x = (imgW - img.getWidth()) / 2; // Центрируем по горизонтали
            
            // Сдвигаем Y вверх на высоту картинки
            y = y - c(img.getHeight()/2); 
            
            // Рисуем
            g2.drawImage(img.getAwtImage(), x, y, null);
        
            // y -= 5; 
        }   
    
        g2.dispose();
        setImage(foodImage);
        
        // Обновляем размер объекта, если это нужно для коллизий
        sizeY = finalHeight; 
            
        // чтобы не съезжаьт
        if (this.home != null) {
            ((Plate)(home)).returnIngredient(this);}       
    }
    

    private String getIngredientType(Ingredient ing) {
        if (ing instanceof Tomato) return "tomato";
        if (ing instanceof Lettuce) return "lettuce";
        if (ing instanceof Sauce) return "sauce";
        if (ing instanceof Bread) return "bread";
        if (ing instanceof MashedPotatoes) return "mashed";
        if (ing instanceof Rice) return "rice";
        if (ing instanceof Egg) return "egg";
        if (ing instanceof Cutlet) return "cutlet";
        return "unknown";
    }
    
    public int ySpawnOffset() {
        return sizeY/2  + c(20);
    }
}