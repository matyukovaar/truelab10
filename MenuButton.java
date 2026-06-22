import greenfoot.*;

public class MenuButton extends Actor {
    private String actionType; 
    static double COEF = (Kuhnya.COEF);

    public MenuButton(String text) {
        this.actionType = text.toUpperCase();
        createButtonImage(text);
    }

    private void createButtonImage(String text) {
        GreenfootImage img = new GreenfootImage((int)(COEF * 440), (int)(COEF * 120));
        img.setColor(new Color(255, 218, 185)); 
        img.fill();
        img.setColor(Color.BLACK);
        img.drawRect(0, 0, (int)(COEF * 438), (int)(COEF * 118));
        Font buttonFont = new Font("Arial", true, false, (int)(COEF * 44));
        img.setFont(buttonFont);
        
        java.awt.Graphics graphics = img.getAwtImage().getGraphics();
        graphics.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, (int)(COEF * 44)));
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
            GreenfootImage bg = getWorld().getBackground();
            int worldWidth = getWorld().getWidth();
            int worldHeight = getWorld().getHeight();
            int paneWidth = worldWidth / 3;
            int rectHeight = worldHeight;
            bg.setColor(new Color(255, 218, 185));
            bg.fillRect(0, 0, paneWidth, rectHeight);
            bg.fillRect(worldWidth - paneWidth, 0, paneWidth, rectHeight);
           
            bg.setColor(Color.BLACK);
            int startLeftX = (int)(COEF * 24);
            int startRightX = (worldWidth - paneWidth) + (int)(COEF * 24);
            int lineHeight = (int)(COEF * 28);                      

            int leftY = (int)(COEF * 50);
         
            bg.setFont(new Font("Arial", true, false, (int)(COEF * 28)));
            bg.drawString("ИНТЕРФЕЙС:", startLeftX, leftY);
            leftY += (int)(COEF * 36);
            
            bg.setFont(new Font("Arial", false, false, (int)(COEF * 22)));
            bg.drawString("1. Счет отображается в левом верхнем углу.", startLeftX, leftY); leftY += lineHeight;
            bg.drawString("2. Таймер: 90 сек. При 0 раунд завершается.", startLeftX, leftY); leftY += lineHeight;
            bg.drawString("3. Над клиентом - облачко с заказом и", startLeftX, leftY); leftY += lineHeight;
            bg.drawString("   индикатор терпения. Быстрее сервис -", startLeftX, leftY); leftY += lineHeight;
            bg.drawString("   больше очков получить.", startLeftX, leftY); leftY += lineHeight;
            bg.drawString("4. Пауза - кнопка в правом верхнем углу.", startLeftX, leftY); leftY += (int)(COEF * 50);
            
            bg.setFont(new Font("Arial", true, false, (int)(COEF * 28)));
            bg.drawString("ГЕЙМПЛЕЙ:", startLeftX, leftY);
            leftY += (int)(COEF * 36);
            
            bg.setFont(new Font("Arial", false, false, (int)(COEF * 22)));
            bg.drawString("Посетители сверху ждут 2 блюда и напиток.", startLeftX, leftY); leftY += lineHeight;
            bg.drawString("Перетаскивайте еду прямо на клиента.", startLeftX, leftY); leftY += lineHeight;
            bg.drawString("Внизу зоны заготовок (Основы, Добавки,", startLeftX, leftY); leftY += lineHeight;
            bg.drawString("Жарка, Посуда).", startLeftX, leftY);
           
            int rightY = (int)(COEF * 50);
            
            // Подраздел: Жарка
            bg.setFont(new Font("Arial", true, false, (int)(COEF * 24)));
            bg.drawString("Жарка (6 сковородок):", startRightX, rightY);
            rightY += (int)(COEF * 32);
            bg.setFont(new Font("Arial", false, false, (int)(COEF * 22)));
            bg.drawString("• Стадии: Сырое -> Готовое -> Сгоревшее.", startRightX, rightY); rightY += lineHeight;
            bg.drawString("• Сгоревшее выбрасывайте в Мусорку.", startRightX, rightY); rightY += lineHeight;
            bg.drawString("• Готовое снимайте на тарелку.", startRightX, rightY); rightY += (int)(COEF * 44);
            
            // Подраздел: Сборка
            bg.setFont(new Font("Arial", true, false, (int)(COEF * 24)));
            bg.drawString("Сборка (9 тарелок):", startRightX, rightY);
            rightY += (int)(COEF * 32);
            bg.setFont(new Font("Arial", false, false, (int)(COEF * 22)));
            bg.drawString("• Хлеб -> Сэндвич. Рис -> Рис. Пюре -> Пюре.", startRightX, rightY); rightY += lineHeight;
            bg.drawString("• Добавляйте соус, овощи, яйца, котлеты.", startRightX, rightY); rightY += (int)(COEF * 44);
            
            // Подраздел: Напитки
            bg.setFont(new Font("Arial", true, false, (int)(COEF * 24)));
            bg.drawString("Напитки и брак:", startRightX, rightY);
            rightY += (int)(COEF * 32);
            bg.setFont(new Font("Arial", false, false, (int)(COEF * 22)));
            bg.drawString("• Стакан из зоны -> на станцию -> клиенту.", startRightX, rightY); rightY += lineHeight;
            bg.drawString("• Ошиблись? Несите брак в Мусорку.", startRightX, rightY); rightY += (int)(COEF * 50);
            
            // Раздел: Награды
            bg.setFont(new Font("Arial", true, false, (int)(COEF * 28)));
            bg.drawString("НАГРАДЫ:", startRightX, rightY);
            rightY += (int)(COEF * 36);
            bg.setFont(new Font("Arial", false, false, (int)(COEF * 22)));
            bg.drawString("• 150 очков — отличное настроение", startRightX, rightY); rightY += lineHeight;
            bg.drawString("• 100 очков — нормальное настроение", startRightX, rightY); rightY += lineHeight;
            bg.drawString("• 50 очков — плохое настроение", startRightX, rightY); rightY += lineHeight;
            bg.drawString("• Без чаевых, если терпение упадет до 0.", startRightX, rightY);
        }
        else if (actionType.equals("ВЫХОД")) {
            Greenfoot.stop(); 
        }
    }
}