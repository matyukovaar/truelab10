import greenfoot.*;
import java.util.List;

public class GameManager extends Actor {
    // Состояние паузы (доступно для всех классов игры)
    public static boolean isPaused = false; 

    // Игровые показатели
    private int score = 0;
    private int timeLeft = 5400; // Примерно 90 секунд уровня при 60 FPS

    // Настройки спавна клиентов
    private int spawnTimer = 0;
    private int spawnInterval = 360; // Каждые 6 секунд (60 кадров * 6)
    private int maxClientsInRoom = 4; // Максимальное число клиентов на кухне одновременно

    // Координаты столов для генерации клиентов (X-координаты фиксированных мест)
    private int[] tableCoordinates = {150, 350, 550, 750}; 

    public GameManager() {
        // Делаем менеджер системным (невидимым) объектом на экране
        getImage().clear(); 
        isPaused = false; // При перезапуске уровня сбрасываем паузу
    }

    public void act() {
        // Если игра поставлена на паузу, менеджер замирает
        if (isPaused) {
            return; 
        }

        countDownTime();
        handleCustomerSpawn();
        updateUI();
    }

    /**
     * Таймер обратного отсчета времени уровня
     */
    private void countDownTime() {
        if (timeLeft > 0) {
            timeLeft--;
        } else {
            endLevel();
        }
    }

    /**
     * Логика генерации клиентов по времени
     */
    private void handleCustomerSpawn() {
        spawnTimer++;
        if (spawnTimer >= spawnInterval) {
            spawnTimer = 0;

            // Проверяем, сколько клиентов сейчас на кухне
            List<Client> currentClients = getWorld().getObjects(Client.class);
            if (currentClients.size() < maxClientsInRoom) {
                spawnClientAtFreeTable(currentClients);
            }
        }
    }

    /**
     * Поиск свободного стола и создание там клиента
     */
    private void spawnClientAtFreeTable(List<Client> currentClients) {
        // Перебираем все доступные координаты столов
        for (int xCoord : tableCoordinates) {
            boolean isTableBusy = false;

            // Проверяем, стоит ли уже кто-то на этих координатах X
            for (Client c : currentClients) {
                if (c.getX() == xCoord) {
                    isTableBusy = true;
                    break;
                }
            }

            // Если стол свободен, спавним туда нового клиента
            if (!isTableBusy) {
                // Высота спавна (Y = 150) подбирается под вашу графику стоек
                getWorld().addObject(new Client(), xCoord, 150); 

                // Слегка ускоряем появление следующих клиентов по ходу игры (усложнение)
                if (spawnInterval > 180) { 
                    spawnInterval -= 15; // Сокращаем интервал до минимума в 3 секунды
                }
                break; // Выходим, так как за один такт спавним только одного клиента
            }
        }
    }

    /**
     * Метод для добавления очков (вызывается из класса Client)
     */
    public void addScore(int points) {
        score += points;
    }

    /**
     * Вывод счета и времени на экран
     */
    private void updateUI() {
        int seconds = timeLeft / 60;

        // Рисуем текст в левом верхнем углу (координаты можно изменить под ваш UI)
        getWorld().showText("SCORE: " + score, 120, 25);
        getWorld().showText("TIME: " + seconds + "s", 120, 55);
    }

    /**
     * Завершение уровня при истечении времени
     */
    private void endLevel() {
        int finalScore = score;
        getWorld().showText("LEVEL COMPLETED!", getWorld().getWidth() / 2, getWorld().getHeight() / 2);
        getWorld().showText("FINAL SCORE: " + finalScore, getWorld().getWidth() / 2, (getWorld().getHeight() / 2) + 40);

        // Останавливаем игровой цикл Greenfoot
        Greenfoot.stop();
    }
}
