package com.sunrise.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


import com.sunrise.config.DBConnection;
import com.sunrise.model.User;



public class ReceptionistDAO {



    /*
     * Get all receptionists
     * Default sorting by created date DESC
     */
    public List<User> getAllReceptionists() {


        List<User> receptionists = new ArrayList<>();


        String sql =
                "SELECT user_id, full_name, email, phone, role, status, created_at, updated_at "
                +
                "FROM users "
                +
                "WHERE role='receptionist' "
                +
                "ORDER BY created_at DESC";



        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery()) {



            while(rs.next()) {


                User user = new User();


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


                receptionists.add(user);

            }


        }
        catch(SQLException e) {


            e.printStackTrace();

        }


        return receptionists;

    }


    /*
     * Search Receptionists
     *
     * Search:
     * full_name
     * email
     * phone
     *
     * Sort:
     * name
     * newest
     * oldest
     * status
     */
    public List<User> searchReceptionists(
            String keyword,
            String sortBy) {



        List<User> receptionists = new ArrayList<>();


        String orderBy = "created_at DESC";



        if(sortBy != null) {


            switch(sortBy) {


                case "name_asc":

                    orderBy = "full_name ASC";
                    break;



                case "name_desc":

                    orderBy = "full_name DESC";
                    break;



                case "oldest":

                    orderBy = "created_at ASC";
                    break;



                case "status":

                    orderBy = "status ASC";
                    break;



                case "newest":

                    orderBy = "created_at DESC";
                    break;



                default:

                    orderBy = "created_at DESC";

            }


        }





        String sql =
                "SELECT user_id, full_name, email, phone, role, status, created_at, updated_at "
                +
                "FROM users "
                +
                "WHERE role='receptionist' "
                +
                "AND (full_name LIKE ? "
                +
                "OR email LIKE ? "
                +
                "OR CAST(phone AS CHAR) LIKE ?) "
                +
                "ORDER BY " + orderBy;



        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)) {



            String search =
                    "%" + keyword + "%";



            ps.setString(1, search);

            ps.setString(2, search);

            ps.setString(3, search);





            ResultSet rs =
                    ps.executeQuery();




            while(rs.next()) {


                User user = new User();



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



                receptionists.add(user);


            }



        }
        catch(SQLException e) {


            e.printStackTrace();

        }




        return receptionists;

    }

    
    /*
     * Check email already exists
     */
    public boolean emailExists(String email) {


        boolean exists = false;



        String sql =
                "SELECT COUNT(*) FROM users WHERE email=?";



        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)) {



            ps.setString(1, email);



            ResultSet rs =
                    ps.executeQuery();



            if(rs.next()) {


                exists =
                    rs.getInt(1) > 0;

            }


        }
        catch(SQLException e) {


            e.printStackTrace();

        }



        return exists;

    }


    /*
     * Check phone already exists
     */
    public boolean phoneExists(int phone) {


        boolean exists = false;



        String sql =
                "SELECT COUNT(*) FROM users WHERE phone=?";



        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)) {



            ps.setInt(1, phone);



            ResultSet rs =
                    ps.executeQuery();



            if(rs.next()) {


                exists =
                    rs.getInt(1) > 0;

            }


        }
        catch(SQLException e) {


            e.printStackTrace();

        }



        return exists;

    }

    
    /*
     * Add Receptionists
     * */
    public boolean addReceptionist(User user) {


        boolean result = false;


        String sql =
                "INSERT INTO users "
                +
                "(full_name, email, phone, role, password, status) "
                +
                "VALUES (?, ?, ?, ?, ?, ?)";



        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)) {



            ps.setString(1, user.getFullname());

            ps.setString(2, user.getEmail());

            ps.setInt(3, user.getPhone());

            ps.setString(4, user.getRole());

            ps.setString(5, user.getPassword());

            ps.setString(6, user.getStatus());



            int rows =
                    ps.executeUpdate();



            if(rows > 0) {

                result = true;

            }



        }
        catch(SQLException e) {


            e.printStackTrace();

        }



        return result;


    }

    /*
     * Get Receptionists by ID
     * */
    public User getReceptionistById(int userId) {


        User user = null;


        String sql =
                "SELECT user_id, full_name, email, phone, role, status, created_at, updated_at "
                +
                "FROM users "
                +
                "WHERE user_id=? AND role='receptionist'";



        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)) {



            ps.setInt(1, userId);



            ResultSet rs = ps.executeQuery();



            if(rs.next()) {


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
        catch(SQLException e){

            e.printStackTrace();

        }



        return user;


    }
    
    
    /*
     * Update Receptionists
     * */
    public boolean updateReceptionist(User user) {


    boolean updated = false;



//    System.out.println(user.getFullname());
    
    String sql;



    if(user.getPassword() != null &&
       !user.getPassword().isEmpty()) {



        sql =
        "UPDATE users SET "
        +
        "full_name=?, "
        +
        "password=?, "
        +
        "status=?, "
        +
        "updated_at=CURRENT_TIMESTAMP "
        +
        "WHERE user_id=? "
        +
        "AND role='receptionist'";



    }
    else {



        sql =
        "UPDATE users SET "
        +
        "full_name=?, "
        +
        "status=?, "
        +
        "updated_at=CURRENT_TIMESTAMP "
        +
        "WHERE user_id=? "
        +
        "AND role='receptionist'";


    }





    try(Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(sql)) {



        ps.setString(1,user.getFullname());



        if(user.getPassword()!=null &&
           !user.getPassword().isEmpty()) {



            ps.setString(2,user.getPassword());

            ps.setString(3,user.getStatus());

            ps.setInt(4,user.getUserId());


        }
        else {


            ps.setString(2,user.getStatus());

            ps.setInt(3,user.getUserId());


        }





        int rows =
                ps.executeUpdate();



        if(rows>0){

            updated=true;

        }


    }
    catch(SQLException e){

        e.printStackTrace();

    }




    return updated;


    }


}