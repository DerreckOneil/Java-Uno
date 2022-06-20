/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author oneil
 */
public enum CardType {
    NULL(0),
    RED(1),
    BLUE(2),
    YELLOW(3),
    GREEN(4),
    REDDRAW2(5),
    BLUEDRAW2(6),
    YELLOWDRAW2(7),
    GREENDRAW2(8),
    REDREVERSE(9),
    BLUEREVERSE(10),
    YELLOWREVERSE(11),
    GREENREVERSE(12),
    DRAWFOUR(13),
    CHANGECOLOR(14)    
    ;
    
    private int cardIndex;

    public int getCardIndex() {
        return cardIndex;
    }

    public void setCardIndex(int cardIndex) {
        this.cardIndex = cardIndex;
    }
    private CardType(int cardIndex)
    {
        this.cardIndex = cardIndex;
    }
}
