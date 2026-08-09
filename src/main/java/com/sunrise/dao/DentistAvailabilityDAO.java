package com.sunrise.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.sunrise.config.DBConnection;
import com.sunrise.model.DentistAvailability;


public class DentistAvailabilityDAO {


    // =====================================================
    // Get all availability for dentist
    // =====================================================

    public List<DentistAvailability> getAvailabilityByDentistId(
            int dentistId) {


        List<DentistAvailability> list =
                new ArrayList<>();


        String sql =
                "SELECT availability_id, dentist_id, " +
                "day_of_week, start_time, end_time " +
                "FROM dentist_availability " +
                "WHERE dentist_id=? " +
                "ORDER BY FIELD(day_of_week, " +
                "'Monday','Tuesday','Wednesday','Thursday'," +
                "'Friday','Saturday','Sunday')";


        try (
            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {


            ps.setInt(1, dentistId);


            ResultSet rs =
                    ps.executeQuery();


            while (rs.next()) {


                DentistAvailability availability =
                        new DentistAvailability();


                availability.setAvailabilityId(
                        rs.getInt("availability_id")
                );


                availability.setDentistId(
                        rs.getInt("dentist_id")
                );


                availability.setDayOfWeek(
                        rs.getString("day_of_week")
                );


                availability.setStartTime(
                        rs.getTime("start_time")
                );


                availability.setEndTime(
                        rs.getTime("end_time")
                );


                list.add(availability);
            }


        }
        catch (SQLException e) {

            e.printStackTrace();

        }


        return list;
    }



    // =====================================================
    // Get availability by ID
    // =====================================================

    public DentistAvailability getAvailabilityById(
            int availabilityId) {


        DentistAvailability availability = null;


        String sql =
                "SELECT availability_id, dentist_id, " +
                "day_of_week, start_time, end_time " +
                "FROM dentist_availability " +
                "WHERE availability_id=?";


        try (
            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {


            ps.setInt(1, availabilityId);


            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {


                availability =
                        new DentistAvailability();


                availability.setAvailabilityId(
                        rs.getInt("availability_id")
                );


                availability.setDentistId(
                        rs.getInt("dentist_id")
                );


                availability.setDayOfWeek(
                        rs.getString("day_of_week")
                );


                availability.setStartTime(
                        rs.getTime("start_time")
                );


                availability.setEndTime(
                        rs.getTime("end_time")
                );

            }


        }
        catch (SQLException e) {

            e.printStackTrace();

        }


        return availability;
    }



    // =====================================================
    // Check whether dentist already has availability
    // for a particular day
    // =====================================================

    public boolean availabilityExists(
            int dentistId,
            String dayOfWeek) {


        boolean exists = false;


        String sql =
                "SELECT availability_id " +
                "FROM dentist_availability " +
                "WHERE dentist_id=? " +
                "AND day_of_week=?";


        try (
            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {


            ps.setInt(1, dentistId);

            ps.setString(2, dayOfWeek);


            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {

                exists = true;

            }


        }
        catch (SQLException e) {

            e.printStackTrace();

        }


        return exists;
    }



    // =====================================================
    // Add availability
    // =====================================================

    public boolean addAvailability(
            DentistAvailability availability) {


        boolean result = false;


        String sql =
                "INSERT INTO dentist_availability " +
                "(dentist_id, day_of_week, start_time, end_time) " +
                "VALUES (?, ?, ?, ?)";


        try (
            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {


            ps.setInt(
                    1,
                    availability.getDentistId()
            );


            ps.setString(
                    2,
                    availability.getDayOfWeek()
            );


            ps.setTime(
                    3,
                    availability.getStartTime()
            );


            ps.setTime(
                    4,
                    availability.getEndTime()
            );


            int rows =
                    ps.executeUpdate();


            if (rows > 0) {

                result = true;

            }


        }
        catch (SQLException e) {

            e.printStackTrace();

        }


        return result;
    }



    // =====================================================
    // Update availability
    // =====================================================

    public boolean updateAvailability(
            DentistAvailability availability) {


        boolean result = false;


        String sql =
                "UPDATE dentist_availability " +
                "SET day_of_week=?, " +
                "start_time=?, " +
                "end_time=? " +
                "WHERE availability_id=?";


        try (
            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {


            ps.setString(
                    1,
                    availability.getDayOfWeek()
            );


            ps.setTime(
                    2,
                    availability.getStartTime()
            );


            ps.setTime(
                    3,
                    availability.getEndTime()
            );


            ps.setInt(
                    4,
                    availability.getAvailabilityId()
            );


            int rows =
                    ps.executeUpdate();


            if (rows > 0) {

                result = true;

            }


        }
        catch (SQLException e) {

            e.printStackTrace();

        }


        return result;
    }



    // =====================================================
    // Delete availability
    // =====================================================

    public boolean deleteAvailability(
            int availabilityId) {


        boolean result = false;


        String sql =
                "DELETE FROM dentist_availability " +
                "WHERE availability_id=?";


        try (
            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql)
        ) {


            ps.setInt(1, availabilityId);


            int rows =
                    ps.executeUpdate();


            if (rows > 0) {

                result = true;

            }


        }
        catch (SQLException e) {

            e.printStackTrace();

        }


        return result;
    }

}