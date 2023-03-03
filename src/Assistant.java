import javax.swing.*;

public class Assistant extends User implements Element {

    public Assistant(String firstName, String lastName) {
        super(firstName, lastName);
        setUsername(firstName + lastName + "Assistant");
    }

    @Override
    // validates the grades
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    // get the courses to which the assistant is assigned
    public DefaultListModel<Course> getCourses() {
        DefaultListModel<Course> courses = new DefaultListModel<>();
        Catalog catalog = Catalog.getCatalog();
        for (Course i : catalog.getCourses())
            if (i.getAssistants().contains(this))
                courses.addElement(i);

        return courses;
    }
}
