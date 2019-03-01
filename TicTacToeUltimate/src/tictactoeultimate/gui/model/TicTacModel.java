/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeultimate.gui.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.InvalidationListener;
import tictactoeultimate.bll.GameManager;
import tictactoeultimate.bll.GameState.GameState;
import tictactoeultimate.bll.Bot.IBot;
import tictactoeultimate.bll.GameState.IGameState;
import tictactoeultimate.bll.Move.IMove;
import javafx.beans.Observable;

/**
 *
 * @author Christian Occhionero
 */
public class TicTacModel implements Observable
{
    private final List<InvalidationListener> listeners = new ArrayList<>();
    private final GameManager game;
    
    public TicTacModel() 
    {
        game = new GameManager(new GameState());
    }
    
    public TicTacModel(IBot bot, boolean humanPlaysFirst) 
    {
        game = new GameManager(new GameState(), bot, humanPlaysFirst);
    }
    
    public TicTacModel(IBot bot1, IBot bot2) 
    {
        game = new GameManager(new GameState(), bot1, bot2);
    }

    private void notifyAllListeners(){
        for (InvalidationListener listener : listeners){
            listener.invalidated((Observable) this);
        }
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }
    
    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }

    public IGameState getGameState() {
        return game.getCurrentState();
    }

    public boolean doMove() {
        boolean valid = game.updateGame();
        if(valid)
            notifyAllListeners();
        return valid;
    }

    public boolean doMove(IMove move){
        boolean valid = game.updateGame(move);
        if(valid)
            notifyAllListeners();
        return valid;
    }

    public String[][] getMacroboard()
    {
        return game.getCurrentState().getField().getMacroboard();
    }

    public String[][] getBoard(){
        return game.getCurrentState().getField().getBoard();
    }

    public int getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public GameManager.GameOverState getGameOverState() {
        return game.getGameOver();
    }
}
