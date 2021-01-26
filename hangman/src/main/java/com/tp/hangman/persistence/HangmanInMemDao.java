package com.tp.hangman.persistence;

import com.tp.hangman.models.HangmanGame;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class HangmanInMemDao implements HangmanDao {

    //Map<Integer, HangmanGame> allGames;

    List<HangmanGame> allGames = new ArrayList<>();

    public HangmanInMemDao(){
        HangmanGame onlyGame = new HangmanGame( 100, "zebra" );
        allGames.add( onlyGame );
    }

    @Override
    public HangmanGame getGameById(Integer gameId) {

//        HangmanGame toReturn = null;
//
//        for( HangmanGame toCheck : allGames ){
//            if( toCheck.getGameId().equals( gameId) ){
//                toReturn = toCheck;
//                break;
//            }
//        }
//
//        return toReturn;

        return allGames.stream().filter( g -> g.getGameId().equals(gameId) ).findFirst().orElse(null);
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
