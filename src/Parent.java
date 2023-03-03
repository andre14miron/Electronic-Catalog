import javax.swing.*;
import java.util.ArrayList;

public class Parent extends User implements Observer{
    private ArrayList<Notification> notifications;

    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
        setUsername(firstName + lastName + "Parent");
        notifications = new ArrayList<>();
    }

    @Override
    // get the courses of the child
    DefaultListModel<Course> getCourses() {
        if (this.getChild() != null)
            return this.getChild().getCourses();
        return null;
    }

    @Override
    public void update(Notification notification) {
        notifications.add(notification);
    }

    public DefaultListModel<String> getNotifications() {
        DefaultListModel<String> list = new DefaultListModel<>();
        for (Notification i : notifications) {
            list.addElement(i.toString());
            String text = "PartialScore: " + i.getGrade().getPartialScore();
            text = text + "       ExamScore: " + i.getGrade().getExamScore();
            list.addElement(text);
        }

        return list;
    }

    // return the child
    public Student getChild() {
        Catalog c = Catalog.getCatalog();
        for (Student i : c.getStudents()) {
            if(i.getMother() != null)
                if (i.getMother().equals(this))
                   return i;

            if(i.getFather() != null)
                if (i.getFather().equals(this))
                    return i;
        }

        return null;
    }
}
