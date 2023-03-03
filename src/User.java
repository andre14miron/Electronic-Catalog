import javax.swing.*;

public abstract class User {
    private String firstName, lastName;
    private String username, password;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        password = "helloWorld";
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setUsername(String username) {this.username = username;}

    public String getUsername() { return username;}

    public void setPassword(String password) {this.password = password;}

    public String getPassword() {return password;}

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public boolean equalsUser(User user) {
        if (getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()))
            return true;
        return false;
    }

    abstract DefaultListModel<Course> getCourses();
}
