package com.webScraper;

import java.util.Calendar;
import java.util.Date;

import com.HelperCL;

public class ScraperThread implements Runnable{
	
	protected String[] urls2Scrape;
	private long sleepTime;
	private static final String WORKING = "WORKING";
	private static final String WAITING = "WAITING";
	public static String IN_STATE = WAITING;
	protected ScraperThread(){}

	public ScraperThread(String[] urls2scrape){
		this.urls2Scrape = urls2scrape;
	}
	@Override
	public void run() {
		if(this.urls2Scrape != null && this.urls2Scrape.length > 0){
			IN_STATE = WORKING;
			Scraper scrap = new Scraper("");
			String ext = scrap.getExstention();
			if(ext != null && ext.length() > 0 && !ext.equalsIgnoreCase("FAIL")){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				System.out.println(calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE));
				for(int i=0;i<this.urls2Scrape.length;i++){
					String theURL = this.urls2Scrape[i].replace("~", ext);
					scrap.setPageURL(theURL);
					if(scrap.getPageURL().length() != 0){
						scrap.getTVShows();
					}
				}
			}
		}
		IN_STATE = WAITING;
		try {
			Thread.sleep(getWaitTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized(HelperCL.ThreadAlarm){
			HelperCL.ThreadAlarm.notify();
		}
	}
	
	
	public static long getWaitTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int houreOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		if(houreOfDay > 15 && houreOfDay < 21){
			System.out.println("sleeping for 15 min");
			return 900000l; //15 min
		}
		System.out.println("sleeping for 60 min");
		return 3600000l; //60 min
	}

	/**
	 * @return the sleepTime
	 */
	public long getSleepTime() {
		this.setSleepTime(getWaitTime());
		return sleepTime;
	}

	/**
	 * @param sleepTime the sleepTime to set
	 */
	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	
	

}
