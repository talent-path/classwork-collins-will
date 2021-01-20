import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PokerHand {
    //field variables
    //we'll "compose" a poker hand from multiple sub-objects that we pull into a collection
    private Card[] cards;
    Map<FaceValue, Integer> valueCount;
    Map<Suit, Integer> suitCount;

    //need a constructor to make sure every hand is built with all the cards needed
    public PokerHand( Card[] cards ) {
        //for now we'll cross our fingers and hope its always 5
//        if( cards.length < 5 ){
//            //TODO: create a specific exception for this
//            throw new Exception("Invalid number of cards.");
//        }


        this.cards = cards;

        sortCards();

        this.valueCount = countFaceValues();
        this.suitCount = countSuits();
    }

    private void sortCards() {

        //bubble sort
        boolean sorted = false;

        while( !sorted ){

            sorted = true;
            for( int i = 0; i < cards.length - 1; i++){
                //does the element at i need to be flipped with the element at i + 1?
                if( (cards[i].getCardValue().value < cards[i+1].getCardValue().value) ||

                        (cards[i].getCardValue().value == cards[i+1].getCardValue().value
                        && cards[i].getCardSuit().value < cards[i+1].getCardSuit().value
                        )
                ){
                    //we need to swap elements at i and i + 1
                    //it also means we found elements out of order
                    sorted = false;
                    Card temp = cards[i+1];
                    cards[i+1] = cards[i];
                    cards[i] = temp;
                }
            }

        }

    }

    public Card[] getCards(){
        return cards;
    }

    public Map<FaceValue, Integer> countFaceValues(){

        Map<FaceValue, Integer> count = new HashMap<>();

        for( Card toCount : cards ){
            if( !count.containsKey(toCount.getCardValue())){
                count.put(toCount.getCardValue(), 0);
            }

            Integer currentCount = count.get(toCount.getCardValue());

            count.put( toCount.getCardValue(),
                    1 + currentCount );

        }

        return count;
    }

    public Map<Suit, Integer> countSuits(){

        Map<Suit, Integer> count = new HashMap<>();

        for( Card toCount : cards ){
            if( !count.containsKey(toCount.getCardSuit())){
                count.put(toCount.getCardSuit(), 0);
            }

            Integer currentCount = count.get(toCount.getCardSuit());

            count.put( toCount.getCardSuit(),
                    1 + currentCount );

        }

        return count;
    }


    //if no straight, return a null
    public FaceValue straightHighCardValue(){
        for (int i = 0; i < cards.length - 1; i++) {
            if (cards[i].getCardValue().value != cards[i+1].getCardValue().value + 1) {
                return null;
            }
        }
        return cards[0].getCardValue();
    }
    public boolean isFlush(){
        for (Suit key : suitCount.keySet()) {
            if (suitCount.get(key) == 5) {
                return true;
            }
        }
        return false;
    }

    public boolean isStraightFlush(){
        return (straightHighCardValue() != null) && isFlush();
    }

    public boolean isFullHouse(){
        return pairValue() != null && threeOfAKindValue() != null;
    }

    public boolean isRoyalFlush(){
        return isStraightFlush() && cards[0].getCardValue() == FaceValue.ACE;
    }

    //if not 4 of a kind, return null
    public FaceValue fourOfAKindValue(){
        for (FaceValue key : valueCount.keySet()) {
            if (valueCount.get(key) == 4) {
                return key;
            }
        }
        return null;
    }

    //should return null if there are really 4
    public FaceValue threeOfAKindValue(){
        for (FaceValue key : valueCount.keySet()) {
            if (valueCount.get(key) == 3) {
                return key;
            }
        }
        return null;
    }

    //should return null if there are really 3 (or more of the same card)
    public FaceValue pairValue(){
        for (FaceValue key : valueCount.keySet()) {
            if (valueCount.get(key) == 2) {
                return key;
            }
        }
        return null;
    }

    //should return null when there is only one pair
    public FaceValue lowerPairValue(FaceValue firstPairValue){
        if (firstPairValue == null) {
            return null;
        }
        for (FaceValue key : valueCount.keySet()) {
            if (valueCount.get(key) == 2 && key != firstPairValue) {
                return key;
            }
        }
        return null;
    }




    //return 0 if "this" ties with "that"
    //return negative number if "this" wins over "that"
    //return positive number if "this" loses to "that"
    public int compareTo( PokerHand that ) {
        int thisScore = 0;
        HandType thisHand;
        FaceValue thisHighCard = null;
        int thatScore = 0;
        FaceValue thatHighCard = null;

        if (this.isRoyalFlush()) {
            thisScore = 10;
            thisHand = HandType.ROYALFLUSH;
        } else if (this.isStraightFlush()) {
            thisScore = 9;
            thisHand = HandType.STRAIGHTFLUSH;
            thisHighCard = this.getCards()[0].getCardValue();
        } else if (this.fourOfAKindValue() != null) {
            thisScore = 8;
            thisHand = HandType.FOUROFAKIND;
            thisHighCard = this.fourOfAKindValue();
        } else if (this.isFullHouse()) {
            thisScore = 7;
            thisHand = HandType.FULLHOUSE;
            thisHighCard = this.threeOfAKindValue();
        } else if (this.isFlush()) {
            thisScore = 6;
            thisHand = HandType.FLUSH;
            thisHighCard = this.getCards()[0].getCardValue();
        } else if (this.straightHighCardValue() != null) {
            thisScore = 5;
            thisHand = HandType.STRAIGHT;
            thisHighCard = this.straightHighCardValue();
        } else if (this.threeOfAKindValue() != null) {
            thisScore = 4;
            thisHand = HandType.THREEOFAKIND;
            thisHighCard = this.threeOfAKindValue();
        } else if (this.pairValue() != null && this.lowerPairValue(this.pairValue()) != null) {
            thisScore = 3;
            thisHand = HandType.TWOPAIRS;
            thisHighCard = this.pairValue();
        } else if (this.pairValue() != null) {
            thisScore = 2;
            thisHand = HandType.ONEPAIR;
            thisHighCard = this.pairValue();
        } else {
            thisScore = 1;
            thisHand = HandType.HIGHCARD;
            thisHighCard = this.getCards()[0].getCardValue();
        }

        if (that.isRoyalFlush()) {
            thatScore = 10;
        } else if (that.isStraightFlush()) {
            thatScore = 9;
            thatHighCard = that.getCards()[0].getCardValue();
        } else if (that.fourOfAKindValue() != null) {
            thatScore = 8;
            thatHighCard = that.fourOfAKindValue();
        } else if (that.isFullHouse()) {
            thatScore = 7;
            thatHighCard = that.threeOfAKindValue();
        } else if (that.isFlush()) {
            thatScore = 6;
            thatHighCard = that.getCards()[0].getCardValue();
        } else if (that.straightHighCardValue() != null) {
            thatScore = 5;
            thatHighCard = that.straightHighCardValue();
        } else if (that.threeOfAKindValue() != null) {
            thatScore = 4;
            thatHighCard = that.threeOfAKindValue();
        } else if (that.pairValue() != null && that.lowerPairValue(that.pairValue()) != null) {
            thatScore = 3;
            thatHighCard = that.pairValue();
        } else if (that.pairValue() != null) {
            thatScore = 2;
            thatHighCard = that.pairValue();
        } else {
            thatScore = 1;
            thatHighCard = that.getCards()[0].getCardValue();
        }

        System.out.println("Player 1 Score: " + thisScore);
        System.out.println("Player 2 Score: " + thatScore);
        if (thisScore > thatScore) {
            return -1;
        } else if (thisScore < thatScore) {
            return 1;
        } else {
            switch (thisHand) {
                case ROYALFLUSH:
                    return 0;
                case STRAIGHTFLUSH:
                    return 0;
                case FOUROFAKIND:
                    return fourOfAKindTie(that, thisHighCard, thatHighCard);
                case FULLHOUSE:
                    return fullHouseTie(that, thisHighCard, thatHighCard);
                case FLUSH:
                    return flushTie(that);
                case STRAIGHT:
                    return 0;
                case THREEOFAKIND:
                    return fourOfAKindTie(that, thisHighCard, thatHighCard);
                case TWOPAIRS:
                    return twoPairTie(that, thisHighCard, thatHighCard);
                case ONEPAIR:
                    return onePairTie(that, thisHighCard, thatHighCard);
                case HIGHCARD:
                    return flushTie(that);
                default:
                    System.out.println("oh no");
                    return 0;
            }
        }
    }

    public int fourOfAKindTie(PokerHand that, FaceValue thisHighCard, FaceValue thatHighCard) {
        if (thisHighCard.value != thatHighCard.value) {
            return thisHighCard.value > thatHighCard.value ? - 1 : 1;
        } else {
            return 0;
        }
    }

    public int fullHouseTie(PokerHand that, FaceValue thisHighCard, FaceValue thatHighCard) {
        if (thisHighCard != thatHighCard) {
            return thisHighCard.value > thatHighCard.value ? -1 : 1;
        } else {
            if (this.pairValue() != that.pairValue()) {
                return this.pairValue().value > that.pairValue().value ? -1 : 1;
            } else {
                return 0;
            }
        }
    }

    public int flushTie(PokerHand that) {
        FaceValue thisHighCard = this.getCards()[0].getCardValue();
        FaceValue thatHighCard = that.getCards()[0].getCardValue();
        if (thisHighCard.value != thatHighCard.value) {
            return thisHighCard.value > thatHighCard.value ? -1 : 1;
        } else {
            for (int i = 1; i < this.getCards().length; i++) {
                if (this.getCards()[i].getCardValue() != that.getCards()[i].getCardValue()) {
                    return this.getCards()[i].getCardValue().value > that.getCards()[i].getCardValue().value ? -1 : 1;
                }
            }
            return 0;
        }
    }

    public int twoPairTie(PokerHand that, FaceValue thisHigh, FaceValue thatHigh) {
        if (thisHigh != thatHigh) {
            return thisHigh.value > thatHigh.value ? -1 : 1;
        }

        FaceValue thisLowerPair = this.lowerPairValue(thisHigh);
        FaceValue thatLowerPair = that.lowerPairValue(thatHigh);

        FaceValue thisHighCard = null;
        FaceValue thatHighCard = null;

        if (thisLowerPair.value > thatLowerPair.value) {
            return -1;
        } else if (thisLowerPair.value < thatLowerPair.value) {
            return 1;
        } else {
            for (FaceValue key : this.valueCount.keySet()) {
                if (key != thisHigh && key != thisLowerPair) {
                    thisHighCard = key;
                }
            }
            for (FaceValue key : that.valueCount.keySet()) {
                if (key != thatHigh && key != thatLowerPair) {
                    thatHighCard = key;
                }
            }

            if (thisHighCard != thatHighCard) {
                return thisHighCard.value > thatHighCard.value ? -1 : 1;
            } else {
                return 0;
            }
        }
    }

    public int onePairTie(PokerHand that, FaceValue thisHighCard, FaceValue thatHighCard) {
        if (thisHighCard != thatHighCard) {
            return thisHighCard.value > thatHighCard.value ? -1 : 1;
        }

        ArrayList<FaceValue> thisUnusedPairKeys = new ArrayList<>();
        ArrayList<FaceValue> thatUnusedPairKeys = new ArrayList<>();

        for (FaceValue key : this.valueCount.keySet()) {
            if (key != thisHighCard) {
                thisUnusedPairKeys.add(key);
            }
        }
        for (FaceValue key : that.valueCount.keySet()) {
            if (key != thatHighCard) {
                thatUnusedPairKeys.add(key);
            }
        }

        for (int i = 0; i < this.getCards().length; i++) {
            if (this.getCards()[i].getCardValue() != that.getCards()[i].getCardValue()
                    && thisUnusedPairKeys.contains(this.getCards()[i].getCardValue())
                    && thatUnusedPairKeys.contains(that.getCards()[i].getCardValue())) {
                return this.getCards()[i].getCardValue().value > that.getCards()[i].getCardValue().value ? -1 : 1;
            }
        }
        return 0;
    }

    //in general compareTo() sematics are
    // - means "this before that"
    // 0 means "they're equal"
    // + means "that before this"




}
