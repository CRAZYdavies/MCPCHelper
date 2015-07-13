package com.beans;

public class TorrentBean {

	private String hash;
	private int status;
	private String name;
	private int size;
	private int percent_progress;
	private int downloaded;
	private int uploaded;
	private int ratio;
	private int upload_speed;
	private int download_speed;
	private int eta;
	private String label;
	private int peers_connected;
	private int peers_in_swarm;
	private int seeds_connected;
	private int seeds_in_swarm;
	private int availability;
	private int torrent_queue_order;
	private int remaining;
	
	
	public TorrentBean(){}
	
	public TorrentBean(String rawtorrent){
		String[] parts = rawtorrent.split(",");
		if(parts.length == 19){
			setHash(parts[0]);
			setStatus(Integer.parseInt(parts[1]));
			setName(parts[2]);
			setSize(Integer.parseInt(parts[3]));
			setPercent_progress(Integer.parseInt(parts[4]));
			setDownloaded(Integer.parseInt(parts[5]));
			setUploaded(Integer.parseInt(parts[6]));
			setRatio(Integer.parseInt(parts[7]));
			setUpload_speed(Integer.parseInt(parts[8]));
			setDownload_speed(Integer.parseInt(parts[9]));
			setEta(Integer.parseInt(parts[10]));
			setLabel(parts[11]);
			setPeers_connected(Integer.parseInt(parts[12]));
			setPeers_in_swarm(Integer.parseInt(parts[13]));
			setSeeds_connected(Integer.parseInt(parts[14]));
			setSeeds_in_swarm(Integer.parseInt(parts[15]));
			setAvailability(Integer.parseInt(parts[16]));
			setTorrent_queue_order(Integer.parseInt(parts[17]));
			setRemaining(Integer.parseInt(parts[18]));
		}
	}
	
	
	
	
	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}
	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the percent_progress
	 */
	public int getPercent_progress() {
		return percent_progress;
	}
	/**
	 * @param percent_progress the percent_progress to set
	 */
	public void setPercent_progress(int percent_progress) {
		this.percent_progress = percent_progress;
	}
	/**
	 * @return the downloaded
	 */
	public int getDownloaded() {
		return downloaded;
	}
	/**
	 * @param downloaded the downloaded to set
	 */
	public void setDownloaded(int downloaded) {
		this.downloaded = downloaded;
	}
	/**
	 * @return the uploaded
	 */
	public int getUploaded() {
		return uploaded;
	}
	/**
	 * @param uploaded the uploaded to set
	 */
	public void setUploaded(int uploaded) {
		this.uploaded = uploaded;
	}
	/**
	 * @return the ratio
	 */
	public int getRatio() {
		return ratio;
	}
	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	/**
	 * @return the upload_speed
	 */
	public int getUpload_speed() {
		return upload_speed;
	}
	/**
	 * @param upload_speed the upload_speed to set
	 */
	public void setUpload_speed(int upload_speed) {
		this.upload_speed = upload_speed;
	}
	/**
	 * @return the download_speed
	 */
	public int getDownload_speed() {
		return download_speed;
	}
	/**
	 * @param download_speed the download_speed to set
	 */
	public void setDownload_speed(int download_speed) {
		this.download_speed = download_speed;
	}
	/**
	 * @return the eta
	 */
	public int getEta() {
		return eta;
	}
	/**
	 * @param eta the eta to set
	 */
	public void setEta(int eta) {
		this.eta = eta;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the peers_connected
	 */
	public int getPeers_connected() {
		return peers_connected;
	}
	/**
	 * @param peers_connected the peers_connected to set
	 */
	public void setPeers_connected(int peers_connected) {
		this.peers_connected = peers_connected;
	}
	/**
	 * @return the peers_in_swarm
	 */
	public int getPeers_in_swarm() {
		return peers_in_swarm;
	}
	/**
	 * @param peers_in_swarm the peers_in_swarm to set
	 */
	public void setPeers_in_swarm(int peers_in_swarm) {
		this.peers_in_swarm = peers_in_swarm;
	}
	/**
	 * @return the seeds_connected
	 */
	public int getSeeds_connected() {
		return seeds_connected;
	}
	/**
	 * @param seeds_connected the seeds_connected to set
	 */
	public void setSeeds_connected(int seeds_connected) {
		this.seeds_connected = seeds_connected;
	}
	/**
	 * @return the seeds_in_swarm
	 */
	public int getSeeds_in_swarm() {
		return seeds_in_swarm;
	}
	/**
	 * @param seeds_in_swarm the seeds_in_swarm to set
	 */
	public void setSeeds_in_swarm(int seeds_in_swarm) {
		this.seeds_in_swarm = seeds_in_swarm;
	}
	/**
	 * @return the availability
	 */
	public int getAvailability() {
		return availability;
	}
	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(int availability) {
		this.availability = availability;
	}
	/**
	 * @return the torrent_queue_order
	 */
	public int getTorrent_queue_order() {
		return torrent_queue_order;
	}
	/**
	 * @param torrent_queue_order the torrent_queue_order to set
	 */
	public void setTorrent_queue_order(int torrent_queue_order) {
		this.torrent_queue_order = torrent_queue_order;
	}
	/**
	 * @return the remaining
	 */
	public int getRemaining() {
		return remaining;
	}
	/**
	 * @param remaining the remaining to set
	 */
	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}
}
