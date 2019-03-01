package tictactoeultimate.bll.Bot;

import tictactoeultimate.bll.GameState.IGameState;
import tictactoeultimate.bll.GameState.IGameState;
import tictactoeultimate.bll.Move.IMove;

/**
 *
 * @author mjl
 */
public interface IBot {

    /**
     * Makes a turn. Implement this method to make your bot do something.
     *
     * @param state the current game state
     * @return The column where the turn was made.
     */
    IMove doMove(IGameState state);
    
}
