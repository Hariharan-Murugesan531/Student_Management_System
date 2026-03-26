package main;

import dao.StudentDAO;
import model.Student;

import java.util.List;
import java.util.Scanner;

public class Main {
	static StudentDAO dao = new StudentDAO();
	static Scanner sc    = new Scanner(System.in);
	public static void main(String[] args) {
			System.out.println("╔══════════════════════════════════════╗");
			System.out.println("║                                      ║");
	        System.out.println("║      Student Management System       ║");
	        System.out.println("║                                      ║");
	        System.out.println("╚══════════════════════════════════════╝");
	        
	        while (true) {
	            System.out.println("\n──────────────────────────────");
	            System.out.println("  1. Add Student");
	            System.out.println("  2. View All Students");
	            System.out.println("  3. Search Student by Name");
	            System.out.println("  4. View Student by ID");
	            System.out.println("  5. Update Student");
	            System.out.println("  6. Delete Student");
	            System.out.println("  7. Exit");
	            System.out.println("──────────────────────────────");
	            System.out.print("  Enter choice: ");
	            
	            int choice;
	            try {
					choice=Integer.parseInt(sc.nextLine().trim());
				} catch (NumberFormatException	 e) {
					System.out.println("Please enter a valid number.");
					continue;
				}
	            switch(choice) {
                case 1 -> addStudent();
                case 2 -> viewAll();
                case 3 -> searchByName();
                case 4 -> viewById();
                case 5 -> updateStudent();
                case 6 -> deleteStudent();
                case 7 -> {
                    System.out.println("Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
	            }
	        }
	}
    static void addStudent() {
        System.out.print("  Name   : "); String name   = sc.nextLine().trim();
        System.out.print("  Age    : "); int age        = Integer.parseInt(sc.nextLine().trim());
        System.out.print("  Course : "); String course  = sc.nextLine().trim();
        System.out.print("  Email  : "); String email   = sc.nextLine().trim();
        dao.addStudent(new Student(0, name, age, course, email));
    }
    static void viewAll() {
        List<Student> list = dao.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("  No students found.");
        } else {
            System.out.println("\n  " + "-".repeat(75));
            list.forEach(s -> System.out.println("  " + s));
            System.out.println("  " + "-".repeat(75));
            System.out.println("  Total: " + list.size() + " student(s)");
        }
    }

    static void searchByName() {
        System.out.print("  Enter name to search: ");
        String keyword = sc.nextLine().trim();
        List<Student> list = dao.searchByName(keyword);
        if (list.isEmpty()) System.out.println("  No match found.");
        else list.forEach(s -> System.out.println("  " + s));
    }

    static void viewById() {
        System.out.print("  Enter Student ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        Student s = dao.getStudentById(id);
        if (s != null) System.out.println("  " + s);
        else System.out.println("  Student not found.");
    }

    static void updateStudent() {
        System.out.print("  Enter Student ID to update: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        Student existing = dao.getStudentById(id);
        if (existing == null) { System.out.println("  Student not found."); return; }

        System.out.println("  Current: " + existing);
        System.out.print("  New Name   (Enter to keep): "); String name   = sc.nextLine().trim();
        System.out.print("  New Age    (Enter to keep): "); String ageStr = sc.nextLine().trim();
        System.out.print("  New Course (Enter to keep): "); String course = sc.nextLine().trim();
        System.out.print("  New Email  (Enter to keep): "); String email  = sc.nextLine().trim();

        dao.updateStudent(
            id,
            name.isEmpty()   ? existing.getName()   : name,
            ageStr.isEmpty() ? existing.getAge()     : Integer.parseInt(ageStr),
            course.isEmpty() ? existing.getCourse()  : course,
            email.isEmpty()  ? existing.getEmail()   : email
        );
    }

    static void deleteStudent() {
        System.out.print("  Enter Student ID to delete: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        dao.deleteStudent(id);
    }
}
