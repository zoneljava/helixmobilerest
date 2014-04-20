package com.example.helixmobile;

public class SyncHelper {
	
	public boolean activRunState = true;

	public synchronized boolean isActivRunState() {
		return activRunState;
	}

	public synchronized void setActivRunState(boolean activRunState) {
		this.activRunState = activRunState;
	}
	
	

}
