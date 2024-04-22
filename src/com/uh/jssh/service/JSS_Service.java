package com.uh.jssh.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.uh.jssh.util.Learner;
import com.uh.jssh.util.Learner.LearnerDetails;

public class JSS_Service {
	   private Scanner scanner = new Scanner(System.in);
	    private Learner learner = new Learner();

	  // Method to book a session. Takes user and class selection parameters as input instead of reading from console
    public List<List<String>> bookASession(List<String> timetable, List<String> bookingData,Map<String, LearnerDetails> learnerInfo,String learnerID) {
        Scanner scanner = new Scanner(System.in);
        String selection="";
    	Learner learner = new Learner();
//    	System.out.println("Enter you Learner ID: (Sample input: cust01,cust02,cust03....cust16");
//		String learnerID = scanner.next();
//		boolean isLearnerValid = learner.validateLearner(learnerID,learnerInfo);
//		while(!isLearnerValid) {
//			System.out.println("You have entered wrong learnerID\n"
//					+ "Enter Valid Learner ID:");
//			learnerID  = scanner.next();
//			isLearnerValid = learner.validateLearner(learnerID,learnerInfo);
//		}
		LearnerDetails learnerDetails = learner.getLearnerData(learnerID);
		
//		learner.getLearnerData(learnerID,learnerInfo)
    	
    	
    	
    	String gradeLevel = learnerDetails.getCurrentGradeLevel().split(" ")[1] ;//split(",")[4];
        String filterCriteria = "";
        boolean validClassIdFound = false;
//        String classRecord = "";

        
        System.out.println("Choose your booking option:");
        System.out.println("1 - View by Day (e.g., Monday)");
        System.out.println("2 - View by Grade Level (e.g., Grade 1)");
        System.out.println("3 - View by Coach's Name (e.g., (Raj, Joseph, Madhu, Venu, Gopi))");
        System.out.print("Enter your option: ");
        int choice = scanner.nextInt();
        
        
        switch (choice) {
		/*
		 * case 1: // Day filterCriteria = selection.toLowerCase(); // Assuming
		 * 'selection' contains the day break; case 2: // Grade Level filterCriteria =
		 * "grade " + selection; // Assuming 'selection' contains the grade level number
		 * break; case 3: // Coach's Name filterCriteria = selection.toLowerCase(); //
		 * Assuming 'selection' contains the coach's name break;
		 */
        
        case 1: // Day
            System.out.println("Enter the day (Monday, Wednesday, Friday, Saturday.):");
            scanner.nextLine();
            selection = scanner.nextLine().toLowerCase(); // Read user input for day
            filterCriteria = selection; // Assuming 'selection' contains the day
            break;
        case 2: // Grade Level
            System.out.println("Enter the grade level (sample input: 1, 2, 3, 4, 5):");
            scanner.nextLine();
            selection = scanner.nextLine(); // Read user input for grade level
            filterCriteria = "grade " + selection; // Assuming 'selection' contains the grade level number
            break;
        case 3: // Coach's Name
            System.out.println("Enter the coach's name: (Raj, Joseph, Madhu, Venu, Gopi)");
            scanner.nextLine();
            selection = scanner.nextLine().toLowerCase(); // Read user input for coach's name
            filterCriteria = selection; // Assuming 'selection' contains the coach's name
            break;
        }
        System.out.println(" selection value "+ selection);

        List<String> filteredTimetable = filterTimetable(timetable, filterCriteria, choice);
        if (filteredTimetable.isEmpty()) {
            System.out.println("No matching classes found for your selection.");
            return  null; //Arrays.asList(timetable, bookingData); // Return original lists if no match is found
        }
        printTimetable(filteredTimetable);
        
        System.out.println("enter lesson Id you want to attend:");
int lessonId = scanner.nextInt();
String timetableRecord=null;
for (String entry : filteredTimetable) {
    String[] details = entry.split(",");
//    System.out.println("entry_-->"+entry+"--"+lessonId);
    if (details[0].equalsIgnoreCase(String.valueOf(lessonId))) {
         timetableRecord = entry; // Return the matching entry
         break;
    }
};
        // Simulating class ID selection. In a real scenario, this would be replaced with user input or selection mechanism.
//        classRecord = filteredTimetable.get(0); // Selecting the first class as an example
        if( timetableRecord!=null) {
        	validClassIdFound = true;
        }
        else {
        	System.out.println("you have entered wrong lesson Id.. Please try booking again..");
        	return null;
    	}

        if (validClassIdFound) {

            int enrollCount = Integer.parseInt(timetableRecord.split(",")[4]); // Assuming the current booking count is at index 3

            int gradeCheck = Integer.valueOf(timetableRecord.split(",")[1].split(" ")[1]) - Integer.parseInt(gradeLevel);

            for(String booking : bookingData) {
            	String []bookinginf= booking.split(",");
            	if(bookinginf[1].equalsIgnoreCase(learnerID) &&
            			bookinginf[8].equalsIgnoreCase(String.valueOf( lessonId)) &&
            			(bookinginf[5].equalsIgnoreCase("booked") || bookinginf[5].equalsIgnoreCase("attended") ) 
            			) {
                  System.out.println("You have already enrolled to this lesson...");
                  return Arrays.asList(timetable, bookingData); // Return original lists if booking constraints not met
            	}


            }
            if ( enrollCount + 1 > 4) {
                System.out.println("Booking constraints not met. Cannot proceed with booking.");
                return Arrays.asList(timetable, bookingData); // Return original lists if booking constraints not met
            }
            else if (gradeCheck > 1 ) {
                System.out.println("You are not allowed to book this grade level lesson.");
                return Arrays.asList(timetable, bookingData); // Return original lists if booking constraints not met
            }

            else {

            	JSS_Service jss_Service = new JSS_Service();
            	timetable = jss_Service.updateTimetableList(timetable, lessonId, 1);
//            	
            	
    			bookingData = jss_Service.updateBookingLists(bookingData, timetableRecord,learnerID);

            	System.out.println("Booking successful for class ID: " + timetableRecord.split(",")[0]);
             
            }
        } else {
            System.out.println("No valid class selected.");
            return Arrays.asList(timetable, bookingData); // Return original lists if no valid class ID found
        }

        // Return updated lists. Placeholder return statement; replace with actual updated lists.
        return Arrays.asList(timetable, bookingData);
    }
	
	
	  private List<String> filterTimetable(List<String> timetable, String criteria, int choice) {
	        List<String> filtered = new ArrayList<>();
	        for (String record : timetable) {

	            String[] parts = record.split(",");
	            String grade = parts[1];

	            switch (choice) {
	                case 1: // Filter by day
	                    if (parts[6].toLowerCase().equals(criteria)) { // Assuming day is at index 5
	                        filtered.add(record);
	                    }
	                    break;
	                case 2: // Filter by grade level
	                    if (parts[1].toLowerCase().contains(criteria)) { // Assuming grade level is at index 1
	                        filtered.add(record);
	                    }
	                    
	                    if (grade.equalsIgnoreCase(criteria)) { // Assuming grade level is at index 1
	                        filtered.add(record);
	                    }
	                    break;
	                case 3: // Filter by coach's name

	                        if (parts[5].toLowerCase().equalsIgnoreCase(criteria)) { // Assuming coach's name is at index 4
	        	                        filtered.add(record);
	                    }
	                    break;
	            }
	        }
	        return filtered;
	    }
	  
