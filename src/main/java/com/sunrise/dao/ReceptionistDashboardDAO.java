package com.sunrise.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.sunrise.config.DBConnection;
import com.sunrise.model.DashboardStats;



public class ReceptionistDashboardDAO {



    public DashboardStats getDashboardStats(){


        DashboardStats stats = new DashboardStats();
 
        
        String usersSQL =
                "SELECT COUNT(*) FROM users WHERE status = 'active'";        
       
        
        String patientSQL =
                "SELECT COUNT(*) FROM patients";

        

        String appointmentSQL =
                "SELECT COUNT(*) FROM appointments "
                +
                "WHERE DATE(appointment_date_time)=CURDATE()";



        String dentistSQL =
                "SELECT COUNT(*) FROM dentists "
                +
                "WHERE status='active'";



        String treatmentSQL =
                "SELECT COUNT(*) FROM treatments";



        try(Connection con = DBConnection.getConnection()){



            // Total Patients

            try(
                PreparedStatement ps =
                con.prepareStatement(patientSQL);

                ResultSet rs = ps.executeQuery();
            ){

                if(rs.next()){

                    stats.setTotalPatients(
                        rs.getInt(1)
                    );

                }

            }





            // Today's Appointments

            try(
                PreparedStatement ps =
                con.prepareStatement(appointmentSQL);

                ResultSet rs = ps.executeQuery();
            ){

                if(rs.next()){

                    stats.setTodayAppointments(
                        rs.getInt(1)
                    );

                }

            }






            // Active Dentists

            try(
                PreparedStatement ps =
                con.prepareStatement(dentistSQL);

                ResultSet rs = ps.executeQuery();
            ){

                if(rs.next()){

                    stats.setActiveDentists(
                        rs.getInt(1)
                    );

                }

            }



            // Active Users

            try(
                PreparedStatement ps =
                con.prepareStatement(usersSQL);

                ResultSet rs = ps.executeQuery();
            ){

                if(rs.next()){

                    stats.setActiveUsers(
                        rs.getInt(1)
                    );
                }

            }



            // Treatment Types

            try(
                PreparedStatement ps =
                con.prepareStatement(treatmentSQL);

                ResultSet rs = ps.executeQuery();
            ){

                if(rs.next()){

                    stats.setTotalTreatments(
                        rs.getInt(1)
                    );

                }

            }



        }
        catch(SQLException e){


            e.printStackTrace();


        }



        return stats;


    }


}