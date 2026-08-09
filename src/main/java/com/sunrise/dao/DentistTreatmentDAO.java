package com.sunrise.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


import com.sunrise.config.DBConnection;
import com.sunrise.model.DentistTreatment;
import com.sunrise.model.Treatments;



public class DentistTreatmentDAO {



    // =====================================================
    // Get all dentists with assigned treatments
    // Also returns dentists without treatments
    // =====================================================


    public List<DentistTreatment> getAllDentistTreatmentsByDentistId(int dentistId){


        List<DentistTreatment> list = new ArrayList<>();


        String sql =

        		"SELECT " +
        		" dt.id, " +
        		" dt.treatment_id, " +
        		" dt.dent_commision_perc, " +
        		" t.treatment_name, " +
        		" t.default_fee " +

        		"FROM dentist_treatments dt " +

        		"INNER JOIN treatments t " +

        		"ON dt.treatment_id = t.treatment_id " +

        		"WHERE dt.dentist_id = ?";


        try(Connection con = DBConnection.getConnection();

        	    PreparedStatement ps = con.prepareStatement(sql)) {


        	System.out.println("Searching dentist = " + dentistId);

        	
        	    ps.setInt(1, dentistId);


        	    ResultSet rs = ps.executeQuery();


        	    while(rs.next()) {

        	    	DentistTreatment dt =
                            new DentistTreatment();



                    // Dentist details

        	    	dt.setId(
        	    			rs.getInt("id")
        	    			);

                    dt.setDentistId(
                            dentistId
                    );

                    dt.setTreatmentId(
                            rs.getInt("treatment_id")
                    );

                    dt.setDentCommissionPerc(
                            rs.getDouble("dent_commision_perc")
                    );



                    // Treatment details


                    dt.setTreatmentName(
                            rs.getString("treatment_name")
                    );


                    dt.setDefaultFee(
                            rs.getDouble("default_fee")
                    );



                    // Calculate dentist earning


                    double amount =

                    dt.getDefaultFee()
                    *
                    dt.getDentCommissionPerc()
                    /
                    100;



                    dt.setCommissionAmount(amount);



                    list.add(dt);

        	    }


        	}
        	catch(SQLException e){

        	    e.printStackTrace();

        	}
        
        return list;

    }







    // =====================================================
    // Get treatments which are NOT assigned to dentist
    // =====================================================


