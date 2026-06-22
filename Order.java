import greenfoot.*;
import java.util.*;
import java.util.function.Supplier;

public class Order extends Actor {
    // ======================== НАСТРОЙКИ ГЕНЕРАЦИИ ========================
    private static final int DISHES_COUNT = 2;

    private static final Map<Class<? extends Food>, Supplier<Food>> DISH_FACTORIES = new HashMap<>();
    private static final Map<Class<? extends Ingredient>, Supplier<Ingredient>> INGREDIENT_FACTORIES = new HashMap<>();

    private static final List<Class<? extends Ingredient>> SANDWICH_INGREDIENTS = new ArrayList<>();
    private static final List<Class<? extends Ingredient>> RICE_INGREDIENTS = new ArrayList<>();

    private static final int SANDWICH_MIN = 1;
    private static final int SANDWICH_MAX = 3; 
    private static final int RICE_MIN = 0;
    private static final int RICE_MAX = 2;

    static {
        DISH_FACTORIES.put(Sandwich.class, Sandwich::new);
        DISH_FACTORIES.put(RiceDish.class, RiceDish::new);

        INGREDIENT_FACTORIES.put(Bread.class, Bread::new); 
        INGREDIENT_FACTORIES.put(Tomato.class, Tomato::new);
        INGREDIENT_FACTORIES.put(Lettuce.class, Lettuce::new);
        INGREDIENT_FACTORIES.put(Sauce.class, Sauce::new);
        INGREDIENT_FACTORIES.put(Egg.class, Egg::new);
        INGREDIENT_FACTORIES.put(Cutlet.class, Cutlet::new);

        SANDWICH_INGREDIENTS.add(Tomato.class);
        SANDWICH_INGREDIENTS.add(Lettuce.class);
        SANDWICH_INGREDIENTS.add(Sauce.class);
        SANDWICH_INGREDIENTS.add(Egg.class);
        SANDWICH_INGREDIENTS.add(Cutlet.class);

        RICE_INGREDIENTS.add(Egg.class);
        RICE_INGREDIENTS.add(Cutlet.class);
    }

    private List<Food> requiredDishes;
    private List<Food> deliveredDishes;
    private boolean isHovered = false;
    private double currentMood = 1.0;

    public Order() {
        requiredDishes = new ArrayList<>();
        deliveredDishes = new ArrayList<>();
        generateOrder();
        redrawCloud();
    }

    public void act() {
        checkHover();
    }

