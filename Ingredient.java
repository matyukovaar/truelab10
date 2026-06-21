import greenfoot.*;  

// абстрактный класс для ингридиентов
public abstract class Ingredient extends Actor {
    // коэф для размера спрайта
    double COEF = Kuhnya.COEF;
    // размер по базе, может меняться
    protected int size = 100;
    // картинка
    String picture = "bomj.png";
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
    // получение названия спрайта для отрисовки
    public String getPicture() {
        return picture;
    }
    // контроль перетаскивания
    private void mouseControl(){
        if(Greenfoot.mousePressed(this)){
            dragging = true;
            startX = getX();
            startY = getY();
        }
        if(dragging){
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if(mouse != null){
                setLocation(
                    mouse.getX(),
                    // чтобы высокие спрайты еды хватались не за центр
                    mouse.getY() - getImage().getHeight()/2 + 15
                );
            }
        }
        if(Greenfoot.mouseDragEnded(this)){
            dragging = false;
            checkDrop();
        }
    }
    private void checkDrop(){
        // координаты мыши
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null) return; 
        int mouseX = mouse.getX();
        int mouseY = mouse.getY();
        
        // получение мусорки если с ней есть пересечение
        Trash trash = getContainerAtMouse(Trash.class);
        if (trash != null) {
            // если блюдо выбрасывается отвязываем его от домика
            if (this instanceof Food) {
                Container homeContainer = ((Food) this).home;
                if (homeContainer instanceof Plate) {
                    ((Plate) homeContainer).makeFree();
                }
            }
            getWorld().removeObject(this);
            return;
        }
        
        // если ТАРЕЛКА
        Plate plate = getContainerAtMouse(Plate.class);
        if (plate != null) {
            // если можем добавить на тарелку
            if(plate.addIngredient(this)){
                // если это еда - тарелка нас сама запомнит
                // если не еда - надо удалиться
                if (!(this instanceof Food)) {
                    getWorld().removeObject(this);
                }
                return;
            }
        }



        // если никуда не положили
        
        // setLocation(        );
    }
    
    // проверка на пересечения с объектами класса, потом проверка на нем ли курсор
    public <T extends Actor> T getContainerAtMouse(Class<T> type) {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null) return null;
        
        int mouseX = mouse.getX();
        int mouseY = mouse.getY();
        
        for (Actor actor : getIntersectingObjects(type)) {
            T container = (T) actor;
            if (container instanceof Container) {
                if (((Container) container).inZone(mouseX, mouseY)) {
                    return container;
                }
            }
        }
        return null;
    }
    
    // перевод всех координат в отмасштабированные
    public int c(int x) {return (int)(x*COEF);}
}