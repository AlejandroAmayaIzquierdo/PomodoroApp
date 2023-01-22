package pomo.util;

public class Temporizador {
	
	
	private int hours,minutes,seconds;
	
	
	private int hoursInitial,minutesInitial,secondsInitial;
	
	public Temporizador() {
		
	}
	
	
	public Temporizador(int hours,int minutes,int seconds){
		setValues(hours,minutes,seconds);
	}
	
	public void setValues(int hours,int minutes,int seconds) {
		if((hours < 0) && (minutes < 0 && minutes > 60) && (seconds < 0 && seconds > 60)) {
			throw new RuntimeException("Tiempo Invalido");
		}
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		
		hoursInitial = hours;
		minutesInitial = minutes;
		secondsInitial = seconds;
	}
	
	public void restTime() {
		
		if(seconds > 0) {
			seconds--;
		}else {
			if(minutes > 0) {
				minutes--;
				seconds = 59;
			}
			
		}
		
		if(minutes <= 0 && hours > 0) {
			hours--;
			minutes = 60;
		}
	}
	
	public boolean checkFinish() {
		if(hours <= 0 && minutes <= 0 && seconds <= 0) {
			return true;
		}
		return false;
	}
	
	public void restartTime() {
		this.hours = hoursInitial;
		this.minutes = minutesInitial;
		this.seconds = secondsInitial;
	}
	public static void staticTimer(int hours,int minutes,int seconds) {
		
		int _hours = hours;
		int _minutes = minutes;
		int _seconds = seconds;
		
		boolean stop = false;
		
		long lastTime = System.nanoTime();
		long currentTime;
		double delta = 0;
		
		while(!stop) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime);
			
			lastTime = currentTime;
			
			
			if(delta >= 1000000000) {
				delta = 0;
				if(_seconds > 0) {
					_seconds--;
				}else {
					if(_minutes > 0) {
						_minutes--;
						_seconds = 60;
					}
					
				}
				
				if(_minutes <= 0 && _hours > 0) {
					_hours--;
					_minutes = 60;
				}
				
				if(_hours == 0 && _minutes == 0 && _seconds == 0) {
					stop = true;
				}
			}
		}
	}	
	
	
	public String toString() {
		return minutes + ":" + seconds;
	}


	public int getHours() {
		return hours;
	}


	public int getMinutes() {
		return minutes;
	}


	public int getSeconds() {
		return seconds;
	}


	public int getHoursInitial() {
		return hoursInitial;
	}


	public int getMinutesInitial() {
		return minutesInitial;
	}


	public int getSecondsInitial() {
		return secondsInitial;
	}
	
	

}
