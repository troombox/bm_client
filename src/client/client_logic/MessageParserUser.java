package client.client_logic;

import java.util.ArrayList;
import java.util.Arrays;

import utility.entity.User;
import utility.enums.DataType;
import utility.enums.RequestType;

public class MessageParserUser {

	public static ArrayList<String> createMessageToServerDataType_User(User user, RequestType requestType) {
		ArrayList<String> result = new ArrayList<String>();
		result.add(requestType.toString());
		result.add(DataType.USER.toString());
		result.addAll(Arrays.asList(String.valueOf(user.getUser_ID()),user.getFirstName(),user.getLastName(),
				user.getPersonalBranch(),user.getEmail(), user.getPhone(), user.getUserType().toString(),
				user.getStatus(), user.getW4c(), String.valueOf(user.isSignedIn()), user.getPassword() ));
		return result;
	}

}
