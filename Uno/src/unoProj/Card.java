
package unoProj;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author oneil
 */
public class Card {
    private CardType type;
    private CardEnum enumeration;
    private int cardValue;
    private int colorValue;

    public Card(int value) {
        this.cardValue = value;
    }

    public Card(int value, int colorValue, CardType cardType, CardEnum cardEnum) {
        this.cardValue = value;
        this.colorValue = colorValue;
        this.type = cardType;
        this.enumeration = cardEnum;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardEnum getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(CardEnum enumeration) {
        this.enumeration = enumeration;
    }

    public int getColorValue() {
        return colorValue;
    }

    public void setColorValue(int ColorValue) {
        this.colorValue = ColorValue;
    }

    public int getCardValue() {
        return cardValue;
    }

    public void setCardValue(int value) {
        this.cardValue = value;

    }

    @Override
    public String toString() {
        String str = "";

        str = "Card: " + this.type.name() + " colorValue: " + this.colorValue + " cardValue: " + this.cardValue;
        return str;
    }

    public boolean equals(Object obj) {
        boolean tf = false;

        if (obj instanceof Card) {
            Card CardObj = (Card) obj;

            /*
             * System.out.println("Comparing!");
             * System.out.println(this.toString());
             * System.out.println(CardObj.toString());
             */

            if (this.cardValue == CardObj.cardValue && this.colorValue == CardObj.colorValue) {
                tf = true;
            }
        } else
            tf = false;

        return tf;
    }

}
