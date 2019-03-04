package tictactoeultimate.bll;

import tictactoeultimate.bll.GameState.GameState;
import tictactoeultimate.bll.Bot.IBot;
import tictactoeultimate.bll.Field.IField;
import tictactoeultimate.bll.Move.IMove;
import tictactoeultimate.bll.GameState.IGameState;
import tictactoeultimate.bll.Move.Move;

/**
 * This is a proposed GameManager for Ultimate Tic-Tac-Toe, the implementation
 * of which is up to whoever uses this interface. Note that initializing a game
 * through the constructors means that you have to create a new instance of the
 * game manager for every new game of a different type (e.g. Human vs Human,
 * Human vs Bot or Bot vs Bot), which may not be ideal for your solution, so you
 * could consider refactoring that into an (re-)initialize method instead.
 *
 * @author mjl
 */
public class GameManager
{

    /**
     * Three different game modes.
     */
    public enum GameMode
    {
        HumanVsHuman,
        HumanVsBot,
        BotVsBot
    }
    
        public enum GameOverState{
        Active,
        Win,
        Tie
    }

//    private final String NON_AVAILABLE_MACRO_CELL = "-";
    private final IGameState currentState;
    private int currentPlayer = 0; //player0 == 0 && player1 == 1
    private GameMode mode = GameMode.HumanVsHuman;
    private IBot bot1 = null;
    private IBot bot2 = null;
    private boolean playerGoesFirst = false;
    private volatile GameOverState gameOver = GameOverState.Active;
    
     public void setGameOver(GameOverState state) {
        gameOver = state;
    }
    public GameOverState getGameOver() {
        return gameOver;
    }

