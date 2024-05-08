package kh.student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentMain {
	public static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//System.out.println("main 시작");
		//Connection con = SQLStudentConnect.makeConnection();
		
		boolean quit = false;
		int numberSelection = 0;
		while (!quit) {
			menuIntroduction();	//메뉴 출력
			
			try {
				System.out.print("메뉴 번호를 선택해주세요 >>");
				numberSelection = sc.nextInt();
				sc.nextLine();
				if (numberSelection < 1 || numberSelection > 6) {
					System.out.println("1부터 9까지의 숫자를 입력");
				} else {
					switch (numberSelection) {
					case 1: //1. 학생 전체 목록 보기
						selectAllStudent();
						break;
					case 2:	//2. 학생 정보입력하기
						Student student = inputStudent();
						insertStudent(student);
						break;
					case 3:	//3. 학생 검색, 조회 하기
						searchStudent();
						break;
					case 4:	//4. 학생 정보 수정하기
						updateStudent();
						break;
					case 5:	//5. 학생 정보 삭제하기
						deleteStudent();
						break;
					case 6:	//6. 학생 로그인
						login();
						break;
					case 7:	//7. 종료
						System.out.println("종료하겠습니다.");
						quit = true;
						break;
					}// end of switch
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
				quit = true;
			}
			
		}//end of while
		
	}//end of main
	
	//6. 학생 로그인
	private static void login() {
		System.out.println("아이디 입력 >> ");
		String studentId = sc.nextLine();
		System.out.println("비밀번호 입력 >> ");
		String studentPw = sc.nextLine();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = SQLStudentConnect.makeConnection();
			String sql = "select * from student where sd_id = ? and sd_password = ?"; 
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, studentId);
			pstmt.setString(2, studentPw);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				System.out.println("로그인 성공");
				int sd_no = rs.getInt("sd_no");
				String sd_num = rs.getString("sd_num");
				String sd_name = rs.getString("sd_name");
				String sd_id = rs.getString("sd_id");
				String sd_password = rs.getString("sd_password");
				String sd_phone = rs.getString("sd_phone");
				String sd_birth = rs.getString("sd_birth");
				String sd_address = rs.getString("sd_address");
				Student student = new Student(sd_no, sd_num, sd_name, sd_id, sd_password, sd_phone, sd_birth, sd_address);
				System.out.println(student.toString());
			}else {
				System.out.println("로그인 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("rs, pstmt, con close error");
				e.printStackTrace();
			}
		}
		
		
	}

	//5. 학생 정보 삭제하기
	private static void deleteStudent() {
		selectAllStudent(); // 전체 목록 출력
		System.out.println("삭제할 학생의 번호 입력 >> ");
		int sd_no = sc.nextInt();
		sc.nextLine();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = SQLStudentConnect.makeConnection();
			String sql = "delete from student where sd_no = ?"; 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, sd_no);

			int i = pstmt.executeUpdate();	//리턴값 int
			if(i == 1) {
				System.out.println(sd_no + " 학생 삭제 성공하였습니다.");
			} else {
				System.out.println(sd_no + " 학생 삭제 실패하였습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("pstmt, con close error");
				e.printStackTrace();
			}
		}
	}

	//4. 학생 정보 수정하기
	public static void updateStudent() {
		Student student = searchStudent();
		
		System.out.println("수정할 이름 입력("+student.getSd_name()+") >> ");
		String sd_name = sc.nextLine();
		System.out.println("수정할 아이디 입력("+student.getSd_id()+") >> ");
		String sd_id = sc.nextLine();
		System.out.println("수정할 비밀번호 입력("+student.getSd_password()+") >> ");
		String sd_password = sc.nextLine();
		System.out.println("수정할 전화번호 입력("+student.getSd_phone()+") >> ");
		String sd_phone = sc.nextLine();
		System.out.println("수정할 생일 입력("+student.getSd_birth()+") >> ");
		String sd_birth = sc.nextLine();
		System.out.println("수정할 주소 입력("+student.getSd_address()+") >> ");
		String sd_address = sc.nextLine();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = SQLStudentConnect.makeConnection();
			String sql = "update student set sd_name = ?, sd_id= ?, sd_password = ?, sd_phone = ?, sd_birth = ?, sd_address = ? where sd_no =?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, sd_name);
			pstmt.setString(2, sd_id);
			pstmt.setString(3, sd_password);
			pstmt.setString(4, sd_phone);
			pstmt.setString(5, sd_birth);
			pstmt.setString(6, sd_address);
			pstmt.setInt(7, student.getSd_no());
			
			//rs = pstmt.executeQuery(); select 에만 씀
			int i = pstmt.executeUpdate();	//리턴값 int
			if(i == 1) {
				System.out.println(sd_name + " 학생의 정보수정이 성공하였습니다.");
			} else {
				System.out.println(sd_name + " 학생의 정보수정이 실패하였습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("pstmt, con close error");
				e.printStackTrace();
			}
		}
		
	}

	//3. 학생 정보 조회하기
	private static Student searchStudent() {
		System.out.println("찾고자 하는 학생 이름 입력 >>");
		String student_name = sc.nextLine();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Student student = null;
		try {
			con = SQLStudentConnect.makeConnection();
			String sql = "select * from student where sd_name = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, student_name);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int sd_no = rs.getInt("sd_no");
				String sd_num = rs.getString("sd_num");
				String sd_name = rs.getString("sd_name");
				String sd_id = rs.getString("sd_id");
				String sd_password = rs.getString("sd_password");
				String sd_phone = rs.getString("sd_phone");
				String sd_birth = rs.getString("sd_birth");
				String sd_address = rs.getString("sd_address");
				student = new Student(sd_no, sd_num, sd_name, sd_id, sd_password, sd_phone, sd_birth, sd_address);
				System.out.println(student.toString());
			}else {
				System.out.println(student_name + " 학생이 존재하지 않습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("rs, pstmt, con close error");
				e.printStackTrace();
			}
		}
		return student;
	}

	//2. 학생 정보 입력하기
	public static Student inputStudent() {
		System.out.print("학생 번호 입력 >>");
		String sd_num = sc.nextLine();
		System.out.print("학생 이름 입력 >>");
		String sd_name = sc.nextLine();
		System.out.print("학생 아이디 입력 >>");
		String sd_id = sc.nextLine();
		System.out.print("학생 비밀번호 입력 >>");
		String sd_password = sc.nextLine();
		System.out.print("학생 전화번호 입력 >>");
		String sd_phone = sc.nextLine();
		System.out.print("학생 생일 입력 >>");
		String sd_birth = sc.nextLine();
		System.out.print("학생 주소 입력 >>");
		String sd_address = sc.nextLine();
		
		Student student = new Student(0, sd_num, sd_name, sd_id, sd_password, sd_phone, sd_birth, sd_address);
		return student;
	}

	//2. 학생 정보 입력하기
	public static void insertStudent(Student student) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = SQLStudentConnect.makeConnection();
			String sql = "insert into student values (student_seq.nextval, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, student.getSd_num());
			pstmt.setString(2, student.getSd_name());
			pstmt.setString(3, student.getSd_id());
			pstmt.setString(4, student.getSd_password());
			pstmt.setString(5, student.getSd_phone());
			pstmt.setString(6, student.getSd_birth());
			pstmt.setString(7, student.getSd_address());
			
			//rs = pstmt.executeQuery(); select 에만 씀
			int i = pstmt.executeUpdate();	//리턴값 int
			if(i == 1) {
				System.out.println(student.getSd_name() + " 학생의 입력이 성공하였습니다.");
			} else {
				System.out.println(student.getSd_name() + " 학생의 입력이 실패하였습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("pstmt, con close error");
				e.printStackTrace();
			}
		}
	}

	//1. 학생 전체 보기
	public static void selectAllStudent() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			//Statement stmt = con.createStatement();
			con = SQLStudentConnect.makeConnection();
			String sql = "select * from student order by sd_no asc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int sd_no = rs.getInt("sd_no");
				String sd_num = rs.getString("sd_num");
				String sd_name = rs.getString("sd_name");
				String sd_id = rs.getString("sd_id");
				String sd_password = rs.getString("sd_password");
				String sd_phone = rs.getString("sd_phone");
				String sd_birth = rs.getString("sd_birth");
				String sd_address = rs.getString("sd_address");
				Student student = new Student(sd_no, sd_num, sd_name, sd_id, sd_password, sd_phone, sd_birth, sd_address);
				System.out.println(student.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("rs, pstmt, con close error");
				e.printStackTrace();
			}
		}
		
	}// end of selectAllStudent()


	private static void menuIntroduction() {
		System.out.println("============================================================");
		System.out.println("\t\t Student Menu");
		System.out.println("============================================================");
		System.out.println(" 1. 학생 전체 정보");
		System.out.println(" 2. 학생 정보입력하기");
		System.out.println(" 3. 학생 정보조회하기");
		System.out.println(" 4. 학생 정보수정하기");
		System.out.println(" 5. 학생 정보삭제하기");
		System.out.println(" 6. 학생 로그인하기");
		System.out.println(" 7. 종료하기");
		System.out.println("============================================================");		
	}

}
