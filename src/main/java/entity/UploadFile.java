package entity;

import java.sql.Timestamp;

public class UploadFile {
	/**
	 * 上传的文件
	 */
	private String id;
	private String name;
	private long size;
	private String version;
	private String savepath;
	private String downloadpath;
	private Timestamp datetime;
	public UploadFile() {
		
		this.id = null;
		this.name = "";
		this.size = 0;
		this.version = "";
		this.savepath = "";
		this.downloadpath = "";
		this.datetime = null;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSavepath() {
		return savepath;
	}
	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}
	public String getDownloadpath() {
		return downloadpath;
	}
	public void setDownloadpath(String downloadpath) {
		this.downloadpath = downloadpath;
	}
	public Timestamp getDatetime() {
		return datetime;
	}
	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}
	@Override
	public String toString() {
		return "UploadFile [id=" + id + ", name=" + name + ", size=" + size
				+ ", version=" + version + ", savepath=" + savepath
				+ ", downloadpath=" + downloadpath + ", datetime=" + datetime
				+ "]";
	}
	
	
	

}