    public void setCurrentPlayer(int player) {
        currentPlayer = player;
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    /**
     * Set's the currentState so the game can begin. Game expected to be played
     * Human vs Human
     *
     * @param currentState Current game state, usually an empty board, but could
     * load a saved game.
     */
    public GameManager(IGameState currentState)
    {
        this.currentState = currentState;
        mode = GameMode.HumanVsHuman;
    }

    /**
     * Set's the currentState so the game can begin. Game expected to be played
     * Human vs Bot
     *
     * @param currentState Current game state, usually an empty board, but could
     * load a saved game.
     * @param bot The bot to play against in vsBot mode.
     */
    public GameManager(IGameState currentState, IBot bot, boolean humanPlaysFirst)
    {
        this.currentState = currentState;
        mode = GameMode.HumanVsBot;
        this.bot1 = bot;
        playerGoesFirst = humanPlaysFirst;
    }

    /**
     * Set's the currentState so the game can begin. Game expected to be played
     * Bot vs Bot
     *
     * @param currentState Current game state, usually an empty board, but could
     * load a saved game.
     * @param bot The first bot to play.
     * @param bot2 The second bot to play.
     */
    public GameManager(IGameState currentState, IBot bot, IBot bot2)
    {
        this.currentState = currentState;
        mode = GameMode.BotVsBot;
        this.bot1 = bot;
        this.bot2 = bot2;
    }

    public IGameState getCurrentState()
    {
        return currentState;
    }
    /**
     * User input driven Update
     *
     * @param move The next user move
     * @return Returns true if the update was successful, false otherwise.
     */
    public Boolean updateGame(IMove move)
    {
        //Verify the new move
        if (!verifyMoveLegality(move))
        {
            return false;
        }

        //Update the currentState
        updateBoard(move);
        updateMacroboard(move);

        //Update currentPlayer
        currentPlayer = (currentPlayer + 1) % 2;
//        currentState.setMoveNumber(currentState.getMoveNumber()+1);

        return true;
    }

    /**
     * Non-User driven input, e.g. an update for playing a bot move.
     *
     * @return Returns true if the update was successful, false otherwise.
     */
    public Boolean updateGame()
    {
        //Check game mode is set to one of the bot modes.
        assert (mode != GameMode.HumanVsHuman);

        //Check if player is bot, if so, get bot input and update the state based on that.
        if (mode == GameMode.HumanVsBot && currentPlayer == 1)
        {
            //Check bot is not equal to null, and throw an exception if it is.
            assert (bot1 != null);

            IMove botMove = bot1.doMove(currentState);

            //Be aware that your bots might perform illegal moves.
            return updateGame(botMove);
        }

        //Check bot is not equal to null, and throw an exception if it is.
        assert (bot1 != null);
        assert (bot2 != null);

        //Implements a bot vs bot Update.
        if (mode == GameMode.BotVsBot)
        {
            assert (bot1 != null);
            assert (bot2 != null);
            
            IMove botMove = currentPlayer == 0 ? bot1.doMove(new GameState(currentState)) : bot2.doMove(new GameState(currentState));
            
            return updateGame(botMove);
        }
        return false;
    }

    private Boolean verifyMoveLegality(IMove move)
    {
        //Test if the move is legal   
        //NOTE: should also check whether the move is placed on an occupied spot.
        System.out.println("Checking move validity against macroboard available field");
        System.out.println("Not currently checking move validity actual board");
        return currentState.getField().isInActiveMicroboard(move.getX(), move.getY());
//        boolean isInActiveMicroBoard = currentState.getField().isInActiveMicroBoard(move.getX(), move.getY());
//        boolean isAvailable = currentState.getField().getPlayerId(move.getX(), move.getY()).equals(IField.EMPTY_FIELD);
//        return isInActiveBoard && isAvailable;
    }

    private void updateBoard(IMove move)
    {
        String [][] gameBoard = currentState.getField().getBoard();
        gameBoard[move.getX()][move.getY()] = currentPlayer + "";
        currentState.setMoveNumber(currentState.getMoveNumber() + 1);
        if(currentState.getMoveNumber() % 2 == 0) { currentState.setRoundNumber(currentState.getRoundNumber() + 1);}
        updateMacroboard(move);   
        
          
        //currentState.getField().getBoard()[move.getX()][move.getY()] = "" + currentPlayer;
    }

    private void updateMacroboard(IMove move)
    {
        int macroX = move.getX()/3;
        int macroY = move.getY()/3;
//        String[][] macroBoard = currentState.getField().getMacroboard();
        
//        //Checks if already won. If already won all macro cells available for play
//        if(currentState.getField().getMacroboard()[macroX][macroY] != IField.AVAILABLE_FIELD
//                && currentState.getField().getMacroboard()[macroX][macroY] != NON_AVAILABLE_MACRO_CELL)
//        {
//            for (int x = 0; x < macroBoard.length; x++)
//            {
//                for (int y = 0; y < macroBoard.length[x]; y++)
//                {
//                    if(currentState.getField().getMacroboard()[x][y] == NON_AVAILABLE_MACRO_CELL)
//                    {
//                        macroBoard[x][y] = IField.AVAILABLE_FIELD;
//                    }
//                    
//                }
//                
//            }
//            
//            //If already not won, set all to non available., and set only clicked to available
//            if(currentState.getField().getMacroboard()[macroX][macroY] != IField.NON_AVAILABLE_MACRO_CELL
//                && currentState.getField().getMacroboard()[macroX][macroY] !=IField.AVAILABLE_FIELD)
//            {
//                for (int x = 0; x < macroBoard.length; x++)
//                {
//                    for (int y = 0; y < macroBoard.length[x]; y++)
//                    {
//                        if(currentState.getField().getMacroboard()[x][y] == IField.AVAILABLE_FIELD)
//                        {
//                        macroBoard[x][y] = NON_AVAILBLE_MACRO_CELL;
//                        }
//                    
//                    }
//                    
//                }
//                currentState.getField().getMacroboard()[macroX][macroY] =IField.AVAILABLE_FIELD;
//            }
//        }
        
        String[][] macroBoard = currentState.getField().getMacroboard();
            for (int i = 0; i < macroBoard.length; i++)
                for (int j = 0; j < macroBoard[i].length; j++) 
                {
                    if(macroBoard[i][j].equals(IField.AVAILABLE_FIELD))
                    {
                    macroBoard[i][j] = IField.EMPTY_FIELD;
                    }
                }

        int xTransfer = move.getX() % 3;
        int yTransfer = move.getY() % 3;

        if(macroBoard[xTransfer][yTransfer].equals(IField.EMPTY_FIELD))
        {
            macroBoard[xTransfer][yTransfer] = IField.AVAILABLE_FIELD;
        }
        
        else 
        {
            // Field is already won, set all fields not won to avail.
            for (int i = 0; i < macroBoard.length; i++)
                for (int j = 0; j < macroBoard[i].length; j++) 
                {
                    if(macroBoard[i][j].equals(IField.EMPTY_FIELD))
                    {
                        macroBoard[i][j] = IField.AVAILABLE_FIELD;
                    }    
                }
        }
    }
}
