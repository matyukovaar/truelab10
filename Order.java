import greenfoot.*;

public class Order extends Actor {
    private String food1;
    private String food2;
    private String drink;

    private boolean food1Delivered = false;
    private boolean food2Delivered = false;
    private boolean drinkDelivered = false;

    private String currentMood = "good";
    private String[] poolFood = {"MashedDish", "RiceDish", "Sandwich"};
    private boolean isDrink = 1 == Greenfoot.getRandomNumber(2); 

    public Order() {
        food1 = poolFood[Greenfoot.getRandomNumber(poolFood.length)];
        food2 = poolFood[Greenfoot.getRandomNumber(poolFood.length)];
        drink = isDrink ? "Coffe" : "Nothing";
        if (drink.equals("Nothing")) {
            drinkDelivered = true; 
        }
        drawOrderCloud("good");
    }

    public void updateMood(double percent) {
        String newMood = "good";
        if (percent <= 0.25) {
            newMood = "angry";
        } else if (percent <= 0.6) {
            newMood = "ok";
        }
        if (!newMood.equals(currentMood)) {
            currentMood = newMood;
            drawOrderCloud(currentMood);
        }
    }

    private void drawOrderCloud(String mood) {
        GreenfootImage img = new GreenfootImage(120, 100);
        img.setColor(Color.WHITE);
        img.fillOval(0, 0, 120, 100);
        img.setColor(Color.BLACK);
        img.drawOval(0, 0, 119, 99);
        try {
            GreenfootImage moodImg = new GreenfootImage("mood_" + mood + ".png");
            moodImg.scale(25, 25);
            int moodX = (img.getWidth() - 25) / 2;
            int moodY = 8;
            img.drawImage(moodImg, moodX, moodY);
        } catch (Exception e) {
            System.out.println("Предупреждение: Файл mood_" + mood + ".png не найден в папке images!");
        }
        img.setFont(new Font("Arial", true, false, 11));
        img.setColor(Color.BLACK);
        img.drawString((food1Delivered ? "✓ " : "• ") + food1, 15, 48);
        img.drawString((food2Delivered ? "✓ " : "• ") + food2, 15, 66);
        if(drink != "Nothing") {img.drawString((drinkDelivered ? "✓ " : "• ") + drink, 15, 84);}
        setImage(img);
    }

    public boolean tryDeliver(Food food) {
        if (food == null) {
            return false;
        }

        String foodName = food.getClass().getSimpleName();

        if (!food1Delivered && foodName.equals(food1)) {
            food1Delivered = true;
            drawOrderCloud(currentMood);
            return true;
        }
        if (!food2Delivered && foodName.equals(food2)) {
            food2Delivered = true;
            drawOrderCloud(currentMood);
            return true;
        }
        return false;
    }

    public boolean deliverDrink(String drinkName) {
        if (!drinkDelivered && drink.equals(drinkName)) {
            drinkDelivered = true;
            drawOrderCloud(currentMood);
            return true;
        }
        return false;
    }

    public boolean isComplete() {
        return food1Delivered && food2Delivered && drinkDelivered;
    }
}