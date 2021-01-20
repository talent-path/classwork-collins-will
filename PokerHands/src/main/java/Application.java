import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Application {
    public static void main(String[] args) {

        //define a variable of the TYPE Suit
        //Suit cardSuit = Suit.DIAMONDS;
        //FaceValue cardValue = FaceValue.ACE;

        //we're using the new keyword to "instantiate" a PlayingCard object
        //when we use the new keyword, it calls the constructor method
        //we haven't defined a constructor, so it just calls the automatic default that does nothing
        //we get back a reference to the object (which is on the heap)
        //that reference is what's stored inside of aceOfDiamonds
        //PlayingCard aceOfDiamonds = new PlayingCard();

        //this no longer works now that we have a non-default constructor, the default goes away
        //now we have to instantiate with the data already provided
        //the advantage is that it is impossible to build a playing card object
        //without supplying all the of information necessary to describe the card
        //Card aceOfDiamonds = new Card( FaceValue.ACE, cardSuit );

        //so now we can call non-static methods
        //NOT on PlayingCard, but instead on aceOfDiamonds
        //aceOfDiamonds.setCardValue( FaceValue.ACE );
        //aceOfDiamonds.setCardSuit( Suit.DIAMONDS );

        //System.out.println( aceOfDiamonds.getCardValue() );
        //System.out.println( aceOfDiamonds.getCardSuit() );

        //this doesn't work because these methods are not static:
//        PlayingCard.setCardValue( FaceValue.ACE );
//        PlayingCard.setCardSuit( Suit.DIAMONDS );


        //                   straight flush vs 4 of a kind
        //                   2D  3D 4D 5D  6D  7S  7D  7C  7H  8S
        //int[] bothHands = { 1, 5, 9, 13, 17, 20, 21, 22, 23, 24  };
        int [] bothHands = {28, 44, 40, 36, 32, 29,45,41,37,33};
        Card[] hand1Cards = new Card[5];
        Card[] hand2Cards = new Card[5];

        for( int i = 0; i < bothHands.length; i++ ){
            if( i < 5 ){
                hand1Cards[i] = new Card(bothHands[i]);
            } else {
                hand2Cards[i-5] = new Card(bothHands[i]);
            }
        }

        PokerHand hand1 = new PokerHand(hand1Cards);
        PokerHand hand2 = new PokerHand(hand2Cards);

        //test suit and face value counts
        Map<FaceValue,Integer> valueCountsH1 = hand1.countFaceValues();
        Map<FaceValue,Integer> valueCountsH2 = hand2.countFaceValues();

        Map<Suit, Integer> suitCountsH1 = hand1.countSuits();
        Map<Suit, Integer> suitCountsH2 = hand2.countSuits();

        System.out.println("hand 1: ");
        for( FaceValue key : valueCountsH1.keySet() ){
            System.out.println( key + ": " + valueCountsH1.get(key) );
        }

        for( Suit key : suitCountsH1.keySet() ){
            System.out.println( key + ": " + suitCountsH1.get(key) );
        }

        System.out.println("hand 2: ");
        for( FaceValue key : valueCountsH2.keySet() ){
            System.out.println( key + ": " + valueCountsH2.get(key) );
        }

        for( Suit key : suitCountsH2.keySet() ){
            System.out.println( key + ": " + suitCountsH2.get(key) );
        }


        int hand1WinsResult = hand1.compareTo(hand2);

        if( hand1WinsResult < 0 ){
            //should see this
            System.out.println("player 1 wins");
        } else if( hand1WinsResult > 0 ){
            System.out.println( "player 2 wins");
        } else {
            System.out.println("tie!");
        }

    }

    //player one's cards are 0-4
    //player two's cards are 5-9
    public static boolean playerOneWins( int[] bothHands ){
        Card[] hand1Cards = new Card[5];
        Card[] hand2Cards = new Card[5];

        for( int i = 0; i < bothHands.length; i++ ){
            if( i < 5 ){
                hand1Cards[i] = new Card(bothHands[i]);
            } else {
                hand2Cards[i-5] = new Card(bothHands[i]);
            }
        }

        PokerHand hand1 = new PokerHand(hand1Cards);
        PokerHand hand2 = new PokerHand(hand2Cards);
        throw new UnsupportedOperationException();
    }

    //generates an array of size 10 representing 2 hands of poker
    //the values are 0-51
    //the suit is value%4            (0-spades, 1-diamonds, 2-clubs, 3-hearts)
    //the card is value/4 + 2       J = 11, Q = 12, K = 13, A = 14
    public static int[] generatePokerHands(){
        int [] cards = new int[10];
        for( int i = 0; i < 10; i++ ){
            int card = Integer.MIN_VALUE;

            while( card == Integer.MIN_VALUE ){
                card = Rng.nextInt(0,51);
                for( int j = 0; j < i; j++ ){
                    if( cards[j] == card ) card = Integer.MIN_VALUE;
                }

            }
            cards[i] = card;
        }

        Arrays.sort(cards, 0, 4);
        Arrays.sort(cards, 5, 9);

        return cards;
    }

}
