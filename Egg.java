import greenfoot.*;

public class Egg extends cookableIngredient {
    public Egg() {
        // заполнение полей готовимого ингредиента
        rawPicture = "egg.png";
        cookedPicture = "egg2.png";
        burntPicture = "egg3.png";
        size = 85;
        sizeY = 50;
        timeForReady = 80;
        timeForBurn = 300;
        yoff = 30;
        
        
        picture = rawPicture;
        setPicture();
    }
}