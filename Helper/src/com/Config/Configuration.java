package com.Config;



import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.beans.Favorite;

public class Configuration {

	
	
	private static Map<String,Favorite> FAVORITES = new TreeMap<String,Favorite>();
	private static String DBFILENAME = "tvshows.db";
	private static String BASE_URL = "http://127.0.0.1:";
	private static String PORT = "8088";
	private static String DOM = "/gui/";
	private static String SERVER_PORT = "8787";
	
	
	
	public static void setPORT(String port){
		PORT = port;
	}
	public static void setDBFILENAME(String file){
		DBFILENAME = file;
	}
	public static String getDBFILENAME(){
		return DBFILENAME;
	}
	public static void setBASE_URL(String url){
		BASE_URL = url;
	}
	public static String getSERVER_PORT() {
		return SERVER_PORT;
	}
	public static int getSERVER_PORT_INT(){
		return Integer.parseInt(SERVER_PORT);
	}
	public static void setSERVER_PORT(String sERVER_PORT) {
		SERVER_PORT = sERVER_PORT;
	}
	public static String getMainURL(){
		return BASE_URL+PORT+DOM;
	}
	
	public static int addFavorite(Favorite fav){
		if(FAVORITES.containsKey(fav.getShowname())){
			return 0;
		}
		else{
			FAVORITES.put(fav.getShowname(), fav);
			return 1;
		}
	}
	public static Map<String,Favorite> getFavorits(){
		return FAVORITES;
	}
	
	
	public static void readConfigFile(String file){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			File f = new File(file);
			//parse using builder to get DOM representation of the XML file
			Document dom = db.parse(f);
			dom.getDocumentElement().normalize();
			String dbfilename = null;
			String port = null;
			String url = null;
			
			NodeList nodeList = dom.getDocumentElement().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				switch(node.getNodeName()){
				case "DBFILENAME":
					dbfilename = node.getTextContent();
					break;
				case "PORT":
					port = node.getTextContent();
					break;
				case "BASE_URL":
					url = node.getTextContent();
					break;
				case "FAVORITES":
					NodeList childNodes = node.getChildNodes();
					for (int j = 0; j < childNodes.getLength(); j++) {
						Node cNode = childNodes.item(j);
						String showname = cNode.getTextContent();
						addFavorite(new Favorite(showname));
					}
				}
				
			}
			
			if(dbfilename != null){
				String filename = dbfilename;
				if(filename != null && filename.length() > 4 && filename.endsWith(".db")){
					setDBFILENAME(filename);
				}
			}
			if(port != null){
				String thePort = port;
				try{
					Integer.parseInt(thePort);
				}catch(NumberFormatException nfe){
					System.out.println("Invalid PORT!!!  "+thePort);
				}
			}
			if(url != null){
				String baseURL = url;
				if(baseURL != null && baseURL.startsWith("http://") && baseURL.endsWith(":") && baseURL.length() > 8){
					setBASE_URL(baseURL);
				}
				else{
					System.out.println("BASE_URL is not formated correctly!!!!");
					System.out.println("eg: http://[ip to utorrent server]:");
					System.out.println("The BASE_URL must end with a : and nothing after");
				}
			}
			

		}catch(ParserConfigurationException pce) {
			System.out.println("XML Parse exception!"+pce.getMessage());
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			System.out.println("IOException check thats the file is an .xml file and is there");
			System.out.println(ioe.getMessage());
		}
	}
}
