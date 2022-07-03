    
import java.util.*;
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author oneil
 */
public class Uno {
    //todo: Make sure the AI is picking up a card if they didn't play any!
    /**
     * @param args the command line arguments
     */
    private static Stack<Card> CardsPlayed = new Stack<Card>();
    private static List<Card> PlayerOneCards = new ArrayList<Card>();
    private static List<Card> PlayerTwoCards = new ArrayList<Card>();
    private static GameMove RulebookGameMove = new GameMove();
    private static Scanner kbd = new Scanner(System.in);
    private static boolean AIPlacedACard;
    private static boolean wildCardPlaced;
    private static boolean colorWildCardPlaced;
    private static boolean uno;
    private static boolean win;

    public static void main(String[] args) {
        // TODO code application logic here
        //Game goes here :)

        PlayGame();
        //TODO the colorvalue will no longer be attached to the card. It must be separated...
        //Stacked rule: Player will only be able to stack with same color. One wildcard per play.
        //-Same color and same number (basically same card) will be stack-able
        //TODO: If the player places down a draw two, he has to be able to check 
        //TODO: Make sure the reverse and draw 2s do not have color values.
        //TODO: Re-organize the script to allow color on color's draw 2s. 
        
    }

    private static void PlayGame() {

        System.out.println("Player one cards size: " + PlayerOneCards.size());
        for (int i = 0; i < 7; i++) {
            PlayerOneCards.add(DrawCard());
            System.out.println("P1 Card added at index: " + i + 
                    "\nName: " + PlayerOneCards.get(i).getEnumeration() +
                    "\nCardType: " + PlayerOneCards.get(i).getType() + 
                    "\nColorValue: " + PlayerOneCards.get(i).getColorValue() + 
                    "\nCardValue: " + PlayerOneCards.get(i).getCardValue());
        }
        for (int i = 0; i < 7; i++) {
            PlayerTwoCards.add(DrawCard());
            System.out.println("P2 Card added at index: " + i + 
                    "\nName: " + PlayerTwoCards.get(i).getEnumeration() + 
                    "\nCardType: " + PlayerTwoCards.get(i).getType() + 
                    "\nColorValue: " + PlayerTwoCards.get(i).getColorValue() + 
                    "\nCardValue: " + PlayerTwoCards.get(i).getCardValue());
        }
        FirstIterationPlay();
       
        MainGameLoop();

    }
    private static void FirstIterationPlay() {
         System.out.print("Lets Print the first card after setting all cards");
        PrintCardStats(PlayerOneCards.get(0));
        System.out.println("Pushing first card onto the stack. ");
        CardsPlayed.push(DrawCard());
        CheckLastCardPlayed();

        Scanner kbd = new Scanner(System.in);
        System.out.println("Are you playing stack rule? \n1:Yes \n2:No");
        int option = kbd.nextInt();
        switch (option) {
            case 1:
                RulebookGameMove.IsPlayingStackRule(true);
                break;
            case 2:
                RulebookGameMove.IsPlayingStackRule(false);
                break;
            default:
                System.out.println("Invalid choice");
                FirstIterationPlay();
                break;
        }
        
    }
    
