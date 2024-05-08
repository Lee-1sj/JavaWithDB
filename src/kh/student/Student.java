package kh.student;

public class Student {
	private int sd_no;
	private String sd_num;
	private String sd_name;
	private String sd_id;
	private String sd_password;
	private String sd_phone;
	private String sd_birth;
	private String sd_address;
	
	public Student() {
		super();
	}

	public Student(int sd_no, String sd_num, String sd_name, String sd_id, String sd_password, String sd_phone,
			String sd_birth, String sd_address) {
		super();
		this.sd_no = sd_no;
		this.sd_num = sd_num;
		this.sd_name = sd_name;
		this.sd_id = sd_id;
		this.sd_password = sd_password;
		this.sd_phone = sd_phone;
		this.sd_birth = sd_birth;
		this.sd_address = sd_address;
	}

	public int getSd_no() {
		return sd_no;
	}

	public void setSd_no(int sd_no) {
		this.sd_no = sd_no;
	}

	public String getSd_num() {
		return sd_num;
	}

	public void setSd_num(String sd_num) {
		this.sd_num = sd_num;
	}

	public String getSd_name() {
		return sd_name;
	}

	public void setSd_name(String sd_name) {
		this.sd_name = sd_name;
	}

	public String getSd_id() {
		return sd_id;
	}

	public void setSd_id(String sd_id) {
		this.sd_id = sd_id;
	}

	public String getSd_password() {
		return sd_password;
	}

	public void setSd_password(String sd_password) {
		this.sd_password = sd_password;
	}

	public String getSd_phone() {
		return sd_phone;
	}

	public void setSd_phone(String sd_phone) {
		this.sd_phone = sd_phone;
	}

	public String getSd_birth() {
		return sd_birth;
	}

	public void setSd_birth(String sd_birth) {
		this.sd_birth = sd_birth;
	}

	public String getSd_address() {
		return sd_address;
	}

	public void setSd_address(String sd_address) {
		this.sd_address = sd_address;
	}

	@Override
	public String toString() {
		return "[" + sd_no + ", " + sd_num + ", " + sd_name + ", " + sd_id
				+ ", " + sd_password + ", " + sd_phone + ", " + sd_birth + ", "
				+ sd_address + "]";
	}
	
}