	  public void printTimetable(List<String> timetable) {
		    // Header for clarity in output
		    System.out.println("===================================================");
		    System.out.println("LessonID\tGrade\tDate\t\tTime\tseats\tCoach\tDay");
		    System.out.println("===================================================");
		    
		    // Loop through each entry in the list
		    for (String entry : timetable) {
		        // Assuming the structure is "ID,Grade,Date,Time,CurrentBookingCount,Coach,Day"
		        String[] details = entry.split(",");
		        System.out.printf("%s\t\t%s\t%s\t%s\t%s\t%s\t%s\n",
		                details[0], // ID
		                details[1], // Grade
		                details[2], // Date
		                details[3], // Time
		                4-Integer.valueOf(details[4]), // Current Booking Count
		                details[5], // Coach
		                details[6]); // Day
		    }
		}

	  public List<String> updateTimetableList(List<String> timetable, int classId, int additionalAttendees) {
		    List<String> updatedTimetable = new ArrayList<>();

		    for (String entry : timetable) {
		        String[] details = entry.split(",");
		        int currentClassId = Integer.parseInt(details[0]);

		        if (currentClassId == classId) {
		            // Update attendee count for the matching class ID
		            int currentAttendeeCount = Integer.parseInt(details[4]);
		            int updatedAttendeeCount = currentAttendeeCount + additionalAttendees;

		            // Reconstruct the entry with the updated attendee count
		            String updatedEntry = details[0] + "," +
		                                  details[1] + "," +
		                                  details[2] + "," +
		                                  details[3] + "," +
		                                  updatedAttendeeCount + "," +
		                                  details[5] + "," +
		                                  details[6];

		            updatedTimetable.add(updatedEntry);
		        } else {
		            // Add unmodified entry for non-matching class IDs
		            updatedTimetable.add(entry);
		        }
		    }

		    return updatedTimetable;
		}

