package com.sunrise.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.sunrise.config.DBConnection;
import com.sunrise.model.Patient;


public class PatientDAO {


    // =====================================================
    // Search + Sort Patients
    // =====================================================

    public List<Patient> searchPatients(
            String keyword,
            String sortBy) {


        List<Patient> patients =
                new ArrayList<>();


        String orderBy =
                "registered_at DESC";


        // =========================
        // Sorting
        // =========================

        if (sortBy != null) {

            switch (sortBy) {

                case "name_asc":

                    orderBy =
                            "full_name ASC";

                    break;


                case "name_desc":

                    orderBy =
                            "full_name DESC";

                    break;


                case "oldest":

                    orderBy =
                            "registered_at ASC";

                    break;


                case "newest":

                    orderBy =
                            "registered_at DESC";

                    break;


                default:

                    orderBy =
                            "registered_at DESC";

                    break;
            }
        }


        // =========================
        // SQL
        // =========================

        String sql =

                "SELECT patient_id, full_name, email, phone, " +
                "gender, dob, address, registered_at " +

                "FROM patients " +

                "WHERE full_name LIKE ? " +
                "OR email LIKE ? " +
                "OR CAST(phone AS CHAR) LIKE ? " +
                "OR gender LIKE ? " +
                "OR address LIKE ? " +

                "ORDER BY " + orderBy;


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {


            String search =
                    "%" + keyword + "%";


            ps.setString(1, search);

            ps.setString(2, search);

            ps.setString(3, search);

            ps.setString(4, search);

            ps.setString(5, search);


            ResultSet rs =
                    ps.executeQuery();


            while (rs.next()) {


                Patient patient =
                        new Patient();


                patient.setPatientId(
                        rs.getInt("patient_id")
                );


                patient.setFullName(
                        rs.getString("full_name")
                );


                patient.setEmail(
                        rs.getString("email")
                );


                patient.setPhone(
                        rs.getInt("phone")
                );


                patient.setGender(
                        rs.getString("gender")
                );


                patient.setDob(
                        rs.getDate("dob")
                );


                patient.setAddress(
                        rs.getString("address")
                );


                patient.setRegisteredAt(
                        rs.getTimestamp("registered_at")
                );


                patients.add(patient);
            }


        }
        catch (SQLException e) {

            e.printStackTrace();

        }


        return patients;
    }


    // =====================================================
    // Get Patient By ID
    // =====================================================

    public Patient getPatientById(int patientId) {


        Patient patient = null;


        String sql =

                "SELECT patient_id, full_name, email, phone, " +
                "gender, dob, address, registered_at " +

                "FROM patients " +

                "WHERE patient_id=?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {


            ps.setInt(1, patientId);


            ResultSet rs =
                    ps.executeQuery();


            if (rs.next()) {


                patient =
                        new Patient();


                patient.setPatientId(
                        rs.getInt("patient_id")
                );


                patient.setFullName(
                        rs.getString("full_name")
                );


                patient.setEmail(
                        rs.getString("email")
                );


                patient.setPhone(
                        rs.getInt("phone")
                );


                patient.setGender(
                        rs.getString("gender")
                );


                patient.setDob(
                        rs.getDate("dob")
                );


                patient.setAddress(
                        rs.getString("address")
                );


                patient.setRegisteredAt(
                        rs.getTimestamp("registered_at")
                );
            }


        }
        catch (SQLException e) {

            e.printStackTrace();

        }


        return patient;
    }


    // =====================================================
    // Add Patient
    // =====================================================

    public boolean addPatient(Patient patient) {


        boolean result = false;


        String sql =

                "INSERT INTO patients " +

                "(full_name, email, phone, gender, dob, address) " +

                "VALUES (?, ?, ?, ?, ?, ?)";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {


            ps.setString(
                    1,
                    patient.getFullName()
            );


            ps.setString(
                    2,
                    patient.getEmail()
            );


            ps.setInt(
                    3,
                    patient.getPhone()
            );


            ps.setString(
                    4,
                    patient.getGender()
            );


            ps.setDate(
                    5,
                    patient.getDob()
            );


            ps.setString(
                    6,
                    patient.getAddress()
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
    // Update Patient
    // =====================================================

    public boolean updatePatient(Patient patient) {


        boolean result = false;


        String sql =

                "UPDATE patients SET " +

                "full_name=?, " +
                "email=?, " +
                "phone=?, " +
                "gender=?, " +
                "dob=?, " +
                "address=? " +

                "WHERE patient_id=?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {


            ps.setString(
                    1,
                    patient.getFullName()
            );


            ps.setString(
                    2,
                    patient.getEmail()
            );


            ps.setInt(
                    3,
                    patient.getPhone()
            );


            ps.setString(
                    4,
                    patient.getGender()
            );


            ps.setDate(
                    5,
                    patient.getDob()
            );


            ps.setString(
                    6,
                    patient.getAddress()
            );


            ps.setInt(
                    7,
                    patient.getPatientId()
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
    // Delete Patient
    // =====================================================

    public boolean deletePatientById(int patientId) {


        boolean result = false;


        String sql =

                "DELETE FROM patients " +

                "WHERE patient_id=?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {


            ps.setInt(
                    1,
                    patientId
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
    // Email Duplicate Check
    // =====================================================

    public boolean emailExists(String email) {


        boolean exists = false;


        String sql =

                "SELECT patient_id " +
                "FROM patients " +
                "WHERE email=?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {


            ps.setString(
                    1,
                    email
            );


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
    // Phone Duplicate Check
    // =====================================================

    public boolean phoneExists(int phone) {


        boolean exists = false;


        String sql =

                "SELECT patient_id " +
                "FROM patients " +
                "WHERE phone=?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {


            ps.setInt(
                    1,
                    phone
            );


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
    // Email Duplicate Check While Editing
    // =====================================================

    public boolean emailExistsExceptCurrent(
            String email,
            int patientId) {


        boolean exists = false;


        String sql =

                "SELECT patient_id " +
                "FROM patients " +
                "WHERE email=? " +
                "AND patient_id<>?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {


            ps.setString(
                    1,
                    email
            );


            ps.setInt(
                    2,
                    patientId
            );


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
    // Phone Duplicate Check While Editing
    // =====================================================

    public boolean phoneExistsExceptCurrent(
            int phone,
            int patientId) {


        boolean exists = false;


        String sql =

                "SELECT patient_id " +
                "FROM patients " +
                "WHERE phone=? " +
                "AND patient_id<>?";


        try (
                Connection con =
                        DBConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
        ) {


            ps.setInt(
                    1,
                    phone
            );


            ps.setInt(
                    2,
                    patientId
            );


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

}