package com.uh.jssh.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Learner {

	 private static final HashMap<String, LearnerDetails> learnerData = new HashMap<>();

	    static {
	        // Initialize learner details statically
	        learnerData.put("cust01", new LearnerDetails("Alice", "male", 10, "07812121315", "grade 1"));
	        learnerData.put("cust02", new LearnerDetails("Catherine", "male", 10, "07812121315", "grade 1"));
	        learnerData.put("cust03", new LearnerDetails("Danny", "male", 7, "07812121315", "grade 1"));
	        learnerData.put("cust04", new LearnerDetails("Alok", "male", 8, "07812121315", "grade 1"));
	        learnerData.put("cust05", new LearnerDetails("Sai", "male", 5, "07812121315", "grade 1"));
	        
	        learnerData.put("cust06", new LearnerDetails("Alina", "male", 10, "07212121315", "grade 1"));
	        learnerData.put("cust07", new LearnerDetails("Frank", "male", 10, "07442121315", "grade 1"));
	        learnerData.put("cust08", new LearnerDetails("Vishnu", "male", 7, "07412121315", "grade 1"));
	        learnerData.put("cust09", new LearnerDetails("Sumanth", "male", 8, "07712121315", "grade 1"));
	        learnerData.put("cust10", new LearnerDetails("Manoj", "male", 5, "07877121315", "grade 1"));
	        
	        learnerData.put("cust11", new LearnerDetails("Lara", "female", 6, "0781212124", "grade 1"));
	        learnerData.put("cust12", new LearnerDetails("william", "male", 10, "07812121141", "grade 1"));
	        learnerData.put("cust13", new LearnerDetails("Raj", "male", 7, "0781215515", "grade 1"));
	        learnerData.put("cust14", new LearnerDetails("Ram", "male", 8, "07812121111", "grade 1"));
	        learnerData.put("cust15", new LearnerDetails("Tagure", "male", 5, "0781212100", "grade 1"));
	        
	        // Repeat for other learners...
	    }

	    public static HashMap<String, LearnerDetails> getLearnerDetails() {
	        return learnerData;
	    }

	    public static boolean validateLearner(String learnerId) {
	        return learnerData.containsKey(learnerId);
	    }  
	    
	    public static boolean validateLearner(String learnerId,Map<String, LearnerDetails> learners) {
	        return learners.containsKey(learnerId);
	    }

	    public static LearnerDetails getLearnerData(String learnerId) {
	        return learnerData.get(learnerId);
	    }

	    public static class LearnerDetails {
	        private String name;
	        private String gender;
	        private int age;
	        private String emergencyContact;
	        private String currentGradeLevel;

	        public LearnerDetails(String name, String gender, int age, String emergencyContact, String currentGradeLevel) {
	            this.name = name;
	            this.gender = gender;
	            this.age = age;
	            this.emergencyContact = emergencyContact;
	            this.currentGradeLevel = currentGradeLevel;
	        }

	        // Getters
	        public String getName() { return name; }
	        public String getGender() { return gender; }
	        public int getAge() { return age; }
	        public String getEmergencyContact() { return emergencyContact; }
	        public String getCurrentGradeLevel() { return currentGradeLevel; }
	 	    }
	    
	    public static HashMap<String, LearnerDetails> addNewLearner() {
	        Scanner scanner = new Scanner(System.in);

	        System.out.println("Enter learner name:");
	        String learnerName = scanner.nextLine();

	        System.out.println("Enter learner age:");
	        while (!scanner.hasNextInt()) {
	            System.out.println("Invalid input. Please enter a valid age:");
	            scanner.next();
	        }

	        System.out.println("Enter learner age (4 to 11 years):");
	        int age = scanner.nextInt();
	        while (age < 4 || age > 11) {
	            System.out.println("Invalid age. Please enter an age between 4 and 11 years:");
	            age = scanner.nextInt();
	        }

	        scanner.nextLine();  
	        

	        System.out.println("Enter learner's mobile number:");
//	        scanner.nextLine();
	        String mobileNumber = scanner.nextLine();

	        String gender = getGender(scanner);

	        String key = "cust" + (learnerData.size() + 1);
	        LearnerDetails details = new LearnerDetails(learnerName, gender, age, mobileNumber, "Grade 1");
	        learnerData.put(key, details);

	        System.out.println("New learner added with ID: " + key);
	        // Scanner should ideally be closed by the method that opened it, or managed globally
	        return learnerData;
	    }

	    private static String getGender(Scanner scanner) {
	        System.out.println("Choose Gender: 1 - male, 2 - female");
	        while (true) {
	            int choice = scanner.nextInt();
	            switch (choice) {
	                case 1: return "male";
	                case 2: return "female";
	                default: System.out.println("Invalid choice. Please choose 1 for male or 2 for female.");
	            }
	        }
	    }

}
