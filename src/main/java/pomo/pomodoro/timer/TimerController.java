package pomo.pomodoro.timer;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import pomo.main.App;
import pomo.util.Notification;
import pomo.util.Pomodoro;

public class TimerController implements Initializable {
	
    //Model
    
    private Pomodoro pomo = new Pomodoro();
    
    public static Notification notification = new Notification("/sound/bell.wav");
    
	//View
	
	@FXML
    private Button startStopButton;
    @FXML
    private Button skipButton;


    @FXML
    private Text time;
    @FXML
    private Text sessionsText;
    @FXML
    private Text stateText;

    @FXML
    private GridPane view;
    

    
    public TimerController() {
		
		try {
			
			// FXML
			
			FXMLLoader loader = new FXMLLoader(TimerController.class.getResource("/fxml/TimerView.fxml"));
			System.out.println(loader.getLocation());
			loader.setController(this);
			loader.load();
			
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//binds
		
		time.textProperty().bind(pomo.timerProperty());
		
		sessionsText.textProperty().bindBidirectional(pomo.sessionDoneProperty());
		stateText.textProperty().bind(pomo.stateProperty());
		
		skipButton.disableProperty().bind(pomo.canSkipProperty().not());
		
		//Listeners
//		
//		sessionsText.textProperty().addListener((o,ov,nv) -> {
//			sessionsText.setText(nv + "/" + pomo.getNumSessions());
//		});
	}
	
    @FXML
    void startStopAction(ActionEvent event) {
    	if(pomo.pausePlay()) {
    		startStopButton.setText("Pause");
    	}else {
    		startStopButton.setText("Play");
    	}
    	
    }
	
    @FXML
    void skipAction(ActionEvent event) {
    	pomo.nextState();
    }
    
    @FXML
    void settingsAction(ActionEvent event) throws IOException {
    	PomoDialog dialog = new PomoDialog(new int[] {
    			pomo.getPomoTime(),
    			pomo.getBreakTime(),
    			pomo.getNumSessions()}
    	);
    	
    	dialog.setTitle("config pomo");
    	dialog.setHeaderText("Configure the values");
    	dialog.initOwner(App.mainStage);
    	
    	Optional<int[]> result = dialog.showAndWait();
    	
    	if(result.isPresent()) {
    		int[] resultInt = result.get();
    		pomo.setValues(resultInt[0], resultInt[1], resultInt[2]);
    	}
    }


	
    @FXML
    void MouseOnButton(MouseEvent event) {
    	Button e = (Button) event.getSource();
    	e.setStyle("-fx-background-color: #2d3133;");

    }
    @FXML
    void MouseExitButton(MouseEvent event) {
    	Button e = (Button) event.getSource();
    	e.setStyle("-fx-background-color: #181a1b;");
    }
	
	public GridPane getView() {
		return this.view;
	}
	public Pomodoro getPomo() {
		return this.pomo;
	}
    public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}
    
    

}
