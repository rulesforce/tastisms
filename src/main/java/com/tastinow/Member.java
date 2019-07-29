package com.tastinow;

public class Member {
	
	private String nickname;
	private String phone;
	private String optout;	//Y for yes and N for no
	
	public Member() {
		nickname = "";
		phone = "";
		optout = "";
	}
	
	public Member(String nickname, String phone, String optout) {
		this.nickname = nickname;
		this.phone = phone;
		this.optout = optout;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOptout() {
		return optout;
	}
	public void setOptout(String optout) {
		this.optout = optout;
	}

	
}
