import greenfoot.*;  

public class pieceIngredient extends Ingredient 
{
    // также не уверен что нужно
    public pieceIngredient () {
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