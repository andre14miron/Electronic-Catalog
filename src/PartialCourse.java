import java.util.ArrayList;

public class PartialCourse extends Course{

    private PartialCourse(PartialCourseBuilder builder) {
        super(builder);
    }

    public static class PartialCourseBuilder extends CourseBuilder{

        public PartialCourseBuilder(String name, Teacher teacher, Strategy strategy) {
            super(name, teacher, strategy);
        }

        public PartialCourse build() {
            return new PartialCourse(this);
        }
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        for (Grade i : getGrades()) {
            if (i.getTotal() >= 5)
                students.add(i.getStudent());
        }
        return students;
    }
}
