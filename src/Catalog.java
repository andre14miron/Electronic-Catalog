import java.util.ArrayList;
import java.util.HashSet;

public class Catalog implements Subject{
    private static Catalog catalog = new Catalog();
    private ArrayList<Course> courses;
    private ArrayList<Observer> observers;

    private Catalog() {
        courses = new ArrayList<>();
        observers = new ArrayList<>();
    }

    // return the catalog
    public static Catalog getCatalog() {
        return catalog;
    }

    // add a course to the catalog
    public void addCourse(Course course) {
        if (!courses.contains(course))
            courses.add(course);
    }

    // remove a course from the catalog
    public void removeCourse(Course course) {
        courses.remove(course);
    }

    // return the list of courses
    public ArrayList<Course> getCourses() {return courses;}

    // return the course with the given name
    public Course getCourse(String courseName) {
        for (Course i : courses)
            if (i.getName().equals(courseName))
                return i;
        return null;
    }

    // return the list of all the students
    public HashSet<Student> getStudents() {
        HashSet<Student> students = new HashSet<>();
        for (Course i : courses)
            students.addAll(i.getAllStudents());

        return students;
    }

    // check if the assistant already exists and if so, return it
    public Assistant verifyAssistant(Assistant assistant) {
        for (Course i : courses)
            for (Assistant j : i.getAssistants())
                if (j.equalsUser(assistant))
                    return j;

        return assistant;
    }

    // check if the teacher already exists and if so, return it
    public Teacher verifyTeacher(Teacher teacher) {
        for (Course i : courses)
            if (i.getTeacher().equalsUser(teacher))
                return i.getTeacher();

        return teacher;
    }

    // check if the student already exists and if so, return it
    public Student verifyStudent(Student student) {
        for (Course i : courses)
            for (Student j : i.getAllStudents())
                if (j.equalsUser(student))
                    return j;
        return student;
    }

    // check if the parent already exists and if so, return it
    public Parent verifyParent(Parent parent) {
        for (Student i : getStudents()) {
            if (i.getMother() != null)
                if (i.getMother().equalsUser(parent))
                    return i.getMother();
            if (i.getFather() != null)
                if (i.getFather().equalsUser(parent))
                    return i.getFather();
        }

        return parent;
    }

    @Override
    // add the observer to the list of observers
    public void addObserver(Observer observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    @Override
    // remove the observer from the list of observers
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    // notify the observers
    public void notifyObservers(Grade grade) {
        Notification notification = new Notification(grade);
        Student student = grade.getStudent();

        // notify the observer whose child has a new grade added to the catalog
        if (student.getMother() != null && observers.contains(student.getMother()))
                student.getMother().update(notification);

        if (student.getFather() != null && observers.contains(student.getFather()))
            student.getFather().update(notification);
    }
}
