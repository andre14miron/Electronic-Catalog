public class Grade implements Comparable, Cloneable{
    private Double partialScore, examScore;
    private Student student;
    private String course;

    public Grade(Student student, String course) {
        partialScore = 0d;
        examScore = 0d;
        this.student = student;
        this.course = course;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getPartialScore() {
        return partialScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public Double getTotal() {
        return partialScore + examScore;
    }

    @Override
    public int compareTo(Object o) {
        Grade grade = (Grade) o;
        if (this.getTotal() > grade.getTotal())
            return 1;
        if (this.getTotal() == grade.getTotal())
            return 0;
        return -1;
    }

    @Override
    public Grade clone() {
        try {
            Grade clone = (Grade) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public String toString() {
        return partialScore + " " + examScore;
    }
}
