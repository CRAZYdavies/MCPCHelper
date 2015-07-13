package com.webScraper;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.DB.DBActions;
import com.beans.TvShowEpisode;
import com.dao.HtmlToTvShowEpisode;

public class Scraper {
	private String pageURL;
	
	
	public Scraper(String url){
		setPageURL(url);
	}


	public String getPageURL() {
		return pageURL;
	}


	public void setPageURL(String pageURL) {
		if(isValidURI(pageURL)){
			this.pageURL = pageURL;
			return;
		}
		this.pageURL = "";
	}
	
	public String getExstention() {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try{
			HttpGet httpGet = new HttpGet("http://thepiratebay.org");
			httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
			response = httpClient.execute(httpGet);
			Header[] cookieJar = response.getHeaders("set-cookie");
			if(response.getStatusLine().getStatusCode() == 200){
				for(int i=0;i<cookieJar.length;i++){
					String[] cookies = response.getHeaders("set-cookie")[i].getValue().split(";");
					for(int c=0;c<cookies.length;c++){
						if(cookies[c].contains("domain=")){
							return cookies[c].substring(cookies[c].indexOf('=')+2);
						}
					}
				}
			}
			else{
				return "FAIL";
			}
			response.close();
		}
		catch(Exception e){
			return e.getMessage();
		}
		return "";
	}
	
	@SuppressWarnings("unused")
	public void getTVShows(){
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet(pageURL);
			httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
			response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();
			Header contentType = response.getFirstHeader("Content-Type");
			String[] contentArray = contentType.getValue().split(";");
			String charset = "UTF-8";
			String mimeType = contentArray[0].trim();
			if(contentArray.length > 1 && contentArray[1].contains("=")){
				charset = contentArray[1].trim().split("=")[1];
			}
			Document pageDoc = Jsoup.parse(httpEntity.getContent(), charset, httpGet.getURI().getPath());
			Element results = pageDoc.getElementById("searchResult");
			response.close();
			Elements rawShowObjects = results.select("td.vertTh+td");
			HtmlToTvShowEpisode makeShows = new HtmlToTvShowEpisode();
			List<TvShowEpisode> theShows = makeShows.makeTSEBeans(rawShowObjects);
			DBActions.insertTvEpisodes(theShows,pageURL);
		} catch (MalformedURLException MURLe) {
			// TODO Auto-generated catch block
			MURLe.printStackTrace();
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	
	@SuppressWarnings("unused")
	private boolean isValidURI(String uriStr) {
	    try {
	      URI uri = new URI(uriStr);
	      return true;
	    }
	    catch (URISyntaxException e) {
	        return false;
	    }
	}
}
