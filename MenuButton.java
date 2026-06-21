import greenfoot.*;

public class MenuButton extends Actor {
    private String actionType; 

    public MenuButton(String text) {
        this.actionType = text.toUpperCase();
        createButtonImage(text);
    }

    private void createButtonImage(String text) {
    GreenfootImage img = new GreenfootImage(220, 60);
    img.setColor(new Color(255, 218, 185)); 
    img.fill();
    img.setColor(Color.BLACK);
    img.drawRect(0, 0, 219, 59);
    Font buttonFont = new Font("Arial", true, false, 22);
    img.setFont(buttonFont);
    
    java.awt.Graphics graphics = img.getAwtImage().getGraphics();
    graphics.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 22));
    java.awt.FontMetrics metrics = graphics.getFontMetrics();
    
    // Вычисляем X и Y строго по центру картинки
    int textX = (img.getWidth() - metrics.stringWidth(text)) / 2;
    int textY = ((img.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
    
    // Рисуем отцентрованный текст
    img.drawString(text, textX, textY);
    setImage(img);
}


    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            executeAction();
        }
    }


    private void executeAction() {
        if (actionType.equals("ИГРАТЬ")) {
            Greenfoot.setWorld(new Kuhnya()); 
        } 
        else if (actionType.equals("ИНСТРУКЦИЯ")) {
            getWorld().showText("ПРАВИЛА ИГРЫ:\n1. Готовьте Sandwich на плите.\n2. Собирайте заказы на Plate.\n3. Сгоревшую еду кидайте в Trash.\n4. Кликайте на Client, чтобы отдать заказ.", 
                                getWorld().getWidth() / 2, 100);
        } 
        else if (actionType.equals("ВЫХОД")) {
            Greenfoot.stop(); 
        }
    }
}
