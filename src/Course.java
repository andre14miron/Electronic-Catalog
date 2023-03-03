import java.util.*;

public abstract class Course {
    private String name;
    private Teacher teacher;
    private int credits;
    private Strategy strategy;
    private HashSet<Assistant> assistants;
    private Vector<Grade> grades;
    private HashMap<String, Group> groups;
    private Snapshot snapshot = new Snapshot();

    public Course() {
    }

    public Course(CourseBuilder builder) {
        this.name = builder.name;
        this.teacher = builder.teacher;
        this.credits = builder.credits;
        this.strategy = builder.strategy;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.groups = builder.groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public HashSet<Assistant> getAssistants() { return assistants; }

    public void getAssistants(HashSet<Assistant> assistants) { this.assistants = assistants; }

    public Vector<Grade> getGrades() { return grades; }

    public void getGrades(Vector<Grade> grades) { this.grades = grades; }

    public HashMap<String, Group> getGroups() { return groups; }

    public void getGroups(HashMap<String, Group> groups) { this.groups = groups; }

    public Strategy getStrategy() { return strategy; }

    public void getStrategy(Strategy strategy) { this.strategy = strategy; }

    // class use for back-up
    private class Snapshot {
        private Vector<Grade> grades;

        public Vector<Grade> getGrades() {
            Vector<Grade> grades2 = new Vector<>();
            for (Grade i : grades)
                grades2.add(i.clone());
            return grades2;
        }

        public void setGrades(Vector<Grade> grades) {
            this.grades = new Vector<>();
            for (Grade i : grades)
                this.grades.add(i.clone());
        }
    }

    // builder for the course
    public abstract static class CourseBuilder {
        private String name;
        private Teacher teacher;
        private int credits;
        private Strategy strategy;
        private HashSet<Assistant> assistants = new HashSet<>();
        private Vector<Grade> grades = new Vector<>();
        private HashMap<String, Group> groups = new HashMap<>();

        public CourseBuilder(String name, Teacher teacher, Strategy strategy) {
            this.name = name;
            this.teacher = teacher;
            this.strategy = strategy;
        }

        public CourseBuilder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public CourseBuilder assistants(HashSet<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        public CourseBuilder grades(Vector<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public CourseBuilder groups(HashMap<String, Group> groups) {
            this.groups = groups;
            return this;
        }

        abstract Course build();
    }

    // set the assistant in the group with the indicated ID
    public void addAssistant(String ID, Assistant assistant) {
        if (!assistants.contains(assistant))
            assistants.add(assistant);
        Group<Student> group = groups.get(ID);
        group.setAssistant(assistant);
    }

    // returns the assistant associated with a student
    public Assistant getAssistant(Student student) {
        for (Map.Entry<String, Group> entry: groups.entrySet()) {
            if (entry.getValue().contains(student)) {
                return entry.getValue().getAssistant();
            }
        }
        return null;
    }

    // add the student to the group with the given ID
    public void addStudent(String ID, Student student) {
        Group<Student> group = groups.get(ID);
        group.add(student);
    }

    // add the group
    public void addGroup(Group group) {
        String ID = group.getID();
        groups.put(ID, group);
        if (!assistants.contains(group.getAssistant()))
            assistants.add(group.getAssistant());
    }

    // instantiate a group and add it
    public void addGroup(String ID, Assistant assistant) {
        groups.put(ID, new Group<Student>(ID, assistant));
        if (!assistants.contains(assistant))
            assistants.add(assistant);
    }

    // instantiate a group and add it
    public void addGroup(String ID, Assistant assist, Comparator<Student> comp) {
        groups.put(ID, new Group<>(ID, assist, comp));
        if (!assistants.contains(assist))
            assistants.add(assist);
    }

    // returns the grade of a student or null
    public Grade getGrade(Student student) {
        for (Grade i : grades)
            if (i.getStudent() == student)
                return i;
        return null;
    }

    // add a grade
    public void addGrade(Grade grade) {
        Grade g = null;
        for (Grade i : grades)
            if (i.getStudent().equalsUser(grade.getStudent()))
                g = i;

        if (g != null)
            grades.removeElement(g);

        grades.add(grade);
    }

    // returns a list of all students
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (Map.Entry<String, Group> entry: groups.entrySet())
            students.addAll(entry.getValue());

        return students;
    }

    // returns a dictionary with the students' status
    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> dictionary = new HashMap<>();
        for (Grade i : grades) {
            dictionary.put(i.getStudent(), i);
        }
        return dictionary;
    }

    // returns the best student according to the strategy chosen by the teacher
    public Student getBestStudent() {
        return strategy.getBestScore(getAllStudentGrades());
    }

    // make back-up at notes
    public void makeBackup() {
        snapshot.setGrades(grades);
    }

    // undo to previous grades
    public void undo() {
        grades = snapshot.getGrades();
    }

    // return true if the course contains a specific student
    public boolean containsStudent(Student student) {
        return getAllStudents().contains(student);
    }

    public String toString() {
        return name;
    }

    public abstract ArrayList<Student> getGraduatedStudents();
}
