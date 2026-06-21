import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Kuhnya extends World
{
    //========================ОТЛАДКА=============================
    // коэф от базового размера 
    public static final double COEF = 0.5;
    // тесты
    public static final boolean TESTZONES = true;
    //============================================================
    
    private static final int WIDTH = (int)(1600*COEF);
    private static final int HEIGHT = (int)(800*COEF);

    public Kuhnya()
    {    
        super(WIDTH, HEIGHT, 1); 
        GreenfootImage image = new GreenfootImage("kuhnya.png");
        image.scale(WIDTH, HEIGHT);
        setBackground(image);
        prepare();
    }
    private void setZones() {
        // помойка трешшш
        Trash trash = new Trash();
        addObject(trash, trash.getFinalX(), trash.getFinalY());
        // тарелки платы
        Plate plate1 = new Plate(770, 930, 430, 490);
        addObject(plate1, plate1.getFinalX(), plate1.getFinalY());
    }
    
    private void prepare()
    {
        // зона зоны передачка на зону сидеть на зоне няшка зоняшка 
        setZones();

        Tomato tomato = new Tomato();
        addObject(tomato,666,308);
        Tomato tomato2 = new Tomato();
        addObject(tomato2,588,329);
        tomato2.setLocation(588,304);
        Tomato tomato3 = new Tomato();
        addObject(tomato3,588,304);
        Tomato tomato4 = new Tomato();
        addObject(tomato4,517,314);
        Bread bread = new Bread();
        addObject(bread,626,282);
    }
}