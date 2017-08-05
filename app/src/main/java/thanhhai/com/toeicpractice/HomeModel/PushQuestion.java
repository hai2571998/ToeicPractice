package thanhhai.com.toeicpractice.HomeModel;

public class PushQuestion {
    private String Question;
    private String Answer_A;
    private String Answer_B;
    private String Answer_C;
    private String Answer_D;
    private int totalA;
    private int totalB;
    private int totalC;
    private int totalD;

    public PushQuestion() {

    }

    public PushQuestion(String question, String answer_A, String answer_B, String answer_C, String answer_D, int totalA, int totalB, int totalC, int totalD) {
        Question = question;
        Answer_A = answer_A;
        Answer_B = answer_B;
        Answer_C = answer_C;
        Answer_D = answer_D;
        this.totalA = totalA;
        this.totalB = totalB;
        this.totalC = totalC;
        this.totalD = totalD;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer_A() {
        return Answer_A;
    }

    public void setAnswer_A(String answer_A) {
        Answer_A = answer_A;
    }

    public String getAnswer_B() {
        return Answer_B;
    }

    public void setAnswer_B(String answer_B) {
        Answer_B = answer_B;
    }

    public String getAnswer_C() {
        return Answer_C;
    }

    public void setAnswer_C(String answer_C) {
        Answer_C = answer_C;
    }

    public String getAnswer_D() {
        return Answer_D;
    }

    public void setAnswer_D(String answer_D) {
        Answer_D = answer_D;
    }

    public int getTotalA() {
        return totalA;
    }

    public void setTotalA(int totalA) {
        this.totalA = totalA;
    }

    public int getTotalB() {
        return totalB;
    }

    public void setTotalB(int totalB) {
        this.totalB = totalB;
    }

    public int getTotalC() {
        return totalC;
    }

    public void setTotalC(int totalC) {
        this.totalC = totalC;
    }

    public int getTotalD() {
        return totalD;
    }

    public void setTotalD(int totalD) {
        this.totalD = totalD;
    }
}
