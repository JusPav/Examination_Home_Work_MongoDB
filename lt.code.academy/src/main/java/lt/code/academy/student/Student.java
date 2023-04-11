package lt.code.academy.student;

public class Student {
    private int Id;
    private String name;
    private String surname;
    public StudentAnswer studentAnswer;
    public Student() {
    }
    public Student(int id, String name, String surname, StudentAnswer studentAnswer) {
        this.Id = id;
        this.name = name;
        this.surname = surname;
        this.studentAnswer = studentAnswer;
    }
    public StudentAnswer getStudentAnswer() {
        return studentAnswer;
    }
    public void setStudentAnswer(StudentAnswer studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        this.Id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
