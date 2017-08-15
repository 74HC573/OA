package com.yc.bean;

import java.io.Serializable;
import java.util.UUID;

public class Fileupload implements Serializable {

	private static final long serialVersionUID = 8853796702754803815L;

	private Integer fid;
	private String fuuid;
	private String fname;
	private String description;
	private String path;
	private Integer uid;
	private String uptime;
	private Integer downtimes;
	private Integer fweight;
	private Integer touid;
	private Integer togid;
	private Integer todid;

	public String getFuuid() {
		fuuid = UUID.randomUUID().toString();
		return fuuid;
	}

	public void setFuuid(String fuuid) {
		this.fuuid = fuuid;
	}

	public Integer getTouid() {
		return touid;
	}

	public void setTouid(Integer touid) {
		this.touid = touid;
	}

	public Integer getTogid() {
		return togid;
	}

	public void setTogid(Integer togid) {
		this.togid = togid;
	}

	public Integer getTodid() {
		return todid;
	}

	public void setTodid(Integer todid) {
		this.todid = todid;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public Integer getDowntimes() {
		return downtimes;
	}

	public void setDowntimes(Integer downtimes) {
		this.downtimes = downtimes;
	}

	public Integer getFweight() {
		return fweight;
	}

	public void setFweight(Integer fweight) {
		this.fweight = fweight;
	}

	@Override
	public String toString() {
		return "Fileupload [fid=" + fid + ", fuuid=" + fuuid + ", fname=" + fname + ", description=" + description
				+ ", path=" + path + ", uid=" + uid + ", uptime=" + uptime + ", downtimes=" + downtimes + ", fweight="
				+ fweight + ", touid=" + touid + ", togid=" + togid + ", todid=" + todid + "]";
	}

}