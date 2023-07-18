package com.stock.management.system;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StockManagementSystem {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		StockDatabaseManager dsm = new StockDatabaseManager();
		
		//connecting to mysql server
		dsm.getMysqlConnection();
		
		System.out.println("Welcome to Stock Management System!");
		
		boolean exit = false;
		//program loop
		while(!exit) {
		
			System.out.println();
			
			//validating option
			int op = 0;
			while(true) {
				System.out.println("1. Enter Stock\n"
								+ "2. View Stock\n"
								+ "3. Availability of Stock\n"
								+ "4. Dispatch Stock\n"
								+ "5. Delivery Stock\n"
								+ "6. Exit");
				System.out.print("Choose the feature: ");
				try {
					op = sc.nextInt();
					System.out.println();
				}
				catch (InputMismatchException e) {
					op = 0;
			    }
				finally {
					sc.nextLine();
					if(op < 1 || op > 6) {
						System.out.println("Please enter a valid feature in the range of 1 to 6!\n");
					}
					else break;
				}
			}
			
			//switch case for the above given features
			switch (op) {
			
			//1. Enter Stock
			case 1:
				System.out.println("Please, provide the following Stock details:");
				String tagId = "";
				while(true) {
					System.out.print("Tag ID: ");
					tagId = sc.nextLine();
					if(tagId.matches("^[a-zA-Z0-9]*$")) {
						break;
					}
					else {
						System.out.println("Please enter a valid Tag ID!\n");
					}
				}
				
				String name = "";
				while(true) {
					System.out.print("Name: ");
					name = sc.nextLine();
					if(name.matches("^[a-zA-Z ]*$")) {
						break;
					}
					else {
						System.out.println("Please enter a valid name!\n");
					}
				}
				
				int availableQuantity = -1;
				while(true){
					try {
						System.out.print("Quantity: ");
						availableQuantity = sc.nextInt();
					}
					catch (InputMismatchException e) {
						availableQuantity = -1;
				    }
					finally {
						sc.nextLine();
						if(availableQuantity < 0 || availableQuantity > 1000000) {
							System.out.println("Please enter the quantity only in the range of 0 to 1000000!\n");
						}
						else break;
					}
				}
				
				Double price = -1.0;
				while(true){
					try {
						System.out.print("Price: ");
						price = sc.nextDouble();
					}
					catch (InputMismatchException e) {
						price = -1.0;
				    }
					finally {
						sc.nextLine();
						if(price < 0.0 || price > 1000000.0) {
							System.out.println("Please enter the price only in the range of 0.0 to 1000000.0!\n");
						}
						else break;
					}
				}
				
				Stock item = new Stock(tagId,name,availableQuantity,price);
				dsm.addStock(item);
				System.out.println("The Stock is Successfully added in the system!");
				break;
				
			//2. View Stock
			case 2:
				String tagId1 = "";
				while(true) {
					System.out.print("Enter the Stock Tag ID: ");
					tagId1 = sc.nextLine();
					if(tagId1.matches("^[a-zA-Z0-9]*$")) {
						break;
					}
					else {
						System.out.println("Please enter a valid Tag ID!\n");
					}
				}
				
				dsm.viewStockDetails(tagId1);
				break;
				
			//3. Availability of Stock
			case 3:
				String tagId2 = "";
				while(true) {
					System.out.print("Enter the Stock Tag ID: ");
					tagId2 = sc.nextLine();
					if(tagId2.matches("^[a-zA-Z0-9]*$")) {
						break;
					}
					else {
						System.out.println("Please enter a valid Tag ID!\n");
					}
				}
				dsm.viewAvailabilityOfStock(tagId2);
				break;
				
			//4. Dispatch Stock
			case 4:
				String tagId3 = "";
				while(true) {
					System.out.print("Enter the Stock Tag ID: ");
					tagId3 = sc.nextLine();
					if(tagId3.matches("^[a-zA-Z0-9]*$")) {
						break;
					}
					else {
						System.out.println("Please enter a valid Tag ID!\n");
					}
				}
				if(dsm.viewAvailabilityOfStock(tagId3)) {
					Stock s = dsm.getStockDetails(tagId3);
					if(s.getAvailableQuantity() == 0) System.out.println("\nSorry, the item is out of stock!");
					else {
						int dispatchQuantityOfStock = -1;
						while(true){
							try {
								System.out.print("Enter the quantity of stock to be dispatched: ");
								dispatchQuantityOfStock = sc.nextInt();
							}
							catch (InputMismatchException e) {
								dispatchQuantityOfStock = -1;
						    }
							finally {
								sc.nextLine();
								if(dispatchQuantityOfStock < 1 || dispatchQuantityOfStock > s.getAvailableQuantity()) {
									System.out.println("Please enter the quantity only in the range of 1 to available quantity "+s.getAvailableQuantity()+"\n");
								}
								else break;
							}
						}
						dsm.updateStock(tagId3,dispatchQuantityOfStock,"Dispatch");
						System.out.println("The Stock is dispatched Successfully!");
					}
				}
				break;
			
			//5. Delivery Stock
			case 5:
				String tagId4 = "";
				while(true) {
					System.out.print("Enter the Stock Tag ID: ");
					tagId4 = sc.nextLine();
					if(tagId4.matches("^[a-zA-Z0-9]*$")) {
						break;
					}
					else {
						System.out.println("Please enter a valid Tag ID!\n");
					}
				}
				
				if(dsm.viewDispatchedQuantityofStock(tagId4)) {
					Stock s1 = dsm.getStockDetails(tagId4);
					if(s1.getDispatchedQuantity() == 0) System.out.println("\nSorry, the item is out of stock!");
					else {
						int deliveryQuantityOfStock = -1;
						while(true){
							try {
								System.out.print("Enter the quantity of stock to be dispatched: ");
								deliveryQuantityOfStock = sc.nextInt();
							}
							catch (InputMismatchException e) {
								deliveryQuantityOfStock = -1;
						    }
							finally {
								sc.nextLine();
								if(deliveryQuantityOfStock < 1 || deliveryQuantityOfStock > s1.getDispatchedQuantity()) {
									System.out.println("Please enter the quantity only in the range of 1 to Dispatched quantity: "+s1.getDispatchedQuantity()+"\n");
								}
								else break;
							}
						}
						dsm.updateStock(tagId4,deliveryQuantityOfStock,"Deliver");
						System.out.println("The Stock is delivered Successfully!");
					}
				}
				break;
				
			//8. Exit
			case 6:
				exit = true;
				break;
				
			}
		}
		
		
		sc.close();
	}

}
