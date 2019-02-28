/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeultimate.gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tictactoeultimate.bll.GameResult;

/**
 *
 * @author simge
 */
public class StatsModel {
    private final ObservableList<GameResult> gameResults = 
            FXCollections.observableArrayList();
    
    private final StringProperty lastSimulationResults = 
            new SimpleStringProperty("");

    public StringProperty lastSimulationResultsProperty() {
        return lastSimulationResults;
    }
    
    public String getLastSimulationResults() {
        return lastSimulationResults.get();
    }

    public void setLastSimulationResults(String lastSimulationResults) {
        this.lastSimulationResults.set(lastSimulationResults);
    }
    
    public ObservableList<GameResult> getGameResults(){
        return gameResults;
    }
    
    public synchronized void addGameResult(GameResult gr) {
        gameResults.add(gr);
    }

    public void clear() {
        gameResults.clear();
    }
}
