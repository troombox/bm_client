package server.db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utility.entity.Dish;
import utility.entity.Order;

public class OrderDBController {

	private final String orderTableNameInDB = "orders";
	private final String refundsTableNameInDB = "refunds";
	private final String dishInOrderTableNameInDB = "dish_in_order";

	Connection dbConnection;
	String dbName;

	public OrderDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
	}

	public void writeOrderDataToDB(Order order) {
		System.out.println("entered writeOrderDataToDB");
		System.out.println(order.toString());
		try {
			System.out.println("writeOrderDataToDB started");
			String query = "INSERT INTO `" + dbName + "`." + orderTableNameInDB
					+ " (resId,typeOfOrder,address,status,totalPrice,timeOfOrder,nameOfReceiver, phone, orderPrice, orderDeliveryPrice, isPersonal) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = dbConnection.prepareStatement(query);
			ps.setInt(1, order.getRestaurantID());
			ps.setString(2, order.getTypeOfOrder().toString());
			ps.setString(3, order.getDeliveryAddress());
			ps.setString(4, "not_approved");
			ps.setInt(5, order.getOrderPrice() + order.getDeliveryPrice());
			ps.setString(6,order.getTimeOfOrder());
			String name = order.getUserFirstName() + " " + order.getUserLastName();
			ps.setString(7, name);
			ps.setString(8, order.getUserPhone());
			ps.setInt(9, order.getOrderPrice());
			ps.setInt(10, order.getDeliveryPrice());
			ps.setBoolean(11, order.isPrivateOrder());
			ps.executeUpdate();
			
			query = "SELECT MAX(orderId) FROM `"+dbName+"`."+orderTableNameInDB+" WHERE resId = ? AND timeOfOrder = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setInt(1, order.getRestaurantID());
			ps.setString(2,order.getTimeOfOrder());
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				System.out.println("writeOrderDataToDB: could not find the order we just added!");
				return;
			}
			int orderID = rs.getInt(1);
			System.out.println("writeOrderDataToDB - orderID: "+ orderID);
			query = "INSERT INTO `" + dbName + "`." + dishInOrderTableNameInDB + " (orderId,dishId,userId,size,cookingLevel,exceptions) VALUES (?,?,?,?,?,?)";
			for(Dish d : order.getDishesInOrder()) {
				System.out.println(d.getName());
				ps = dbConnection.prepareStatement(query);
				ps.setInt(1, orderID);
				ps.setString(2, d.getDish_ID());
				ps.setString(3, "");
				ps.setString(4, d.getSize());
				ps.setString(5, d.getCooking_level());
				ps.setString(6, d.getDescription());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e1) {
			System.out.println(e1.getMessage());
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void updateBudegetForBClient(int valuePaid) {
		
	}

	public int getCRFDataForRestaurantsGiven(ArrayList<String> resIDs) {
		String userID = resIDs.get(0);
		int result = 0;
		PreparedStatement ps;
		try {
			for(int i = 1; i < resIDs.size(); i++) {
				String query = "SELECT refundAmount FROM `"+dbName+"`."+refundsTableNameInDB+" WHERE userID = ? AND resID = ?";
				ps = dbConnection.prepareStatement(query);
				ps.setString(1, userID);
				ps.setString(2, resIDs.get(i));
				ResultSet rs = ps.executeQuery();
				if(!rs.next()) {
					continue;
				}
				result += Integer.parseInt(rs.getString(1));
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}
