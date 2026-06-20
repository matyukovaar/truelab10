import greenfoot.*;  

// абстрактный класс для ингридиентов
public abstract class Ingredient extends Actor {
    // коэф для размера спрайта
    double COEF = Kuhnya.COEF;
    // размер по базе, может меняться
    int size = 100;
    // картинка
    String picture = "bomj.png";
    // помнит контейнер которому принадлежит
    Container home;
    // перетаскивается ли
    boolean dragging = false;
    // коорды начала перетаскивания
    int startX;
    int startY;
    
    public void act(){
        mouseControl();
    }
    // задает спрайт нужной картинки нужных размеров size и c
    // варианты с аргументом и без
    protected void setPicture(String fileName) {
        picture = fileName;
        setPicture();
    }
    protected void setPicture() {
        GreenfootImage image = new GreenfootImage(picture);
        image.scale(c(size), c(size));
        setImage(image);
    }
    // контроль перетаскивания
    private void mouseControl(){
        if(Greenfoot.mousePressed(this)){
            size = 600;
            setPicture();
            dragging = true;
            startX = getX();
            startY = getY();
        }
        if(dragging){
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if(mouse != null){
                setLocation(
                    mouse.getX(),
                    mouse.getY()
                );
            }
        }
        if(Greenfoot.mouseDragEnded(this)){
            dragging = false;
            checkDrop();
        }
    }
    private void checkDrop(){
        // Plate plate = (Plate)getOneIntersectingObject(Plate.class);
        // if(plate != null){
            // if(plate.canAccept(this)){
                // plate.addIngredient(this);
                // getWorld().removeObject(this);
                // return;
            // }
        // }



        // Trash trash =
        // (Trash)getOneIntersectingObject(Trash.class);



        // if(trash != null){

            // getWorld().removeObject(this);

            // return;

        // }



        // если никуда не положили
        // setLocation(        );
    }
    // перевод всех координат в отмасштабированные
    public int c(int x) {return (int)(x*COEF);}
}