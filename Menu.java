import greenfoot.*;

public class Menu extends World {
     //========================ОТЛАДКА=============================
    // коэф от базового размера 
    static double COEF = (Kuhnya.COEF);
    // тесты
    public static final boolean TESTZONES = true;
    //============================================================
    
    private static final int WIDTH = (int)(1600*COEF);
    private static final int HEIGHT = (int)(800*COEF);
    public Menu() {    
        super(WIDTH, HEIGHT, 1); 
        GreenfootImage back = new GreenfootImage("kek.png");
        back.scale(WIDTH, HEIGHT); 
        setBackground(back); 
        
        prepareMenu();
    }

    private void prepareMenu() {
        int centerX = getWidth() / 2; 
        
        MenuButton playButton = new MenuButton("ИГРАТЬ");
        addObject(playButton, centerX, (int)(COEF*300));
        
        MenuButton helpButton = new MenuButton("ИНСТРУКЦИЯ");
        addObject(helpButton, centerX, (int)(COEF*440));
        
        MenuButton exitButton = new MenuButton("ВЫХОД");
        addObject(exitButton, centerX, (int)(580*COEF));
    }
}
