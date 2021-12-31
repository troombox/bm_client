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
	
	public Boolean moveOrder(String resId, String status) {
		PreparedStatement ps;
		try {
			String query = "UPDATE `" + dbName + "`." +  ordersTableNameInDB +
							" SET status = ?" +
							" WHERE orderId = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, status);
			ps.setInt(2, Integer.parseInt(resId));
			ps.executeUpdate();
			if(status.equals("in the kitchen")) {
                String query1 = "UPDATE `" + dbName +"`." + ordersTableNameInDB +
                                " SET timeOfApproval = CURRENT_TIMESTAMP" +
                                " WHERE orderId = ?";

                PreparedStatement ps1 = dbConnection.prepareStatement(query1);
                ps1.setInt(1, Integer.parseInt(resId));
                ps1.executeUpdate();
            }
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Boolean cancelOrder(String orderId) {
		PreparedStatement ps;
		PreparedStatement ps1;
		PreparedStatement ps2;
		PreparedStatement ps3;
		PreparedStatement ps4;
		
		ResultSet rs1;
		ResultSet rs2;
		ResultSet rs3;
		
		try {
			String query5 = "SELECT recieverID" +
							" FROM `" + dbName + "`." + ordersTableNameInDB +
							" WHERE orderId = ?";
			ps4 = dbConnection.prepareStatement(query5);
			ps4.setInt(1, Integer.parseInt(orderId));
			rs1 = ps4.executeQuery();
			if(rs1.next()) {
				
				int recieverId = rs1.getInt(1);
				String query2 = "SELECT totalPrice" +
								" FROM `" + dbName + "`." + ordersTableNameInDB +
								" WHERE orderId = ?";
				ps1 = dbConnection.prepareStatement(query2);
				ps1.setInt(1, Integer.parseInt(orderId));
				rs2 = ps1.executeQuery();
				rs2.next();
				int totalPrice = rs2.getInt(1);
				
				String query3 = "SELECT budget" +
								" FROM `" + dbName + "`.business_client BC, `" + dbName + "`." + ordersTableNameInDB + " O"+
								" WHERE O.orderId = ? AND BC.userId = O.recieverID";
				ps2 = dbConnection.prepareStatement(query3);
				ps2.setInt(1, Integer.parseInt(orderId));
				rs3 = ps2.executeQuery();
				rs3.next();
				int budget = rs3.getInt(1);
				
				String query4 = "UPDATE `" + dbName + "`.business_client" +
								" SET budget = ?" +
								" WHERE userId = ?";
				ps3 = dbConnection.prepareStatement(query4);
				int sum = budget + totalPrice;
				ps3.setInt(1, sum);
				ps3.setInt(2, recieverId);
				ps3.executeUpdate();
			}
			
			String query = "DELETE FROM `" + dbName + "`." +  ordersTableNameInDB +
							" WHERE orderId = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setInt(1, Integer.parseInt(orderId));
			ps.executeUpdate();
			
			String query1= "DELETE FROM `" + dbName + "`.dish_in_order" +
					" WHERE orderId = ?";
			ps1 = dbConnection.prepareStatement(query1);
			ps1.setInt(1, Integer.parseInt(orderId));
			ps1.executeUpdate();
		
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
		

	public ArrayList<Order> getOrdersByResId(String resId, String status) throws BMServerException{
		ArrayList<Order> result = new ArrayList<>();
		PreparedStatement ps;
		boolean flag = true;

		try {
			String query = "SELECT * FROM  `"+ dbName + "`." + ordersTableNameInDB +
					" WHERE resID = '" + resId + "' AND status = '" + status +"'"; //get list of all orders in this specific restaurant that are in specific status
			ps = dbConnection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()) {//if there are no orders
//				throw new BMServerException(ErrorType.ORDERS_NOT_FOUND, "no orders found");
				flag = false;
			}
			
		
			while(flag == true) { //for each order
				//saves all dishes in ordes
				String query2 = "SELECT * FROM  `"+ dbName + "`." + "dish_in_order" +
						" WHERE orderId = '" + rs.getInt(1) + "'"; //get list of all dishes in this order
				PreparedStatement ps2 = dbConnection.prepareStatement(query2);
				ResultSet rs2 = ps2.executeQuery();
				
				//saves email of client
				
				String query6 = "SELECT email FROM  `"+ dbName + "`." + "users" +
						" WHERE userId = '" + rs.getInt(11) + "'"; //get list of all dishes in this order
				PreparedStatement ps6 = dbConnection.prepareStatement(query6);
				ResultSet rs6 = ps6.executeQuery();
				
				Order order = new Order(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6),
						rs.getString(7), rs.getString(9), rs.getString(10));
				
				if(rs6.next())
					order.setUserEmail(rs6.getString(1));
				
				rs6.close();
				
				if(!rs2.next()) {//if there are no dishes in this order 
					rs2.close();
					break;
				}
				
				
				while(true) {//for each dish in this order
					String query3 = "SELECT * FROM  `"+ dbName + "`." + "dishes" +
						" WHERE dishId = '" + rs2.getInt(3) + "'"; //get details for this dish
					PreparedStatement ps3 = dbConnection.prepareStatement(query3);
					ResultSet rs3 = ps3.executeQuery();
					if(rs3.next()) { 
						Dish dish = new Dish(rs3.getString(1), rs3.getString(2), rs3.getString(3), rs3.getString(4),
								rs3.getString(5), rs2.getString(5), rs2.getString(6), rs3.getString(8));
						dish.setExceptions(rs2.getString(7));
						System.out.println(rs2.getString(7));
						order.getDishesInOrder().add(dish);
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
