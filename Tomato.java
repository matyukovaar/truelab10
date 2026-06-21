import greenfoot.*;  

public class Tomato extends Ingredient 
{
    public Tomato() {
        size = 85;
        sizeY = 30;
        setPicture("tomato.png");
    }
    
    public void act() {
        mouseControl();
    }
    
    private void mouseControl() {
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