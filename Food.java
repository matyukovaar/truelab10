import greenfoot.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public abstract class Food extends Ingredient {
    public Container home;
    
    public Food() {
        //отладочное
        //setRotation(Greenfoot.getRandomNumber(360));
    }
    
    public abstract int ySpawnOffset();

    public abstract boolean addIngredient(Ingredient ing);

    public abstract void removeIngredient(Ingredient ing);
    protected void makeHomeFree() {
        if (home != null && home instanceof Plate) {
                ((Plate) home).makeFree();
            }
    }
    public void mouseControl() {
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
        // ==============================
        // очень опасная часть!!!!!!!!!!!!!!!!!! null или this
        if (Greenfoot.mouseClicked(this)) {
            //System.out.println("Отпустили!"+Greenfoot.getRandomNumber(100));
            dragging = false;
            checkDrop();
        }
    }

    protected void checkDrop() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null) return;
        // клиентище
        List<Order> orders = getIntersectingObjects(Order.class);
        if (!orders.isEmpty()) {
            Order order = orders.get(0);
            boolean accepted = order.tryDeliver(this);
            if (accepted) {
                if (home != null && home instanceof Plate) {
                    ((Plate) home).makeFree();
                }
                if (getWorld() != null) {
                    getWorld().removeObject(this);
                }
                return; // Заказ принят, выходим
            }
            // Если не accepted, код пойдет дальше и еда просто вернется на место/упадет
        }

        // клиентское облако
        Client client = getClientAtMouse();
        if (client != null) {
            boolean accepted = client.receiveFood(this);
            if (accepted) {
                if (home != null && home instanceof Plate) {
                    ((Plate) home).makeFree();
                }
                if (getWorld() != null) {
                    getWorld().removeObject(this);
                }
                return;
            }
        }


        Plate plate = getContainerAtMouse(Plate.class);
        if (plate != null && plate.isEmpty()) {
            makeHomeFree();
            plate.food = this;
            this.home = plate;
            int centerX = plate.x1 + (plate.x2 - plate.x1) / 2;
            setLocation(c(centerX), c(plate.y2 - ySpawnOffset()));
            
            return;
        }

        Trash trash = getContainerAtMouse(Trash.class);
        if (trash != null) {
            if (home != null && home instanceof Plate) {
                ((Plate) home).makeFree();
            }
            getWorld().removeObject(this);
            return;
        }

        if (home != null && home instanceof Plate) {
            Plate oldPlate = (Plate) home;
            oldPlate.food = this;
            int centerX = oldPlate.x1 + (oldPlate.x2 - oldPlate.x1) / 2;
            setLocation(c(centerX), c(oldPlate.y2 - ySpawnOffset()));
        } else {
            getWorld().removeObject(this);
        }
    }

    private Client getClientAtMouse() {
        if (getWorld() == null) return null;

        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null) return null;

        for (Actor actor : getIntersectingObjects(Client.class)) {
            return (Client) actor;
        }
        return null;
    }
}