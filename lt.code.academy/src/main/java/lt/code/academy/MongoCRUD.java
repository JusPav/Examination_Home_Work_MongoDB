package lt.code.academy;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lt.code.academy.exam.ExamQuestionDB;
import lt.code.academy.student.Student;

import java.util.Scanner;

public class MongoCRUD {
    private final MongoCollection<Student> studentCollection;
    private final MongoCollection<ExamQuestionDB> examCollection;
    Scanner sc = new Scanner(System.in);
    int examID;


    public MongoCRUD() {

        MongoClient client = MongoObjectClientProvider.getClient();
        MongoDatabase database = client.getDatabase("exam_db");

        studentCollection = database.getCollection("students", Student.class);
        examCollection = database.getCollection("exams", ExamQuestionDB.class);
    }
    public void startMongoCRUD() {
        MongoCRUD mongoCRUD = new MongoCRUD();

        String action;

        do {
            menu();
            action = sc.nextLine();

            mongoCRUD.userAction(action);
        } while (!action.equals("4"));
    }

    private void userAction(String action) {

        switch (action) {
            case "1" -> editExam();
            case "2" -> maxRating();
            case "3" -> minRating();
            case "4" -> System.out.println("Program is finished");
            default -> System.out.println("There is no such choice");
        }
    }

    public static void menu() {
        System.out.println(""" 
                1 - Edit exam answer
                2 - Get student with max rating
                3 - Get student with min rating
                4 - Return to main menu """);
    }

    public void editExam() {
        FindIterable<ExamQuestionDB> exam = examCollection.find();
        for (ExamQuestionDB ex : exam) {
            System.out.printf("Exam ID: %s, examName: %s%n", ex.getExam().getExamId(), ex.getExam().getExamName());
        }
        System.out.println("Please enter Exam ID:");
        examID = sc.nextInt();
        FindIterable<ExamQuestionDB> answers = examCollection.find(Filters.eq("exam.examId", examID));
        for (ExamQuestionDB ex : answers) {
            for (int i = 0; i < ex.getExamAnswers().size(); i++) {
                System.out.printf("Question - %s : %s %n", ex.getExamAnswers().get(i).getQuestionNumb(), ex.getExamAnswers().get(i).getCorrectAnswers());
            }
        }
        System.out.println("Enter number of question");
        int questionNumber = sc.nextInt();
        System.out.println("Enter new answer");
        String newAnswer = sc.next().toUpperCase();
        examCollection.updateOne(Filters.and(Filters.eq("exam.examId", examID), Filters.eq("examAnswers.questionNumb", questionNumber)), Updates.set("examAnswers.$.correctAnswers", newAnswer));
    }
    public void maxRating() {
        Student document = studentCollection.find().sort(new BasicDBObject("studentRate", -1)).limit(1).first();
        System.out.println("Student with highest rating score: " + document.getName() + " " + document.getSurname());
    }
    public void minRating() {
        Student document = studentCollection.find().sort(new BasicDBObject("studentRate", 1)).limit(1).first();
        System.out.println("Student with lowest rating score: " + document.getName() + " " + document.getSurname());
    }
}