    private void checkHover() {
        if (getWorld() == null) return;
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            List<Order> orders = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), Order.class);
            if (orders.contains(this)) {
                if (!isHovered) { isHovered = true; drawTextOverlay(); }
            } else {
                if (isHovered) { isHovered = false; redrawCloud(); }
            }
        }
    }

    // ======================== ГЕНЕРАЦИЯ ЗАКАЗА ===========================
    private void generateOrder() {
        List<Class<? extends Food>> availableDishes = new ArrayList<>(DISH_FACTORIES.keySet());
        for (int i = 0; i < DISHES_COUNT; i++) {
            Class<? extends Food> dishClass = availableDishes.get(Greenfoot.getRandomNumber(availableDishes.size()));
            Food dish = DISH_FACTORIES.get(dishClass).get();
            addRandomIngredients(dish, dishClass);
            requiredDishes.add(dish);
        }
    }

    private void addRandomIngredients(Food dish, Class<? extends Food> dishClass) {
        if (dishClass == Sandwich.class) {
            Ingredient bread = INGREDIENT_FACTORIES.get(Bread.class).get();
            dish.addIngredient(bread);
            addRandomFromList(dish, SANDWICH_INGREDIENTS, SANDWICH_MIN, SANDWICH_MAX);
        } else if (dishClass == RiceDish.class) {
            addRandomFromList(dish, RICE_INGREDIENTS, RICE_MIN, RICE_MAX);
        }
    }

    private void addRandomFromList(Food dish, List<Class<? extends Ingredient>> available, int min, int max) {
        if (available.isEmpty()) return;
        int count = min + Greenfoot.getRandomNumber(max - min + 1);
        
        List<Class<? extends Ingredient>> shuffled = new ArrayList<>(available);
        Collections.shuffle(shuffled, new Random());

        for (int i = 0; i < count && i < shuffled.size(); i++) {
            Ingredient ing = INGREDIENT_FACTORIES.get(shuffled.get(i)).get();
            setCookedState(ing); 
            dish.addIngredient(ing); 
        }
    }

    // приготовление всех готовимых
    private void setCookedState(Ingredient ing) {
        // System.out.println("вызов метода");
        if (ing instanceof cookableIngredient) {
            ((cookableIngredient) ing).setState(1);
        }
    }

    // ======================== ОТРИСОВКА ОБЛАЧКА ==========================
    
    private void redrawCloud() {
        int w = c(220);
        int h = c(260); 
        GreenfootImage bg = new GreenfootImage(w, h);
        
        bg.setColor(Color.WHITE);
        bg.fillOval(0, 0, w, h);
        bg.setColor(Color.BLACK);
        bg.drawOval(0, 0, w, h);
        
        // хвостик облачка
        // int[] xPoints = {w/2 - c(15), w/2 + c(15), w/2};
        // int[] yPoints = {h, h, h + c(25)};
        // bg.fillPolygon(xPoints, yPoints, 3);
        // bg.drawPolygon(xPoints, yPoints, 3);

        // Полоска настроения
        int barW = c(160); 
        int barH = c(12);
        int barX = (w - barW) / 2; 
        int barY = h - c(25);
        
        bg.setColor(Color.LIGHT_GRAY); 
        bg.fillRect(barX, barY, barW, barH);
        
        Color moodColor = currentMood > 0.6 ? Color.GREEN : (currentMood > 0.3 ? Color.YELLOW : Color.RED);
        bg.setColor(moodColor); 
        bg.fillRect(barX, barY, (int)(barW * currentMood), barH);
        
        bg.setColor(Color.BLACK); 
        bg.drawRect(barX, barY, barW, barH);

        // БЛЮДА ДРУГ НАД ДРУГОМ
        int dishCount = requiredDishes.size();
        if (dishCount > 0) {
            int slotH = (h - c(50)) / dishCount; 
            int maxW = w - c(40);
            int maxH = slotH - c(10);

            for (int i = 0; i < dishCount; i++) {
                Food reqDish = requiredDishes.get(i);
                GreenfootImage dishImg = new GreenfootImage(reqDish.getImage());
                
                boolean delivered = deliveredDishes.contains(reqDish);
                
                if (dishImg.getWidth() > maxW || dishImg.getHeight() > maxH) {
                    double scale = Math.min((double)maxW / dishImg.getWidth(), (double)maxH / dishImg.getHeight());
                    dishImg.scale((int)(dishImg.getWidth() * scale), (int)(dishImg.getHeight() * scale));
                }
                
                if (delivered) {
                    GreenfootImage overlay = new GreenfootImage(dishImg.getWidth(), dishImg.getHeight());
                    overlay.setColor(new Color(0, 0, 0, 120));
                    overlay.fill();
                    dishImg.drawImage(overlay, 0, 0);
                }
                
                int x = (w - dishImg.getWidth()) / 2;
                int y = c(20) + i * slotH + (slotH - dishImg.getHeight()) / 2;
                bg.drawImage(dishImg, x, y);
            }
        }
        setImage(bg);
    }
    
    private int c(int x) {
        return(int)(Kuhnya.COEF*x);
    }
    
    private void drawTextOverlay() {
        redrawCloud(); 
        GreenfootImage img = getImage();
        
        // Фон под текст
        img.setColor(new Color(255, 255, 255, 220)); 
        img.fillRect(c(10), c(10), img.getWidth() - c(20), img.getHeight() - c(40));
        
        img.setColor(Color.BLACK);
        int fontSize = c(16);
        img.setFont(new Font("Arial", true, false, fontSize));
        
        // Примерно 20 символов в строке для шрифта 16px
        int maxCharsPerLine = 20; 
        List<String> lines = getWrappedLines(getDescription(), maxCharsPerLine);
        
        int y = c(30);
        int lineHeight = fontSize + c(6);
        
        for (String line : lines) {
            img.drawString(line, c(20), y);
            y += lineHeight;
            if (y > img.getHeight() - c(30)) break; 
        }
    }

    private List<String> getWrappedLines(String text, int maxCharsPerLine) {
        List<String> wrapped = new ArrayList<>();
        if (text == null || text.isEmpty()) return wrapped;
        
        String[] paragraphs = text.split("\n");
        
        for (String para : paragraphs) {
            if (para.trim().isEmpty()) {
                wrapped.add("");
                continue;
            }
            
            // Если строка короче лимита - добавляем целиком
            if (para.length() <= maxCharsPerLine) {
                wrapped.add(para);
                continue;
            }
            
            // Разбиваем длинную строку на части
            String[] words = para.split(" ");
            StringBuilder currentLine = new StringBuilder();
            
            for (String word : words) {
                if (currentLine.length() + word.length() + 1 > maxCharsPerLine) {
                    wrapped.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                } else {
                    if (currentLine.length() > 0) currentLine.append(" ");
                    currentLine.append(word);
                }
            }
            if (currentLine.length() > 0) wrapped.add(currentLine.toString());
        }
        return wrapped;
    }

    // ======================== МЕТОДЫ ДЛЯ CLIENT ==========================

    public void updateMood(double percent) {
        this.currentMood = percent;
        if (!isHovered) redrawCloud(); else drawTextOverlay(); 
    }

    public boolean tryDeliver(Food food) {
        for (Food req : requiredDishes) {
            if (!deliveredDishes.contains(req)) {
                if (isDishCorrect(food, req)) {
                    deliveredDishes.add(req);
                    if (!isHovered) redrawCloud(); else drawTextOverlay();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isComplete() {
        return deliveredDishes.size() == requiredDishes.size();
    }

    // ======================== ПРОВЕРКА СОВПАДЕНИЯ ========================
    
    private boolean isDishCorrect(Food cooked, Food required) {
        if (cooked.getClass() != required.getClass()) return false;
        
        try {
            java.lang.reflect.Method m = cooked.getClass().getMethod("getIngredients");
            List<Ingredient> cIngs = (List<Ingredient>) m.invoke(cooked);
            List<Ingredient> rIngs = (List<Ingredient>) m.invoke(required);
            return compareIngredientLists(cIngs, rIngs);
        } catch (Exception e) {
            return true; 
        }
    }

    private boolean compareIngredientLists(List<Ingredient> cIngs, List<Ingredient> rIngs) {
        if (cIngs.size() != rIngs.size()) return false;
        
        List<Ingredient> remainingRequired = new ArrayList<>(rIngs);
        
        for (Ingredient cookedIng : cIngs) {
            boolean foundMatch = false;
            for (int i = 0; i < remainingRequired.size(); i++) {
                Ingredient reqIng = remainingRequired.get(i);
                
                if (cookedIng.getClass() == reqIng.getClass()) {
                    if (isIngredientStateCorrect(cookedIng)) {
                        remainingRequired.remove(i);
                        foundMatch = true;
                        break;
                    }
                }
            }
            if (!foundMatch) return false; 
        }
        
        return remainingRequired.isEmpty();
    }

    private boolean isIngredientStateCorrect(Ingredient ing) {
        if (ing instanceof cookableIngredient) {
            return ((cookableIngredient) ing).getState();
        }
        return true; // Для остальных ингредиентов state не важен
    }

    // ======================== ТЕКСТОВОЕ ОПИСАНИЕ =========================

    public String getDescription() {
        StringBuilder sb = new StringBuilder("Заказ:\n");
        for (int i = 0; i < requiredDishes.size(); i++) {
            Food dish = requiredDishes.get(i);
            sb.append(i + 1).append(". ");
            if (dish instanceof Sandwich) sb.append("Бутерброд");
            else if (dish instanceof RiceDish) sb.append("Рис");
            else sb.append("Блюдо");
            
            appendIngredients(sb, dish, " (", ", ", ")");
            
            if (deliveredDishes.contains(dish)) sb.append(" [Готово]");
            sb.append("\n");
        }
        return sb.toString();
    }

    private void appendIngredients(StringBuilder sb, Food dish, String prefix, String separator, String suffix) {
        try {
            java.lang.reflect.Method m = dish.getClass().getMethod("getIngredients");
            List<Ingredient> ings = (List<Ingredient>) m.invoke(dish);
            if (ings != null && !ings.isEmpty()) {
                sb.append(prefix);
                for (int j = 0; j < ings.size(); j++) {
                    sb.append(getIngredientName(ings.get(j)));
                    if (j < ings.size() - 1) sb.append(separator);
                }
                sb.append(suffix);
            }
        } catch (Exception e) {}
    }

    private String getIngredientName(Ingredient ing) {
        if (ing instanceof Tomato) return "помидор";
        if (ing instanceof Lettuce) return "салат";
        if (ing instanceof Sauce) return "соус";
        if (ing instanceof Egg) return "яйцо";
        if (ing instanceof Cutlet) return "котлета";
        if (ing instanceof Bread) return "хлеб";
        return ing.getClass().getSimpleName();
    }
}