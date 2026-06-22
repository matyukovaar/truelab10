import greenfoot.*;
import java.util.List;


public class SpawnClient extends Actor {
    
    static double COEF = Kuhnya.COEF;
    static int slotAmount = 6;
    
    
    private int spawnTimer = 0;
    // +- 6 секунд, то есть 60*6=360 и дальше рандом
    private int baseInterval = 60; 
    private int realInterval = getInterval(60);
    
    int slotY = (int)(COEF*360);
    private boolean[] slots = new boolean[slotAmount];
    
    public void freeSlot(int num) {
        slots[num] = true;
    }
    
    private int getInterval(int r) {
        return  baseInterval - r + Greenfoot.getRandomNumber(r*2);
    }
    
    public SpawnClient() {
        getImage().clear(); 
        for (int i = 0; i < slotAmount; i++) {
            slots[i] = true;
        }
    }

    public void act() {
        if (GameManager.isPaused) {
            return;
        }
        trySpawnClient();
    }

    private void trySpawnClient() {
        spawnTimer++;
        if (spawnTimer < realInterval) {
            return;
        }
        spawnTimer = 0;
        realInterval = getInterval(60);
        
        // рандомный номер слота
        int num = Greenfoot.getRandomNumber(slotAmount);
        // размер слота, на 2 больше потому что они крайние
        int slotsize = ((num+1)*((int)((1600/(slotAmount+1)))));
        if (slots[num]) {
            // рандомный слот свободен
            Client newClient = new Client(num);
            getWorld().addObject(newClient, (int)(COEF*slotsize), slotY);
            slots[num] = false;
        }
    }
}