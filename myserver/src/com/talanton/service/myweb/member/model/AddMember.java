package com.talanton.service.myweb.member.model;

public class AddMember {
	private String uid;
	private String password;
	private String cPassword;
	private String name;
	private String email;
	private int login_type;
	private String robot;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getcPassword() {
		return cPassword;
	}

	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLogin_type() {
		return login_type;
	}

	public void setLogin_type(int login_type) {
		this.login_type = login_type;
	}

	public String getRobot() {
		return robot;
	}

	public void setRobot(String robot) {
		this.robot = robot;
	}

	public Member toMember() {
		Member member = new Member();
		member.setUid(uid);
		member.setPassword(password);
		member.setName(name);
		member.setEmail(email);
		member.setLogin_type(login_type);
		return member;
	}
}