package pomo.main;

import java.net.URISyntaxException;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pomo.pomodoro.timer.TimerController;
import pomo.util.Notification;

public class App extends Application {
	
	public static Stage mainStage;
	
	private TimerController timerController;

	@Override
	public void start(Stage stage) throws Exception {
		
		this.mainStage = stage;
		
		timerController = new TimerController();
		
		stage.setOnCloseRequest(e -> {
			timerController.getPomo().stopThread();
		});
		
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
		
		stage.setMinHeight(400);
		stage.setMinWidth(600);
		
		stage.setTitle("Pomodoro");
		stage.setScene(new Scene(timerController.getView()));
		stage.show();
		

		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
