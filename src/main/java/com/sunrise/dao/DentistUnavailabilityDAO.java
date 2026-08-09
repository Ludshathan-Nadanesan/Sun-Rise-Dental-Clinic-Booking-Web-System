package com.sunrise.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sunrise.config.DBConnection;
import com.sunrise.model.DentistUnavailability;


public class DentistUnavailabilityDAO {


    // =====================================================
    // Get all unavailability records by dentist
    // =====================================================

    public List<DentistUnavailability> getByDentistId(int dentistId) {

        List<DentistUnavailability> list =
                new ArrayList<>();


        String sql =
                "SELECT unavailability_id, dentist_id, " +
                "start_datetime, end_datetime, reason " +
                "FROM dentist_unavailability " +
                "WHERE dentist_id=? " +
                "ORDER BY start_datetime DESC";


        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, dentistId);

            ResultSet rs = ps.executeQuery();


            while (rs.next()) {

                DentistUnavailability du =
                        new DentistUnavailability();


                du.setUnavailabilityId(
                        rs.getInt("unavailability_id")
                );


                du.setDentistId(
                        rs.getInt("dentist_id")
                );


                Timestamp start =
                        rs.getTimestamp("start_datetime");

                Timestamp end =
                        rs.getTimestamp("end_datetime");


                if (start != null) {

                    du.setStartDatetime(
                            start.toLocalDateTime()
                    );

                }


                if (end != null) {

                    du.setEndDatetime(
                            end.toLocalDateTime()
                    );

                }


                du.setReason(
                        rs.getString("reason")
                );


                list.add(du);

            }


        }
        catch (SQLException e) {

            e.printStackTrace();

        }


        return list;

    }



    // =====================================================
    // Get unavailability by ID
    // =====================================================

    public DentistUnavailability getById(int unavailabilityId) {

        DentistUnavailability du = null;


        String sql =
                "SELECT unavailability_id, dentist_id, " +
                "start_datetime, end_datetime, reason " +
                "FROM dentist_unavailability " +
                "WHERE unavailability_id=?";


        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, unavailabilityId);


            ResultSet rs = ps.executeQuery();


            if (rs.next()) {

                du = new DentistUnavailability();


                du.setUnavailabilityId(
                        rs.getInt("unavailability_id")
                );


                du.setDentistId(
                        rs.getInt("dentist_id")
                );


                Timestamp start =
                        rs.getTimestamp("start_datetime");

                Timestamp end =
                        rs.getTimestamp("end_datetime");


                if (start != null) {

                    du.setStartDatetime(
                            start.toLocalDateTime()
                    );

                }


                if (end != null) {

                    du.setEndDatetime(
                            end.toLocalDateTime()
                    );

                }


                du.setReason(
                        rs.getString("reason")
                );

            }


        }
        catch (SQLException e) {

            e.printStackTrace();

        }


        return du;

    }



    // =====================================================
    // Add Unavailability
    // =====================================================

    public boolean addUnavailability(
            DentistUnavailability du) {


        boolean result = false;


        String sql =
                "INSERT INTO dentist_unavailability " +
                "(dentist_id, start_datetime, end_datetime, reason) " +
                "VALUES (?, ?, ?, ?)";


        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {


            ps.setInt(
                    1,
                    du.getDentistId()
            );


            ps.setTimestamp(
                    2,
                    Timestamp.valueOf(
                            du.getStartDatetime()
                    )
            );


            ps.setTimestamp(
                    3,
                    Timestamp.valueOf(
                            du.getEndDatetime()
                    )
            );


            ps.setString(
                    4,
                    du.getReason()
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
    // Update Unavailability
    // =====================================================

    public boolean updateUnavailability(
            DentistUnavailability du) {


        boolean result = false;


        String sql =
                "UPDATE dentist_unavailability SET " +
                "start_datetime=?, " +
                "end_datetime=?, " +
                "reason=? " +
                "WHERE unavailability_id=?";


        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {


            ps.setTimestamp(
                    1,
                    Timestamp.valueOf(
                            du.getStartDatetime()
                    )
            );


            ps.setTimestamp(
                    2,
                    Timestamp.valueOf(
                            du.getEndDatetime()
                    )
            );


            ps.setString(
                    3,
                    du.getReason()
            );


            ps.setInt(
                    4,
                    du.getUnavailabilityId()
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
    // Delete Unavailability
    // =====================================================

    public boolean deleteUnavailability(
            int unavailabilityId) {


        boolean result = false;


        String sql =
                "DELETE FROM dentist_unavailability " +
                "WHERE unavailability_id=?";


        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {


            ps.setInt(1, unavailabilityId);


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