import greenfoot.*;

public class Egg extends Ingredient {
    private String rawPicture = "egg1.png";
    private String cookedPicture = "egg2.png";
    private String burntPicture = "egg3.png";
    
    public Egg() {
        size = 85;
        sizeY = 50;
        picture = rawPicture;
        setPicture();
    }
    
    public void act() {
        // Обновляем картинку если на сковороде
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
        if (Greenfoot.mouseDragEnded(this)) {
            dragging = false;
            checkDrop();
        }
    }
}