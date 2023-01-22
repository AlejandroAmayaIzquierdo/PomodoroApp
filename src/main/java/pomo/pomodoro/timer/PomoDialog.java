package pomo.pomodoro.timer;


import com.sun.javafx.scene.control.skin.resources.ControlResources;

import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class PomoDialog extends Dialog<int[]> {
	
	private final GridPane grid;
	private final Label  headerlabel;
	private final TextField pomoTextField;
	private final TextField breakTextField;
	private final TextField sessionsTextField;
	
	public PomoDialog(int [] i) {
    	super();
    	final DialogPane dialogPane = getDialogPane();
		
    	//Grid
        
        this.grid = new GridPane();
        this.grid.setHgap(10);
        this.grid.setVgap(10);
        this.grid.setMaxWidth(Double.MAX_VALUE);
        this.grid.setAlignment(Pos.CENTER_LEFT);
        
        //header
        
        headerlabel = new Label(dialogPane.getContentText());
        headerlabel.setMaxWidth(Double.MAX_VALUE);
        headerlabel.setMaxHeight(Double.MAX_VALUE);
        headerlabel.getStyleClass().add("content");
        headerlabel.setWrapText(true);
        headerlabel.setPrefWidth(360);
        headerlabel.setPrefWidth(Region.USE_COMPUTED_SIZE);
        headerlabel.textProperty().bind(dialogPane.contentTextProperty());
        
        //TextField
        
        this.pomoTextField = new TextField();
        this.pomoTextField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(pomoTextField, Priority.ALWAYS);
        GridPane.setFillWidth(pomoTextField, true);
        
        this.breakTextField = new TextField();
        this.breakTextField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(breakTextField, Priority.ALWAYS);
        GridPane.setFillWidth(breakTextField, true);
        
        this.sessionsTextField = new TextField();
        this.sessionsTextField.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(sessionsTextField, Priority.ALWAYS);
        GridPane.setFillWidth(sessionsTextField, true);
        
        //Load Data
        
        pomoTextField.setText(i[0] + "");
        breakTextField.setText(i[1] + "");
        sessionsTextField.setText(i[2] + "");
        
        //Listeners
        
        dialogPane.contentTextProperty().addListener(o -> updateGrid());
        
        setTitle(ControlResources.getString("Dialog.confirm.title"));
        dialogPane.setHeaderText(ControlResources.getString("Dialog.confirm.header"));
        dialogPane.getStyleClass().addAll("text-input-dialog","choice-dialog");
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        
        pomoTextField.textProperty().addListener((o,ov,nv) -> {
            if (!nv.matches("\\d*")) {
            	pomoTextField.setText(nv.replaceAll("[^\\d]", ""));
            }
            if(nv.length() > 15) {
            	pomoTextField.setText(pomoTextField.getText().substring(0,pomoTextField.getText().length() - 1));
            }
        });
        breakTextField.textProperty().addListener((o,ov,nv) -> {
            if (!nv.matches("\\d*")) {
            	breakTextField.setText(nv.replaceAll("[^\\d]", ""));
            }
            if(nv.length() > 15) {
            	breakTextField.setText(breakTextField.getText().substring(0,breakTextField.getText().length() - 1));
            }
        });
        sessionsTextField.textProperty().addListener((o,ov,nv) -> {
            if (!nv.matches("\\d*")) {
            	sessionsTextField.setText(nv.replaceAll("[^\\d]", ""));
            }
            if(nv.length() > 15) {
            	sessionsTextField.setText(sessionsTextField.getText().substring(0,sessionsTextField.getText().length() - 1));
            }
        });
        
        
        
        updateGrid();
        
        setResultConverter((dialogButton) -> {
            ButtonData data = dialogButton == null ? null : dialogButton.getButtonData();
            return data == ButtonData.OK_DONE ?  getSelectedItem() : null;
        });
        
        
		
	}
	
    public final int[] getSelectedItem() {
    	return new int[] {
    			Integer.parseInt(pomoTextField.getText()),
    			Integer.parseInt(breakTextField.getText()),
    			Integer.parseInt(sessionsTextField.getText())
    			};
    }
	
    private void updateGrid() {
        grid.getChildren().clear();
        
        grid.add(headerlabel, 0, 0);
        grid.add(new Label("Pomo Time:"), 1, 0);
        grid.add(pomoTextField, 2, 0);
        grid.add(new Label("Break Time:"), 1, 1);
        grid.add(breakTextField, 2, 1);
        grid.add(new Label("Num of Sessions"), 1, 2);
        grid.add(sessionsTextField, 2, 2);
        
        getDialogPane().setContent(grid);
    }

}
