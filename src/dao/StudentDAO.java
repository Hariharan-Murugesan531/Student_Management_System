package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.Student;


public class StudentDAO {
//CREATE----------
	public void addStudent(Student s) {
		String sql="INSERT INTO students(name,age,course,email) VALUES(?,?,?,?)";
		try (Connection con=DBConnection.getConnection();
				PreparedStatement ps=con.prepareStatement(sql)){
			ps.setString(1, s.getName());
			ps.setInt(2, s.getAge());
			ps.setString(3, s.getCourse());
			ps.setString(4, s.getEmail());
			ps.executeUpdate();
			System.out.println("\\n✔ Student added successfully!");
		} catch (SQLException e) {
			System.out.println("Error adding student: " + e.getMessage());
		}
	}
	
//READ ALL----------
	public List<Student> getAllStudents(){
		List<Student> list=new ArrayList<>();
		String sql="SELECT * FROM students ORDER BY id";
		try(Connection con=DBConnection.getConnection();
				Statement st=con.createStatement();
				ResultSet rs=st.executeQuery(sql);) {
			while (rs.next()) {
				list.add(new Student(rs.getInt("id"),rs.getString("name"), rs.getInt("age"), rs.getString("course"), rs.getString("email")));
			}
		} catch (SQLException e) {
			System.out.println("Error fetching students: " + e.getMessage());
		}
		return list;
	}
	
//READ ONE STUDENT--------
	
	public Student getStudentById(int id) {
		String sql="SELECT * FROM students where id=?";
		try (Connection con=DBConnection.getConnection();
				PreparedStatement ps=con.prepareStatement(sql)){
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				return new Student(rs.getInt("id"),rs.getString("name"), rs.getInt("age"), rs.getString("course"), rs.getString("email"));
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
		return null;
	}
	
//UPDATE ----------
	public void updateStudent(int id,String newName,int newAge,String newCourse,String newEmail) {
		String sql="UPDATE students SET name=?, age=?,course=?,email=? where id=?";
		try(Connection con=DBConnection.getConnection();
				PreparedStatement ps=con.prepareStatement(sql);){
			ps.setString(1, newName);
			ps.setInt(2, newAge);
			ps.setString(3, newCourse);
			ps.setString(4, newEmail);
			ps.setInt(5, id);
			
			int rows=ps.executeUpdate();
			System.out.println(rows>0?"\n✔ Updated successfully!" : "\n✘ Student ID not found.");
			
		} catch (SQLException e) {
			System.out.println("Error updating: " + e.getMessage());
		}
	}
	
//DELETE------------
	public void deleteStudent(int id) {
		String sql="DELETE FRON students where id=?";
		try(Connection con=DBConnection.getConnection();
				PreparedStatement ps=con.prepareStatement(sql);) {
			ps.setInt(1, id);
			int row=ps.executeUpdate();
			System.out.println(row>0?"\n✔ Deleted successfully!" : "\n✘ Student ID not found.");
		} catch (SQLException e) {
			System.out.println("Error deleting: " + e.getMessage());
		}
	}
	
//SEARCH BY NAME---------
	
	public List<Student> searchByName(String keyword){
		List<Student> list=new ArrayList<>();
		String sql="SELECT * FROM students WHERE name LIKE ?";
		try (Connection con=DBConnection.getConnection();
				PreparedStatement ps=con.prepareStatement(sql);){
			ps.setString(1, "%"+keyword+"%");
			ResultSet rs=ps.executeQuery();
			while (rs.next()) {
				list.add(new Student(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getInt("age"),
						rs.getString("course"),
						rs.getString("email")
						));
			}
		} catch (SQLException e) {
			System.out.println("Error searching: " + e.getMessage());
		}
		return list;
	}
}
