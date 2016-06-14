package com.Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.DB.DBActions;

public class ServerAPI {
	private static final String GET_SHOWS_LIST = "GET SHOWS LIST";
	private static final String GET_FAVORITES = "GET FAVORITES";
	private static final String FIND_SHOWS = "FIND SHOWS";
	private static final String DOWNLOAD_EPISODE = "DOWNLOAD EPISODE";
	private static final String DOWNLOAD_SEASON = "DOWNLOAD SEASON";
	private static final String ADD_FAVORITE = "ADD FAVORITE";
	
	public static String processInput(String inputLine) {
		String input = inputLine==null?"":inputLine;
		Object[] inputs = fixInput(input);
		if(inputs.length > 0){
			switch(inputs[0].toString()){
			case GET_SHOWS_LIST:
				return getShowsList();
			case GET_FAVORITES:
				return getFavorites();
			case FIND_SHOWS:
				if(inputs.length > 0 && inputs[1] != null){
					return findShows(inputs[1].toString());
				}
				else{
					return "Invalid inputs!!";
				}
			case DOWNLOAD_EPISODE:
				if(inputs.length > 0 && inputs[1] != null){
					return downlaodEpisode(inputs[1].toString());
				}
				else{
					return "Invalid inputs!!";
				}
			case DOWNLOAD_SEASON:
				if(inputs.length > 0 && inputs[1] != null){
					return downlaodSeason(inputs[1].toString());
				}
				else{
					return "Invalid inputs!!";
				}
			case ADD_FAVORITE:
				if(inputs.length > 0 && inputs[1] != null){
					return addFavorite(inputs[1].toString());
				}
				else{
					return "Invalid inputs!!";
				}
			default:
				return "ServerAPI v1.0";
			}
		}
		return "ServerAPI v1.0";
	}
	
	private static String addFavorite(String showName) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String downlaodSeason(String md5) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String downlaodEpisode(String md5) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String findShows(String showname) {
		JSONArray shows = new JSONArray();
		ResultSet rs = DBActions.findShows(showname);
		boolean done = false;
		try{
		while(!done){
			String showName = rs.getString("showname");
			String season = rs.getString("season") == null?"0":rs.getString("season");
			String episode = rs.getString("episode") == null?"0":rs.getString("episode");
			String md5 = rs.getString("md5_checksum");
			JSONObject jo = new JSONObject();
			jo.put("showname", showName);
			jo.put("season",season);
			jo.put("episode",episode);
			jo.put("MD5",md5 );
			shows.put(jo);
			done = !rs.next();
		}
		}catch(SQLException e){
			System.out.println("SQLException in getShowsList: "+e.getMessage());
		}
		JSONObject jo = new JSONObject();
		jo.put("shows", shows);
		return jo.toString();
	}

	private static String getFavorites() {
		JSONArray shows = new JSONArray();
		ResultSet rs = DBActions.getFavorites();
		boolean done = false;
		try{
		while(!done){
			String showName = rs.getString("showname");
			JSONObject jo = new JSONObject();
			jo.put("showname", showName);
			shows.put(jo);
			done = !rs.next();
		}
		}catch(SQLException e){
			System.out.println("SQLException in getShowsList: "+e.getMessage());
		}
		JSONObject jo = new JSONObject();
		jo.put("favorites", shows);
		return jo.toString();
	}

	private static Object[] fixInput(String input){
		List<String> inputs = new ArrayList<String>();
		if(input.length() > 0){
			String[] temp = input.split(";");
			for(int i=0;i<temp.length;i++){
				inputs.add(temp[i].trim());
			}
		}
		return inputs.toArray();
	}

	private static String getShowsList() {
		JSONArray shows = new JSONArray();
		ResultSet rs = DBActions.getAllShows();
		boolean done = false;
		try{
		while(!done){
			String showName = rs.getString("showname");
			String season = rs.getString("season") == null?"0":rs.getString("season");
			String episode = rs.getString("episode") == null?"0":rs.getString("episode");
			String md5 = rs.getString("md5_checksum");
			JSONObject jo = new JSONObject();
			jo.put("showname", showName);
			jo.put("season",season);
			jo.put("episode",episode);
			jo.put("MD5",md5 );
			shows.put(jo);
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
