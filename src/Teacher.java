import javax.swing.*;

public class Teacher extends User implements Element{

    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
        setUsername(firstName + lastName + "Teacher");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public DefaultListModel<Course> getCourses() {
        DefaultListModel<Course> courses = new DefaultListModel<Course>();
        Catalog catalog = Catalog.getCatalog();
        for (Course i : catalog.getCourses())
            if (i.getTeacher().equals(this))
                courses.addElement(i);

        return courses;
    }
}
