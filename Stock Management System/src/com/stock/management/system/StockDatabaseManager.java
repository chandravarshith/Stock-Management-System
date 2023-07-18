package com.stock.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class StockDatabaseManager {
	private Connection con = null; 
	public Statement st = null;
	
	public Connection getCon() {
		return this.con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}
	
	//connecting to mysql server and creating the stocks table if not present
	public void getMysqlConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String url = "jdbc:mysql://127.0.0.1:3306/stock_management";
		String username = "Demo";
		String password = "pcvg";
		this.con = DriverManager.getConnection(url,username,password);
		this.st = this.con.createStatement();
		
		//checking for stock table and creating it if its not present
		try {
			String query = "create table if not exists stock("
					+ "	tagID varchar(20) not null Primary key,"
					+ " item_name varchar(50) not null,"
					+ " available_quantity int,"
					+ " price decimal(10,2),"
					+ " availability_status varchar(50) not null,"
					+ " dispatched_quantity int,"
					+ " delivered_quantity int"
					+ " )";
			this.st.executeUpdate("use stock_management");
			this.st.executeUpdate(query);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	//checking for the tagID in stock database and return it if present.
	public Stock getStockDetails(String tagId) {
		try {
			String query = "select * from stock where tagID = '"+tagId+"';";
			ResultSet s = this.st.executeQuery(query);
			if(!s.next()) {
				return null;
			}
			
			String name = s.getString("item_name");
			int availableQuantity = Integer.parseInt(s.getString("available_quantity"));
			double price = Double.parseDouble(s.getString("price"));
			String status = s.getString("availability_status");
			int dispatchedQuantity = Integer.parseInt(s.getString("dispatched_quantity"));
			int deliveredQuantity = Integer.parseInt(s.getString("delivered_quantity"));
			Stock item = new Stock(tagId,name,availableQuantity,price,status,dispatchedQuantity,deliveredQuantity);
			return item;
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public void addStock(Stock s) {
		try {
			String query = "insert into stock(tagID,item_name,available_quantity,price,availability_status,dispatched_quantity,delivered_quantity) "
					+ "values('"+s.getTagId()+"','"+s.getName()+"','"+s.getAvailableQuantity()+"','"+s.getPrice()
					+"','"+s.getStatus()+"','"+s.getDispatchedQuantity()+"','"+s.getDeliveredQuantity()+"')";
			this.st.executeUpdate(query);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void viewStockDetails(String tagId) {
		try {
			Stock s = getStockDetails(tagId);
			if(s == null) {
				System.out.println("\nSorry, no stock is available with Tag ID: "+tagId);
				return;
			}
			
			System.out.println("----------------------------------------------------------------------------------------------------------");
			System.out.println("\n\t\t\t\t\t\tStock Details");
			System.out.println("----------------------------------------------------------------------------------------------------------");
			System.out.println("\t\t\t\tName\t\t\t:\t"+s.getName());
			System.out.println("\t\t\t\tAvailable Quantity\t:\t"+s.getAvailableQuantity());
			System.out.println("\t\t\t\tPrice\t\t\t:\t"+s.getPrice());
			System.out.println("\t\t\t\tStatus\t\t\t:\t"+s.getStatus());
			System.out.println("\t\t\t\tDispatched Quantity\t:\t"+s.getDispatchedQuantity());
			System.out.println("\t\t\t\tDelivered Quantity\t:\t"+s.getDeliveredQuantity());
			System.out.println("----------------------------------------------------------------------------------------------------------");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean viewAvailabilityOfStock(String tagId) {
		Stock s = getStockDetails(tagId);
		if(s == null) {
			System.out.println("\nSorry, no stock is available with Tag ID: "+tagId);
			return false;
		}
		
		System.out.println("\nAvailability Status\t:\t"+s.getStatus());
		System.out.println("Available Quantity\t:\t"+s.getAvailableQuantity());
		return true;
	}
	
	public boolean viewDispatchedQuantityofStock(String tagId) {
		Stock s = getStockDetails(tagId);
		if(s == null) {
			System.out.println("\nSorry, no stock is available with Tag ID: "+tagId);
			return false;
		}
		if(s.getStatus().equalsIgnoreCase("Out of Stock")) {
			System.out.println("\nSorry, the stock with Tag ID: "+tagId+" is "+s.getStatus());
			return false;
		}
		System.out.println("Dispatched Quantity\t:\t"+s.getDispatchedQuantity());
		return true;
	}

	
	public void updateStock(String tagId, int quantity, String feature ) {
		Stock s = getStockDetails(tagId);
		if(s == null) {
			System.out.println("\nSorry, no stock is available with Tag ID: "+tagId);
			return;
		}
		
		try {
			if(feature.equalsIgnoreCase("Dispatch")) {
				s.setAvailableQuantity(s.getAvailableQuantity()-quantity);
				s.setDispatchedQuantity(s.getDispatchedQuantity()+quantity);
				
				String query = "update stock set available_quantity=?,dispatched_quantity=? where tagID=? ";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, s.getAvailableQuantity());
		        ps.setInt(2, s.getDispatchedQuantity());
		        ps.setString(3, tagId);
		        ps.executeUpdate();
			}
			else if(feature.equalsIgnoreCase("Deliver")) {
				s.setDispatchedQuantity(s.getDispatchedQuantity()-quantity);
				s.setDeliveredQuantity(s.getDeliveredQuantity()+quantity);
				
				String query = "update stock set dispatched_quantity=?,delivered_quantity=? where tagID=? ";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, s.getDispatchedQuantity());
		        ps.setInt(2, s.getDeliveredQuantity());
		        ps.setString(3, tagId);
		        ps.executeUpdate();
			}
		}
        catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
