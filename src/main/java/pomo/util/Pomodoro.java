package pomo.util;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pomo.pomodoro.timer.TimerController;

public class Pomodoro implements Runnable {
	
	Thread thread;
	Temporizador tempPomo;
	Temporizador tempBreak;
	
	private StringProperty text = new SimpleStringProperty();
	private StringProperty timer = new SimpleStringProperty();

	
	private StringProperty state = new SimpleStringProperty();
	private IntegerProperty index = new SimpleIntegerProperty();
	private IntegerProperty numSessions = new SimpleIntegerProperty();
	
	private StringProperty sessionDone = new SimpleStringProperty();
	
	
	private BooleanProperty canSkip = new SimpleBooleanProperty(true);
	private BooleanProperty isRun = new SimpleBooleanProperty(false);
	
	public Pomodoro() {
		setValues(60,10,4);
		
		
		startThread();
	}
	
	public Pomodoro(int pomo,int breakTime,int num) {
		
		setValues(pomo,breakTime,num);
		
		
		startThread();
	}
	
	public void startThread() {
		thread = new Thread(this);
		thread.start();
	}
	public void setValues(int minutosPomo,int minutosBreak,int numSessions) {
		if((minutosPomo > 60 && minutosPomo < 0) || (minutosBreak > 60 && minutosBreak < 0) || (numSessions - 1 < 0)) {
			throw new RuntimeException("Valores Invalidos");
		}
		tempPomo = new Temporizador(0,minutosPomo,0);
		tempBreak = new Temporizador(0,minutosBreak,0);
		this.numSessions.set(numSessions);
		this.index.set(1);
		
		sessionDone.set(index.get() + "/" + this.numSessions.get());
		
		state.set("SESSION");
		
	}


	@Override
	public void run() {
		
		double drawInterval = 1000000000/60;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		
		while(thread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			

			if(delta >= 1) {
				
				update();
				
				delta--;
			}
			
			if(timer >= 1000000000) {
				timer = 0;
				if(isRun.get()) {
					if(index.get() <= numSessions.get()) {
						switch (state.get()) {
						case "SESSION":
							tempPomo.restTime();
							break;
						case "BREAK":
							tempBreak.restTime();
							break;
						}
					}
				}
				
			}
		}
		
		
	}
	public void update() {
		
		if(index.get() <= numSessions.get()) {
			text.set(state.get() + " " + index.get());
			switch (state.get()) {
			case "SESSION":
				timer.set(tempPomo.toString());
				if(tempPomo.checkFinish()) {
					state.set("BREAK");
					index.set(index.get() + 1);
					this.sessionDone.set(index.get() + "/" + this.numSessions.get());
					tempPomo.restartTime();
					TimerController.notification.playSound();
				}
				break;
			case "BREAK":
				timer.set(tempBreak.toString());
				if(tempBreak.checkFinish()) {
					state.set("SESSION");
					tempBreak.restartTime();
					TimerController.notification.playSound();
				}
				break;
			}
		}
	}
	
	public void nextState() {
		if(getIndex() < getNumSessions()) {
			this.tempPomo.restartTime();
			this.tempBreak.restartTime();
			if(this.state.get().equals("SESSION")) {
				this.index.set(getIndex() + 1);
				this.sessionDone.set(index.get() + "/" + this.numSessions.get());
				this.state.set("BREAK");
			}else {
				this.state.set("SESSION");
			}
			TimerController.notification.playSound();
		} else {
			setCanSkip(false);
		}
	}
	
	public void skipSessions() {
		if(getIndex() < getNumSessions()) {
			this.index.set(getIndex() + 1);
			this.tempPomo.restartTime();
			this.tempBreak.restartTime();
			this.sessionDone.set(index.get() + "/" + this.numSessions.get());
			this.state.set("SESSION");
			TimerController.notification.playSound();
		}else {
			setCanSkip(false);
		}

	}
	
	public boolean pausePlay() {
		if(isRun.get()) {
			isRun.set(false);
		}
		else if(!isRun.get()) {
			isRun.set(true);
		}
		return isRun.get();
	}
	
	public void stopThread() {
		this.thread = null;
	}

	public final StringProperty textProperty() {
		return this.text;
	}
	
	public int getPomoTime() {
		return this.tempPomo.getMinutesInitial();
	}
	public int getBreakTime() {
		return this.tempBreak.getMinutesInitial();
	}
	

	public final String getText() {
		return this.textProperty().get();
	}
	

	public final void setText(final String text) {
		this.textProperty().set(text);
	}
	

	public final StringProperty timerProperty() {
		return this.timer;
	}
	

	public final String getTimer() {
		return this.timerProperty().get();
	}
	

	public final void setTimer(final String timer) {
		this.timerProperty().set(timer);
	}

	public final StringProperty stateProperty() {
		return this.state;
	}
	

	public final String getState() {
		return this.stateProperty().get();
	}
	

	public final void setState(final String state) {
		this.stateProperty().set(state);
	}
	

	public final IntegerProperty indexProperty() {
		return this.index;
	}
	

	public final int getIndex() {
		return this.indexProperty().get();
	}
	

	public final void setIndex(final int index) {
		this.indexProperty().set(index);
	}
	

	public final IntegerProperty numSessionsProperty() {
		return this.numSessions;
	}
	

	public final int getNumSessions() {
		return this.numSessionsProperty().get();
	}
	

	public final void setNumSessions(final int numSessions) {
		this.numSessionsProperty().set(numSessions);
	}

	public final StringProperty sessionDoneProperty() {
		return this.sessionDone;
	}
	

	public final String getSessionDone() {
		return this.sessionDoneProperty().get();
	}
	

	public final void setSessionDone(final String sessionDone) {
		this.sessionDoneProperty().set(sessionDone);
	}

	public final BooleanProperty canSkipProperty() {
		return this.canSkip;
	}
	

	public final boolean isCanSkip() {
		return this.canSkipProperty().get();
	}
	

	public final void setCanSkip(final boolean canSkip) {
		this.canSkipProperty().set(canSkip);
	}
	

	public final BooleanProperty isRunProperty() {
		return this.isRun;
	}
	

	public final boolean isIsRun() {
		return this.isRunProperty().get();
	}
	

	public final void setIsRun(final boolean isRun) {
		this.isRunProperty().set(isRun);
	}
	
	
	
	
	
	


}
