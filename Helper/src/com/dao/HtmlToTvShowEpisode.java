package com.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.beans.TvShowEpisode;

public class HtmlToTvShowEpisode {

	
	public List<TvShowEpisode> makeTSEBeans(Elements results){
		List<TvShowEpisode> showsToAdd2DB = new ArrayList<TvShowEpisode>();
		
		for(Element e : results){
			TvShowEpisode tve = new TvShowEpisode();
			tve.setTid(getTid(e));
			tve.setShowname(getShowName(e));
			tve.setMagnetlink(getMagLink(e));
			tve.setDateUploaded(getDateUploaded(e));
			tve.setEpisode(getEpisode(e));
			tve.setSeason(getSeason(e));
			tve.setMd5_checksum(getMd5_checksum(e));
			tve.setSize(getSize(e));
			tve.setDownloaded('n');
			if(tve.getTid() != 0 && tve.getMd5_checksum() != null){
				showsToAdd2DB.add(tve);
			}
		}
		
		return showsToAdd2DB;
	}

	private String getSize(Element e) {
		Elements subEs = e.select("font.detDesc");
		if(subEs.size() > 0){
			Element subE = subEs.get(0);
			List<Node> children = subE.childNodes();
			if(children.size() > 0){
				for(int i=0;i<children.size();i++){
					if(children.get(i).childNodes().size() == 0){
						String value = ((TextNode)children.get(i)).text().trim();
						if(value.contains("Size")){
							int start = value.indexOf("Size")+4;
							int end = value.indexOf("iB,")+2;
							return value.substring(start,end).trim();
						}
					}
				}
			}
		}
		return null;
	}

	private String getMd5_checksum(Element e) {
		Elements subEs = e.select("div+a");
		if(subEs.size() > 0){
			Element subE = subEs.get(0);
			String href = subE.attr("href");
			if(href != null){
				String[] parts = href.split("&");
				if(parts.length>0){
					return parts[0].substring(parts[0].lastIndexOf(':')+1);
				}
			}
		}
		return null;
	}

	private int getSeason(Element e) {
		Element subE = e.select("a.detLink").get(0);
		if(subE != null){
			String href = subE.attr("href");
			if(href != null){
				String[] parts = href.split("/");
				if(parts.length > 3){
					String fullname = parts[3].trim();
					String[] nameParts = fullname.split("_");
					if(fullname.contains("_")){
						nameParts = fullname.split("_");
					}
					else{
						nameParts = fullname.split("\\.");
					}
					if(nameParts.length == 1){
						return 0;
					}
					if(nameParts.length>1){
						int season = 0;
						for(int i=0;i<nameParts.length;i++){
							if(nameParts[i].trim().matches("S[0-9][0-9]E[0-9][0-9]")){
								String fullSE = nameParts[i].trim();
								String seasonStr = fullSE.substring(1,3);
								season = Integer.parseInt(seasonStr);
								break;
							}
						}
						return season;
					}
				}
			}
		}
		return 0;
	}

	private int getEpisode(Element e) {
		Element subE = e.select("a.detLink").get(0);
		if(subE != null){
			String href = subE.attr("href");
			if(href != null){
				String[] parts = href.split("/");
				if(parts.length > 3){
					String fullname = parts[3].trim();
					String[] nameParts = fullname.split("_");
					if(fullname.contains("_")){
						nameParts = fullname.split("_");
					}
					else{
						nameParts = fullname.split("\\.");
					}
					if(nameParts.length == 1){
						return 0;
					}
					if(nameParts.length>1){
						int episode = 0;
						for(int i=0;i<nameParts.length;i++){
							if(nameParts[i].trim().matches("S[0-9][0-9]E[0-9][0-9]")){
								String fullSE = nameParts[i].trim();
								String episodeStr = fullSE.substring(fullSE.length()-2);
								episode = Integer.parseInt(episodeStr);
								break;
							}
						}
						return episode;
					}
				}
			}
		}
		return 0;
	}

	private long getDateUploaded(Element e) {
		Elements subEs = e.select("font.detDesc");
		if(subEs.size() > 0){
			Element subE = subEs.get(0);
			List<Node> children = subE.childNodes();
			if(children.size() > 0){
				for(int i=0;i<children.size();i++){
					if(children.get(i).siblingIndex() > 0){
						String value = ((TextNode)children.get(i).childNodes().get(0)).text();
						if(value.endsWith("ago")){
							//int minago = Integer.parseInt(value.substring(0, 2));
							return new Date().getTime();// - (minago * 60l * 1000l);
						}
						else if(value.startsWith("Today")){
							return new Date().getTime();
						}
						else if(value.startsWith("Y-day")){
							return new Date().getTime() - 86400000l;
						}
						else if(value.matches("[0-1][0-9]-[0-3][0-9] [0-2][0-9]:[0-9][0-9]")){
							Calendar cal = Calendar.getInstance();
							int month = Integer.parseInt(value.substring(0, 2));
							int day = Integer.parseInt(value.substring(3, 5));
							cal.set(2015, month, day);
							return cal.getTimeInMillis();
						}
						else if(value.matches("[0-1][0-9]-[0-3][0-9] [0-9][0-9][0-9][0-9]")){
							Calendar cal = Calendar.getInstance();
							int month = Integer.parseInt(value.substring(0, 2));
							int day = Integer.parseInt(value.substring(3, 5));
							int year = Integer.parseInt(value.substring(6));
							cal.set(year, month, day);
							return cal.getTimeInMillis();
						}
					}
				}
			}
		}
		return new Date().getTime();
	}

	private String getMagLink(Element e) {
		Elements subEs = e.select("div+a");
		if(subEs.size() > 0){
			Element subE = subEs.get(0);
			String href = subE.attr("href");
			if(href != null){
				return href;
			}
		}
		return null;
	}

	private String getShowName(Element e) {
		Element subE = e.select("a.detLink").get(0);
		if(subE != null){
			String href = subE.attr("href");
			if(href != null){
				String[] parts = href.split("/");
				if(parts.length > 3){
					String fullname = parts[3].trim();
					String[] nameParts = fullname.split("_");
					if(fullname.contains("_")){
						nameParts = fullname.split("_");
					}
					else{
						nameParts = fullname.split("\\.");
					}
					if(nameParts.length == 1){
						return nameParts[0];
					}
					if(nameParts.length>1){
						String showname = "";
						for(int i=0;i<nameParts.length;i++){
							if(nameParts[i].trim().matches("S[0-9][0-9]E[0-9][0-9]")){
								break;
							}
							if(i==0){
								showname=nameParts[i];
							}
							else{
								showname = showname +" "+nameParts[i].trim();
							}
						}
						return showname;
					}
				}
			}
		}
		return "";
	}

	private int getTid(Element e) {
		Element subE = e.select("a.detLink").get(0);
		if(subE != null){
			String href = subE.attr("href");
			if(href != null){
				String[] parts = href.split("/");
				if(parts.length > 2){
					int id = Integer.parseInt(parts[2]);
					return id;
				}
			}
		}
		return 0;
	}
}
