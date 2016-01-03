package com;

import java.io.IOException;

import com.Config.Configuration;
import com.DB.DBConstants;
import com.DB.DBMaster;
import com.Utorrent.StopTorrents;
import com.dao.CheckFavorites;
import com.utilities.Utilities;
import com.webScraper.ScraperThread;

public class HelperCL {

	public static String ThreadAlarm = new String("This is an Alarm");
	
	public static void main(String[] args) throws IOException {
		final String[] urls = {"https://~/user/ettv/0/3/0","https://~/user/sceneline/0/3/0","https://~/user/TvTeam/0/3/0"};
		Configuration.readConfigFile("config.xml");
		DBMaster dbm = new DBMaster();
		dbm.makeConnection(Configuration.getDBFILENAME());
		dbm.update(DBConstants.TVEPISODES_TABLE_CREATE);
		dbm.update(DBConstants.FAVORITES_TABLE_CREATE);
		for(String s: Configuration.getFavorits().keySet()){
			dbm.update(DBConstants.INSERT_FAVORITE.replace("~", "'"+s+"'"));
		}
		
		Thread scrapThread = new Thread(new ScraperThread(urls));
		Thread stopTorrentsThread = new Thread(new StopTorrents());
/*		Thread serverThread = new Thread(new ServerThread());
		serverThread.start();*/
		
		while(true){
			if(scrapThread.getState() == Thread.State.NEW){
				scrapThread.start();
				Thread favoriteThread = new Thread(new CheckFavorites());
				favoriteThread.run();
			}
			if(stopTorrentsThread.getState() == Thread.State.NEW){
				stopTorrentsThread.start();
			}
			if(scrapThread.getState() == Thread.State.TERMINATED){
				scrapThread = new Thread(new ScraperThread(urls));
			}
			if(stopTorrentsThread.getState() == Thread.State.TERMINATED){
				stopTorrentsThread = new Thread(new StopTorrents());
			}
			try {
				synchronized(ThreadAlarm){
					if((stopTorrentsThread.getState() == Thread.State.TIMED_WAITING) 
							&& (scrapThread.getState() == Thread.State.TIMED_WAITING)){
						ThreadAlarm.wait(0);
					}
				}
			} catch (Exception e) {
				Utilities.sendExceptionEmail(e.getMessage());
				e.printStackTrace();
				break;
			}
		}
		
	}
}
