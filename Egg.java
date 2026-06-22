import greenfoot.*;

public class Egg extends cookableIngredient {
    public Egg() {
        // заполнение полей готовимого ингредиента
        rawPicture = "egg.png";
        cookedPicture = "egg2.png";
        burntPicture = "egg3.png";
        size = 85;
        sizeY = 50;
        picture = rawPicture;
        setPicture();
    }
}