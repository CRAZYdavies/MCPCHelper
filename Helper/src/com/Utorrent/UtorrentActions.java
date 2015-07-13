package com.Utorrent;

import java.net.MalformedURLException;
import java.util.Date;

import org.apache.commons.io.IOUtils; 
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.Config.Configuration;
import com.DB.DBActions;
import com.beans.TvShowEpisode;
@SuppressWarnings("unused")
public class UtorrentActions {
	private static final String BASE_GET_URL = Configuration.getMainURL();//"http://192.168.1.121:8088/gui/";
	public static final String AUTH1 = "Authorization";
	public static final String AUTH2 = "Basic c2xrYmx1ZGdlcjpydTY4Y2U0OA==";
	private String token = "";
	CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	
	public UtorrentActions(){
		webUiLogin();
	}
	
	public int webUiLogin(){
		CloseableHttpResponse response = null;
		int code = -1;
		try {
			HttpGet httpGet = makeHttpGet(BASE_GET_URL+"token.html");
			response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			String charset = "UTF-8";
			Document pageDoc = Jsoup.parse(httpEntity.getContent(), charset, httpGet.getURI().getPath());
			this.setToken(pageDoc.select("div").first().ownText());
			code = response.getStatusLine().getStatusCode();
		} catch (MalformedURLException MURLe) {
			// TODO Auto-generated catch block
			MURLe.printStackTrace();
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return code;
	}
	
	
	public JSONArray getTorrents(){
		int code = -1;
		JSONArray ja = new JSONArray();
		try{
			HttpGet httpGet = new HttpGet(BASE_GET_URL+"?list=1"+getToken());
			httpGet.addHeader("Content-Type","application/json");
			httpGet.addHeader("Accept","application/json");
			httpGet.addHeader("Authorization","Basic c2xrYmx1ZGdlcjpydTY4Y2U0OA==");
			CloseableHttpResponse response = httpClient.execute(httpGet);
			code = response.getStatusLine().getStatusCode();
			HttpEntity httpEntity = response.getEntity();
			String theJO = IOUtils.toString(httpEntity.getContent(), "UTF-8");
			response.close();
			JSONObject jo = new JSONObject(theJO);
			ja = jo.getJSONArray("torrents");
		}catch(Exception e){
			e.printStackTrace();
		}
		return ja;
	}
	
	public void killSeeders(JSONArray ja) {
		int numOfDownloading = 0;
		int numDone = 0;
		for(int i=0;i<ja.length();i++){
			JSONArray jaTemp = ja.getJSONArray(i);
			String[] parts = jaTemp.toString().substring(1, jaTemp.toString().length()-1).replace('"', Character.MIN_VALUE).split(",");
			if(parts.length>21){
				String hash = parts[0].trim();
				String status = parts[21].trim();
				if(status != null && hash != null && status.contains("Seeding")){
					try {
						DBActions.setDownloaded(hash);
						DBActions.updateFavoriteSE(hash);
						HttpGet httpGet = new HttpGet(BASE_GET_URL+"?"+"action=stop&hash="+hash+getToken());
						httpGet.addHeader("Accept","application/json");
						httpGet.addHeader("Accept-Encoding","gzip, deflate, sdch");
						httpGet.addHeader("Accept-Language","en-US,en;q=0.8");
						httpGet.addHeader(AUTH1,AUTH2);
						httpGet.addHeader("Connection","keep-alive");
						httpGet.addHeader("DNT","1");
						httpGet.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36");
						httpGet.addHeader("X-Request","JSON");
						httpGet.addHeader("X-Requested-With","XMLHttpRequest");
						CloseableHttpResponse response = httpClient.execute(httpGet);
						HttpEntity httpEntity = response.getEntity();
						String charset = "UTF-8";
						Document pageDoc = Jsoup.parse(httpEntity.getContent(), charset, httpGet.getURI().getPath());
						int code = response.getStatusLine().getStatusCode();
						response.close();
						//TODO add in email/text option to notify download is done
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(status != null && hash != null && status.contains("Downloading")){
					numOfDownloading++;
				}
				if(status != null && hash != null && status.contains("Finished")){
					numDone++;
				}
			}
		}
		System.out.println("Downloading: "+numOfDownloading);
		System.out.println("Finished: "+numDone);
	}
	
	public void downloadTorrent(TvShowEpisode tvs){
		try {
			String mag1 = tvs.getMagnetlink().substring(0,tvs.getMagnetlink().indexOf("&dn=")+3);
			String mag2 = tvs.getShowname()+".S"+tvs.getSeason()+"E"+tvs.getEpisode();
			String mag3 = tvs.getMagnetlink().substring(tvs.getMagnetlink().indexOf("&tr=")-3);
			HttpGet httpGet = makeHttpGet(BASE_GET_URL+"?action=add-url&s="+mag1+mag2+mag3+"&t="+new Date().getTime()+getToken());
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			String charset = "UTF-8";
			Document pageDoc = Jsoup.parse(httpEntity.getContent(), charset, httpGet.getURI().getPath());
			int code = response.getStatusLine().getStatusCode();
			response.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void downloadTorrent(String magnetlink){
		try {
			HttpGet httpGet = makeHttpGet(BASE_GET_URL+"?action=add-url&s="+magnetlink+getToken());
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			String charset = "UTF-8";
			Document pageDoc = Jsoup.parse(httpEntity.getContent(), charset, httpGet.getURI().getPath());
			int code = response.getStatusLine().getStatusCode();
			response.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	protected HttpGet makeHttpGet(String url){
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
		httpGet.addHeader("Accept","application/json,text/html, application/xhtml+xml, */*");
		httpGet.addHeader("Accept-Encoding","gzip, deflate");
		httpGet.addHeader(AUTH1,AUTH2);
		return httpGet;
	}
	
	public String getToken() {
		return "&token="+token;
	}

	public void setToken(String token) {
		this.token = token;
	}


}
