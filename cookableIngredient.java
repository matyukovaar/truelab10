import greenfoot.*;

public class cookableIngredient extends Ingredient {
    protected String rawPicture;
    protected String cookedPicture;
    protected String burntPicture;
    
    // не уверен нужен ли вообще этот конструктор
    public cookableIngredient() {
        //super();
    }
    
    public void act() {
        if (isPlaced() && getHomeContainer() instanceof Pan) {
            Pan pan = (Pan) getHomeContainer();
            int state = pan.getCookingState();
            
            if (state == 1 && !picture.equals(rawPicture)) {
                picture = rawPicture;
                setPicture();
            } else if (state == 2 && !picture.equals(cookedPicture)) {
                picture = cookedPicture;
                setPicture();
            } else if (state == 3 && !picture.equals(burntPicture)) {
                picture = burntPicture;
                setPicture();
            }
        }
        
        mouseControl();
    }
    
    private void mouseControl() {
        if (isPlaced() && getHomeContainer() instanceof Pan) {
            Pan pan = (Pan) getHomeContainer();
            int state = pan.getCookingState();
            
            if (Greenfoot.mousePressed(this)) {
                dragging = true;
            }
            if (dragging) {
                MouseInfo mouse = Greenfoot.getMouseInfo();
                if (mouse != null) {
                    setLocation(mouse.getX(), mouse.getY() - getImage().getHeight()/2 + 15);
                }
            }
            if (Greenfoot.mouseDragEnded(this)) {
                dragging = false;
                MouseInfo mouse = Greenfoot.getMouseInfo();
                if (mouse != null) {
                    Trash trash = getContainerAtMouse(Trash.class);
                    
                    if (state == 1 || state == 3) {
                        if (trash != null) {
                            pan.makeFree();
                            getWorld().removeObject(this);
                        } else {
                            pan.returnIngredient(this);
                        }
                    }
                    
                    if (state == 2) {
                        Plate plate = getContainerAtMouse(Plate.class);
                        if (trash != null) {
                            pan.makeFree();
                            getWorld().removeObject(this);
                        } else if (plate != null && !plate.isEmpty()) {
                            if (plate.getFood().addIngredient(this)) {
                                pan.makeFree();
                                getWorld().removeObject(this);
                            } else {
                                pan.returnIngredient(this);
                            }
                        } else {
                            pan.returnIngredient(this);
                        }
                    }
                }
            }
            return;
        }
        
        if (isPlaced()) return;
        
        if (Greenfoot.mousePressed(this)) {
            dragging = true;
            startX = getX();
            startY = getY();
        }
        if (dragging) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                setLocation(mouse.getX(), mouse.getY() - getImage().getHeight()/2 + 15);
            }
        }
        if (Greenfoot.mouseClicked(this)) {
            dragging = false;
            checkDrop();
        }
    }
}