    public List<Treatments>
    getAvailableTreatmentsForDentist(int dentistId){



        List<com.sunrise.model.Treatments> list =
                new ArrayList<>();




        String sql =


        "SELECT treatment_id, " +

        "treatment_name, " +

        "description, " +

        "estimated_duration, " +

        "default_fee " +

        "FROM treatments " +

        "WHERE treatment_id NOT IN " +

        "(SELECT treatment_id " +

        "FROM dentist_treatments " +

        "WHERE dentist_id=?) " +

        "ORDER BY treatment_name ASC";





        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)){



            ps.setInt(1,dentistId);



            ResultSet rs =
                    ps.executeQuery();




            while(rs.next()){



                Treatments t =
                        new Treatments();



                t.setTreatmentID(
                        rs.getInt("treatment_id")
                );



                t.setTreatmentName(
                        rs.getString("treatment_name")
                );



                t.setDescription(
                        rs.getString("description")
                );



                t.setEstimatedDuration(
                        rs.getInt("estimated_duration")
                );



                t.setDefaultFee(
                        rs.getDouble("default_fee")
                );



                list.add(t);



            }


        }
        catch(SQLException e){

            e.printStackTrace();

        }



        return list;


    }








    // =====================================================
    // Check duplicate assignment
    // =====================================================


    public boolean existsDentistTreatment(
            int dentistId,
            int treatmentId){



        boolean exists = false;



        String sql =

        "SELECT id FROM dentist_treatments " +

        "WHERE dentist_id=? " +

        "AND treatment_id=?";





        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)){



            ps.setInt(1,dentistId);

            ps.setInt(2,treatmentId);



            ResultSet rs =
                    ps.executeQuery();



            if(rs.next()){

                exists=true;

            }



        }
        catch(SQLException e){

            e.printStackTrace();

        }




        return exists;


    }









    // =====================================================
    // Assign treatment
    // =====================================================


    public boolean addDentistTreatment(
            DentistTreatment dt){



        boolean result=false;



        String sql =

        "INSERT INTO dentist_treatments " +

        "(dentist_id, treatment_id, dent_commision_perc) " +

        "VALUES (?, ?, ?)";





        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)){



            ps.setInt(
                    1,
                    dt.getDentistId()
            );



            ps.setInt(
                    2,
                    dt.getTreatmentId()
            );



            ps.setDouble(
                    3,
                    dt.getDentCommissionPerc()
            );




            int rows =
                    ps.executeUpdate();



            if(rows>0){

                result=true;

            }



        }
        catch(SQLException e){

            e.printStackTrace();

        }




        return result;


    }








    // =====================================================
    // Update commission percentage
    // =====================================================


    public boolean updateCommission(
            int id,
            double commission){



        boolean result=false;



        String sql =

        "UPDATE dentist_treatments " +

        "SET dent_commision_perc=? " +

        "WHERE id=?";





        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)){



            ps.setDouble(1, commission);

            ps.setInt(2,id);



            int rows =
                    ps.executeUpdate();



            if(rows>0){

                result=true;

            }



        }
        catch(SQLException e){

            e.printStackTrace();

        }




        return result;


    }

    
 // ====================================================
 // Get assigned dentist treatment by assignment ID
 // ====================================================

 public DentistTreatment getDentistAssignedTreatmentById(int dtId) {

     DentistTreatment dt = null;

     String sql =
             "SELECT " +
             "dt.id, " +
             "dt.dentist_id, " +
             "dt.treatment_id, " +
             "dt.dent_commision_perc, " +

             "d.full_name AS dentist_name, " +
             "d.email AS dentist_email, " +
             "d.phone AS dentist_phone, " +
             "d.status AS dentist_status, " +
             "d.created_at AS dentist_created_at, " +

             "t.treatment_name, " +
             "t.estimated_duration, " +
             "t.default_fee " +

             "FROM dentist_treatments dt " +

             "INNER JOIN dentists d " +
             "ON dt.dentist_id = d.dentist_id " +

             "INNER JOIN treatments t " +
             "ON dt.treatment_id = t.treatment_id " +

             "WHERE dt.id=?";


     try(
         Connection con = DBConnection.getConnection();

         PreparedStatement ps =
                 con.prepareStatement(sql)
     ){

         ps.setInt(1, dtId);

         ResultSet rs = ps.executeQuery();


         if(rs.next()) {

             dt = new DentistTreatment();


             // Dentist treatment assignment

             dt.setId(
                     rs.getInt("id")
             );

             dt.setDentistId(
                     rs.getInt("dentist_id")
             );

             dt.setTreatmentId(
                     rs.getInt("treatment_id")
             );

             dt.setDentCommissionPerc(
                     rs.getDouble("dent_commision_perc")
             );


             // Treatment details

             dt.setTreatmentName(
                     rs.getString("treatment_name")
             );

             dt.setEstimatedDuration(
                     rs.getInt("estimated_duration")
             );

             dt.setDefaultFee(
                     rs.getDouble("default_fee")
             );


             // Calculate dentist earning

             double commissionAmount =
                     dt.getDefaultFee()
                     *
                     dt.getDentCommissionPerc()
                     /
                     100.0;


             dt.setCommissionAmount(
                     commissionAmount
             );

         }

     }
     catch(SQLException e) {

         e.printStackTrace();

     }


     return dt;
 }






    // =====================================================
    // Delete assigned treatment
    // =====================================================


    public boolean deleteDentistTreatment(
            int id){



        boolean result=false;



        String sql =

        "DELETE FROM dentist_treatments " +

        "WHERE id=?";





        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)){



            ps.setInt(1,id);



            int rows =
                    ps.executeUpdate();



            if(rows>0){

                result=true;

            }



        }
        catch(SQLException e){

            e.printStackTrace();

        }



        return result;


    }

    
    
    public boolean assignTreatment(
            DentistTreatment dt
    ){

        boolean result=false;


        String sql =
        "INSERT INTO dentist_treatments " +
        "(dentist_id,treatment_id,dent_commision_perc) " +
        "VALUES (?,?,?)";


        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){


            ps.setInt(1,dt.getDentistId());

            ps.setInt(2,dt.getTreatmentId());

            ps.setDouble(3,dt.getDentCommissionPerc());



            result =
            ps.executeUpdate()>0;


        }
        catch(SQLException e){

            e.printStackTrace();

        }


        return result;

    }
    


}