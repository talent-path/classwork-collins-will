package com.tp.hangman.persistence;

import com.tp.hangman.models.HangmanGame;
import com.tp.hangman.models.HangmanViewModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class HangmanInMemDao implements HangmanDao {

    String[] wordBank = {"ability", "able", "about", "accept", "according"};

    List<HangmanGame> allGames = new ArrayList<>();
    public HangmanInMemDao(){
        this.wordBank =  wordBank;
        for(int i = 0; i < wordBank.length; i++){
            int gameID = 100 + i;
            allGames.add(new HangmanGame(gameID, wordBank[i]));
        }
        // HangmanGame onlyGame = new HangmanGame( 100, "zebra" );
        // allGames.add( onlyGame );
    }

    @Override
    public List<HangmanGame> getAllGames() {
        List<HangmanGame> copyList = new ArrayList<>();

        for (HangmanGame game : allGames) {
            copyList.add(new HangmanGame(game));
        }

        return copyList;
    }

    @Override
    public void updateGame(HangmanGame game) {

        for (int i = 0; i < allGames.size(); i++) {
            if (allGames.get(i).getGameId().equals(game.getGameId())) {
                allGames.set(i, new HangmanGame(game));
                break;
            }
        }

    }

    @Override
    public HangmanGame getGameById(Integer gameId) {

        HangmanGame toReturn = null;

        for( HangmanGame toCheck : allGames ){
            if( toCheck.getGameId().equals( gameId) ){
                toReturn = new HangmanGame(toCheck);
                break;
            }
       }

       return toReturn;

        //return allGames.stream().filter( g -> g.getGameId().equals(gameId) ).findFirst().orElse(null);
    }

    public List<HangmanGame> getVowelGames(){

        List<HangmanGame> toReturn = new ArrayList<>();

        for( HangmanGame toCheck : allGames ){
            String word = toCheck.getHiddenWord().toLowerCase();
            if( word.charAt(0) == 'a' ||
                    word.charAt(0) == 'e' ||
                    word.charAt(0) == 'i' ||
                    word.charAt(0) == 'o' ||
                    word.charAt(0) == 'u'){
                toReturn.add( toCheck );
            }
        }

        return toReturn;
    }
}
