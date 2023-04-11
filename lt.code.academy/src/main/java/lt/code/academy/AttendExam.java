package lt.code.academy;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lt.code.academy.exam.Answer;
import lt.code.academy.exam.ExamAnswer;
import lt.code.academy.exam.ExamQuestionDB;
import lt.code.academy.student.Student;
import lt.code.academy.student.StudentAnswer;

import java.util.HashMap;
import java.util.Scanner;

public class AttendExam {
    private final MongoCollection<Student> studentCollection;
    private final MongoCollection<ExamQuestionDB> examCollection;
    int Id;
    private String name;
    private String surname;
    int rate;
    int correctAnswer;
    String examName;

    public AttendExam() {
        MongoClient client = MongoObjectClientProvider.getClient();
        MongoDatabase database = client.getDatabase("exam_db");

        studentCollection = database.getCollection("students", Student.class);
        examCollection = database.getCollection("exams", ExamQuestionDB.class);
    }
    public void attend() {
        Scanner sc = new Scanner(System.in);
        FindIterable<Student> students = studentCollection.find();
        FindIterable<ExamQuestionDB> exam = examCollection.find();

        for (Student student : students) {
            System.out.printf("ID: %s - %s %s.%n", student.getId(), student.getName(), student.getSurname().charAt(0));
        }
        System.out.println("Enter ID of student");
        do {
            Id = sc.nextInt();
            for (Student student : students) {
                if (Id == student.getId()) {
                    name = student.getName();
                    surname = student.getSurname();
                }
            }
        } while (name == null);
        System.out.println("Welcome: " + name + " " + surname + " (ID: " + Id + ")");

        System.out.println("Enter name of exam");
        examName = sc.next();

        ExamQuestionDB selectedExam = null;
        for (ExamQuestionDB ex : exam) {
            if (ex.getExam().getExamName().equals(examName)) {
                selectedExam = ex;
                break;
            }
        }
        if (selectedExam == null) {
            System.out.println("There is no exam with such name");
            return;
        }
        HashMap<String, Answer> answers = new HashMap<>();

        for (ExamAnswer examAnswer : selectedExam.getExamAnswers()) {

            int i = examAnswer.getQuestionNumb();
            System.out.println(i);

            String a = String.valueOf(examAnswer.getCorrectAnswers());

            String answer = null;

            while (answer == null) {

                answer = sc.next().toUpperCase();

                switch (answer) {
                    case "A" -> {
                        answers.put(Integer.toString(examAnswer.getQuestionNumb()), Answer.A);
                        if (answer.equals(a))
                            correctAnswer++;
                    }
                    case "B" -> {
                        answers.put(Integer.toString(examAnswer.getQuestionNumb()), Answer.B);
                        if (answer.equals(a))
                            correctAnswer++;
                    }
                    case "C" -> {
                        answers.put(Integer.toString(examAnswer.getQuestionNumb()), Answer.C);
                        if (answer.equals(a))
                            correctAnswer++;
                    }
                    case "D" -> {
                        answers.put(Integer.toString(examAnswer.getQuestionNumb()), Answer.D);
                        if (answer.equals(a))
                            correctAnswer++;
                    }
                    default -> System.out.println("There is no such choice");
                }
                rate = (correctAnswer * 10) / i;
            }
        }
        StudentAnswer student = new StudentAnswer(rate, answers);
        studentCollection.updateOne(Filters.eq("_id", Id), Updates.set(examName, student));
    }
}
