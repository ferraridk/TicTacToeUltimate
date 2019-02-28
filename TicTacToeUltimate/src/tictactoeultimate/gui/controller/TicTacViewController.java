/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeultimate.gui.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import tictactoeultimate.be.GameManager;
import tictactoeultimate.bll.GameResult;
import tictactoeultimate.bll.IBot;
import tictactoeultimate.bll.IField;
import tictactoeultimate.bll.IMove;
import tictactoeultimate.bll.Move;
import tictactoeultimate.gui.model.StatsModel;
import tictactoeultimate.gui.model.TicTacModel;

/**
 * FXML Controller class
 *
 * @author Christian Occhionero
 */
public class TicTacViewController implements Initializable
{
    TicTacModel model;
    StatsModel statsModel;
    IBot bot0 = null;
    IBot bot1 = null;
    String player0 = null;
    String player1 = null;
    private boolean simulation;
    private final GridPane[][] gridMacro = new GridPane[3][3];
    private final JFXButton[][] jfxButtons = new JFXButton[9][9];
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    private boolean doMove(IMove move) {
        int currentPlayer = model.getCurrentPlayer();
        boolean validMove = model.doMove(move);
        checkAndLockIfGameEnd(currentPlayer);
        return validMove;
    }
     private void checkAndLockIfGameEnd(int currentPlayer) {
        if (model.getGameOverState() != GameManager.GameOverState.Active) {
            String[][] macroboard = model.getMacroboard();
            // Lock game
            for (int i = 0; i < 3; i++) {
                for (int k = 0; k < 3; k++) {
                    if (macroboard[i][k].equals(IField.AVAILABLE_FIELD)) {
                        macroboard[i][k] = IField.EMPTY_FIELD;
                    }
                }
            }
            if (model.getGameOverState().equals(GameManager.GameOverState.Tie)) {
                Platform.runLater(() -> showWinnerPane("TIE"));
            }
            else {
                Platform.runLater(() -> showWinnerPane(currentPlayer + ""));
            }
        }
    }
     
     private void insertButtonsIntoGridPanes() 
     {
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                GridPane gp = gridMacro[i][k];
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 3; y++) {
                        JFXButton btn = new JFXButton("");
                        btn.setButtonType(JFXButton.ButtonType.RAISED);
                        btn.getStyleClass().add("tictaccell");
                        btn.setUserData(new Move(x + i * 3, y + k * 3));
                        btn.setFocusTraversable(false);
                        btn.setOnMouseClicked(
                                event -> {
                                    doMove((IMove) btn.getUserData()); // Player move

                                    boolean isHumanVsBot = player0 != null ^ player1 != null;
                                    if (model.getGameOverState() == GameManager.GameOverState.Active && isHumanVsBot) {
                                        int currentPlayer = model.getCurrentPlayer();
                                        Boolean valid = model.doMove();
                                        checkAndLockIfGameEnd(currentPlayer);
                                    }
                                }
                        );
                        btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                        gp.add(btn, x, y);
                        jfxButtons[x + i * 3][y + k * 3] = btn;
                    }
                }
            }
        }
    }
//      private void showWinnerPane(String winner) {
//        String winMsg;
//        GameResult.Winner winStatus = GameResult.Winner.tie;
//        if (winner.equalsIgnoreCase("TIE")) {
//            winMsg = "Game tie";
//        }
//        else {
//            int winnerId = Integer.parseInt(winner);
//            winMsg = getNameFromId(winnerId) + " wins";
//            winStatus = winnerId == 0
//                    ? GameResult.Winner.player0
//                    : GameResult.Winner.player1;
//        }
//
//        GameResult gr = new GameResult(
//                getNameFromId(0),
//                getNameFromId(1),
//                winStatus);
//        statsModel.addGameResult(gr);
//
//        Label lblWinAnnounce = new Label(winMsg);
//        lblWinAnnounce.setAlignment(Pos.CENTER);
//        lblWinAnnounce.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        lblWinAnnounce.getStyleClass().add("winner-text");
//        lblWinAnnounce.getStyleClass().add("player" + winner);
//
//        Label lbl = new Label();
//        lbl.getStyleClass().add("winmsg");
//        lbl.getStyleClass().add("player" + winner);
//
//        lbl.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        lbl.setAlignment(Pos.CENTER);
//
//        Text fontAwesomeIcon = getFontAwesomeIconFromPlayerId(winner + "");
//        lbl.setGraphic(fontAwesomeIcon);
//        GridPane gridPane = new GridPane();
//        gridPane.addColumn(0);
//        gridPane.addRow(0);
//        gridPane.addRow(1);
//        ColumnConstraints cc = new ColumnConstraints();
//        cc.setPercentWidth(100);
//        cc.setHgrow(Priority.ALWAYS); // allow column to grow
//        cc.setFillWidth(true); // ask nodes to fill space for column
//        gridPane.getColumnConstraints().add(cc);
//
//        RowConstraints rc = new RowConstraints();
//        rc.setVgrow(Priority.ALWAYS); // allow row to grow
//        rc.setFillHeight(true);
//        rc.setPercentHeight(90);
//        gridPane.getRowConstraints().add(rc);
//        RowConstraints rc2 = new RowConstraints();
//        rc2.setVgrow(Priority.ALWAYS); // allow row to grow
//        rc2.setFillHeight(true);
//        rc2.setPercentHeight(10);
//        gridPane.getRowConstraints().add(rc2);
//
//        gridPane.add(lbl, 0, 0);
//        gridPane.add(lblWinAnnounce, 0, 1);
//        gridPane.setGridLinesVisible(true);
//
//        Platform.runLater(() -> stackMain.getChildren().add(gridPane));

    }
}
