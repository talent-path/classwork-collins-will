import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        // start with 7 strings in SOME collection
        // produce every combination of 5 of those strings

        String[] possibleInputs = {"a", "b", "c", "d", "e", "f", "g"};

        // with recursion
        // loop through each possibility
        //      record that that possibility has been picked
        //      recursively try with the current set of selections
        //      back out the choice we just tried

        List<String> selected = new ArrayList<>();
        List<List<String>> allCombinations = new ArrayList<>();

        sevenChooseFive(possibleInputs,0, selected, allCombinations);

        for(List<String> combination : allCombinations) {
            for(String notCard : combination) {
                System.out.print(notCard);
            }
            System.out.println();
        }
    }

    public static void sevenChooseFive(
            String[] possible,
            int nextIndex,
            List<String> currentlySelected,
            List<List<String>> allCombinations) {

        int chosenNum = currentlySelected.size();
        int remainingNum = 5 - chosenNum;

        int availableCards = possible.length - nextIndex;

        //base cases
        if (currentlySelected.size() == 5) {
            List<String> copy = new ArrayList<>();
            for (String toCopy : currentlySelected) {
                copy.add(toCopy);
            }
            allCombinations.add(copy);
            return;
        }

        if (availableCards < remainingNum) {
            return;
        }
        // iterate through each card
        // for each card, we either include or don't
        // we'll try the recursion with the card included and with
        // the card selected

        // try with the card first
        currentlySelected.add(possible[nextIndex]);
        sevenChooseFive(possible, nextIndex + 1, currentlySelected, allCombinations);

        currentlySelected.remove(currentlySelected.size() - 1);

        // try without choosing the card
        sevenChooseFive(possible, nextIndex + 1, currentlySelected, allCombinations);
    }
}
