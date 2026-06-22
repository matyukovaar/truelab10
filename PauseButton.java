import greenfoot.*;

public class PauseButton extends Actor {

    public PauseButton() {
        updateButtonImage("||");
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            GameManager.isPaused = !GameManager.isPaused;

            if (GameManager.isPaused) {
                updateButtonImage(" > ");
                getWorld().showText("ИГРА НА ПАУЗЕ", getWorld().getWidth() / 2, getWorld().getHeight() / 2);
            } else {
                updateButtonImage(" || ");
                getWorld().showText("", getWorld().getWidth() / 2, getWorld().getHeight() / 2);
            }
        }
    }

    private void updateButtonImage(String text) {
        GreenfootImage img = new GreenfootImage(40, 40);
        img.setColor(new Color(255, 218, 185)); 
        img.fill();
        img.setColor(Color.BLACK);
        img.drawRect(0, 0, 39, 39);

        Font buttonFont = new Font("Arial", true, false, 22);
        img.setFont(buttonFont);

        java.awt.Graphics graphics = img.getAwtImage().getGraphics();
        graphics.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 22));
        java.awt.FontMetrics metrics = graphics.getFontMetrics();

        int textX = (img.getWidth() - metrics.stringWidth(text)) / 2;
        int textY = ((img.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

        img.drawString(text, textX, textY);
        setImage(img);    
    }

}
