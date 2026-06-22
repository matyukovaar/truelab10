import greenfoot.*;

public class cookableIngredient extends Ingredient {
    protected String rawPicture;
    protected String cookedPicture;
    protected String burntPicture;
    
    int timeForReady;
    int timeForBurn;
    int timer = 0;
    int state = 0;
    int yoff = 0;
    Pan pan = null;
    
    // не уверен нужен ли вообще этот конструктор
    public cookableIngredient() {
        //super();
    }
    public void act() {
        mouseControl();
    }
    public int ySpawnOffset() {
        return yoff;
    }
    public void cook() {
        timer++;
        if (timer > timeForBurn) {
            picture = burntPicture;
            setPicture();
            state = 2;
            return;
        }
        if (timer > timeForReady) {
            state = 1;
            picture = cookedPicture;
            setPicture();
            return;
        }
        else {
            picture = rawPicture;
            setPicture();
        }
    }
    private void mouseControl() {
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
        if (Greenfoot.mouseClicked(null)) {
            //System.out.println("Отпустили!"+Greenfoot.getRandomNumber(100));
            dragging = false;
            checkDrop();
        }
    }
    protected void makePanFree() {
        if (pan != null) {
                ((Pan) pan).makeFree();
        }
    }
    protected void checkDrop() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null) return;

        // перекладываение из сковороды в сковороду как food
        Pan new_pan = getContainerAtMouse(Pan.class);
        if (new_pan != null && new_pan.isEmpty()) {
            makePanFree();
            new_pan.cooking = this;
            this.pan = new_pan;
            int centerX = pan.x1 + (pan.x2 - pan.x1) / 2;
            setLocation(c(centerX), c(pan.y2 - ySpawnOffset()));
            
            return;
        }
        
        // перекладывание в еду как ingredient
        Plate plate = getContainerAtMouse(Plate.class);
        if (plate != null) {
            if (plate.addIngredient(this)) {
                makePanFree();
                return;
            }
        }
        
        // перекладывание в мусор как в food
        Trash trash = getContainerAtMouse(Trash.class);
        if (trash != null) {
            if (pan != null) {
                ((Pan)pan).makeFree();
            }
            getWorld().removeObject(this);
            return;
        }

        if (pan != null) {
            Pan oldPan = (Pan) pan;
            oldPan.cooking = this;
            int centerX = oldPan.x1 + (oldPan.x2 - oldPan.x1) / 2;
            setLocation(c(centerX), c(oldPan.y2 - ySpawnOffset()));
        } else {
            getWorld().removeObject(this);
        }
    }
    // private void mouseControl_old() {
        // if (isPlaced() && getHomeContainer() instanceof Pan) {
            // Pan pan = (Pan) getHomeContainer();
            // int state = pan.getCookingState();
            
            // if (Greenfoot.mousePressed(this)) {
                // dragging = true;
            // }
            // if (dragging) {
                // MouseInfo mouse = Greenfoot.getMouseInfo();
                // if (mouse != null) {
                    // setLocation(mouse.getX(), mouse.getY() - getImage().getHeight()/2 + 15);
                // }
            // }
            // if (Greenfoot.mouseClicked(this)) {
                // dragging = false;
                // MouseInfo mouse = Greenfoot.getMouseInfo();
                // if (mouse != null) {
                    // Trash trash = getContainerAtMouse(Trash.class);
                    // if (state == 1 || state == 3) {
                        // if (trash != null) {
                            // pan.makeFree();
                            // getWorld().removeObject(this);
                        // } else {
                            // pan.returnIngredient(this);
                        // }
                    // }
                    // if (state == 2) {
                        // Plate plate = getContainerAtMouse(Plate.class);
                        // if (trash != null) {
                            // pan.makeFree();
                            // getWorld().removeObject(this);
                        // } else if (plate != null && !plate.isEmpty()) {
                            // if (plate.getFood().addIngredient(this)) {
                                // pan.makeFree();
                                // getWorld().removeObject(this);
                            // } else {
                                // pan.returnIngredient(this);
                            // }
                        // } else {
                            // pan.returnIngredient(this);
                        // }
                    // }
                // }
            // }
            // return;
        // }
        
        // if (isPlaced()) return;
        
        // if (Greenfoot.mousePressed(this)) {
            // dragging = true;
            // startX = getX();
            // startY = getY();
        // }
        // if (dragging) {
            // MouseInfo mouse = Greenfoot.getMouseInfo();
            // if (mouse != null) {
                // setLocation(mouse.getX(), mouse.getY() - getImage().getHeight()/2 + 15);
            // }
        // }
        // if (Greenfoot.mouseClicked(this)) {
            // dragging = false;
            // checkDrop();
        // }
    // }
}