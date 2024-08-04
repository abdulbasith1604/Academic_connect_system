package college;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword()); 
            pst.setString(3, user.getRole());
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
            }
        }
    }

    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection con = DbConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password")); 
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        }
        return null;
    }

    public boolean authenticateUser(String username, String password) throws SQLException {
        User user = getUserByUsername(username);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }
}
