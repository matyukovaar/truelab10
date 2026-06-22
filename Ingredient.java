import greenfoot.*;

public abstract class Ingredient extends Actor {
    double COEF = Kuhnya.COEF;
    protected int size = 100;
    protected int sizeY = 100;
    String picture = "bomj.png";
    boolean dragging = false;
    int startX;
    int startY;
    protected boolean isPlaced = false;
    
    protected void setPicture(String fileName) {
        picture = fileName;
        setPicture();
    }
    protected abstract void mouseControl();
    protected void setPicture() {
        GreenfootImage image = new GreenfootImage(picture);
        image.scale(c(size), c(sizeY));
        setImage(image);
    }
    
    // этот акт Арины не будет использоваться так как перезаписан в детях
    public void act() {
        if(GameManager.isPaused) return;
        mouseControl();
    }
    
    public String getPicture() {
        return picture;
    }

    public boolean isPlaced() {
        return isPlaced;
    }
    
    protected void checkDrop() {
        if (getWorld() == null) return;
        
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null) return;
        
        Trash trash = getContainerAtMouse(Trash.class);
        if (trash != null) {
            // removeFromContainer();
            if (getWorld() != null) {
                getWorld().removeObject(this);
            }
            return;
        }
        
        Pan pan = getContainerAtMouse(Pan.class);
        if (pan != null && pan.isEmpty()) {
            System.out.println("11111");
            if (pan.addCookable(this)) {
                isPlaced = true;
                return;
            }
        }
        
        Plate plate = getContainerAtMouse(Plate.class);
        if (plate != null) {
            if (plate.addIngredient(this)) {
                return;
            }
        }
        
        DrinkStation station = getContainerAtMouse(DrinkStation.class);
        if (station != null && station.isEmpty()) {
            if (station.addIngredient(this)) {
                return;
            }
        }
    
        // удаление объекта если не попали, за возвращением следят сами Food и cookedIngredient
        if (getWorld() != null) {
                getWorld().removeObject(this);
        }
    }

    // проверка на попытку положить в контейнер - должен пересекать + мышь именно на контейнере
    public <T extends Actor> T getContainerAtMouse(Class<T> type) {
        if (getWorld() == null) return null;
        
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
    
    // перевод пиксельных координат в человеческие
    public int c(int x) {
        return (int)(x * COEF);
    }
}