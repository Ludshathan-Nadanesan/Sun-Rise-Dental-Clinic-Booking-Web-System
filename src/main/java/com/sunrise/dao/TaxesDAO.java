package com.sunrise.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sunrise.config.DBConnection;
import com.sunrise.model.Tax;

public class TaxesDAO {

    public List<Tax> searchTaxes(String keyword, String sortBy) {
        List<Tax> taxes = new ArrayList<>();
        String orderBy = "tax_id DESC";

        if (sortBy != null) {
            switch(sortBy){
                case "name_asc":
                    orderBy = "tax_name ASC";
                    break;
                case "name_desc":
                    orderBy = "tax_name DESC";
                    break;
                case "oldest":
                    orderBy = "tax_id ASC";
                    break;
                case "newest":
                default:
                    orderBy = "tax_id DESC";
                    break;
            }
        }

        String sql =
            "SELECT tax_id, tax_name, tax_percantage, last_updated_at " +
            "FROM taxes " +
            "WHERE tax_name LIKE ? " +
            "ORDER BY " + orderBy;

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            String search = "%" + keyword + "%";
            ps.setString(1, search);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Tax tax = new Tax();
                tax.setTaxId(rs.getInt("tax_id"));
                tax.setTaxName(rs.getString("tax_name"));
                tax.setTaxPercentage(rs.getDouble("tax_percantage"));
                tax.setLastUpdatedAt(rs.getTimestamp("last_updated_at"));
                taxes.add(tax);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return taxes;
    }

    public boolean addTax(Tax tax){
        boolean result = false;
        String sql = "INSERT INTO taxes (tax_name, tax_percantage) VALUES (?, ?)";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, tax.getTaxName());
            ps.setDouble(2, tax.getTaxPercentage());

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

    public Tax getTaxById(int taxId){
        Tax tax = null;
        String sql = "SELECT tax_id, tax_name, tax_percantage, last_updated_at FROM taxes WHERE tax_id=?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, taxId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                tax = new Tax();
                tax.setTaxId(rs.getInt("tax_id"));
                tax.setTaxName(rs.getString("tax_name"));
                tax.setTaxPercentage(rs.getDouble("tax_percantage"));
                tax.setLastUpdatedAt(rs.getTimestamp("last_updated_at"));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return tax;
    }

    public boolean updateTax(Tax tax){
        boolean result = false;
        String sql = "UPDATE taxes SET tax_name=?, tax_percantage=? WHERE tax_id=?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, tax.getTaxName());
            ps.setDouble(2, tax.getTaxPercentage());
            ps.setInt(3, tax.getTaxId());

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

    public boolean deleteTaxById(int taxId){
        boolean result = false;
        String sql = "DELETE FROM taxes WHERE tax_id=?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, taxId);
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
}