	  private List<String> updateBookingLists(List<String> bookingData, String fitnessInfo, String learnerId) {
		    // Copy existing booking data to a new list
		    List<String> updatedBookingList = new ArrayList<>(bookingData);
		    
		    // Generate a unique ID for the new booking entry
		    int rowId = bookingData.size() + 1;

		    // Parse the fitness info; expected format: "Fit_classID,fitnessname,date,price,sunday"
		    String[] infoParts = fitnessInfo.split(",");
		    // Construct the new booking entry
		    // Expected booking entry format: "Id, custID, fitnessname, day, date, status, price, Fit_classID"
		    String newBooking = String.format("%d,%s,%s,%s,%s,%s,%s,%s,%s",
		                                      rowId,
		                                      learnerId,		// learnerid
		                                      infoParts[1], // grade
		                                      infoParts[5], // coach
		                                      infoParts[2], // date
		                                      "booked",     // status
		                                      "", // rating
		                                      "",  // review
		                                      infoParts[0]
		                                     );
		    
		    // Add the new booking entry to the list
		    updatedBookingList.add(newBooking);

		    return updatedBookingList;
		}

	  
	  
	  public String coachReport(List<String> bookingDetailsList) {
	        HashMap<String, Integer[]> coachRatings = new HashMap<>();
	        System.out.println("Enter month (range between: 1 to 12):");
	          int month = scanner.nextInt();
	          while (month < 1 || month > 12) {
	              System.out.println("Enter valid month (range between: 1 to 12):");
	              month = scanner.nextInt();
	          }
	        // Initialize coach data with coach names and default rating counts
	        String[] coachArray = {" Raj","Joseph","Venu","Madhu","Gopi"};
	        for (String coach : coachArray) {
	            coachRatings.put(coach, new Integer[]{0, 0}); // [Total Rating, Count]
	        }

	        // Process each booking detail
	        for (String bookingDetail : bookingDetailsList) {
	            String[] details = bookingDetail.split(",");
//	            System.out.println("detailsssss==>"+ bookingDetail);
	            String coach = details[3];
	            String status = details[5];
	            if(details[4].split("/")[1].equalsIgnoreCase(String.valueOf(month))) {

	            // Check if the booking was attended and matches one of the known coaches
	            if ("attended".equalsIgnoreCase(status) && coachRatings.containsKey(coach)) {
	                int rating = Integer.parseInt(details[6]);
	                Integer[] ratingData = coachRatings.get(coach);
	                ratingData[0] += rating; // Add current rating to total
	                ratingData[1]++;         // Increment count
	            }
	            }
	        }

	        // Output the report
	        System.out.println("*******Overall Coach Report*******\n");
	        for (Map.Entry<String, Integer[]> entry : coachRatings.entrySet()) {
	            Integer[] data = entry.getValue();
	            if (data[1] > 0) { // Ensure there is at least one rating to avoid division by zero
	                double avgRating = (double) data[0] / data[1];
	                System.out.println("Coach Name: " + entry.getKey() +
	                                   "\nAverage Rating: " + avgRating +
	                                   "\nTotal number of learners attended your lessons: " + data[1] + "\n");
	            }
	        }

	        return "success";
	    }
	  
