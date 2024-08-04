package college;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CollegeDAO {

    public College getCollegeByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM colleges WHERE user_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, userId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    College college = new College();
                    college.setUserId(rs.getInt("user_id"));
                    college.setCollegeId(rs.getString("college_id"));
                    college.setName(rs.getString("name"));
                    college.setLocation(rs.getString("location"));
                    return college;
                }
            }
        }
        return null;
    }

    public void addCollege(College college) throws SQLException {
        String query = "INSERT INTO colleges (user_id, college_id, name, location) VALUES (?, ?, ?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, college.getUserId());
            pst.setString(2, college.getCollegeId());
            pst.setString(3, college.getName());
            pst.setString(4, college.getLocation());
            pst.executeUpdate();
        }
    }

    public void updateCollege(College college) throws SQLException {
        String query = "UPDATE colleges SET name = ?, location = ? WHERE user_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, college.getName());
            pst.setString(2, college.getLocation());
            pst.setInt(3, college.getUserId());
            pst.executeUpdate();
        }
    }

    public void deleteCollege(int userId) throws SQLException {
        String query = "DELETE FROM colleges WHERE user_id = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, userId);
            pst.executeUpdate();
        }
    }
}
