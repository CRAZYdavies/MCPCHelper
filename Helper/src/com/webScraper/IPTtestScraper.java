package com.webScraper;

import java.net.MalformedURLException;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.dao.KATToTvShowEpisode;

public class IPTtestScraper {

	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		try {
			HttpGet httpGet = new HttpGet("https://kat.cr/tv/?field=time_add&sorder=desc");
			httpGet.addHeader("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			httpGet.addHeader("accept-encoding","gzip, deflate, sdch");
			httpGet.addHeader("accept-language","en-US,en;q=0.8");
			httpGet.addHeader("dnt","1");
			httpGet.addHeader("upgrade-insecure-requests","1");
			httpGet.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			response = httpClient.execute(httpGet);
			Header contentType = response.getFirstHeader("Content-Type");
			HttpEntity httpEntity = response.getEntity();
			String[] contentArray = contentType.getValue().split(";");
			String charset = "UTF-8";
			if(contentArray.length > 1 && contentArray[1].contains("=")){
				charset = contentArray[1].trim().split("=")[1];
			}
			Document pageDoc = Jsoup.parse(httpEntity.getContent(), charset, httpGet.getURI().getPath());
			Elements oddResults = pageDoc.getElementsByClass("odd");
			Elements evenResults = pageDoc.getElementsByClass("even");
			Elements allshows = new Elements();
			for(int i=0;i<evenResults.size();i++){
				allshows.add(oddResults.get(i));
				allshows.add(evenResults.get(i));
			}
			allshows.add(oddResults.last());
			response.close();
			KATToTvShowEpisode kat = new KATToTvShowEpisode();
			kat.makeKATBeans(allshows);
		} catch (MalformedURLException MURLe) {
			MURLe.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} 
	}

}
