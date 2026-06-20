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
    private void prepare()
    {
        Tomato tomato = new Tomato();
        addObject(tomato,666,308);
        
        Trash trash = new Trash();
        addObject(trash, trash.getFinalX(), trash.getFinalY());
    }
}