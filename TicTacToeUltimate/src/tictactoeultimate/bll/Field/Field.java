/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeultimate.bll.Field;

import java.util.ArrayList;
import java.util.List;
import tictactoeultimate.bll.Move.IMove;
import tictactoeultimate.bll.Move.Move;

/**
 *
 * @author Caspe
 */
public class Field implements IField
{
    private String[][] gameBoard;
    private String[][] macroBoard; 
    
    
    public Field()
    {
        gameBoard = new String[9][9];
        macroBoard = new String[3][3];
        clearBoard();
    }
    
    @Override
    public void clearBoard()
    {
        gameBoard = new String [9][9];
        for (int x = 0; x < gameBoard.length; x++)
            for (int y = 0; y < gameBoard[x].length; y++) 
            {
                gameBoard[x][y] = EMPTY_FIELD;
            }
        
        for (int x = 0; x < macroBoard.length; x++)
            for (int y = 0; y < macroBoard[x].length; y++) 
            {
                macroBoard[x][y] = AVAILABLE_FIELD;
            }
    }

    @Override
    public List<IMove> getAvailableMoves()
    {
        List<IMove> availMoves = new ArrayList<>();
//        
//        for (int x = 0; x < gameBoard.length; x++)
//            for (int y = 0; y < gameBoard[x].length; y++)
//            {
//                boolean isEmpty = gameBoard[x][y] == EMPTY_FIELD;
//                if (isEmpty && isInActiveMicroboard(x, y))
//                {
//                   availMoves.add(new Move(x,y)); 
//                }               
//            }
//        return availMoves;
        
          for (int x = 0; x < gameBoard.length; x++)
            for (int y = 0; y < gameBoard[x].length; y++)
            {
                if (isInActiveMicroboard(x,y) && gameBoard[x][y].equals(EMPTY_FIELD))
                {  
                   availMoves.add(new Move(x,y)); 
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
        for (int x = 0; x < gameBoard.length; x++)
            for (int y = 0; y < gameBoard[x].length; y++)
            {
                if (!gameBoard[x][y].equals(EMPTY_FIELD))
                {
                    return false;
                }
            } 
        return true;  
    }

    @Override
    public boolean isFull()
    {
        for (int x = 0; x < gameBoard.length; x++)
            for (int y = 0; y < gameBoard[x].length; y++)
            {
                if (!gameBoard[x][y].equals(AVAILABLE_FIELD))
                {
                    return false;
                }
            } 
        return true;  
    }

    @Override
    public Boolean isInActiveMicroboard(int x, int y)
    {
        int macroX =  x/3;
        int macroY = y/3;
        
        return macroBoard[macroX][macroY].equals(AVAILABLE_FIELD);
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
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                this.gameBoard[x][y] = board[x][y];
            }
        }
    }

    @Override
    public void setMacroboard(String[][] macroboard)
    {
        this.macroBoard = macroboard;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                this.macroBoard[x][y] = macroboard[x][y];
            }
        }
    }
    
}
