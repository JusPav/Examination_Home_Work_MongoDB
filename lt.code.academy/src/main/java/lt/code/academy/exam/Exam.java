package lt.code.academy.exam;

public class Exam {
    private int examId;
    private String examName;
    private String examType;

    public Exam() {
    }
    public Exam(int examId, String examName, String examType) {
        this.examId = examId;
        this.examName = examName;
        this.examType = examType;
    }
    public int getExamId() {
        return examId;
    }
    public void setExamId(int examId) {
        this.examId = examId;
    }
    public String getExamName() {
        return examName;
    }
    public void setExamName(String examName) {
        this.examName = examName;
    }
    public String getExamType() {
        return examType;
    }
    public void setExamType(String examType) {
        this.examType = examType;
    }
}
