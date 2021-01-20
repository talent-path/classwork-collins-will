//enum is short for enumeration
//we'll describe a set of allowable values
//which are attached to this enum
//and then in code we can make variables of the type Suit, which can only have these values
public enum Suit {
    SPADES(0),
    DIAMONDS(1),
    CLUBS(2),
    HEARTS(3);

    public int value = -1;

    Suit( int value ){
        this.value = value;
    }
}
