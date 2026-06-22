import greenfoot.*;

public class Kuhnya extends World {
    public static final double COEF = 0.5;
    public static final boolean TESTZONES = true;
    
    private static final int WIDTH = (int)(1600 * COEF);
    private static final int HEIGHT = (int)(800 * COEF);

    public Kuhnya() {    
        super(WIDTH, HEIGHT, 1); 
        GreenfootImage image = new GreenfootImage("kuhnya.png");
        image.scale(WIDTH, HEIGHT);
        setBackground(image);
        prepare();
    }
    
    private void setZones() {
        // Мусорка
        Trash trash = new Trash();
        addObject(trash, trash.getFinalX(), trash.getFinalY());
        
        // 6 Сковородок
        addObject(new Pan(250, 410, 423, 483), c(330), c(453));
        addObject(new Pan(450, 610, 423, 483), c(530), c(453));
    
        addObject(new Pan(210, 370, 500, 560), c(290), c(530));
        addObject(new Pan(420, 580, 500, 560), c(500), c(530));

        addObject(new Pan(155, 315, 585, 645), c(235), c(615));
        addObject(new Pan(380, 540, 585, 645), c(460), c(615));
    
        // 9 Тарелок
        addObject(new Plate(770, 930, 423, 483), c(850), c(453));
        addObject(new Plate(940, 1100, 423, 483), c(1020), c(453));
        addObject(new Plate(1106, 1266, 423, 483), c(1186), c(453));
        
        addObject(new Plate(765, 925, 508, 568), c(845), c(538));
        addObject(new Plate(940, 1100, 508, 568), c(1020), c(538));
        addObject(new Plate(1120, 1280, 508, 568), c(1200), c(538));
        
        addObject(new Plate(760, 920, 614, 674), c(840), c(644));
        addObject(new Plate(960, 1120, 614, 674), c(1040), c(644));
        addObject(new Plate(1145, 1305, 614, 674), c(1225), c(644));
        
        // 3 напитка
        addObject(new DrinkStation(1360, 1420, 450, 550, "coffee1"), c(1390), c(500));
        addObject(new DrinkStation(1430, 1490, 450, 550, "coffee2"), c(1460), c(500));
        addObject(new DrinkStation(1500, 1560, 450, 550, "coffee3"), c(1530), c(500));
    }
    
    private void setIngredientZones() {
        // База
        addObject(new IngredientZone("bread", 170, 100), c(840), c(740));
        addObject(new IngredientZone("mashed", 170, 100), c(1040), c(740));
        addObject(new IngredientZone("rice", 170, 100), c(1240), c(740));
        
        // Дополнительные
        addObject(new IngredientZone("sauce", 120, 100), c(700), c(460));
        addObject(new IngredientZone("tomato", 120, 100), c(680), c(560));
        addObject(new IngredientZone("lettuce", 120, 100), c(660), c(660));
        
        // Для жарки
        addObject(new IngredientZone("egg", 190, 100), c(210), c(735));
        addObject(new IngredientZone("cutlet", 180, 100), c(425), c(735));
        
        // Стаканы
        addObject(new IngredientZone("cup", 150, 150), c(90), c(450));
    }
    
    private void prepare() {
        setZones();
        setIngredientZones();
        addObject(new PauseButton(), getWidth() - 80, 30); 
        //addObject(new GameManager(), 0, 0);
    }
    
    private int c(int x) {
        return (int)(x * COEF);
    }
}