package com.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InvalidNameException;

public class DBMaster {
	private Connection conn;

	public Connection makeConnection(String filename){
		conn = null;
		try{
			if(filename != null && filename.endsWith(".db")){
				this.setConn(DriverManager.getConnection("jdbc:sqlite:"+filename));
			}
			else{
				if(filename == null){
					throw new NullPointerException("The file name cant not be NULL");
				}
				else{
					throw new InvalidNameException("The file name must end with .db not "+filename);
				}
			}
		}catch(InvalidNameException ine){
			ine.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public int update(String sql){
		if(getConn() != null){
			Statement stmt = null;
			try{
				stmt = getConn().createStatement();
				int results = stmt.executeUpdate(sql);
				stmt.close();
				//conn.commit();
				return results;
			}catch(SQLException sqle){
				if("UNIQUE constraint failed: tvepisodes.tid, tvepisodes.md5_checksum".equalsIgnoreCase(sqle.getMessage())){
					return 0;
				}
				if("database is locked".equalsIgnoreCase(sqle.getMessage())){
					return -2;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public ResultSet select(String sql){
		if(getConn() != null){
			Statement stmt = null;
			try{
				stmt = getConn().createStatement();
				ResultSet temp = stmt.executeQuery(sql);
				return temp;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public void closeConn(){
		try {
			getConn().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			setConn(null);
		}
	}
	
	
	
	
	
	
	
	public Connection getConn() {
		return conn;
	}

	private void setConn(Connection conn) {
		this.conn = conn;
	}
}
