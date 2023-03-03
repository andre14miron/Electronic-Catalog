import javax.swing.*;

public class Student extends User{
    private Parent mother, father;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
        mother = null;
        father = null;
        setUsername(firstName + lastName + "Student");
    }

    public void setMother(Parent mother) {
        this.mother = mother;
    }

    public Parent getMother() {
        return mother;
    }

    public void setFather(Parent father) {
        this.father = father;
    }

    public Parent getFather() {
        return father;
    }

    public DefaultListModel<Course> getCourses() {
        DefaultListModel<Course> courses = new DefaultListModel<Course>();
        Catalog catalog = Catalog.getCatalog();
        for (Course i: catalog.getCourses())
            if (i.containsStudent(this))
                courses.addElement(i);

        return courses;
    }
}
