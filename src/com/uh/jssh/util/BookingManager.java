package com.uh.jssh.util;

import java.util.ArrayList;
import java.util.List;

public class BookingManager {


    private static List<String> bookingDetails;

    public static List<String> getBookingDetails() {
        bookingDetails = new ArrayList<>();
//        Raj,Joseph,Venu,Madhu,Gopi
        // Populate the booking details list with sample data
        addBookingDetail("1", "cust11", "Grade 1", "Raj", "18/3/2023", "attended", "4", "good session", "10");
        addBookingDetail("2", "cust02", "Grade 2", "Joseph", "3/4/2023", "booked", "", "", "1");
        addBookingDetail("3", "cust03", "Grade 2", "Joseph", "2/4/2023", "booked", "", "", "2");
        addBookingDetail("4", "cust05", "Grade 1", "Raj", "3/4/2023", "booked", "", "", "1");
        addBookingDetail("5", "cust02", "Grade 3", "Raj", "8/4/2023", "attended", "4", "not good", "22");
        addBookingDetail("6", "cust06", "Grade 4", "Madhu", "9/4/2023", "attended", "5", "not really good", "24");
        addBookingDetail("7", "cust07", "Grade 4", "Gopi", "9/4/2023", "attended", "5", "enjoyed session", "24");
        addBookingDetail("8", "cust02", "Grade 3", "Venu", "8/4/2023", "attended", "5", "not good", "22");
        addBookingDetail("9", "cust04", "Grade 2", "Joseph", "3/4/2023", "booked", "", "", "1");
        addBookingDetail("10", "cust01", "Grade 2", "Joseph", "2/4/2023", "booked", "", "", "2");
        
        return bookingDetails;
    }

    private static void addBookingDetail(String id, String custId, String className, String coach, String date, String status, String rating, String review, String fitClassId) {
        String detail = String.join(",", id, custId, className, coach, date, status, rating, review, fitClassId);
        bookingDetails.add(detail);
    }
}
