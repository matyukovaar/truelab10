import greenfoot.*;  

public class pieceIngredient extends Ingredient 
{
    // также не уверен что нужно
    protected void mouseControl() {
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
        // именно маус клик позволяет отслеживать отпускание мыши, метод stop drag
        // чето там сначала ждет НАЖАТИЯ, а его у нас нет тк предмет сразу
        // создается и следуетза мышкой
        
        // ВСЁ МОЖЕТ СЛОМАТЬСЯ ОНО ЛОМАЕТСЯ ЕСЛИ СТАВИТЬ NULL В FOOD НЕ СТАВЬТЕ NULL В ФУД
        if (Greenfoot.mouseClicked(null)) {
            //System.out.println("Отпустили!"+Greenfoot.getRandomNumber(100));
            dragging = false;
            checkDrop();
        }
    }
}