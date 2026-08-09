package com.sunrise.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


import com.sunrise.config.DBConnection;
import com.sunrise.model.Dentist;



public class DentistDAO {



    // ===============================
    // Get all dentists with search + sort
    // ===============================


    public List<Dentist> searchDentists(String keyword, String sortBy){


        List<Dentist> dentists = new ArrayList<>();


        String orderBy = "created_at DESC";



        if(sortBy != null){


            switch(sortBy){


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



                default:

                    orderBy = "created_at DESC";

                    break;


            }


        }





        String sql =

        		"SELECT " +
        		" d.dentist_id, " +
        		" d.full_name, " +
        		" d.email, " +
        		" d.phone, " +
        		" d.status, " +
        		" d.created_at, " +
        		" d.updated_at, " +
        		" COUNT(dt.id) AS assigned_treatments " +

        		"FROM dentists d " +

        		"LEFT JOIN dentist_treatments dt " +
        		"ON d.dentist_id = dt.dentist_id " +

        		"WHERE d.full_name LIKE ? " +
        		"OR d.email LIKE ? " +
        		"OR CAST(d.phone AS CHAR) LIKE ? " +

        		"GROUP BY " +
        		" d.dentist_id, " +
        		" d.full_name, " +
        		" d.email, " +
        		" d.phone, " +
        		" d.status, " +
        		" d.created_at, " +
        		" d.updated_at " +

        		"ORDER BY " + orderBy;







        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)){





            String search = "%" + keyword + "%";



            ps.setString(1, search);

            ps.setString(2, search);

            ps.setString(3, search);





            ResultSet rs = ps.executeQuery();





            while(rs.next()){


                Dentist dentist = new Dentist();



                dentist.setDentistId(
                        rs.getInt("dentist_id")
                );



                dentist.setFullName(
                        rs.getString("full_name")
                );



                dentist.setEmail(
                        rs.getString("email")
                );



                dentist.setPhone(
                        rs.getInt("phone")
                );



                dentist.setStatus(
                        rs.getString("status")
                );



                dentist.setCreatedAt(
                        rs.getTimestamp("created_at")
                );



                dentist.setUpdatedAt(
                        rs.getTimestamp("updated_at")
                );

                
                dentist.setAssignedTreatments(
                	    rs.getInt("assigned_treatments")
                	);


                dentists.add(dentist);



            }





        }
        catch(SQLException e){

            e.printStackTrace();

        }





        return dentists;


    }









    // ===============================
    // Get dentist by ID
    // ===============================


    public Dentist getDentistById(int dentistId){


        Dentist dentist = null;



        String sql =

        "SELECT dentist_id, full_name, email, phone, status, created_at, updated_at "

        +

        "FROM dentists "

        +

        "WHERE dentist_id=?";






        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)){





            ps.setInt(1, dentistId);



            ResultSet rs = ps.executeQuery();





            if(rs.next()){


                dentist = new Dentist();



                dentist.setDentistId(
                        rs.getInt("dentist_id")
                );



                dentist.setFullName(
                        rs.getString("full_name")
                );



                dentist.setEmail(
                        rs.getString("email")
                );



                dentist.setPhone(
                        rs.getInt("phone")
                );



                dentist.setStatus(
                        rs.getString("status")
                );



                dentist.setCreatedAt(
                        rs.getTimestamp("created_at")
                );



                dentist.setUpdatedAt(
                        rs.getTimestamp("updated_at")
                );



            }





        }
        catch(SQLException e){

            e.printStackTrace();

        }





        return dentist;


    }









    // ===============================
    // Add Dentist
    // ===============================


    public boolean addDentist(Dentist dentist){


        boolean result = false;



        String sql =

        "INSERT INTO dentists "

        +

        "(full_name, email, phone, status) "

        +

        "VALUES (?, ?, ?, ?)";






        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)){





            ps.setString(1, dentist.getFullName());

            ps.setString(2, dentist.getEmail());

            ps.setInt(3, dentist.getPhone());

            ps.setString(4, dentist.getStatus());





            int rows = ps.executeUpdate();





            if(rows > 0){

                result = true;

            }





        }
        catch(SQLException e){

            e.printStackTrace();

        }





        return result;


    }









    // ===============================
    // Update Dentist
    // ===============================


    public boolean updateDentist(Dentist dentist){


        boolean result = false;



        String sql =

        "UPDATE dentists SET "

        +

        "full_name=?, "

        +

        "email=?, "

        +

        "phone=?, "

        +

        "status=?, "

        +

        "updated_at=CURRENT_TIMESTAMP "

        +

        "WHERE dentist_id=?";







        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)){





            ps.setString(1, dentist.getFullName());

            ps.setString(2, dentist.getEmail());

            ps.setInt(3, dentist.getPhone());

            ps.setString(4, dentist.getStatus());

            ps.setInt(5, dentist.getDentistId());





            int rows = ps.executeUpdate();





            if(rows > 0){

                result = true;

            }





        }
        catch(SQLException e){

            e.printStackTrace();

        }





        return result;


    }









    // ===============================
    // Email duplicate check
    // ===============================


    public boolean emailExists(String email){


        boolean exists = false;



        String sql =

        "SELECT dentist_id FROM dentists WHERE email=?";





        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)){





            ps.setString(1,email);



            ResultSet rs = ps.executeQuery();





            if(rs.next()){

                exists = true;

            }





        }
        catch(SQLException e){

            e.printStackTrace();

        }




        return exists;


    }









    // ===============================
    // Phone duplicate check
    // ===============================


    public boolean phoneExists(int phone){


        boolean exists = false;



        String sql =

        "SELECT dentist_id FROM dentists WHERE phone=?";






        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)){





            ps.setInt(1, phone);



            ResultSet rs = ps.executeQuery();





            if(rs.next()){

                exists = true;

            }





        }
        catch(SQLException e){

            e.printStackTrace();

        }




        return exists;


    }









    // ===============================
    // Email check while editing
    // ===============================


    public boolean emailExistsExceptCurrent(
            String email,
            int dentistId){



        boolean exists = false;




        String sql =

        "SELECT dentist_id FROM dentists "

        +

        "WHERE email=? AND dentist_id<>?";






        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)){





            ps.setString(1,email);

            ps.setInt(2,dentistId);





            ResultSet rs = ps.executeQuery();





            if(rs.next()){

                exists=true;

            }





        }
        catch(SQLException e){

            e.printStackTrace();

        }





        return exists;


    }









    // ===============================
    // Phone check while editing
    // ===============================


    public boolean phoneExistsExceptCurrent(
            int phone,
            int dentistId){



        boolean exists = false;




        String sql =

        "SELECT dentist_id FROM dentists "

        +

        "WHERE phone=? AND dentist_id<>?";






        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)){





            ps.setInt(1,phone);

            ps.setInt(2,dentistId);





            ResultSet rs = ps.executeQuery();





            if(rs.next()){

                exists=true;

            }





        }
        catch(SQLException e){

            e.printStackTrace();

        }





        return exists;


    }



}