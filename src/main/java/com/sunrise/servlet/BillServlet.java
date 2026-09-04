package com.sunrise.servlet;

import com.sunrise.dao.BillDAO;
import com.sunrise.model.Bill;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/BillServlet")
public class BillServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    private BillDAO billDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        billDAO = new BillDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            if ("searchPatients".equals(action)) {
                String query = request.getParameter("query");
                List<Map<String, Object>> patients = billDAO.searchPatients(query != null ? query : "");
                
                JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                for (Map<String, Object> p : patients) {
                    JsonObjectBuilder builder = Json.createObjectBuilder()
                            .add("patient_id", (Integer) p.get("patient_id"));
                            
                    if (p.get("full_name") != null) {
                        builder.add("full_name", (String) p.get("full_name"));
                    } else {
                        builder.addNull("full_name");
                    }
                    
                    if (p.get("phone") != null) {
                        builder.add("phone", (String) p.get("phone"));
                    } else {
                        builder.addNull("phone");
                    }
                    
                    if (p.get("email") != null) {
                        builder.add("email", (String) p.get("email"));
                    } else {
                        builder.addNull("email");
                    }
                    
                    if (p.get("gender") != null) {
                        builder.add("gender", (String) p.get("gender"));
                    } else {
                        builder.addNull("gender");
                    }
                    
                    if (p.get("dob") != null) {
                        builder.add("dob", p.get("dob").toString());
                    } else {
                        builder.addNull("dob");
                    }
                    
                    if (p.get("address") != null) {
                        builder.add("address", (String) p.get("address"));
                    } else {
                        builder.addNull("address");
                    }
                    
                    if (p.get("registered_at") != null) {
                        builder.add("registered_at", p.get("registered_at").toString());
                    } else {
                        builder.addNull("registered_at");
                    }
                    
                    jsonArrayBuilder.add(builder);
                }
                out.print(jsonArrayBuilder.build().toString());

            } else if ("getPendingAppointments".equals(action)) {
                int patientId = Integer.parseInt(request.getParameter("patientId"));
                List<Map<String, Object>> apps = billDAO.getPendingAppointments(patientId);
                
                JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                for (Map<String, Object> a : apps) {
                    jsonArrayBuilder.add(Json.createObjectBuilder()
                            .add("appointment_id", (Integer) a.get("appointment_id"))
                            .add("treatment_name", (String) a.get("treatment_name"))
                            .add("default_fee", (Double) a.get("default_fee"))
                            .add("perfomed_at", a.get("perfomed_at").toString()));
                }
                out.print(jsonArrayBuilder.build().toString());

            } else if ("getTaxes".equals(action)) {
                List<Map<String, Object>> taxes = billDAO.getTaxes();
                
                JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                for (Map<String, Object> t : taxes) {
                    jsonArrayBuilder.add(Json.createObjectBuilder()
                            .add("tax_id", (Integer) t.get("tax_id"))
                            .add("tax_name", (String) t.get("tax_name") != null ? (String) t.get("tax_name") : "")
                            .add("tax_percantage", (Double) t.get("tax_percantage")));
                }
                out.print(jsonArrayBuilder.build().toString());
                
            } else if ("getBills".equals(action)) {
                int patientId = Integer.parseInt(request.getParameter("patientId"));
                List<Bill> bills = billDAO.getBillsByPatient(patientId);
                
                JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                for (Bill b : bills) {
                    jsonArrayBuilder.add(Json.createObjectBuilder()
                            .add("bill_id", b.getBillId())
                            .add("sub_total", b.getSubTotal())
                            .add("tax_pay", b.getTaxPay())
                            .add("total_ammount", b.getTotalAmmount())
                            .add("paid_ammount", b.getPaidAmmount())
                            .add("balance_ammount", b.getBalanceAmmount())
                            .add("payment_status", b.getPaymentStatus())
                            .add("created_at", b.getCreatedAt().toString()));
                }
                out.print(jsonArrayBuilder.build().toString());
                
            } else if ("getBillDetails".equals(action)) {
                int billId = Integer.parseInt(request.getParameter("billId"));
                Map<String, Object> billDetails = billDAO.getBillDetails(billId);
                
                if (billDetails.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(Json.createObjectBuilder().add("error", "Bill not found").build().toString());
                    return;
                }
                
                JsonObjectBuilder builder = Json.createObjectBuilder()
                        .add("bill_id", (Integer) billDetails.get("bill_id"))
                        .add("patient_id", (Integer) billDetails.get("patient_id"))
                        .add("patient_name", (String) billDetails.get("patient_name"))
                        .add("patient_phone", (String) billDetails.get("patient_phone") != null ? (String) billDetails.get("patient_phone") : "")
                        .add("patient_email", (String) billDetails.get("patient_email") != null ? (String) billDetails.get("patient_email") : "")
                        .add("patient_address", (String) billDetails.get("patient_address") != null ? (String) billDetails.get("patient_address") : "")
                        .add("sub_total", (Double) billDetails.get("sub_total"))
                        .add("tax_pay", (Double) billDetails.get("tax_pay"))
                        .add("total_amount", (Double) billDetails.get("total_amount"))
                        .add("paid_amount", (Double) billDetails.get("paid_amount"))
                        .add("balance_amount", (Double) billDetails.get("balance_amount"))
                        .add("payment_status", (String) billDetails.get("payment_status"))
                        .add("created_at", billDetails.get("created_at").toString());
                        
                JsonArrayBuilder itemsBuilder = Json.createArrayBuilder();
                List<Map<String, Object>> items = (List<Map<String, Object>>) billDetails.get("items");
                for (Map<String, Object> item : items) {
                    itemsBuilder.add(Json.createObjectBuilder()
                            .add("treatment_name", (String) item.get("treatment_name"))
                            .add("charged_amount", (Double) item.get("charged_amount"))
                            .add("perfomed_at", item.get("perfomed_at").toString())
                    );
                }
                builder.add("items", itemsBuilder);
                
                out.print(builder.build().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(Json.createObjectBuilder().add("error", e.getMessage()).build().toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try (JsonReader jsonReader = Json.createReader(request.getReader())) {
            JsonObject jsonObject = jsonReader.readObject();

            if (jsonObject.containsKey("action") && "settleBalance".equals(jsonObject.getString("action"))) {
                int billId = jsonObject.getInt("billId");
                double paymentAmount = jsonObject.getJsonNumber("paymentAmount").doubleValue();
                
                boolean successResult = billDAO.settleBillBalance(billId, paymentAmount);
                if (successResult) {
                    out.print(Json.createObjectBuilder().add("success", true).build().toString());
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(Json.createObjectBuilder().add("success", false).add("error", "Failed to update balance").build().toString());
                }
            } else if (jsonObject.containsKey("action") && "deleteBill".equals(jsonObject.getString("action"))) {
                int billId = jsonObject.getInt("billId");
                boolean deleted = billDAO.deleteBill(billId);
                if (deleted) {
                    out.print(Json.createObjectBuilder().add("success", true).build().toString());
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print(Json.createObjectBuilder().add("success", false).add("error", "Bill not found or could not be deleted").build().toString());
                }
            } else {
                Bill bill = new Bill();
                bill.setPatientId(jsonObject.getInt("patientId"));
                bill.setSubTotal(jsonObject.getJsonNumber("subTotal").doubleValue());
                bill.setTaxPay(jsonObject.getJsonNumber("taxPay").doubleValue());
                bill.setTotalAmmount(jsonObject.getJsonNumber("totalAmount").doubleValue());
                bill.setPaidAmmount(jsonObject.getJsonNumber("paidAmount").doubleValue());
                bill.setBalanceAmmount(jsonObject.getJsonNumber("balanceAmount").doubleValue());
                bill.setPaymentStatus("pending");

                JsonArray appIdsJson = jsonObject.getJsonArray("appointmentIds");
                List<Integer> appointmentIds = new ArrayList<>();
                for (JsonValue val : appIdsJson) {
                    appointmentIds.add(Integer.parseInt(val.toString()));
                }

                int newBillId = billDAO.createBill(bill, appointmentIds);
                
                JsonObject success = Json.createObjectBuilder()
                        .add("success", true)
                        .add("bill_id", newBillId)
                        .build();
                out.print(success.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(Json.createObjectBuilder().add("error", e.getMessage()).build().toString());
        }
    }
}
