package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.DB.DBActions;
import com.Utorrent.UtorrentActions;
import com.utilities.Utilities;

public class CheckFavorites implements Runnable{

	@Override
	public void run() {
		ResultSet result = DBActions.getFavorites();
		boolean done = false;
		
		try {
			while(!done){
				String showName = result.getString("showname");
				if(showName != null){
					ResultSet fav = DBActions.selectOldestMostRecentShow(showName);
					if(fav.isBeforeFirst()){
						String showsMagnetlink = fav.getString("magnetlink");
						UtorrentActions ua = new UtorrentActions();
						ua.downloadTorrent(showsMagnetlink);
					}
					fav.close();
				}
				done = !result.next();
			}
			result.close();
		} catch (SQLException e) {
			Utilities.sendExceptionEmail(e.getMessage());
			e.printStackTrace();
		}
	}
}
