package server.db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.exceptions.BMServerException;
import utility.entity.Dish;
import utility.entity.Order;
import utility.enums.ErrorType;

public class OrderDBController {
	
	private final String ordersTableNameInDB = "orders";
	
	Connection dbConnection;
	String dbName;
	
	public OrderDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
	}

	public ArrayList<Order> getOrdersByResId(String resId) throws BMServerException{
		ArrayList<Order> result = new ArrayList<>();
		PreparedStatement ps;
		try {
			String query = "SELECT * FROM  `"+ dbName + "`." + ordersTableNameInDB +
					" WHERE resID = '" + resId + "'"; //get list of all orders in this specific restaurant
			ps = dbConnection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()) {//if there are no orders
				throw new BMServerException(ErrorType.ORDERS_NOT_FOUND, "no orders found");
			}
			
		
			while(true) { //for each order
				String query2 = "SELECT dishId FROM  `"+ dbName + "`." + "dish_in_order" +
						" WHERE orderId = '" + rs.getInt(1) + "'"; //get list of all dishes in this order
				PreparedStatement ps2 = dbConnection.prepareStatement(query2);
				ResultSet rs2 = ps2.executeQuery();
				
				Order order = new Order(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6),
						rs.getString(7), rs.getString(9), rs.getString(10));
				
				if(!rs2.next()) {//if there are no dishes in this order 
					rs2.close();
					break;
				}
				
				
				while(true) {//for each dish in this order
					String query3 = "SELECT * FROM  `"+ dbName + "`." + "dishes" +
						" WHERE dishId = '" + rs2.getInt(1) + "'"; //get details for this dish
					PreparedStatement ps3 = dbConnection.prepareStatement(query3);
					ResultSet rs3 = ps3.executeQuery();
					if(rs3.next()) { 
						order.getDishesInOrder().add(new Dish(rs3.getString(1), rs3.getString(2), rs3.getString(3), rs3.getString(4),
						rs3.getString(5), rs3.getString(6), rs3.getString(7), rs3.getString(8)));
					} 
					rs3.close();
					if(!rs2.next()) {
						rs2.close();
						break;
					}
					
				}
				
				
				result.add(order);
				if(!rs.next()) {
					rs.close();
					break;
				}
			}
						
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	

}
