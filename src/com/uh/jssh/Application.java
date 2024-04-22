package com.uh.jssh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.uh.jssh.service.JSS_Service;
import com.uh.jssh.util.BookingManager;
import com.uh.jssh.util.Learner;
import com.uh.jssh.util.Learner.LearnerDetails;
import com.uh.jssh.util.Timetable;

public class Application {

	 private static List<List<String>> updatedLists;
	private static List<String> timeTableList; // Each string could be "ID,Grade,Date,Time,Coach,Day"
	    private static List<String> bookingDetailsList; // Each string could be "BookingID,LearnerID,TimeTableID,Status"
	    private static HashMap<String, LearnerDetails> learnerInfo; // Key: LearnerID, Value: "Name,Age,Gender,Contact"

	    public static void main(String[] args) {
	        initializeData();
	        JSS_Service jss_Service = new JSS_Service();
	        Learner learner = new Learner();
	        Scanner scanner = new Scanner(System.in);
	        try {
	            int option;
	            do {
	                showMainMenu();
	                option = scanner.nextInt();
	                scanner.nextLine(); // Consume newline
	                
	                switch (option) {
	                    case 1:
//	                    	JSS_Service jss_Service = new JSS_Service();
	                    	System.out.println("Enter you Learner ID: (Sample input: cust01,cust02,cust03....cust16");
	                		String learnerID = scanner.next();
	                		boolean isLearnerValid = learner.validateLearner(learnerID,learnerInfo);
	                		while(!isLearnerValid) {
	                			System.out.println("You have entered wrong learnerID\n"
	                					+ "Enter Valid Learner ID:");
	                			learnerID  = scanner.next();
	                			isLearnerValid = learner.validateLearner(learnerID,learnerInfo);
	                		}
	                    	updatedLists = jss_Service.bookASession(timeTableList, bookingDetailsList, learnerInfo,learnerID);
	                        if(updatedLists==null) {
	                        	break;
	                        }
	                    	// Book a swim lesson
	                    	timeTableList=updatedLists.get(0);
	                    	bookingDetailsList = updatedLists.get(1);
	                        break;
	                    case 2:
	                        // Change a lesson
	                    	
	                    	updatedLists = jss_Service.changeBooking(timeTableList, bookingDetailsList, learnerInfo) ;//bookASession(timeTableList, bookingDetailsList, learnerInfo);
	                        if(updatedLists==null) {
	                        	break;
	                        }
	                    	// Book a swim lesson
	                    	timeTableList=updatedLists.get(0);
	                    	bookingDetailsList = updatedLists.get(1);
	                        
	                        break;
	                    case 3:
	                        // Change or cancel a lesson
	                    	updatedLists = jss_Service.cancelBooking(timeTableList, bookingDetailsList, learnerInfo) ;//bookASession(timeTableList, bookingDetailsList, learnerInfo);
	                        if(updatedLists==null) {
	                        	break;
	                        }
	                    	// Book a swim lesson
	                        System.out.println("cancellation successful..");
	                    	timeTableList=updatedLists.get(0);
	                    	bookingDetailsList = updatedLists.get(1);
	                        break;
	                        
	                    case 4:
	                        // Attend a lesson
	                    	bookingDetailsList = jss_Service.attendASession(bookingDetailsList, learnerInfo);
	                        break;
	                    case 5:
	                        // Add a new learner
	                    	learnerInfo = Learner.addNewLearner();
	                        break;
	                    case 6:
	                        // Generate learner report
	                    	jss_Service.generateLearnersReport(bookingDetailsList,learnerInfo);
	                        break;
	                    case 7:
	                        // Generate coach report
	                    	jss_Service.coachReport(bookingDetailsList);
	                        break;
	                    case 8:
	                        System.out.println("Exiting system...");
	                        break;
	                    default:
	                        System.out.println("Invalid option. Please try again.");
	                        break;
	                }
	            } while (option != 8);
	        } finally {
	            scanner.close();
	        }

	        System.out.println("Thank you for using The Hatfield Junior Swimming School System. Exited Successfully!!!");
	    }

	    private static void initializeData() {
	        // Initialize your lists and maps with default data or load from a source
	    	learnerInfo = Learner.getLearnerDetails();
	    	timeTableList = Timetable.getDefaultSwimmingSchedule();
	    	bookingDetailsList = BookingManager.getBookingDetails();
	    }

	    private static void showMainMenu() {
	    	  System.out.println("\n############################################");
	          System.out.println("\t\tWelcome");
	          System.out.println("The Hatfield Junior Swimming School (HJSS)");
	          System.out.println("############################################");
	          System.out.println("1 - Book a Swim Lesson");
	          System.out.println("2 - Change Lesson");
	          System.out.println("3 - Cancel a Lesson");
	          System.out.println("4 - Attend a Lesson");
	          System.out.println("5 - Add a New Learner");
	          System.out.println("6 - Learner Report");
	          System.out.println("7 - Coach's Overall Report");
	          System.out.println("8 - Exit System");
	          System.out.println("--------------------------------------------");
	          System.out.print("Choose the operation to perform: ");
	    }

	 
}
