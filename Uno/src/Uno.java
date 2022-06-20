    
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
    private static boolean uno;
    private static boolean win;

    public static void main(String[] args) {
        // TODO code application logic here
        //Game goes here :)

        PlayGame();
        //TODO the colorvalue will no longer be attached to the card. It must be separated...

    }

    private static void PlayGame() {

        System.out.println("Player one cards size: " + PlayerOneCards.size());
        for (int i = 0; i < 7; i++) {
            PlayerOneCards.add(DrawCard());
            System.out.println("P1 Card added at index: " + i + "\nName: "
                    + PlayerOneCards.get(i).getType().name() + "\nColorValue: "
                    + PlayerOneCards.get(i).getColorValue() + "\nCardValue: "
                    + PlayerOneCards.get(i).getCardValue());
        }
        for (int i = 0; i < 7; i++) {
            PlayerTwoCards.add(DrawCard());
            System.out.println("P2 Card added at index: " + i + "\nName: "
                    + PlayerTwoCards.get(i).getType().name() + "\nColorValue: "
                    + PlayerTwoCards.get(i).getColorValue() + "\nCardValue: "
                    + PlayerTwoCards.get(i).getCardValue());
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
        System.out.println("P1 Choose an option:\nOption 1: Play card. \nOption 2: Pick Up a new card. ");
        int option = kbd.nextInt();
        switch (option) {
            case 1:
                //System.out.println("Playing first card");
                //PlayCard(PlayerOneCards.get(0));
                System.out.println("Pick an index of card you would like to play.");
                int cardOption = kbd.nextInt();
                //PlayCard(PlayerOneCards.get(cardOption));

                DetermineValidityAndPlay(CardsPlayed.peek(), PlayerOneCards.get(cardOption), 1);

                if (RulebookGameMove.PlayingStackRule()) {
                    //Determine if the player has another card to which they can play.
                    System.out.println("Determining if you have another card that can be played.");
                    for (int i = 0; i < PlayerOneCards.size(); i++) {
                        DetermineValidityAndAsk(CardsPlayed.peek(), PlayerOneCards.get(i), 1);
                    }
                }

                break;
            case 2:
                System.out.println("Picking up a new card");
                PlayerOneCards.add(DrawCard());
                break;
            default: 
                System.out.println("Invalid choice");
                MainGameLoop();
                break;
        }
        System.out.println("Now it's Player 2's turn.");
        PrintPlayerCards(2);
        DetermineIfAICanPlayACard(); //Only plays first card available
        if (RulebookGameMove.PlayingStackRule()) {
            System.out.println("Determining if the AI has another card that can be played.");
            for (int i = 0; i < PlayerTwoCards.size(); i++) {
                DetermineValidityAndAsk(CardsPlayed.peek(), PlayerTwoCards.get(i), 2);
            }
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

        for (int i = 0; i < PlayerTwoCards.size(); i++) {
            DetermineValidityAndPlay(CardsPlayed.peek(), PlayerTwoCards.get(i), 2);
        }
    }

    private static void DetermineValidityAndPlay(Card playedCard, Card currentCard, int player) {
        boolean isValidMove = RulebookGameMove.IsValid(playedCard, currentCard);
        boolean isValidCard = RulebookGameMove.IsValidCard(currentCard);
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
    }

    private static void DetermineValidityAndAsk(Card playedCard, Card currentCard, int player) {
        boolean isValidMove = RulebookGameMove.IsValid(playedCard, currentCard);
        boolean isValidCard = RulebookGameMove.IsValidCard(currentCard);
        if (isValidMove && isValidCard && playedCard.getCardValue() < 5) {
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
            System.out.println("Good move you smart bitch");
        } else {
            System.out.println("Onto next card to check.");
        }
    }

    private static void PrintPlayerCards(int player) {
        if (player == 1) {
            System.out.println("Player: " + player);
            for (int i = 0; i < PlayerOneCards.size(); i++) {
                System.out.println("===================================================================================");
                System.out.println(""
                        + "index: " +  i + "\t Name: " + PlayerOneCards.get(i).getType().name() + "\t ColorValue: "
                        + PlayerOneCards.get(i).getColorValue() + "\t CardValue: "
                        + PlayerOneCards.get(i).getCardValue());
                //System.out.println("===================================================================================");
            }
        } else {
            System.out.println("Player: " + player);
            for (int i = 0; i < PlayerTwoCards.size(); i++) {
                System.out.println("===================================================================================");
                System.out.println(""
                        + "index: " +  i + "\t Name: " + PlayerTwoCards.get(i).getType().name() + "\t ColorValue: "
                        + PlayerTwoCards.get(i).getColorValue() + "\t CardValue: "
                        + PlayerTwoCards.get(i).getCardValue());
            }

        }
    }

    private static void CheckLastCardPlayed() {
        System.out.println("Last card Name: " + CardsPlayed.peek().getType().name() + "\nColorValue: " + CardsPlayed.peek().getColorValue() + "\nCardValue: " + CardsPlayed.peek().getCardValue());

    }

    private static void PrintCardStats(Card card) {
        System.out.println("Card Name: " + card.getType().name() + "\nColorValue: " + card.getColorValue() + "\nCardValue: " + card.getCardValue());

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

        randomCard = AttachEnum(randInt, randomCard, randomCard.getType());
        /*
        System.out.println("Random Card has been activated"
                + "\n It's cardValue is:" + randomCard.getCardValue() 
                + "\n It's faceValue is:" + randomCard.getType());
         */

        if (randomCard.getCardValue() < 5) {
            //System.out.println("CardValue: " + randomCard.getCardValue());
            randomCard = AttachColorValue(randomCard);
        } else if (randomCard.getCardValue() == 13) {
            randomCard.setColorValue(11);
        } else if (randomCard.getCardValue() == 14) {
            randomCard.setColorValue(12);
        }

        /*          System.out.println("randomCard:"
                + "\nFace: " + randomCard.name()
                + "\nColorValue: " + randomCard.getColorValue());
         */
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
        Random rand = new Random();
        int randInt = rand.nextInt(10);
        //System.out.println("Attaching Color Value of: " + randInt);
        randomCard.setColorValue(randInt);
        return randomCard;
    }

    private static Card AttachEnum(int cardIndex, Card randomCard, CardType randomCardType) {

        switch (cardIndex) {
            case 1:
                randomCardType = CardType.RED;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(1);
                return randomCard;
            case 2:
                randomCardType = CardType.BLUE;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(2);
                return randomCard;
            case 3:
                randomCardType = CardType.YELLOW;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(3);
                return randomCard;
            case 4:
                randomCardType = CardType.GREEN;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(4);
                return randomCard;
            case 5:
                randomCardType = CardType.REDDRAW2;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(1);
                return randomCard;
            case 6:
                randomCardType = CardType.BLUEDRAW2;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(2);
                return randomCard;
            case 7:
                randomCardType = CardType.YELLOWDRAW2;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(3);
                return randomCard;
            case 8:
                randomCardType = CardType.GREENDRAW2;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(4);
                return randomCard;
            case 9:
                randomCardType = CardType.REDREVERSE;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(1);
                return randomCard;
            case 10:
                randomCardType = CardType.BLUEREVERSE;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(2);
                return randomCard;
            case 11:
                randomCardType = CardType.YELLOWREVERSE;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(3);
                return randomCard;
            case 12:
                randomCardType = CardType.GREENREVERSE;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(4);
                return randomCard;
            case 13:
                randomCardType = CardType.DRAWFOUR;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(13);
                return randomCard;
            case 14:
                randomCardType = CardType.CHANGECOLOR;
                randomCard.setType(randomCardType);
                randomCard.setCardValue(14);
                return randomCard;
            default:
                System.out.println("Unexpected value!:" + cardIndex);
                break;

        }
        return randomCard;
    }

    

}
