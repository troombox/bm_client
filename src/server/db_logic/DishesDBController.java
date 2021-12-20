package server.db_logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import server.exceptions.BMServerException;
import utility.entity.Dish;
import utility.enums.ErrorType;

public class DishesDBController {

	private final String dishesTableNameInDB = "dishes";
	
	Connection dbConnection;
	String dbName;
	
	public DishesDBController(DBController dbController) {
		this.dbConnection = dbController.getDBConnection();
		this.dbName = dbController.getDBName();
	}
	
	public ArrayList<Dish> GetDishesListFromResIDData(String resID) throws BMServerException {
		ArrayList<Dish> result = new ArrayList<>();
		PreparedStatement ps;
		try {
			String query = "SELECT * FROM  `"+ dbName + "`." + dishesTableNameInDB +
					" WHERE resID = '" + resID + "'";
			ps = dbConnection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			if(!rs.next()) {//if restaurant doesn't exist 
				throw new BMServerException(ErrorType.DISHES_NOT_FOUND, "no dishes found");
			}
			
			//DB holds: a list of:[dish_ID|description|size|cooking_level|res_ID|price|name
			//we don't want to return some fields back to client side after authentication, so we leave them blank
			while(true) {
				result.add(new Dish(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8)));
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
