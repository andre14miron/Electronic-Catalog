public class Notification {
    private Grade grade;

    Notification(Grade grade) {
        this.grade = grade.clone();
    }

    public Grade getGrade() {return grade;}

    public void setGrade(Grade grade) {this.grade = grade;}

    public String toString() {
        String text = "Your child has an updated grade in the course " + grade.getCourse();
        return text;
    }
}
