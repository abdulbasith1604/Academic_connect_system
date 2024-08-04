package college;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public Student getStudentByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM students WHERE user_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setUserId(rs.getInt("user_id"));
                    student.setStudentId(rs.getString("student_id"));
                    student.setName(rs.getString("name"));
                    student.setCourse(rs.getString("course"));
                    student.setYear(rs.getInt("year"));
                    return student;
                }
            }
        }
        return null;
    }

    public void addStudent(Student student) throws SQLException {
        String query = "INSERT INTO students (user_id, student_id, name, course, year) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, student.getUserId());
            pst.setString(2, student.getStudentId());
            pst.setString(3, student.getName());
            pst.setString(4, student.getCourse());
            pst.setInt(5, student.getYear());
            pst.executeUpdate();
        }
    }

    public void updateStudent(Student student) throws SQLException {
        String query = "UPDATE students SET name = ?, course = ?, year = ? WHERE user_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, student.getName());
            pst.setString(2, student.getCourse());
            pst.setInt(3, student.getYear());
            pst.setInt(4, student.getUserId());
            pst.executeUpdate();
        }
    }

    public void deleteStudent(int userId) throws SQLException {
        String query = "DELETE FROM students WHERE user_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, userId);
            pst.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String query = "SELECT student_id, name, course, year FROM students";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getString("student_id"));
                student.setName(rs.getString("name"));
                student.setCourse(rs.getString("course"));
                student.setYear(rs.getInt("year"));
                students.add(student);
            }
        }
        return students;
    }
}