	  public String generateLearnersReport(List<String> bookingDetailsList, Map<String, LearnerDetails> learnerInfo) {
	    	
	    	HashMap<String, int[]> statusCountByLearner = new HashMap<>();
	    	 System.out.println("Enter month (range between: 1 to 12):");
	          int month = scanner.nextInt();
	          while (month < 1 || month > 12) {
	              System.out.println("Enter valid month (range between: 1 to 12):");
	              month = scanner.nextInt();
	          }
	        // Initialize the learner map with default counts for attended, booked, cancelled
	        for (String learnerId : learnerInfo.keySet()) {
	            statusCountByLearner.put(learnerId, new int[] {0, 0, 0}); // [attended, booked, cancelled]
	        }

	        // Process each booking detail
	        for (String detail : bookingDetailsList) {
//	        	System.out.println("details--"+ detail);
	            String[] bookingDetails = detail.split(",");
	            String learnerId = bookingDetails[1];
	            String status = bookingDetails[5];
if(bookingDetails[4].split("/")[1].equalsIgnoreCase(String.valueOf(month))) {
	            // Update counts based on status
	            if (statusCountByLearner.containsKey(learnerId)) {
	                int index = -1;
	                switch (status.toLowerCase()) {
	                    case "attended": index = 0; break;
	                    case "booked": index = 1; break;
	                    case "cancelled": index = 2; break;
	                }

	                if (index != -1) {
	                    statusCountByLearner.get(learnerId)[index]++;
	                }
	            }
		}
	        }

	        // Output the report
	        System.out.println("***** Learner Report  *****");
	        for (Map.Entry<String, int[]> entry : statusCountByLearner.entrySet()) {
	            String learnerId = entry.getKey();
	            int[] counts = entry.getValue();
//	            System.out.println("\nlearnId --==>"+learnerId);

	            // Check if the learner has any recorded activity
	            if (counts[0] > 0 || counts[1] > 0 || counts[2] > 0) {
//	            	String learnerDetails = learnerInfo.get(learnerId);
	            	LearnerDetails learnerDetails = learnerInfo.get(learnerId);
	                System.out.println("Learner ID: " + learnerId);
	                System.out.println("Learner Name: " + learnerDetails.getName() +"\t Grade-"+ learnerDetails.getCurrentGradeLevel());
	                System.out.println("Total Attended Lessons: " + counts[0]);
	                System.out.println("Total Booked Lessons: " + counts[1]);
	                System.out.println("Total Cancelled Lessons: " + counts[2] + "\n");
	            }
	        }
	        
	        return "success";
	    }


	    
	    
	    public List<List<String>> cancelBooking(List<String> timeTableList, List<String> bookingDetailsList, HashMap<String, LearnerDetails> learnerInfo) {
	    	JSS_Service jss_Service = new JSS_Service();

	    	String learnerID = getValidLearnerId(learnerInfo);

	        List<String> eligibleBookings = printEligibleBookings(bookingDetailsList, learnerID);
	        if (eligibleBookings.isEmpty()) {
	            System.out.println("You don't have any pending sessions to cancel.");
	            return null;
	        }

	        String bookingId = getBookingIdToCancel();
	        if (!isBookingCancelable(eligibleBookings, bookingId)) {
	            System.out.println("Please choose a Booking ID which is in 'booked' status.");
	            return null;
	        }

	        List<String> timeTableMod = jss_Service.updateTimetableList(timeTableList, Integer.parseInt(bookingId), -1);
	        List<String> bookingDataMod = jss_Service.updateStatus(bookingDetailsList, bookingId, "cancelled");

	        List<List<String>> modifiedLists = new ArrayList<>();
	        modifiedLists.add(timeTableMod);
	        modifiedLists.add(bookingDataMod);
	        return modifiedLists;
	    }

	    private String getValidLearnerId(HashMap<String, LearnerDetails> learnerInfo) {
	        System.out.println("Enter your Learner ID: (Sample input: cust01, cust02...)");
	        String learnerID = scanner.next();
	        while (!learner.validateLearner(learnerID, learnerInfo)) {
	            System.out.println("You have entered an incorrect Learner ID\nEnter Valid learner ID:");
	            learnerID = scanner.next();
	        }
	        return learnerID;
	    }

