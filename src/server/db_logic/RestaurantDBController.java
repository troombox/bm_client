package server.db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.exceptions.BMServerException;
import utility.entity.Restaurant;
import utility.enums.ErrorType;


public class RestaurantDBController {
	private final String restaurantTableNameInDB = "restaurant";
	
	Connection dbConnection;
	String dbName;
	
	public RestaurantDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
	}
	
	public ArrayList<Restaurant> GetRestaurantsListFromSearchData(String searchText) throws BMServerException{
		ArrayList<Restaurant> result = new ArrayList<>();
		PreparedStatement ps;
		try {
			
			String query = "SELECT * FROM  `"+ dbName + "`." + restaurantTableNameInDB +
					" WHERE resName LIKE '%" + searchText + "%'";
			ps = dbConnection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()) {//if restaurant doesn't exist 
				throw new BMServerException(ErrorType.INVALID_CREDENTIALS_RESTAURANT_NOT_FOUND, "no restaurants found");
			}
			
			//DB holds: a list of:[resID|resName|category|branch]
			//we don't want to return some fields back to client side after authentication, so we leave them blank
			while(true) {
				result.add(new Restaurant(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
				if(!rs.next()) break;
			}
			
			rs.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
