package tictactoeultimate.bll.GameState;

import tictactoeultimate.bll.Field.IField;

/**
 *
 * @author mjl
 */
public interface IGameState {

    IField getField();

    int getMoveNumber();
    void setMoveNumber(int moveNumber);

    int getRoundNumber();
    void setRoundNumber(int roundNumber);
    
    int getTimePerMove();
    void setTimePerMove(int milliSeconds);
    
    
}
