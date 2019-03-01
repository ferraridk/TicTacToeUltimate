/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeultimate.bll.Field;

import java.util.ArrayList;
import java.util.List;
import tictactoeultimate.bll.Move.IMove;
//import tictactoeultimate.bll.Move.Move.IMove;
import tictactoeultimate.bll.Move.Move;

/**
 *
 * @author Caspe
 */
public class Field implements IField
{
    private String [][] gameBoard = new String [9][9];
    private String [][] macroBoard = new String [3][3];
    
    
    public Field()
    {
        clearBoard();
    }
    
    @Override
    public void clearBoard()
    {
        gameBoard = new String [9][9];
        for (int i = 0; i < gameBoard.length; i++)
            for (int k = 0; k < gameBoard[i].length; k++) 
            {
                gameBoard[i][k] = EMPTY_FIELD;
            }
        
        for (int i = 0; i < macroBoard.length; i++)
            for (int k = 0; k < macroBoard[i].length; k++) 
            {
                macroBoard[i][k] = AVAILABLE_FIELD;
            }
    }

    @Override
    public List<IMove> getAvailableMoves()
    {
        List<IMove> availMoves = new ArrayList<>();
        
        for (int i = 0; i < gameBoard.length; i++)
            for (int j = 0; j < gameBoard.length; j++)
            {
                if (isInActiveMicroboard(i,j) && gameBoard[i][j].equals(EMPTY_FIELD))
                {
                   availMoves.add(new Move(i,j)); 
                }               
            }
        return availMoves;
    }

    @Override
    public String getPlayerId(int column, int row)
    {
        return gameBoard [column][row];
    }

    @Override
    public boolean isEmpty()
    {
        for (int i = 0; i < gameBoard.length; i++)
            for (int j = 0; j < gameBoard.length; j++)
            {
                if (gameBoard[i][j] != EMPTY_FIELD && gameBoard[i][j] != AVAILABLE_FIELD)
                {
                    return false;
                }
            } 
        return true;  
    }

    @Override
    public boolean isFull()
    {
        for (int i = 0; i < gameBoard.length; i++)
            for (int j = 0; j < gameBoard.length; j++)
            {
                if (gameBoard[i][j] == EMPTY_FIELD && gameBoard[i][j] == AVAILABLE_FIELD)
                {
                    return false;
                }
            } 
        return true;  
    }

    @Override
    public Boolean isInActiveMicroboard(int x, int y)
    {
        int xTransfer = x>0 ? x/3 : 0;
        int yTransfer = y>0 ? y/3 : 0;
        String value = macroBoard [xTransfer][yTransfer];
        
        return value.equals(AVAILABLE_FIELD);
    }

    @Override
    public String[][] getBoard()
    {
        return gameBoard;
    }

    @Override
    public String[][] getMacroboard()
    {
        return macroBoard;
    }

    @Override
    public void setBoard(String[][] board)
    {
        this.gameBoard = board;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.gameBoard[i][j] = board[i][j];
            }
        }
    }

    @Override
    public void setMacroboard(String[][] macroboard)
    {
        this.macroBoard = macroboard;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.macroBoard[i][j] = macroboard[i][j];
            }
        }
    }
    
}
