package com.Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.DB.DBActions;
import com.Utorrent.UtorrentActions;

public class ServerAPI {
	private static final String GET_SHOWS_LIST = "GET SHOWS LIST";
	private static final String GET_FAVORITES = "GET FAVORITES";
	private static final String FIND_SHOWS = "FIND SHOWS";
	private static final String DOWNLOAD_EPISODE = "DOWNLOAD EPISODE";
	private static final String DOWNLOAD_SEASON = "DOWNLOAD SEASON";
	private static final String ADD_FAVORITE = "ADD FAVORITE";
	private UtorrentActions UA = null;
	
	ServerAPI(){
		this.UA = new UtorrentActions();
	}
	
	public String processInput(String inputLine) {
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
					return downlaodEpisode(inputs[1].toString(),UA);
				}
				else{
					return "Invalid inputs!!";
				}
			case DOWNLOAD_SEASON:
				if(inputs.length > 0 && inputs[1] != null){
					return downlaodSeason(inputs[1].toString(),UA);
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
	
	private static String addFavorite(String md5) {
		int result = DBActions.addFavorite(md5);
		JSONObject jo = new JSONObject();
		jo.put("result", result);
		return jo.toString();
	}

	private static String downlaodSeason(String md5, UtorrentActions ua) {
		JSONObject jo = new JSONObject();
		jo.put("result", "ERROR: Not implamented yet!");
		return jo.toString();
	}

	private static String downlaodEpisode(String magnetlink, UtorrentActions ua) {
		ua.downloadTorrent(magnetlink);
		JSONObject jo = new JSONObject();
		jo.put("result", "downloading");
		return jo.toString();
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
			String magnetlink = rs.getString("magnetlink");
			JSONObject jo = new JSONObject();
			jo.put("showname", showName);
			jo.put("season",season);
			jo.put("episode",episode);
			jo.put("MD5",md5 );
			jo.put("magnetlink",magnetlink);
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
			String md5 = rs.getString("md5_checksum");
			Long dateuploaded = rs.getLong("dateuploaded");
			String magnetlink = rs.getString("magnetlink");
			JSONObject jo = new JSONObject();
			jo.put("showname", showName);
			jo.put("dateuploaded", dateuploaded);
			jo.put("MD5",md5 );
			jo.put("magnetlink",magnetlink);
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
