import greenfoot.*;

public class Cutlet extends cookableIngredient {
    public Cutlet() {
        // заполнение полей готовимого ингредиента
        rawPicture = "cutlet.png";
        cookedPicture = "cutlet2.png";
        burntPicture = "cutlet3.png";
        size = 95;
        sizeY = 60;
        timeForReady = 40;
        timeForBurn = 400;
        yoff = 30;
        
        
        picture = rawPicture;
        setPicture();
    }
}