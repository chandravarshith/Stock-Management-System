package com.stock.management.system;

public class Stock {
	private String tagId;
	private String name;
	private int availableQuantity;
	private double price;
	private String status;
	private int dispatchedQuantity;
	private int deliveredQuantity;

	public Stock(String tagId, String name, int availableQuantity,double price) {
		super();
		this.tagId = tagId;
		this.name = name;
		this.availableQuantity = availableQuantity;
		this.price = price;
		
		if(this.availableQuantity > 0)
			this.status = "Available";
		else this.status = "Out of Stock";
		this.dispatchedQuantity = 0;
		this.deliveredQuantity = 0;
	}

	public Stock(String tagId, String name, int availableQuantity, double price, String status, int dispatchedQuantity,
			int deliveredQuantity) {
		super();
		this.tagId = tagId;
		this.name = name;
		this.availableQuantity = availableQuantity;
		this.price = price;
		this.status = status;
		this.dispatchedQuantity = dispatchedQuantity;
		this.deliveredQuantity = deliveredQuantity;
	}



	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double sellPrice) {
		this.price = sellPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDispatchedQuantity() {
		return dispatchedQuantity;
	}

	public void setDispatchedQuantity(int dispatchedQuantity) {
		this.dispatchedQuantity = dispatchedQuantity;
	}

	public int getDeliveredQuantity() {
		return deliveredQuantity;
	}

	public void setDeliveredQuantity(int deliveredQuantity) {
		this.deliveredQuantity = deliveredQuantity;
	}
	
	
}
