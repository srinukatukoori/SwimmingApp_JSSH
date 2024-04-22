package com.uh.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.uh.jssh.service.JSS_Service;
import com.uh.jssh.util.BookingManager;
import com.uh.jssh.util.Learner;
import com.uh.jssh.util.Timetable;
import com.uh.jssh.util.Learner.LearnerDetails;

class SwimTests {

	@Test
	public void swimLessonBooking() {
		System.out.println("test -- book a swim lesson");
		List<String> timeTableList; // Each string could be "ID,Grade,Date,Time,Coach,Day"
	    List<String> bookingDetailsList; // Each string could be "BookingID,LearnerID,TimeTableID,Status"
	     HashMap<String, LearnerDetails> learnerInfo; // Key: LearnerID, Value: "Name,Age,Gender,Contact"

		learnerInfo = Learner.getLearnerDetails();
    	timeTableList = Timetable.getDefaultSwimmingSchedule();
    	bookingDetailsList = BookingManager.getBookingDetails();
        JSS_Service jss_Service = new JSS_Service();
        
        List<List<String>> bookedResults = jss_Service.bookASession(timeTableList,bookingDetailsList ,  learnerInfo,"cust12");
        assertNotNull(bookedResults);
	}
	
	@Test
	public void swimLessonCacel() {

		System.out.println("test -- cancel a swim lesson");
		List<String> timeTableList; // Each string could be "ID,Grade,Date,Time,Coach,Day"
	    List<String> bookingDetailsList; // Each string could be "BookingID,LearnerID,TimeTableID,Status"
	     HashMap<String, LearnerDetails> learnerInfo; // Key: LearnerID, Value: "Name,Age,Gender,Contact"

		learnerInfo = Learner.getLearnerDetails();
    	timeTableList = Timetable.getDefaultSwimmingSchedule();
    	bookingDetailsList = BookingManager.getBookingDetails();
        JSS_Service jss_Service = new JSS_Service();
        List<List<String>> cancelResults = jss_Service.cancelBooking(timeTableList,bookingDetailsList ,  learnerInfo);
        assertNotNull(cancelResults);
	}
	
	@Test
	public void swimLessonChange() {

		System.out.println("test -- change a swim lesson");
		List<String> timeTableList; // Each string could be "ID,Grade,Date,Time,Coach,Day"
	    List<String> bookingDetailsList; // Each string could be "BookingID,LearnerID,TimeTableID,Status"
	     HashMap<String, LearnerDetails> learnerInfo; // Key: LearnerID, Value: "Name,Age,Gender,Contact"

		learnerInfo = Learner.getLearnerDetails();
    	timeTableList = Timetable.getDefaultSwimmingSchedule();
    	bookingDetailsList = BookingManager.getBookingDetails();
        JSS_Service jss_Service = new JSS_Service();
        List<List<String>> changeResults = jss_Service.changeBooking(timeTableList,bookingDetailsList ,  learnerInfo);
        assertNotNull(changeResults);
	}
	
	@Test
	public void swimLessonAttend() {

		System.out.println("test -- attend a swim lesson");
		List<String> bookingDetailsList; // Each string could be "BookingID,LearnerID,TimeTableID,Status"
	     HashMap<String, LearnerDetails> learnerInfo; // Key: LearnerID, Value: "Name,Age,Gender,Contact"

		learnerInfo = Learner.getLearnerDetails();
    	bookingDetailsList = BookingManager.getBookingDetails();
        JSS_Service jss_Service = new JSS_Service();
//        List<List<String>> 
        List<String> attendResults = jss_Service.attendASession(bookingDetailsList ,  learnerInfo);
        assertNotNull(attendResults);
	}


	@Test
	public void learnerReport() {

		System.out.println("test -- learner report");
		List<String> bookingDetailsList; // Each string could be "BookingID,LearnerID,TimeTableID,Status"
	     HashMap<String, LearnerDetails> learnerInfo; // Key: LearnerID, Value: "Name,Age,Gender,Contact"

		learnerInfo = Learner.getLearnerDetails();
    	bookingDetailsList = BookingManager.getBookingDetails();
        JSS_Service jss_Service = new JSS_Service();
        String result = jss_Service.generateLearnersReport(bookingDetailsList, learnerInfo );
        assertEquals(result, "success");
	}
	
	@Test
	public void coachReport() {

		System.out.println("test -- coach report method");
		List<String> bookingDetailsList; // Each string could be "BookingID,LearnerID,TimeTableID,Status"
	     bookingDetailsList = BookingManager.getBookingDetails();
        JSS_Service jss_Service = new JSS_Service();
        String result = jss_Service.coachReport(bookingDetailsList );
        assertEquals(result, "success");
	}


}

