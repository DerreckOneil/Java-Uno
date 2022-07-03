/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author oneil
 */
public class GameMove 
{
    //Lessson: Control Slash makes something //
    //public abstract boolean PlayingStackRule();
    
    private boolean isValid;
    private boolean isValidCard;
    private boolean sameColor;
    private boolean sameNumber;
    private boolean sameCard;
    private boolean playingStackRule;
    
    public boolean IsValid(Card playedCard,Card currentCard)
    {
        
        if(SameColor(playedCard, currentCard)|| SameNumber(playedCard, currentCard))
        {
            System.out.println("It has the same color or number");
            isValid = true;
        }
        else if(SameColor(playedCard, currentCard) && SameNumber(playedCard, currentCard))
        {
            System.out.println("It has the same color and number");
            isValid = true; 
        }
        else if(IsWildCard(currentCard) || IsWildCard(playedCard))
        {
            System.out.println("It's a wildcard");
            isValid = true;
        }
        else
        {
            System.out.println("nope");
            isValid = false;
        }
        
        return isValid;
    }
    public boolean IsValidStacked(Card playedCard,Card currentCard)
    {
        
        if(SameCard(playedCard, currentCard))
        {
            System.out.println("Same Card");
            isValid = true;
        }
        else if(SameNumber(playedCard, currentCard))
        {
            System.out.println("It has the same color or number");
            isValid = true;
        }
        else
        {
            System.out.println("nope");
            isValid = false;
        }
        
        return isValid;
    }
    public boolean SameColor(Card playedCard, Card card)
    {
        if(playedCard.getEnumeration() == card.getEnumeration())
            sameColor = true;
        else
            sameColor = false;
        return sameColor;
    }
    
    public boolean SameCard(Card playedCard, Card card)
    {
        //Does this work? yeah not taking the chance. 
        //sameCard = playedCard.equals(card);
        if(playedCard.getColorValue() == card.getColorValue() && playedCard.getEnumeration() == card.getEnumeration() && playedCard.getType() == card.getType())
            sameCard = true;
        else
            sameCard = false;
        
        return sameCard;
    }
    public boolean IsWildCard(Card card)
    {
        if(card.getType() == CardType.WILDCARD)
            return true;
        else
            return false;
    }
    public boolean SameNumber(Card playedCard, Card card)
    {
        if(playedCard.getColorValue() == card.getColorValue())
        {
            sameNumber = true;
        }
        else
            sameNumber = false;
        
        return sameNumber;
    }
    
    public void IsPlayingStackRule(boolean tf)
    {
        this.playingStackRule = tf;
    }
    public boolean PlayingStackRule()
    {
        return playingStackRule;
    }
}
