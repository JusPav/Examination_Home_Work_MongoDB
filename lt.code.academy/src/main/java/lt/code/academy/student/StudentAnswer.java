package lt.code.academy.student;

import lt.code.academy.exam.Answer;

import java.util.HashMap;

public class StudentAnswer {
    private int studentRate;
    private HashMap<String, Answer> studentAnswers;
    public StudentAnswer() {
    }
    public StudentAnswer(int studentRate, HashMap<String, Answer> studentAnswers) {
        this.studentRate =studentRate;
        this.studentAnswers = studentAnswers;
    }
    public int getStudentRate() {
        return studentRate;
    }
    public void setStudentRate(int studentRate) {
        this.studentRate = studentRate;
    }
    public HashMap<String, Answer> getStudentAnswers() {
        return studentAnswers;
    }
    public void setStudentAnswers(HashMap<String, Answer> studentAnswers) {
        this.studentAnswers = studentAnswers;
    }
}
