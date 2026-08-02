package com.sunrise.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


import com.sunrise.config.DBConnection;
import com.sunrise.model.Dentist;
import com.sunrise.model.Treatments;

public class TreatmentDAO {
	
	public List<Treatments> searchTreatments(String keyword, String sortBy) {
		
		List<Treatments> treatments = new ArrayList<>();
		
		String orderBy = "treatment_id DESC";
		
		if (sortBy != null) {
			
			switch(sortBy){


            case "name_asc":

                orderBy = "treatment_name ASC";

                break;



            case "name_desc":

                orderBy = "treatment_name DESC";

                break;



            case "oldest":

                orderBy = "treatment_id ASC";

                break;

            default:

                orderBy = "treatment_id DESC";

                break;


			}
		}
		
		String sql =
			    "SELECT treatment_id, treatment_name, description, estimated_duration, default_fee " +
			    "FROM treatments " +
			    "WHERE treatment_name LIKE ? " +
			    "ORDER BY " + orderBy;
		
		try(Connection con = DBConnection.getConnection();

	            PreparedStatement ps = con.prepareStatement(sql)){


	            String search = "%" + keyword + "%";


	            ps.setString(1, search);


	            ResultSet rs = ps.executeQuery();


	            while(rs.next()){


	                Treatments treatment = new Treatments();


	                treatment.setTreatmentID(rs.getInt("treatment_id"));


	                treatment.setTreatmentName(rs.getString("treatment_name"));



	                treatment.setDescription(rs.getString("description"));



	                treatment.setEstimatedDuration(rs.getInt("estimated_duration"));



	                treatment.setDefaultFee(rs.getDouble("default_fee"));



	                treatments.add(treatment);


	            }

	        }
	        catch(SQLException e){

	            e.printStackTrace();

	        }

	        return treatments;
		
		
	}

	public boolean treatmentExist(String name) {
		boolean exists = false;
		
		String sql = "SELECT treatment_id FROM treatments WHERE treatment_name=?";
		
		try (
			Connection con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(sql)
			) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				exists = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}
	
	public boolean addTreatment(Treatments treatment){
		
		
		System.out.println("Name: " + treatment.getTreatmentName());
		System.out.println("Description: " + treatment.getDescription());
		System.out.println("Duration: " + treatment.getEstimatedDuration());
		System.out.println("Fee: " + treatment.getDefaultFee());

        boolean result = false;

        String sql =
        "INSERT INTO treatments "
        +
        "(treatment_name, description, estimated_duration, default_fee) "
        +
        "VALUES (?, ?, ?, ?)";

        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, treatment.getTreatmentName());

            ps.setString(2, treatment.getDescription());

            ps.setInt(3, treatment.getEstimatedDuration());

            ps.setDouble(4, treatment.getDefaultFee());

            int rows = ps.executeUpdate();
            
            System.out.println("Rows inserted = " + rows);

            if(rows > 0){

                result = true;

            }
        }
        catch(SQLException e){
        	System.out.println("SQL State : " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message   : " + e.getMessage());
            e.printStackTrace();
        }
        
        return result;

    }
	
	public Treatments getTreatmentById(int treatmentId){


        Treatments treatment = null;



        String sql =

        "SELECT treatment_id, treatment_name, description, estimated_duration, default_fee "

        +

        "FROM treatments "

        +

        "WHERE treatment_id=?";






        try(Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql)){





            ps.setInt(1, treatmentId);



            ResultSet rs = ps.executeQuery();





            if(rs.next()){


                treatment = new Treatments();

                treatment.setTreatmentID(rs.getInt("treatment_id"));

                treatment.setTreatmentName(rs.getString("treatment_name"));

                treatment.setDescription(rs.getString("description"));
                
                treatment.setEstimatedDuration(rs.getInt("estimated_duration"));
                
                treatment.setDefaultFee(rs.getDouble("default_fee"));


            }


        }
        catch(SQLException e){

            e.printStackTrace();

        }

        return treatment;


    }
	
	public boolean updateTreatment(Treatments treatment){

	    boolean result = false;

	    String sql =
	            "UPDATE treatments " +
	            "SET treatment_name=?, " +
	            "description=?, " +
	            "estimated_duration=?, " +
	            "default_fee=? " +
	            "WHERE treatment_id=?";

	    try(Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql)){

	        ps.setString(1, treatment.getTreatmentName());

	        ps.setString(2, treatment.getDescription());

	        ps.setInt(3, treatment.getEstimatedDuration());

	        ps.setDouble(4, treatment.getDefaultFee());

	        ps.setInt(5, treatment.getTreatmentID());

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
	
	public boolean deleteTreatmentById(int treatmentId){

	    boolean result = false;

	    String sql =
	            "DELETE FROM treatments WHERE treatment_id=?";

	    try(Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql)){

	        ps.setInt(1, treatmentId);

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
	
	public boolean treatmentExists(String name, int treatmentId){

	    boolean exists = false;

	    String sql =
	            "SELECT treatment_id " +
	            "FROM treatments " +
	            "WHERE treatment_name=? " +
	            "AND treatment_id<>?";

	    try(Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql)){

	        ps.setString(1, name);

	        ps.setInt(2, treatmentId);

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
	
}
