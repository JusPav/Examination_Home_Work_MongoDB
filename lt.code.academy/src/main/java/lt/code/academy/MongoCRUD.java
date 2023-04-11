package lt.code.academy;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lt.code.academy.exam.ExamQuestionDB;
import lt.code.academy.student.Student;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;
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
        List<Document> pipeline = Arrays.asList(
                new Document("$project", new Document("name", 1).append("surname", 1)
                        .append("maxStudentRate", new Document("$max", Arrays.asList("$PHP.studentRate", "$Java.studentRate", "$Python.studentRate")))),
                new Document("$sort", new Document("maxStudentRate", -1)),
                new Document("$limit", 1));

        AggregateIterable<Student> output = studentCollection.aggregate(pipeline);
        for (Student rating : output) {
            System.out.printf("Student who have the highest rating: %s %s%n", rating.getName(), rating.getSurname());
        }
    }

    public void minRating() {
        List<Document> pipeline = Arrays.asList(
                new Document("$project", new Document("name", 1).append("surname", 1)
                        .append("maxStudentRate", new Document("$max", Arrays.asList("$PHP.studentRate", "$Java.studentRate", "$Python.studentRate")))),
                new Document("$sort", new Document("maxStudentRate", 1)),
                new Document("$limit", 1));

        AggregateIterable<Student> output = studentCollection.aggregate(pipeline);
        for (Student rating : output) {
            System.out.printf("Student who have the lowest rating: %s %s%n", rating.getName(), rating.getSurname());
        }
    }
}
