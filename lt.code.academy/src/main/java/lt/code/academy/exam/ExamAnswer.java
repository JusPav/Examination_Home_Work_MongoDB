package lt.code.academy.exam;

public class ExamAnswer {
    private int questionNumb;
    private Answer correctAnswers;
    public ExamAnswer() {
    }
    ExamAnswer(int questionNumb, Answer correctAnswers) {
        this.questionNumb = questionNumb;
        this.correctAnswers = correctAnswers;

    }
    public int getQuestionNumb() {
        return questionNumb;
    }
    public void setQuestionNumb(int questionNumb) {
        this.questionNumb = questionNumb;
    }
    public Answer getCorrectAnswers() {
        return correctAnswers;
    }
    public void setCorrectAnswers(Answer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