	    private List<String> printEligibleBookings(List<String> bookingDetailsList, String learnerId) {
	        List<String> eligibleBookings = new ArrayList<>();
	        System.out.println("Available bookings with this customer ID: " + learnerId);
	        System.out.println("======================================================");
	        System.out.println("BookingId\t Grade \t\t Date\t\t Status");
	        System.out.println("======================================================");

	        for (String data : bookingDetailsList) {
	            String[] details = data.split(",");
	            if (details[1].equals(learnerId) && details[5].equals("booked")) {
	                System.out.println(details[0] + "\t\t" + details[2] + "\t\t" + details[4] + "\t" + details[5]);
	                eligibleBookings.add(data);
	            }
	        }
	        System.out.println("--------------------------------------------------------");
	        return eligibleBookings;
	    }

	    private String getBookingIdToCancel() {
	        System.out.println("Enter Booking ID to cancel the session:");
	        return scanner.next();
	    }

	    private boolean isBookingCancelable(List<String> eligibleBookings, String bookingId) {
	        return eligibleBookings.stream().anyMatch(b -> b.split(",")[0].equals(bookingId));
	    }
	    
	    public List<String> updateStatus(List<String> bookingDetails, String bookingId, String newStatus) {
	        List<String> updatedList = new ArrayList<>();

	        for (String booking : bookingDetails) {
	            String[] details = booking.split(",");
	            if (details[0].equals(bookingId)) {
	                // Logging the change for clarity; consider removing or changing to a logging framework in production
//	                System.out.println("Old Booking Details: " + booking);

	                // Update only the status field
	                details[5] = newStatus;

	                // Reconstruct the booking detail string with the updated status
	                String updatedBooking = String.join(",", details);

	                // Logging the updated booking details
//	                System.out.println("Updated Booking Details: " + updatedBooking);

	                updatedList.add(updatedBooking);
	            } else {
	                updatedList.add(booking);
	            }
	        }
	        return updatedList;
	    }


	    
	    public List<String> attendASession(List<String> bookingDetailsList, Map<String, LearnerDetails> learnerInfo) {
	    	
	    	String learnerID;
	    	System.out.println("Enter you learner ID: (Sample input: cust01,cust02...");
			learnerID = scanner.next();
			 Boolean isLearnerValid = learner.validateLearner(learnerID,learnerInfo);
			while(!isLearnerValid) {
				System.out.println("You have entered wrong learnerID\n"
						+ "Enter Valid Learner ID:");
				learnerID  = scanner.next();
				isLearnerValid = learner.validateLearner(learnerID,learnerInfo);
			}
	    	
	    	
	    	
	    	if(!printAvailableBookings(bookingDetailsList, learnerID)) {
	    	    System.out.println("You don't have any pending lessons attend!!!");
	            return bookingDetailsList;
	        	
	    	}
JSS_Service jss_Service = new JSS_Service();
	        String lessonId = getInput("Enter lesson Id to attend");
	        if (!hasBookedLesson(bookingDetailsList, lessonId, learnerID)) {
	            System.out.println("You don't have any booked lessons or the lesson ID is incorrect.");
	            return bookingDetailsList;
	        }

	        int rating = getValidRating();
	        System.out.println("Enter Review:");
	        scanner.nextLine();  // Consume newline leftover
	        String review = scanner.nextLine();

	        return jss_Service.updateReview(bookingDetailsList, lessonId, rating, review);
	    }

	    private Boolean printAvailableBookings(List<String> bookingDetailsList, String learnerId) {
	        System.out.println("Available bookings with this lesson ID:" + learnerId);
	        System.out.println("======================================================");
	        System.out.println("LessonId\t Grade \t Date\t\t Status");
	        System.out.println("======================================================");
	        Boolean check=false;
	        for (String data : bookingDetailsList) {
	            String[] details = data.split(",");
	            if (details[1].equals(learnerId)) {
	                System.out.println(details[0] + "\t\t" + details[2] + "\t\t" + details[4] + "\t" + details[5]);
	                if(details[5].equalsIgnoreCase("booked")) {
	                	check=true;
	                }
	            }
	        }
	        System.out.println("--------------------------------------------------------");
	        return check;
	    }

	    private boolean hasBookedLesson(List<String> bookingDetailsList, String lessonId, String learnerId) {
	        return bookingDetailsList.stream()
	                .anyMatch(b -> b.split(",")[0].equals(lessonId) && b.split(",")[1].equals(learnerId) && b.split(",")[5].equals("booked"));
	    }

