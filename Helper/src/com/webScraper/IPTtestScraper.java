package com.webScraper;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.beans.TvShowEpisode;
import com.dao.IPTToTvShowEpisode;
import com.dao.TPBToTvShowEpisode;

public class IPTtestScraper {

	public static void main(String[] args) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		String pageURL = "https://www.iptorrents.com";
		try {
			HttpGet httpGet = new HttpGet(pageURL);
			httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
			response = httpClient.execute(httpGet);
			response.removeHeaders("Transfer-Encoding");
			HttpPost thePost = new HttpPost(pageURL+"?username=mcpchelper81&password=ru68ce48&php=");
			thePost.setHeaders(response.getAllHeaders());
			response.close();
			response = null;
			response = httpClient.execute(thePost);
			httpGet = new HttpGet("https://www.iptorrents.com/t?5");
			httpGet.setHeaders(response.getHeaders("set-cookie"));
			httpGet.addHeader("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			httpGet.addHeader("accept-encoding","gzip, deflate, sdch");
			httpGet.addHeader("accept-language","en-US,en;q=0.8");
			httpGet.addHeader("dnt","1");
			httpGet.addHeader("upgrade-insecure-requests","1");
			httpGet.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			response.close();
			response = null;
			response = httpClient.execute(httpGet);
			Header contentType = response.getFirstHeader("Content-Type");
			HttpEntity httpEntity = response.getEntity();
			String[] contentArray = contentType.getValue().split(";");
			String charset = "UTF-8";
			//String mimeType = contentArray[0].trim();
			if(contentArray.length > 1 && contentArray[1].contains("=")){
				charset = contentArray[1].trim().split("=")[1];
			}
			Document pageDoc = Jsoup.parse(httpEntity.getContent(), charset, httpGet.getURI().getPath());
			Elements results = pageDoc.getElementsByClass("torrents");
			response.close();
			Elements rawShowObjects = results.select("tr");
			IPTToTvShowEpisode makeShows = new IPTToTvShowEpisode();
			List<TvShowEpisode> theShows = makeShows.makeTSEBeans(rawShowObjects);
		} catch (MalformedURLException MURLe) {
			MURLe.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} 
	}

}