    private static void MainGameLoop() {
        PrintPlayerCards(1);
        CheckLastCardPlayed();
        AIPlacedACard = false;
        wildCardPlaced = false;
        System.out.println("P1 Choose an option:\nOption 1: Play card. \nOption 2: Pick Up a new card. "
                + "\nOption 3: Print the 2nd Player's Cards");
        int option = kbd.nextInt();
        switch (option) {
            case 1:
                //System.out.println("Playing first card");
                //PlayCard(PlayerOneCards.get(0));
                System.out.println("Pick an index of card you would like to play.");
                int cardOption = kbd.nextInt();
                //PlayCard(PlayerOneCards.get(cardOption));

                DetermineValidityAndPlay(CardsPlayed.peek(), PlayerOneCards.get(cardOption), 1);
                if (RulebookGameMove.PlayingStackRule() && !wildCardPlaced) {
                    //Determine if the player has another card to which they can play.
                    System.out.println("Determining if you have another card that can be played.");
                    for (int i = 0; i < PlayerOneCards.size(); i++) {
                        DetermineValidityAndPlayStacked(CardsPlayed.peek(), PlayerOneCards.get(i), 1);
                    }
                }
                break;
            case 2:
                System.out.println("Picking up a new card");
                PlayerOneCards.add(DrawCard());
                break;
            case 3: 
                PrintPlayerCards(2);
                MainGameLoop();
                break;
            default: 
                System.out.println("Invalid choice");
                MainGameLoop();
                break;
        }
        System.out.println("Now it's Player 2's turn.");
        wildCardPlaced = false;
        PrintPlayerCards(2);
        DetermineIfAICanPlayACard(); //Only plays first card available
        
        if(!AIPlacedACard)
        {
            System.out.println("Giving the AI a card!-----------------");
            PlayerTwoCards.add(DrawCard());
        }
        
        if (PlayerOneCards.size() == 1) {
            System.out.println("Player 1 has UNO");
        }
        if (PlayerTwoCards.size() == 1) {
            System.out.println("Player 2 has UNO");
        }
        if (PlayerOneCards.isEmpty()) {
            System.out.println("Player 1 won");
            System.exit(0);
        }
        if (PlayerTwoCards.isEmpty()) {
            System.out.println("Player 2 won");
            System.exit(0);
        }
        MainGameLoop();
    }

    private static void DetermineIfAICanPlayACard() {
        //DetermineValidityAndPlay(CardsPlayed.peek(), PlayerTwoCards.get(i), 2);
        for (int i = 0; i < PlayerTwoCards.size(); i++) 
        {
            if(!AIPlacedACard)
            {
                DetermineValidityAndPlay(CardsPlayed.peek(), PlayerTwoCards.get(i), 2);
            }
            else
                break;
            
        }
        if(RulebookGameMove.PlayingStackRule() && !wildCardPlaced)
        {
            System.out.println("time to search for other cards for P2");
            for (int i = 0; i < PlayerTwoCards.size(); i++) 
            {
                DetermineValidityAndPlayStacked(CardsPlayed.peek(), PlayerTwoCards.get(i), 2);
            }
        }
    }

    private static void DetermineValidityAndPlay(Card playedCard, Card currentCard, int player) {
        boolean isValidMove = RulebookGameMove.IsValid(playedCard, currentCard);
        /*
        if (isValidMove && isValidCard) {
            if (player == 2) {
                AIPlacedACard = true;
            }

            PlayCard(currentCard, player);
            System.out.println("Good move ");
        } else {
            System.out.println("Invalid move");
            return;
        }
        */
        if (isValidMove && !wildCardPlaced) {
            System.out.println(wildCardPlaced);
            if (player == 1) {
                System.out.println("Would you like to play this card? 1:Y 2:N");
                PrintCardStats(currentCard);
                System.out.println("Onto this card?");
                PrintCardStats(playedCard);
                int option = kbd.nextInt();
                switch (option) {
                    case 1:
                        PlayCard(currentCard, player);
                        break;
                    case 2:
                        return;
                }
            } else {
                System.out.println("Playing");
                PrintCardStats(currentCard);
                System.out.println("Onto this card");
                PrintCardStats(playedCard);
                PlayCard(currentCard, player);
                AIPlacedACard = true;
            }
            //PlayCard(currentCard);
            System.out.println("Good move ");
        } else {
            System.out.println("Onto next card to check.");
        }
    }

    private static void DetermineValidityAndPlayStacked(Card playedCard, Card currentCard, int player) {
        boolean isValidMove = RulebookGameMove.IsValidStacked(playedCard, currentCard);
        if (isValidMove  && !wildCardPlaced) 
        {
            if (player == 1) {
                System.out.println("Would you like to play this card? 1:Y 2:N");
                PrintCardStats(currentCard);
                System.out.println("Onto this card?");
                PrintCardStats(playedCard);
                int option = kbd.nextInt();
                switch (option) {
                    case 1:
                        PlayCard(currentCard, player);
                        break;
                    case 2:
                        return;
                }
            } else {
                System.out.println("Playing");
                PrintCardStats(currentCard);
                System.out.println("Onto this card");
                PrintCardStats(playedCard);
                PlayCard(currentCard, player);
                AIPlacedACard = true;
            }
            //PlayCard(currentCard);
            System.out.println("Good move ");
        } 
        else 
        {
            System.out.println("Onto next card to check.");
        }
    }

