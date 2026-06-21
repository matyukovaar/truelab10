import greenfoot.*;
import java.util.ArrayList;

public class Order extends Actor {
    // Компоненты текущего заказа
    private String food1;
    private String food2;
    private String drink;
    
    // Переменная для отслеживания текущей эмоции (чтобы не перерисовывать холст каждый кадр)
    private String currentMood = "good"; 

    // Пулы ингредиентов для генерации (строки должны совпадать с getName() ваших классов)
    private String[] poolFood1 = {"BurgerPatty", "FriedEgg", "Sandwich"}; 
    private String[] poolFood2 = {"Tomato", "Bread"};                   
    private String[] poolDrink = {"Cola", "Juice"};                     

    /**
     * Конструктор класса Order.
     * Случайно формирует заказ и выполняет стартовую отрисовку.
     */
    public Order() {
        // Рандомно собираем три составляющие заказа
        food1 = poolFood1[Greenfoot.getRandomNumber(poolFood1.length)];
        food2 = poolFood2[Greenfoot.getRandomNumber(poolFood2.length)];
        drink = poolDrink[Greenfoot.getRandomNumber(poolDrink.length)];
        
        // Рисуем начальное состояние облачка (с хорошим настроением)
        drawOrderCloud("good");
    }

    /**
     * Метод динамического обновления настроения.
     * Вызывается каждую секунду из класса Client.
     * 
     * @param percent значение от 1.0 (максимум терпения) до 0.0 (терпение вышло)
     */
    public void updateMood(double percent) {
        String newMood = "good";
        
        if (percent <= 0.25) {
            newMood = "angry";
        } else if (percent <= 0.6) {
            newMood = "ok";
        }

        // Если настроение изменилось, перерисовываем графику облачка
        if (!newMood.equals(currentMood)) {
            currentMood = newMood;
            drawOrderCloud(currentMood);
        }
    }

    /**
     * Внутренний метод генерации изображения облачка заказа.
     * Компонует картинку настроения сверху и текст ингредиентов снизу.
     * 
     * @param mood имя текущего состояния ("good", "ok", "angry")
     */
    private void drawOrderCloud(String mood) {
        // Создаем чистый холст размером 120х100 пикселей
        GreenfootImage img = new GreenfootImage(120, 100);
        
        // Рисуем основу: белое облачко с аккуратной черной рамкой
        img.setColor(Color.WHITE);
        img.fillOval(0, 0, 120, 100);
        img.setColor(Color.BLACK);
        img.drawOval(0, 0, 119, 99);
        
        // 1. Отрисовка картинки настроения (смайлика) СВЕРХУ заказа
        // ВАЖНО: В папку images проекта нужно положить файлы: mood_good.png, mood_ok.png, mood_angry.png
        try {
            GreenfootImage moodImg = new GreenfootImage("mood_" + mood + ".png");
            moodImg.scale(25, 25); // Масштабируем смайлик до компактного размера
            
            // Вычисляем координаты, чтобы отцентровать смайлик по горизонтали
            int moodX = (img.getWidth() - 25) / 2;
            int moodY = 8; // Небольшой отступ от верхнего края облачка
            
            img.drawImage(moodImg, moodX, moodY);
        } catch (Exception e) {
            // Если картинок смайликов пока нет в папке, выведем настроение обычным текстом в консоль, чтобы игра не вылетала
            System.out.println("Предупреждение: Файл mood_" + mood + ".png не найден в папке images!");
        }
        
        // 2. Отрисовка списка заказа СНИЗУ под смайликом
        img.setFont(new Font("Arial", true, false, 11)); // Настраиваем мелкий читаемый шрифт
        img.setColor(Color.BLACK);
        
        // Выводим сгенерированные строки друг под другом с аккуратными отступами по Y
        img.drawString("• " + food1, 15, 48);
        img.drawString("• " + food2, 15, 66);
        img.drawString("• " + drink, 15, 84);
        
        // Применяем готовое скомпонованное изображение к объекту Actor
        setImage(img);
    }

    /**
     * ПРОВЕРКА ПРАВИЛЬНОСТИ ЗАКАЗА.
     * Вызывается из класса Client, когда игрок отпускает тарелку над ним.
     * 
     * @param plate Объект тарелки, которую принес игрок.
     * @return true, если на тарелке лежат именно те 3 ингредиента, которые загаданы в заказе.
     */
    /*
    public boolean checkPlate(Plate plate) {
        // Считываем список строк-ингредиентов из тарелки
        ArrayList<String> plateIngredients = plate.getIngredientsList();

        // Если на тарелке нет ингредиентов или их количество не равно 3, заказ сразу неверный
        if (plateIngredients == null || plateIngredients.size() != 3) {
            return false;
        }

        // Проверяем, есть ли в списке тарелки каждый из загаданных компонентов
        // Метод contains() ищет совпадение по всей коллекции, поэтому порядок сборки не важен
        boolean hasFood1 = plateIngredients.contains(food1);
        boolean hasFood2 = plateIngredients.contains(food2);
        boolean hasDrink = plateIngredients.contains(drink);

        // Возвращаем истину только в том случае, если совпали все три элемента
        return hasFood1 && hasFood2 && hasDrink;
    }*/
}
