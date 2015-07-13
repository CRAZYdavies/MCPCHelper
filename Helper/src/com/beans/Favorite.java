package com.beans;

public class Favorite {

	private String showname;
	private int last_season;
	private int last_episode;
	private long last_downloaded;
	
	
	public Favorite(String _showname){
		setShowname(_showname);
	}
	/**
	 * @return the showname
	 */
	public String getShowname() {
		return showname;
	}
	/**
	 * @param showname the showname to set
	 */
	public void setShowname(String showname) {
		this.showname = showname;
	}
	/**
	 * @return the last_season
	 */
	public int getLast_season() {
		return last_season;
	}
	/**
	 * @param last_season the last_season to set
	 */
	public void setLast_season(int last_season) {
		this.last_season = last_season;
	}
	/**
	 * @return the last_episode
	 */
	public int getLast_episode() {
		return last_episode;
	}
	/**
	 * @param last_episode the last_episode to set
	 */
	public void setLast_episode(int last_episode) {
		this.last_episode = last_episode;
	}
	/**
	 * @return the last_downloaded
	 */
	public long getLast_downloaded() {
		return last_downloaded;
	}
	/**
	 * @param last_downloaded the last_downloaded to set
	 */
	public void setLast_downloaded(long last_downloaded) {
		this.last_downloaded = last_downloaded;
	}
	
	
}
