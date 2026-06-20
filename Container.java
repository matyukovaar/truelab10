import greenfoot.*;  

public abstract class Container extends Actor
{
    // коэф для размера спрайта
    double COEF = Kuhnya.COEF;
    boolean TESTZONES = Kuhnya.TESTZONES;
    
    // координаты для размещения 
    protected int finalX, finalY; 
    
    public Container(int x1, int x2, int y1, int y2) {
        setZone(x1, x2, y1, y2);
    }
    public void act()
    {
    }
    // Задается зона (прозрачная)
    public void setZone(int x1, int x2, int y1, int y2) {
        int w = c(x2 - x1);
        int h = c(y2 - y1);
        int t = 0;
        if (TESTZONES == true) t = 150;
        GreenfootImage transparent = new GreenfootImage(w, h);
        transparent.setColor(new Color(Greenfoot.getRandomNumber(256), 
        Greenfoot.getRandomNumber(256), Greenfoot.getRandomNumber(256), t));
        transparent.fill();
        setImage(transparent);
        setLocation(c(x1) + w / 2, c(y1) + h / 2);
        finalX = c(x1) + w / 2;
        finalY = c(y1) + h / 2;
    }
    // геттеры под координаты размещения зоны
    public int getFinalX() { return finalX; }
    public int getFinalY() { return finalY; }
    // перевод всех координат в отмасштабированные
    public int c(int x) {return (int)(x*COEF);}
}
