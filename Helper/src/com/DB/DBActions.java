package com.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.beans.TvShowEpisode;

public class DBActions {

	public static int insertTvEpisodes(List<TvShowEpisode> shows, String url){
		DBMaster dbm = new DBMaster();
		dbm.makeConnection(DBConstants.DB_FILE_NAME);
		int showsAdded = 0;
		int result = -1;
		for(TvShowEpisode tse : shows){
			if(tse.isValid()){
				boolean dupe = checkForDups(tse, dbm);
				if(dupe){
					continue;
				}
				result = dbm.update(tse.insertSQLstatment());
				if(result == 1){
					System.out.println("Added to the db: "+tse.getShowname()+"-S"+tse.getSeason()+"E"+tse.getEpisode());
					showsAdded++;
				}
				else if(result == -2){
					showsAdded = 0;
					System.out.println("DB is LOCKED!!!");
					break;
				}
				else if (result == -1){
					//System.out.println("Allready in db: "+tse.getShowname()+"-S"+tse.getSeason()+"E"+tse.getEpisode());
				}
				else{
					continue;
				}
			}
		}
		System.out.println("Shows added "+showsAdded+" from: "+url);
		dbm.closeConn();
		return showsAdded;
	}
	
	public static boolean checkForDups(TvShowEpisode tse,DBMaster dbm){
		String sql = DBConstants.CHECK_FOR_DUPS.replace("~1", tse.getShowname()).replace("~2", ""+tse.getSeason()).replace("~3", ""+tse.getEpisode());
		ResultSet temp = dbm.select(sql);
		try {
			if(temp.isBeforeFirst()){
				return true;
			}
		} catch (SQLException e) {
			return false;
		}
		return false;
	}
	
	public static int addFavorite(String theshowname){
		DBMaster dbm = new DBMaster();
		dbm.makeConnection(DBConstants.DB_FILE_NAME);
		String sql = DBConstants.INSERT_FAVORITE.replace("~", "'"+theshowname+"'");
		int result = dbm.update(sql);
		dbm.closeConn();
		return result;
	}
	
	public static ResultSet getFavorites(){
		DBMaster dbm = new DBMaster();
		dbm.makeConnection(DBConstants.DB_FILE_NAME);
		String sql = DBConstants.SELECT_ALL_FAVORITE;
		ResultSet result = dbm.select(sql);
		return result;
	}
	
	
	public static ResultSet selectOldestMostRecentShow(String theshowname){
		DBMaster dbm = new DBMaster();
		dbm.makeConnection(DBConstants.DB_FILE_NAME);
		String sql = DBConstants.SELECT_FAVORITE_BY_NEW_SHOW;
		ResultSet result = dbm.select(sql.replace("~", "'"+theshowname+"'"));
		return result;
	}
	
	public static int setDownloaded(String hash){
		DBMaster dbm = new DBMaster();
		dbm.makeConnection(DBConstants.DB_FILE_NAME);
		String sql = DBConstants.UPDATE_DOWNLOADED_BY_SHOWNAME.replace("~", hash.toLowerCase());
		int result = dbm.update(sql);
		dbm.closeConn();
		return result;
	}
	
	public static int updateFavoriteSE(String hash){
		DBMaster dbm = new DBMaster();
		dbm.makeConnection(DBConstants.DB_FILE_NAME);
		String sql = DBConstants.UPDATE_FAVORITE_S_E.replace("~", hash.toLowerCase());
		int result = dbm.update(sql);
		dbm.closeConn();
		return result;
	}
	
	public static ResultSet getAllShows(){
		DBMaster dbm = new DBMaster();
		dbm.makeConnection(DBConstants.DB_FILE_NAME);
		String sql = DBConstants.GET_ALL_SHOWS;
		ResultSet result = dbm.select(sql);
		return result;
	}
}
