package com.sunrise.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.sunrise.config.DBConnection;
import com.sunrise.model.User;
import com.sunrise.util.PasswordUtil;



public class UserDAO {

    public User authenticate(String email, String password) {

        User user = null;

        String sql = 
        "SELECT user_id, full_name, email, phone, password, role, status, created_at, updated_at "
        +
        "FROM users "
        +
        "WHERE email=? AND status=?";



        try(
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
        ){

            ps.setString(1, email);
            ps.setString(2, "active");
            
            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                String hashedPassword =
                        rs.getString("password");

                // BCrypt password checking
                boolean passwordMatch =
                        PasswordUtil.checkPassword(
                                password,
                                hashedPassword
                        );

                if(passwordMatch){
                	
                    user = new User();

                    user.setUserId(
                            rs.getInt("user_id")
                    );


                    user.setFullname(
                            rs.getString("full_name")
                    );


                    user.setEmail(
                            rs.getString("email")
                    );


                    user.setPhone(
                            rs.getInt("phone")
                    );


                    user.setPassword(
                            hashedPassword
                    );


                    user.setRole(
                            rs.getString("role")
                    );


                    user.setStatus(
                            rs.getString("status")
                    );


                    user.setCreatedAt(
                            rs.getTimestamp("created_at")
                    );


                    user.setUpdatedAt(
                            rs.getTimestamp("updated_at")
                    );

                }

            }


        }
        
        catch(SQLException e){
            e.printStackTrace();
        }

        return user;
    }

    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT user_id, full_name, email, phone, password, role, status, created_at, updated_at " +
                     "FROM users WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setFullname(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getInt("phone"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean updateName(int userId, String newName) {
        String sql = "UPDATE users SET full_name = ?, updated_at = CURRENT_TIMESTAMP WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(int userId, String hashedPassword) {
        String sql = "UPDATE users SET password = ?, updated_at = CURRENT_TIMESTAMP WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hashedPassword);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getPasswordHashById(int userId) {
        String sql = "SELECT password FROM users WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}