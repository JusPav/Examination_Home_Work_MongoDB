package lt.code.academy.exam;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lt.code.academy.MongoObjectClientProvider;

import java.util.ArrayList;
import java.util.Scanner;

public class CreateExam {
    private final MongoCollection<ExamQuestionDB> examCollection;
    public ExamQuestionDB examQuestionDB;
    ArrayList<ExamAnswer> examAnswers = new ArrayList<>();
    Exam exam = new Exam();
    Scanner sc = new Scanner(System.in);

    public CreateExam() {
        MongoClient client = MongoObjectClientProvider.getClient();
        MongoDatabase database = client.getDatabase("exam_db");

        examCollection = database.getCollection("exams", ExamQuestionDB.class);
    }
    public void createExamInfo() {

        System.out.println("Enter Exam ID");
        int examId = sc.nextInt();
        System.out.println("Enter Exam name");
        sc.nextLine();
        String examName = sc.nextLine();
        System.out.println("Enter Exam type");
        String examType = sc.nextLine();
        exam = new Exam(examId, examName, examType);
    }
    public void createQuestionsAndAnswers() {

        int questionNumb = 0;
        String action = "";
        Answer correctAnswers;

        while (!action.equals("EXIT")) {
            questionNumb++;
            System.out.printf("Question Nr. %s", questionNumb + ". Enter correct answer A,B,C,D\n");
            String correct = sc.nextLine().toUpperCase();

            switch (correct) {
                case "A" -> correctAnswers = Answer.A;
                case "B" -> correctAnswers = Answer.B;
                case "C" -> correctAnswers = Answer.C;
                case "D" -> correctAnswers = Answer.D;
                default -> {
                    System.out.println("There is no such choice");
                    continue;
                }
            }
            examAnswers.add(new ExamAnswer(questionNumb, correctAnswers));
            System.out.println("Continue press ENTER. Finish type Exit");
            action = sc.nextLine().toUpperCase();
        }
        examQuestionDB = new ExamQuestionDB(exam, examAnswers);
        examCollection.insertOne(examQuestionDB);
    }
}
