/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeultimate.bll.GameState;

import tictactoeultimate.bll.Field.IField;
import tictactoeultimate.bll.Field.Field;

/**
 *
 * @author Caspe
 */
public class GameState implements IGameState
{
    IField field;
        int moveNumber;
        int roundNumber;
        int timePerMove = 1000;
        
    public GameState()
    {
        field = new Field();
        moveNumber = 0;
        roundNumber = 0;
    }
    
    public GameState(IGameState gamestate)
    {
        field = new Field();
        field.setMacroboard(gamestate.getField().getMacroboard());
        field.setBoard(gamestate.getField().getBoard());
        moveNumber = gamestate.getMoveNumber();
        roundNumber = gamestate.getRoundNumber();
    }

    @Override
    public IField getField()
    {
        return field;
    }

    @Override
    public int getMoveNumber()
    {
        return moveNumber;
    }

    @Override
    public void setMoveNumber(int moveNumber)
    {
        this.moveNumber = moveNumber;
    }

    @Override
    public int getRoundNumber()
    {
        return roundNumber;
    }

    @Override
    public void setRoundNumber(int roundNumber)
    {
        this.roundNumber = roundNumber;
    }
}
