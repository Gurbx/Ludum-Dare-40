package com.gurbx.ld40.inventory;

import java.util.LinkedList;

public class Inventory {
	private int playerCrystals;
	private int storageCrystals;
	
	private LinkedList<InventoryObserver> observers;
	
	public Inventory() {
		playerCrystals = 0;
		storageCrystals = 0;
		observers = new LinkedList<InventoryObserver>();
	}
	
	public void addObserver(InventoryObserver observer) {
		observers.add(observer);
	}
	
	public void addCrystalToPlayer() {
		playerCrystals++;
		notifyObservers();
	}
	
	public void removeCrystalFromPlayer() {
		playerCrystals--;
		if (playerCrystals <= 0) playerCrystals = 0;
		notifyObservers();
	}
	
	public void transferToStorage() {
		storageCrystals += playerCrystals;
		playerCrystals = 0;
		notifyObservers();
	}
	
	public int getPlayerCrystals() {
		return playerCrystals;
	}
	
	public int getStorageCrystals() {
		return storageCrystals;
	}
	
	public void notifyObservers() {
		for (int i = 0; i < observers.size(); i++) {
			observers.get(i).inventoryStatus(playerCrystals, storageCrystals);
		}
	}

	public boolean canDropCrystal() {
		return playerCrystals >= 1;
	}

}
