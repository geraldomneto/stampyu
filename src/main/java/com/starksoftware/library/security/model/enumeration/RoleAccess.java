package com.starksoftware.library.security.model.enumeration;

public enum RoleAccess {
	NONE(0) {
		public String getNome() {
			return "";
		}
	},
	ADMIN(3) {
		public String getNome() {
			return "admin";
		}
	},
	USER(1) {
		public String getNome() {
			return "user";
		}
	};

	private int flag;

	public abstract String getNome();

	RoleAccess(int flag) {
		this.flag = flag;
	}

	public int getFlag() {
		return flag;
	}
}
