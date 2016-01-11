package com.beans;
import com.DB.*;

public class TvShowEpisode {
	
	private int tid;
	private String showname = "";
	private String magnetlink;
	private String torentlink;
	private long dateUploaded;
	private char downloaded;
	private int episode;
	private int season;
	private String tvdbid = "";
	private String md5_checksum;
	private int aireddate;
	private String episode_name = "";
	private String size = "";
	private String resolution;
	
	
	
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getShowname() {
		return showname;
	}
	public void setShowname(String showname) {
		this.showname = showname;
	}
	public String getMagnetlink() {
		return magnetlink;
	}
	public void setMagnetlink(String magnetlink) {
		this.magnetlink = magnetlink;
	}
	public long getDateUploaded() {
		return dateUploaded;
	}
	public void setDateUploaded(long dateUploaded) {
		this.dateUploaded = dateUploaded;
	}
	public char getDownloaded() {
		return downloaded;
	}
	public void setDownloaded(char downloaded) {
		this.downloaded = downloaded;
	}
	public int getEpisode() {
		return episode;
	}
	public void setEpisode(int episode) {
		this.episode = episode;
	}
	public int getSeason() {
		return season;
	}
	public void setSeason(int season) {
		this.season = season;
	}
	public String getTvdbid() {
		return tvdbid;
	}
	public void setTvdbid(String tvdbid) {
		this.tvdbid = tvdbid;
	}
	public String getMd5_checksum() {
		return md5_checksum;
	}
	public void setMd5_checksum(String md5_checksum) {
		this.md5_checksum = md5_checksum;
	}
	public int getAireddate() {
		return aireddate;
	}
	public void setAireddate(int aireddate) {
		this.aireddate = aireddate;
	}
	public String getEpisode_name() {
		return episode_name;
	}
	public void setEpisode_name(String episode_name) {
		this.episode_name = episode_name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	public boolean isValidTPB(){
		return (this.getMd5_checksum() != null && this.getMagnetlink() != null);
	}
	public boolean isValidIPT(){
		return this.getTorentlink() != null;
	}
	public String getTorentlink() {
		return torentlink;
	}
	public void setTorentlink(String torentlink) {
		this.torentlink = torentlink;
	}
	public String getResolution() {
		return resolution;
	}
	public void setResolution(String resolution) {
		this.resolution = resolution;
	}
	public String insertSQLstatment(){
		StringBuilder sb = new StringBuilder(DBConstants.INSERT_TVEPISODES_1);
		if(isValidTPB()){
			sb.append(getTid());
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getShowname());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getMagnetlink());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(DBConstants.COMMA);
			sb.append(getDateUploaded());
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getDownloaded());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(DBConstants.COMMA);
			sb.append(getEpisode());
			sb.append(DBConstants.COMMA);
			sb.append(getSeason());
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getTvdbid());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getMd5_checksum());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(DBConstants.COMMA);
			sb.append(getAireddate());
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getEpisode_name());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getSize());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(" )");
			return sb.toString();
		}
		return null;
	}
	
	public String insertIPTSQLstatment(){
		StringBuilder sb = new StringBuilder(DBConstants.INSERT_TVEPISODESIPT_1);
		if(isValidIPT()){
			sb.append(getTid());
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getShowname());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getTorentlink());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(DBConstants.COMMA);
			sb.append(getDateUploaded());
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getDownloaded());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(DBConstants.COMMA);
			sb.append(getEpisode());
			sb.append(DBConstants.COMMA);
			sb.append(getSeason());
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getTvdbid());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(DBConstants.COMMA);
			sb.append(getAireddate());
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getEpisode_name());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getSize());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(DBConstants.COMMA);
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(getResolution());
			sb.append(DBConstants.SINGLE_QUOTE);
			sb.append(" )");
			return sb.toString();
		}
		return null;
	}

}
