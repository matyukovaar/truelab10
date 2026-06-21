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
        // тарелко тарелко тарелко
        Plate plate1 = new Plate(770, 930, 430, 490);
        addObject(plate1, plate1.getFinalX(), plate1.getFinalY());
        Plate plate2 = new Plate(943, 1096, 430, 490);
        addObject(plate2, plate2.getFinalX(), plate2.getFinalY());
    }
    
    private void prepare()
    {
        // зона зоны передачка на зону сидеть на зоне няшка зоняшка 
        setZones();
        Bread bread = new Bread();
        addObject(bread,555,304);
    }
}