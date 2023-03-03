public class UserFactory {
    public static User getUser(String role, String firstName, String lastName) {
        if (role.equals("parent"))
            return new Parent(firstName, lastName);

        if (role.equals("student"))
            return new Student(firstName, lastName);

        if (role.equals("assistant"))
            return new Assistant(firstName, lastName);

        if (role.equals("teacher"))
            return new Teacher(firstName, lastName);

        return null;
    }
}
