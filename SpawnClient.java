import greenfoot.*;
import java.util.List;


public class SpawnClient extends Actor {
    private int spawnTimer = 0;
    private int spawnInterval = 300; 

    private int[] slotX = {200, 400, 600, 800};
    private int[] slotY = {180, 180, 180, 180};

    public SpawnClient() {
        getImage().clear(); 
    }

    public void act() {
        if (GameManager.isPaused) {
            return;
        }
        trySpawnClient();
    }

    private void trySpawnClient() {
        spawnTimer++;
        if (spawnTimer < spawnInterval) {
            return;
        }
        spawnTimer = 0;

        int freeSlot = findFreeSlot();
        if (freeSlot == -1) {
            return; // все слоты очереди заняты
        }

        Client newClient = new Client();
        getWorld().addObject(newClient, slotX[freeSlot], slotY[freeSlot]);
    }

    private int findFreeSlot() {
        List<Client> currentClients = getWorld().getObjects(Client.class);
        for (int i = 0; i < slotX.length; i++) {
            boolean occupied = false;
            for (Client c : currentClients) {
                if (c.getX() == slotX[i] && c.getY() == slotY[i]) {
                    occupied = true;
                    break;
                }
            }
            if (!occupied) {
                return i;
            }
        }
        return -1;
    }
}