	    private int getValidRating() {
	        int rating;
	        do {
	            System.out.println("Provide Session Rating ranging 1 - 5 (1: Very dissatisfied, 5: Very Satisfied)");
	            while (!scanner.hasNextInt()) {
	                System.out.println("Please enter a valid integer.");
	                scanner.next(); // consume non-int input
	            }
	            rating = scanner.nextInt();
	        } while (rating < 1 || rating > 5);

	        return rating;
	    }

	    private String getInput(String prompt) {
	        System.out.println(prompt);
	        return scanner.next();
	    }

	    public List<String> updateReview(List<String> bookingDetails, String bookingId, int rating, String review) {
	        List<String> updatedList = new ArrayList<>();

	        for (String booking : bookingDetails) {
	            String[] details = booking.split(",");
	            if (details[0].equals(bookingId)) {
	                details[5] = "attended"; // Change status to attended
	                details[6] = String.valueOf(rating); // Update rating
	                details[7] = review; // Update review
	                updatedList.add(String.join(",", details));
	            } else {
	                updatedList.add(booking);
	            }
	        }

	        System.out.println("Attended lesson successfully..");
	        return updatedList;
	    }

	    
	    public List<List<String>> changeBooking(List<String> timeTableList, List<String> bookingDetailsList, HashMap<String, LearnerDetails> learnerInfo) {
	        JSS_Service jss_Service = new JSS_Service();

	    	String learnerID = getValidCustomerId(learnerInfo);
	    	Boolean isBookingsAvailable= printAvailableBookings(bookingDetailsList, learnerID);
	    	if (!isBookingsAvailable) {
	            System.out.println("You don't have any pending sessions to modify booking.");
	            return null;
	        }
	    	
			
	        System.out.println("Enter booking Id to modify the session:");
	        
	        String modifyId = scanner.next(); 
	        
	        String bookInfo = getBookingInfo(bookingDetailsList, modifyId, "booked");
//System.out.println("bookInfo==?"+bookInfo);
	        if (bookInfo.isEmpty()) {
	            System.out.println("Please choose a booking Id which is in booked status.");
	            return null;
	        }

	        // Update timetable and modify booking status
	        List<String> timeTableMod = jss_Service.updateTimetable(timeTableList, Integer.parseInt(bookInfo.split(",")[8]), -1);
	        List<String> bookingDataMod = jss_Service.updateStatus(bookingDetailsList, modifyId, "cancelled");
 
	        List<List<String>> modifiedList = new ArrayList<>();
//	        modifiedList.add(timeTableMod);
//	        modifiedList.add(bookingDataMod);
	        modifiedList =bookASession(timeTableMod, bookingDataMod, learnerInfo,learnerID); 

	        System.out.println("Modified booking successful.");
	        return modifiedList;
	    }

	    private String getValidCustomerId(HashMap<String, LearnerDetails> custInfo) {
	        System.out.println("Enter your customer ID: (Sample input: cust01, cust02...)");
	        String custID = scanner.next();
	        while (!custInfo.containsKey(custID)) {
	            System.out.println("You have entered wrong customer ID. Enter Valid Customer ID:");
	            custID = scanner.next();
	        }
	        return custID;
	    }

	    private String getBookingInfo(List<String> bookingDetailsList, String bookingId, String expectedStatus) {
	        return bookingDetailsList.stream()
	                .filter(b -> b.split(",")[0].equals(bookingId) && b.split(",")[5].equals(expectedStatus))
	                .findFirst()
	                .orElse("");
	    }

	    public List<String> updateTimetable(List<String> timetable, int classId, int attendees) {
	        List<String> newList = new ArrayList<>();
	        for (String entry : timetable) {
	            String[] details = entry.split(",");
	            if (Integer.parseInt(details[0]) == classId) {
	                // Update the attendee count
	                int updatedCount = Integer.parseInt(details[4]) + attendees;
	                details[4] = String.valueOf(updatedCount);

	                // Reconstruct the string with updated count
	                String updatedEntry = String.join(",", details);
	                newList.add(updatedEntry);
	            } else {
	                // Add the unmodified entry
	                newList.add(entry);
	            }
	        }
	        return newList;
	    }
	   
}
