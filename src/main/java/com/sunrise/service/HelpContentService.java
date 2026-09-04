package com.sunrise.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sunrise.model.HelpTopic;

public class HelpContentService {

    public static List<HelpTopic> getHelpTopics(String rootPath, String role) {
        List<HelpTopic> topics = new ArrayList<>();
        File roleDir = new File(rootPath, "How to use" + File.separator + role);

        if (roleDir.exists() && roleDir.isDirectory()) {
            File[] subDirs = roleDir.listFiles(File::isDirectory);
            if (subDirs != null) {
                for (File dir : subDirs) {
                    String folderName = dir.getName();
                    String title = capitalizeTitle(folderName);
                    String description = getDescriptiveText(role, folderName);
                    
                    List<String> imageUrls = new ArrayList<>();
                    File[] images = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));
                    
                    if (images != null) {
                        // Sort images by number (e.g. 1.jpg, 2.jpg)
                        Arrays.sort(images, (f1, f2) -> {
                            String n1 = f1.getName().replaceAll("[^0-9]", "");
                            String n2 = f2.getName().replaceAll("[^0-9]", "");
                            int val1 = n1.isEmpty() ? 0 : Integer.parseInt(n1);
                            int val2 = n2.isEmpty() ? 0 : Integer.parseInt(n2);
                            return Integer.compare(val1, val2);
                        });
                        
                        for (File img : images) {
                            // Convert to web path
                            imageUrls.add("How to use/" + role + "/" + folderName + "/" + img.getName());
                        }
                    }
                    
                    String id = folderName.replaceAll("\\s+", "-").toLowerCase();
                    topics.add(new HelpTopic(id, title, description, imageUrls));
                }
            }
        }
        return topics;
    }

    private static String capitalizeTitle(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    private static String getDescriptiveText(String role, String folderName) {
        folderName = folderName.toLowerCase();
        
        // Admin descriptions
        if (role.equalsIgnoreCase("Admin")) {
            if (folderName.contains("dentist working periods")) {
                return "Follow these steps to configure the working days and times for a specific dentist.";
            } else if (folderName.contains("add a tax")) {
                return "Learn how to add new tax rules that will automatically apply to patient bills.";
            } else if (folderName.contains("add a treatment")) {
                return "Step-by-step guide to adding a new treatment service to the clinic's catalog.";
            } else if (folderName.contains("unavailabilty")) {
                return "Set up leave or unavailability dates for a dentist so appointments cannot be booked on those days.";
            } else if (folderName.contains("add dentist")) {
                return "Learn how to register a new dentist into the system with their contact and login details.";
            } else if (folderName.contains("add receptionist")) {
                return "Learn how to register a new receptionist to allow them to manage appointments.";
            } else if (folderName.contains("assign a treatment to the dentist")) {
                return "Link a treatment to a specific dentist and configure their commission percentage for it.";
            } else if (folderName.contains("update dentist details")) {
                return "Steps to modify a dentist's profile, including their status and password.";
            } else if (folderName.contains("update receptionist")) {
                return "Steps to modify a receptionist's profile, including their status and password.";
            } else if (folderName.contains("view reports")) {
                return "Explore the Analytics Dashboard to view financial metrics, charts, and dentist payouts.";
            }
        } 
        // Receptionist descriptions
        else if (role.equalsIgnoreCase("Receptionist")) {
            if (folderName.contains("bill a patient")) {
                return "Learn how to generate an invoice for a completed appointment and process the payment.";
            } else if (folderName.contains("book a appointment")) {
                return "Step-by-step guide on how to search for available dentists and book a new appointment for a patient.";
            } else if (folderName.contains("register a patient")) {
                return "Register a new walk-in or calling patient into the system to allow booking appointments.";
            }
        }
        
        return "Follow the highlighted steps in the screenshots below to complete this action.";
    }
}
