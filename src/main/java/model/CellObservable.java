package model;
import gui.ModelObserver;

public interface CellObservable {
	
	public void addObserver(ModelObserver observer);
	public void notifyObserver(String address, String text);
}
