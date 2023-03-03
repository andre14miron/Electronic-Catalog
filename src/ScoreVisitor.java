import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreVisitor implements Visitor{
    private static ScoreVisitor scoreVisitor = new ScoreVisitor();
    private HashMap<Teacher, ArrayList<Tuple<Student, String, Double>>> examScores;
    private HashMap<Assistant, ArrayList<Tuple<Student, String, Double>>> partialScores;

    private ScoreVisitor() {
        examScores = new HashMap<>();
        partialScores = new HashMap<>();
    }

    public static ScoreVisitor getScoreVisitor() {
        return scoreVisitor;
    }

    private class Tuple<S, C, G> {
        S student;
        C course;
        G grade;

        Tuple(S student, C course, G grade) {
            this.student = student;
            this.course = course;
            this.grade = grade;
        }
    }

    @Override
    // the assistant validates the grades
    public void visit(Assistant assistant) {
        Catalog catalog = Catalog.getCatalog();

        // get the assistant's list of unvalidated grades
        if (partialScores.containsKey(assistant)) {
            ArrayList<Tuple<Student, String, Double>> grades = partialScores.get(assistant);

            /* for each grade we update the current grade if it exists, otherwise we create
            it and add it to the course */
            for (Tuple i : grades) {
                Course course = catalog.getCourse((String) i.course);
                Grade grade = course.getGrade((Student) i.student);
                if (grade == null)
                    grade = new Grade((Student) i.student, (String) i.course);

                grade.setPartialScore((Double) i.grade);
                course.addGrade(grade);
                // notify observers that a new grade has been added
                catalog.notifyObservers(grade);
            }
            partialScores.remove(assistant);
        }
    }

    @Override
    // the teacher validates the grades
    public void visit(Teacher teacher) {
        Catalog catalog = Catalog.getCatalog();

        // get the teacher's list of unvalidated grades
        if (examScores.containsKey(teacher)) {
            ArrayList<Tuple<Student, String, Double>> grades = examScores.get(teacher);

            /* for each grade we update the current grade if it exists, otherwise we create
            it and add it to the course */
            for (Tuple i : grades) {
                Course course = catalog.getCourse((String) i.course);
                Grade grade = course.getGrade((Student) i.student);
                if (grade == null)
                    grade = new Grade((Student) i.student, (String) i.course);

                grade.setExamScore((Double) i.grade);
                course.addGrade(grade);
                // notify observers that a new grade has been added
                catalog.notifyObservers(grade);
            }
            examScores.remove(teacher);
        }
    }

    // return the list of unvalidated partial grades
    public DefaultListModel<String> getUnvalidatedPartialGrades(Assistant assistant) {
        DefaultListModel<String> unvalidatedGrades = new DefaultListModel<>();
        for (Map.Entry<Assistant, ArrayList<Tuple<Student, String, Double>>> entry: partialScores.entrySet())
            if (entry.getKey().equals(assistant))
                for (Tuple i: entry.getValue())
                    unvalidatedGrades.addElement(i.student + " " + i.grade);

        return unvalidatedGrades;
    }

    // return the list of unvalidated exam grades
    public DefaultListModel<String> getUnvalidatedExamGrades(Teacher teacher) {
        DefaultListModel<String> unvalidatedGrades = new DefaultListModel<>();
        for (Map.Entry<Teacher, ArrayList<Tuple<Student, String, Double>>> entry: examScores.entrySet())
            if (entry.getKey().equals(teacher))
                for (Tuple i: entry.getValue())
                    unvalidatedGrades.addElement(i.student + " " + i.grade);

        return unvalidatedGrades;
    }

    // add an unvalidated partial grade to the partial dictionary
    public void addPartialGrade(Assistant assist, Student stud, String course, Double grade) {
        Tuple<Student, String, Double> partialGrade = new Tuple<>(stud, course, grade);

        if (partialScores.containsKey(assist)) {
            ArrayList<Tuple<Student, String, Double>> list = partialScores.get(assist);
            list.add(partialGrade);
            partialScores.replace(assist, list);
        }
        else {
            ArrayList<Tuple<Student, String, Double>> list = new ArrayList<>();
            list.add(partialGrade);
            partialScores.put(assist, list);
        }
    }

    // add an unvalidated exam grade to the exam dictionary
    public void addExamGrade(Teacher teacher, Student stud, String course, Double grade) {
        Tuple<Student, String, Double> examGrade = new Tuple<>(stud, course, grade);

        if (examScores.containsKey(teacher)) {
            ArrayList<Tuple<Student, String, Double>> list = examScores.get(teacher);
            list.add(examGrade);
            examScores.replace(teacher, list);
        }
        else {
            ArrayList<Tuple<Student, String, Double>> list = new ArrayList<>();
            list.add(examGrade);
            examScores.put(teacher, list);
        }
    }
}
