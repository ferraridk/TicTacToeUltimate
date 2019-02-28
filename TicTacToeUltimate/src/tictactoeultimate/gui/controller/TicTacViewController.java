/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeultimate.gui.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import tictactoeultimate.be.GameManager;
import tictactoeultimate.bll.IMove;
import tictactoeultimate.bll.Move;

/**
 * FXML Controller class
 *
 * @author Christian Occhionero
 */
public class TicTacViewController implements Initializable
{

    @FXML
    private GridPane gridMacro;

    private GridPane[][] gridMicroes = new GridPane[3][3];
    private JFXButton[][] jfxButtons = new JFXButton[9][9];

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        gridMacro.toFront();
        createMicroGridPanes();
    }

    @FXML
    private void handleButtonAction(ActionEvent event)
    {
    }

    private void createMicroGridPanes()
    {
        for (int i = 0; i < 3; i++)
        {
            gridMacro.addRow(i);
            for (int k = 0; k < 3; k++)
            {
                GridPane gp = new GridPane();
                for (int m = 0; m < 3; m++)
                {
                    gp.addColumn(m);
                    gp.addRow(m);
                }
                gridMicroes[i][k] = gp;
                for (int j = 0; j < 3; j++)
                {
                    ColumnConstraints cc = new ColumnConstraints();
                    cc.setPercentWidth(33);
                    cc.setHgrow(Priority.ALWAYS); // allow column to grow
                    cc.setFillWidth(true); // ask nodes to fill space for column
                    gp.getColumnConstraints().add(cc);

                    RowConstraints rc = new RowConstraints();
                    rc.setVgrow(Priority.ALWAYS); // allow row to grow
                    rc.setFillHeight(true);
                    rc.setPercentHeight(33);
                    gp.getRowConstraints().add(rc);
                }

                gp.setGridLinesVisible(true);
                gridMacro.addColumn(k);
                gridMacro.add(gp, i, k);
            }
        }
        insertButtonsIntoGridPanes();
    }

    private void insertButtonsIntoGridPanes()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int k = 0; k < 3; k++)
            {
                GridPane gp = gridMicroes[i][k];
                for (int x = 0; x < 3; x++)
                {
                    for (int y = 0; y < 3; y++)
                    {
                        JFXButton btn = new JFXButton("");
                        btn.setButtonType(JFXButton.ButtonType.RAISED);
                        btn.getStyleClass().add("tictaccell");
                        btn.setUserData(new Move(x + i * 3, y + k * 3));
                        btn.setFocusTraversable(false);
                        btn.setOnMouseClicked(
                                event ->
                        {
                            doMove((IMove) btn.getUserData()); // Player move

                            boolean isHumanVsBot = player0 != null ^ player1 != null;
                            if (model.getGameOverState() == GameManager.GameOverState.Active && isHumanVsBot)
                            {
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
}
