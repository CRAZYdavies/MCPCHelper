package com.Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.DB.DBActions;

public class ServerAPI {
	private static final String GET_SHOWS_LIST = "GET SHOWS LIST";
	
	
	public static String processInput(String inputLine) {
		String input = inputLine==null?"":inputLine;
		switch(input){
		case GET_SHOWS_LIST:
			return getShowsList();
		default:
			return "ServerAPI v1.0";
		}
	}

	private static String getShowsList() {
		List<String> shows = new ArrayList<String>();
		ResultSet rs = DBActions.getAllShows();
		boolean done = false;
		try{
		while(!done){
			String showName = rs.getString("showname");
			String season = rs.getString("season") == null?"0":rs.getString("season");
			String episode = rs.getString("episode") == null?"0":rs.getString("episode");
			shows.add(showName+":S"+season+"E"+episode);
			done = !rs.next();
		}
		}catch(SQLException e){
			System.out.println("SQLException in getShowsList: "+e.getMessage());
		}
		JSONObject jo = new JSONObject();
		jo.put("shows", shows);
		return jo.toString();
	}
}