    private static void PrintPlayerCards(int player) {
        if (player == 1) {
            System.out.println("Player: " + player);
            for (int i = 0; i < PlayerOneCards.size(); i++) {
                System.out.println("===================================================================================");
                System.out.println(""
                        + "index: " +  i + "\tName: " + PlayerOneCards.get(i).getEnumeration() + 
                        "\t Type: " + PlayerOneCards.get(i).getType().name() + "\t ColorValue: "
                        + PlayerOneCards.get(i).getColorValue() + "\t CardValue: "
                        + PlayerOneCards.get(i).getCardValue()); 
                //System.out.println("===================================================================================");
            }
        } else {
            System.out.println("Player: " + player);
            for (int i = 0; i < PlayerTwoCards.size(); i++) {
                System.out.println("===================================================================================");
                System.out.println(""
                        + "index: " +  i + "\tName: " + PlayerTwoCards.get(i).getEnumeration() + 
                        "\t Type: " + PlayerTwoCards.get(i).getType().name() + "\t ColorValue: "
                        + PlayerTwoCards.get(i).getColorValue() + "\t CardValue: "
                        + PlayerTwoCards.get(i).getCardValue());
            }

        }
    }

    private static void CheckLastCardPlayed() {
        System.out.println("Last card Name: " + CardsPlayed.peek().getEnumeration() + CardsPlayed.peek().getType().name() + "\nColorValue: " + CardsPlayed.peek().getColorValue() + "\nCardValue: " + CardsPlayed.peek().getCardValue());

    }

    private static void PrintCardStats(Card card) {
        System.out.println("Card Name: " + card.getEnumeration() + "\nCardType: " + card.getType() + "\nColorValue: " + card.getColorValue() + "\nCardValue: " + card.getCardValue());

    }

    private static Card ChooseFromDeck(List<Card> PlayerHand) {
        System.out.println("Pick a card by it's index: ");
        Scanner kbd = new Scanner(System.in);
        int option = kbd.nextInt();
        PrintCardStats(PlayerHand.get(option));
        return PlayerHand.get(option);
    }

    private static void PlayCard(Card card, int player) {
        CheckLastCardPlayed();
        System.out.print("I'm trying to play ");
        PrintCardStats(card);
        System.out.println("Pushing this card onto the stack.");
        CardsPlayed.push(card);
        if (player == 1) {
            PlayerOneCards.remove(card);
            System.out.print("Getting rid of ");
            PrintCardStats(card);
        } else {
            PlayerTwoCards.remove(card);
        }
        if(card.getCardValue() >= 13) //count the drawTwo as wildcards
        {
            System.out.println("Wildcard has been placed!");
            wildCardPlaced = true;
        }
        else if (card.getCardValue() >= 5 && card.getCardValue() < 13)
        {
            System.out.println("Color wild card placed. ");
            colorWildCardPlaced = true;
        }
    }

    private static Card DrawCard() {
        int randInt = (int) Math.floor(Math.random() * (14) + 1);
        //System.out.println("RandomNumber: " +randInt);
        Card randomCard = new Card(0);
        if (randInt > 4 && randInt < 13) {
            //System.out.println("1/3RDs roll time! rare card detected");
            randInt = TriplesRoll(randInt);
            //return;
        }

        randomCard = AttachEnum(randInt, randomCard, randomCard.getType(), randomCard.getEnumeration());
        
        randomCard = AttachColorValue(randomCard);
        

       
        return randomCard;
    }

    private static int FourthsRoll(int randomNum) {
        Random rand = new Random();
        int randInt = rand.nextInt(4);
        System.out.println("RandInt Value (Zero wins)");
        if (randInt == 0) {
            System.out.println("Continue...keep your value of: " + randomNum);
            return randomNum;
        } else {
            randInt = (int) Math.floor(Math.random() * (4) + 1);
            randomNum = randInt;
            System.out.println("Time to change. your new val is:" + randomNum);
            return randomNum;
        }
    }

