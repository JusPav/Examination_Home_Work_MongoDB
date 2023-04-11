package lt.code.academy;

import lt.code.academy.exam.CreateExam;
import lt.code.academy.student.AddStudent;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Main main = new Main();

        String action;

        do {
            menu();
            action = sc.nextLine();

            main.userAction(action);
        } while (!action.equals("5"));
    }
    private void userAction(String action) {

        switch (action) {
            case "1" -> addStudents();
            case "2" -> createExam();
            case "3" -> examAttend();
            case "4" -> mongoDB();
            case "5" -> System.out.println("Program is finished");
            default -> System.out.println("There is no such choice");
        }
    }
    public void addStudents() {
        AddStudent addStudent = new AddStudent();
        addStudent.addStudent();
    }
    public void createExam() {
        CreateExam createExam = new CreateExam();
        createExam.createExamInfo();
        createExam.createQuestionsAndAnswers();
    }
    public void examAttend() {
        AttendExam attendExam = new AttendExam();
        attendExam.attend();
    }
    public void mongoDB() {
        MongoCRUD mongoCRUD = new MongoCRUD();
        mongoCRUD.startMongoCRUD();
    }
    public static void menu() {
        System.out.println(""" 
                1 - Add Student
                2 - Create Exam
                3 - Attend Exam
                4 - MongoDB some CRUD options
                5 - Finish program """);
    }
}