package com.tp.connectfour.persistence;


import com.tp.connectfour.exceptions.InvalidGameIDException;
import com.tp.connectfour.exceptions.NullGameIDException;
import com.tp.connectfour.models.ConnectFourViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConnectFourInMemDao implements ConnectFourDao {


    List<ConnectFourViewModel> allGames = new ArrayList<>();

    @Override
    public ConnectFourViewModel getGameByID(Integer gameID) throws NullGameIDException {
        if (gameID == null) {
            throw new NullGameIDException("Game ID cannot be null.");
        }

        ConnectFourViewModel toReturn = null;

        for( ConnectFourViewModel toCheck : allGames ){
            if( toCheck.getGameID().equals( gameID) ){
                toReturn = new ConnectFourViewModel(toCheck);
                toReturn.setInProgress(toCheck.isInProgress());
                break;
            }
        }

        return toReturn;
    }

    @Override
    public List<ConnectFourViewModel> getAllGames() {
        List<ConnectFourViewModel> copyList = new ArrayList<>();
        for( ConnectFourViewModel toCopy : allGames ){
            copyList.add( new ConnectFourViewModel(toCopy));
        }
        return copyList;
    }

    @Override
    public void clearBoard(Integer gameID) throws NullGameIDException, InvalidGameIDException
    {
        if (gameID == null) {
            throw new NullGameIDException("Game ID cannot be null.");
        }

        boolean found = false;
        for( int i = 0; i < allGames.size(); i++){
            if( allGames.get(i).getGameID().equals(gameID)){
                //we found the game to update
                allGames.set(i, new ConnectFourViewModel(gameID) );
                found = true;
            }
        }

        if (!found) {
            throw new InvalidGameIDException("Game cannot be found with ID " + gameID);
        }
    }

    @Override
    public ConnectFourViewModel startGame() {
        int id = 0;

        for( ConnectFourViewModel toCheck : allGames ){
            if( toCheck.getGameID() > id ){
                id = toCheck.getGameID();
            }
        }

        id++;

        ConnectFourViewModel toAdd = new ConnectFourViewModel(id);
        allGames.add( toAdd );
        return toAdd;
    }

    @Override
    public void deleteGame(Integer gameId) throws InvalidGameIDException {

        int removeIndex = -1;

        for( int i = 0; i < allGames.size(); i++ ){
            if( allGames.get(i).getGameID().equals(gameId)){
                removeIndex = i;
                break;
            }
        }
        if( removeIndex != -1 ){
            allGames.remove(removeIndex);
        } else {
            throw new InvalidGameIDException("Could not find game with id " + gameId + "to delete.");
        }

    }


}
