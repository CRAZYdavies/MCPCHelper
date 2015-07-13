package com.DB;

public class DBConstants {

	public static final String TVEPISODES_TABLE_CREATE = "create table if not exists tvepisodes (tid integer not null,showname varchar(50),magnetlink varchar(500) not null,dateuploaded integer,downloaded char(1) default 'n',episode integer,season integer,tvdbid varchar(64),md5_checksum varchar(64) not null,aireddate integer,episode_name varchar(50),file_size varchar(12),primary key (tid, md5_checksum))";
	public static final String FAVORITES_TABLE_CREATE = "create table if not exists favorites (showname	varchar(50) not null,latest_season	integer,latest_episode	integer,last_downloaded	integer,date_added	integer, primary key(showname))";
	public static final String TVEPISODES = "tvepisodes";
	public static final String INSERT_TVEPISODES_1 = "insert into tvepisodes (tid,showname,magnetlink,dateuploaded,downloaded,episode,season,tvdbid,md5_checksum,aireddate,episode_name,file_size) values (";
	public static final String INSERT_FAVORITE = "insert into favorites (showname,latest_season,latest_episode,last_downloaded,date_added) values (~,0,0,0,date('now'))";
	public static final String COMMA = ",";
	public static final String SINGLE_QUOTE = "'";
	public static final String DB_FILE_NAME = "tvshows.db";
	public static final String SELECT_TVSHOWS_BY_TID = "select * from tvepisodes where tid = ";
	public static final String SELECT_FAVORITE_BY_NEW_SHOW = "select * from(select * from(select * from tvepisodes where showname = ~ group by episode order by episode desc) order by season desc) where downloaded = 'n' order by dateuploaded desc";
	public static final String SELECT_ALL_FAVORITE = "select * from favorites";
	public static final String UPDATE_DOWNLOADED = "update tvepisodes set downloaded='y' where md5_checksum ='~'";
	public static final String UPDATE_DOWNLOADED_BY_SHOWNAME = "update tvepisodes set downloaded='y' where showname =(select showname from tvepisodes where md5_checksum ='~')";
	public static final String UPDATE_FAVORITE_S_E = "update favorites set latest_season=(select season from tvepisodes where md5_checksum ='~') ,latest_episode=(select episode from tvepisodes where md5_checksum ='~'),last_downloaded=date('now') where showname = (select showname from tvepisodes where md5_checksum ='~')";
	public static final String CHECK_FOR_DUPS = "select * from tvepisodes where showname = '~1' and season = ~2 and episode = ~3";
	public static final String GET_ALL_SHOWS = "select distinct season,episode,showname from tvepisodes order by showname,season,episode";
}
