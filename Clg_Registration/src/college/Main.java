package college;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            UserDAO userDAO = new UserDAO();
            StudentDAO studentDAO = new StudentDAO();
            CollegeDAO collegeDAO = new CollegeDAO();

            System.out.println("Welcome to the Student-College Management System");
            while (true) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int option = Integer.parseInt(scan.nextLine());

                if (option == 1) {

                    System.out.println("Register as:");   // Registration
                    System.out.println("1. Student");
                    System.out.println("2. College");
                    System.out.print("Choose an option: ");
                    int roleOption = Integer.parseInt(scan.nextLine());

                    if (roleOption == 1) {
                        
                        User user = new User();   // Student registration
                        user.setRole("student");

                        System.out.print("Enter username: ");
                        user.setUsername(scan.nextLine());

                        System.out.print("Enter password: ");
                        user.setPassword(scan.nextLine());

                        userDAO.addUser(user);

                        Student student = new Student();
                        student.setUserId(user.getUserId());

                        System.out.print("Enter student ID: ");
                        student.setStudentId(scan.nextLine());

                        System.out.print("Enter name: ");
                        student.setName(scan.nextLine());

                        System.out.print("Enter course: ");
                        student.setCourse(scan.nextLine());

                        System.out.print("Enter year: ");
                        student.setYear(Integer.parseInt(scan.nextLine()));

                        studentDAO.addStudent(student);

                        System.out.println("Student registered successfully!");

                    } 
                    else if (roleOption == 2) {
                        
                        User user = new User();
                        user.setRole("college");

                        System.out.print("Enter username: ");    // College registration
                        user.setUsername(scan.nextLine());

                        System.out.print("Enter password: ");
                        user.setPassword(scan.nextLine());

                        userDAO.addUser(user);

                        College college = new College();
                        college.setUserId(user.getUserId());

                        System.out.print("Enter college ID: ");
                        college.setCollegeId(scan.nextLine());

                        System.out.print("Enter name: ");
                        college.setName(scan.nextLine());

                        System.out.print("Enter location: ");
                        college.setLocation(scan.nextLine());

                        collegeDAO.addCollege(college);

                        System.out.println("College registered successfully!");
                    }

                } 
                else if (option == 2) {
                   
                    System.out.print("Enter username: ");    // Login
                    String username = scan.nextLine();

                    System.out.print("Enter password: ");
                    String password = scan.nextLine();

                    if (userDAO.authenticateUser(username, password)) {
                        User user = userDAO.getUserByUsername(username);
                        if (user != null) {
                            System.out.println("Logged in as " + user.getRole());
                            if ("student".equals(user.getRole())) {
                                
                                while (true) {     // If Student options below
                                    System.out.println("1. Update Data");
                                    System.out.println("2. Delete Data");
                                    System.out.println("3. Logout");
                                    System.out.print("Choose an option: ");
                                    int studentOption = Integer.parseInt(scan.nextLine());

                                    
                                    if (studentOption == 1) {  //to  update student data
                                       
                                        Student student = studentDAO.getStudentByUserId(user.getUserId());

                                        System.out.print("Enter new name (leave blank to keep current): ");
                                        String name = scan.nextLine();
                                        if (!name.isEmpty()) student.setName(name);

                                        System.out.print("Enter new course (leave blank to keep current): ");
                                        String course = scan.nextLine();
                                        if (!course.isEmpty()) student.setCourse(course);

                                        System.out.print("Enter new year (leave blank to keep current): ");
                                        String yearStr = scan.nextLine();
                                        if (!yearStr.isEmpty()) student.setYear(Integer.parseInt(yearStr));

                                        studentDAO.updateStudent(student);
                                        System.out.println("Student data updated successfully!");

                                    } 
                                    else if (studentOption == 2) {
                                        studentDAO.deleteStudent(user.getUserId()); // to delete student data
                                        System.out.println("Student data deleted successfully!");
                                        break;

                                    } 
                                    else if (studentOption == 3) {
                                        		break;	  // Logout
                                    }
                                }
                            } 
                            else if ("college".equals(user.getRole())) {
                            												// College options
                                while (true) {
                                    System.out.println("1. Update Data");
                                    System.out.println("2. Delete Data");
                                    System.out.println("3. View Students");
                                    System.out.println("4. Logout");
                                    System.out.print("Choose an option: ");
                                    int collegeOption = Integer.parseInt(scan.nextLine());

                                    if (collegeOption == 1) {
                                        										// to update college data
                                        College college = collegeDAO.getCollegeByUserId(user.getUserId());

                                        System.out.print("Enter new name (leave blank to keep current): ");
                                        String name = scan.nextLine();
                                        if (!name.isEmpty()) college.setName(name);

                                        System.out.print("Enter new location (leave blank to keep current): ");
                                        String location = scan.nextLine();
                                        if (!location.isEmpty()) college.setLocation(location);

                                        collegeDAO.updateCollege(college);
                                        System.out.println("College data updated successfully!");

                                    } 
                                    else if (collegeOption == 2) {
                                    										// to delete college data
                                        collegeDAO.deleteCollege(user.getUserId());
                                        System.out.println("College data deleted successfully!");
                                        break;

                                    } 
                                    else if (collegeOption == 3) {
                                    										// to view students data
                                        List<Student> students = studentDAO.getAllStudents();
                                        System.out.println("Student Data:");
                                        for (Student student : students) {
                                            System.out.println("ID: " + student.getStudentId());
                                            System.out.println("Name: " + student.getName());
                                            System.out.println("Course: " + student.getCourse());
                                            System.out.println("Year: " + student.getYear());
                                            System.out.println();
                                        }

                                    } 
                                    else if (collegeOption == 4) {
                                        // Logout
                                        break;
                                    }
                                }
                            }
                        }
                    } 
                    else {
                        System.out.println("Invalid username or password!");
                    }

                } 
                else if (option == 3) {
                	System.out.println("Exits all the operation");
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
