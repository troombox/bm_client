package db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Order;
import utility.RequestType;

public class OrderDBController {
	
	private final String orderTableNameInDB = "order";
	
	Connection dbConnection;
	String dbName;
	
	public OrderDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
	}
	
	
	//method that get order details from the DB 
	public Order getOrderDataFromDB(String orderNumber) {
		Order result;
		PreparedStatement ps;
		try {
			String query = "SELECT * FROM "+dbName+"."+orderTableNameInDB+" WHERE OrderNumber = ?";
			ps = dbConnection.prepareStatement(query);
			ps.setString(1, orderNumber);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				System.out.println("Did not find an order with orderNumber: " + orderNumber);
				return null;
			}
			result = new Order(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
			rs.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//TODO: check if exists!
	//method that writes a new order in the DB
	public boolean writeOrderDataToDB(Order order) {
		try {
			System.out.println("writeOrderDataToDB started");
			String query = "INSERT INTO "+dbName+"."+orderTableNameInDB+" (OrderNumber,Restaurant,OrderTime,PhoneNumber,TypeOfOrder,OrderAddress) VALUES (?,?,?,?,?,?)";
			PreparedStatement ps = dbConnection.prepareStatement(query);
			ps.setString(1, order.getOrderNumber());
			ps.setString(2, order.getRestaurantName());
			ps.setString(3, order.getOrderTime());
			ps.setString(4, order.getPhoneNumber());
			ps.setString(5, order.getTypeOfOrder());
			ps.setString(6, order.getOrderAddress());
			ps.executeUpdate();
			System.out.println("writeOrderDataToDB ended successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//method that updates existing order in DB
	public boolean updateOrderDataToDB(Order order) {
		System.out.println("updateOrderDataToDB started");
		Order currentOrderValue = null;
		Order newOrderValue = order;
		currentOrderValue = getOrderDataFromDB(order.getOrderNumber());
		if (currentOrderValue == null) {
			// TODO: take care of the error
			System.out.println("updateOrder Failed ,no order found to update");
			return false;
		}
		if (newOrderValue.getOrderAddress().equals(""))
			newOrderValue.setOrderAddress(currentOrderValue.getOrderAddress());
		if (newOrderValue.getOrderTime().equals(""))
			newOrderValue.setOrderTime(currentOrderValue.getOrderTime());
		if (newOrderValue.getTypeOfOrder().equals(""))
			newOrderValue.setTypeOfOrder(currentOrderValue.getTypeOfOrder());
		if (newOrderValue.getPhoneNumber().equals(""))
			newOrderValue.setPhoneNumber(currentOrderValue.getPhoneNumber());
		if (newOrderValue.getRestaurantName().equals(""))
			newOrderValue.setRestaurantName(currentOrderValue.getRestaurantName());
		try {
			String query = "UPDATE "+dbName+"."+orderTableNameInDB+" SET Restaurant = ? ,OrderTime = ? ,PhoneNumber = ? , TypeOfOrder = ?,OrderAddress = ? WHERE OrderNumber = ?";
			PreparedStatement ps = dbConnection.prepareStatement(query);
			ps.setString(1, newOrderValue.getRestaurantName());
			ps.setString(2, newOrderValue.getOrderTime());
			ps.setString(3, newOrderValue.getPhoneNumber());
			ps.setString(4, newOrderValue.getTypeOfOrder());
			ps.setString(5, newOrderValue.getOrderAddress());
			ps.setString(6, newOrderValue.getOrderNumber());
			ps.executeUpdate();
			System.out.println("updateOrderDataToDB ended successfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//method that handles write requests for Order data 
	public boolean handleWriteRequestMessage(RequestType actionRequired, Order orderData) {
		boolean result;
		if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_NEW_TO_DB) {
			result = writeOrderDataToDB(orderData);
		}else if (actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB) {
			result = updateOrderDataToDB(orderData);
		}else {
			System.out.println("ERROR handleWriteRequestMessage, unknown request");
			result = false;
			//TODO: add error handling
		}
		return result;
	}
	
}
