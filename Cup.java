import greenfoot.*;

public class Cup extends Ingredient {
    protected Container homeContainer;
    
    public Cup() {
        size = 50;
        sizeY = 70;
        setPicture("cup.png");
        
    }
    public void setHomeContainer(Container container) {
        this.homeContainer = container;
        this.isPlaced = true;
    }
    public Container getHomeContainer() {
        return homeContainer;
    }    
    protected void mouseControl() {
        if (isPlaced() && getHomeContainer() instanceof DrinkStation) {
            DrinkStation station = (DrinkStation) getHomeContainer();
            int state = station.getState();
            
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
                if (mouse != null && getWorld() != null) {
                    Trash trash = getContainerAtMouse(Trash.class);
                    
                    if (state == 1 || state == 2) {
                        if (trash != null) {
                            station.makeFree();
                            if (getWorld() != null) {
                                getWorld().removeObject(this);
                            }
                        } else {
                            station.returnIngredient(this);
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
            if (getWorld() != null) {
                checkDrop();
            }
        }
    }
}