    private static int TriplesRoll(int randomNum) {
        //System.out.println("1/3rds chance!");
        Random rand = new Random();
        int randInt = rand.nextInt(3);
        //System.out.println("RandInt Value (Zero wins)");
        if (randInt == 0) {
            //System.out.println("Continue...keep your value of: " + randomNum);
            return randomNum;
        } else {
            randInt = (int) Math.floor(Math.random() * (4) + 1);
            randomNum = randInt;
            //System.out.println("Time to change. your new val is:" + randomNum);
            return randomNum;
        }
    }

    private static Card AttachColorValue(Card randomCard) {
        if(randomCard.getCardValue() >= 5 && randomCard.getCardValue() <= 8)
        {
            System.out.println("Not applicable for a colorValue setting to Sentinel Val");
            randomCard.setColorValue(-1);
        }
        else if(randomCard.getCardValue() >= 9 && randomCard.getCardValue() <= 12)
        {
            System.out.println("Not applicable for a colorValue setting to Sentinel Val");
            randomCard.setColorValue(-1);
        }
        else if(randomCard.getCardValue() >= 13)
        {
            System.out.println("Not applicable for a colorValue setting to Sentinel Val");
            randomCard.setColorValue(-1);
        }
        else
        {
            Random rand = new Random();
            int randInt = rand.nextInt(10);
            //System.out.println("Attaching Color Value of: " + randInt);
            randomCard.setColorValue(randInt);
        }
        return randomCard;
    }

    private static Card AttachEnum(int cardIndex, Card randomCard, CardType randomCardType, CardEnum randomCardEnum) {

        switch (cardIndex) {
            case 1:
                randomCardType = CardType.NORMAL;
                randomCardEnum = CardEnum.RED;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(1);
                return randomCard;
            case 2:
                randomCardType = CardType.NORMAL;
                randomCardEnum = CardEnum.BLUE;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(2);
                return randomCard;
            case 3:
                randomCardType = CardType.NORMAL;
                randomCardEnum = CardEnum.YELLOW;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(3);
                return randomCard;
            case 4:
                randomCardType = CardType.NORMAL;
                randomCardEnum = CardEnum.GREEN;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(4);
                return randomCard;
            case 5:
                randomCardType = CardType.DRAWTWO;
                randomCardEnum = CardEnum.RED;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(5);
                return randomCard;
            case 6:
                randomCardType = CardType.DRAWTWO;
                randomCardEnum = CardEnum.BLUE;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(6);
                return randomCard;
            case 7:
                randomCardType = CardType.DRAWTWO;
                randomCardEnum = CardEnum.YELLOW;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(7);
                return randomCard;
            case 8:
                randomCardType = CardType.DRAWTWO;
                randomCardEnum = CardEnum.GREEN;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(8);
                return randomCard;
            case 9:
                randomCardType = CardType.REVERSE;
                randomCardEnum = CardEnum.RED;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(9);
                return randomCard;
            case 10:
                randomCardType = CardType.REVERSE;
                randomCardEnum = CardEnum.BLUE;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(10);
                return randomCard;
            case 11:
                randomCardType = CardType.REVERSE;
                randomCardEnum = CardEnum.YELLOW;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(11);
                return randomCard;
            case 12:
                randomCardType = CardType.REVERSE;
                randomCardEnum = CardEnum.GREEN;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(12);
                return randomCard;
            case 13:
                randomCardType = CardType.WILDCARD;
                randomCardEnum = CardEnum.DRAWFOUR;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(13);
                return randomCard;
            case 14:
                randomCardType = CardType.WILDCARD;
                randomCardEnum = CardEnum.CHANGECOLOR;
                randomCard.setType(randomCardType);
                randomCard.setEnumeration(randomCardEnum);
                randomCard.setCardValue(14);
                return randomCard;
            default:
                System.out.println("Unexpected value!:" + cardIndex);
                break;

        }
        return randomCard;
    }

    

}
