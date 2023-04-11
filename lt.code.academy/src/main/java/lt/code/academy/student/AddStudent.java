package lt.code.academy.student;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lt.code.academy.MongoObjectClientProvider;

import java.util.Scanner;

public class AddStudent {
    private final MongoCollection<Student> studentCollection;
    int Id;
    String name;
    String surname;
    public Student student;

    public AddStudent() {
        MongoClient client = MongoObjectClientProvider.getClient();
        MongoDatabase database = client.getDatabase("exam_db");

        studentCollection = database.getCollection("students", Student.class);
    }
    public void addStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter student ID:");
        Id = sc.nextInt();
        if (studentCollection.find(Filters.eq("_id", Id)).first() != null) {
            System.out.println("Student with ID " + Id + " already exists.");
            return;
        }
        System.out.println("Enter student name:");
        name = sc.next();
        System.out.println("Enter student surname:");
        surname = sc.next();

        student = new Student(Id, name, surname, null);
        studentCollection.insertOne(student);
    }
}
