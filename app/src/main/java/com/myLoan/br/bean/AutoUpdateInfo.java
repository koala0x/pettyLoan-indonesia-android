package com.myLoan.br.bean;

/**
 * @author Administrator
 * 版本更新服务器返回JSON数据
 */
public class AutoUpdateInfo {

	private  String new_version;
	private  String new_versionname;
	private  String apk_url;
	private  boolean isStrong;//是否强制更新
	private  String update_log;

	public boolean isStrong() {
		return isStrong;
	}

	public void setStrong(boolean strong) {
		isStrong = strong;
	}
	/*	private  String new_md5;
	private  String target_size;*/
	
	
/*	public String getOld_version() {
		return old_version;
	}
	public void setOld_version(String old_version) {
		this.old_version = old_version;
	}*/
	public String getNew_version() {
		return new_version;
	}
	public void setNew_version(String new_version) {
		this.new_version = new_version;
	}
	public String getNew_versionname() {
		return new_versionname;
	}
	public void setNew_versionname(String new_versionname) {
		this.new_versionname = new_versionname;
	}
	public String getApk_url() {
		return apk_url;
	}
	public void setApk_url(String apk_url) {
		this.apk_url = apk_url;
	}
	public String getUpdate_log() {
		return update_log;
	}
	public void setUpdate_log(String update_log) {
		this.update_log = update_log;
	}
	/*public String getNew_md5() {
		return new_md5;
	}
	public void setNew_md5(String new_md5) {
		this.new_md5 = new_md5;
	}
	public String getTarget_size() {
		return target_size;
	}
	public void setTarget_size(String target_size) {
		this.target_size = target_size;
	}*/
	@Override
	public String toString() {
		return "AutoUpdateInfo [new_version="
				+ new_version + ", new_versionname=" + new_versionname
				+ ", apk_url=" + apk_url + ", update_log=" + update_log
				/*+ ", new_md5=" + new_md5 + ", target_size=" + target_size*/ + "]";
	}
	
}
