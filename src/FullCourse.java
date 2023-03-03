import java.util.ArrayList;

public class FullCourse extends Course{

    private FullCourse(FullCourseBuilder builder) {
        super(builder);
    }

    public static class FullCourseBuilder extends CourseBuilder{
        public FullCourseBuilder(String name, Teacher teacher, Strategy strategy) {
            super(name, teacher, strategy);
        }

        public FullCourse build() {
            return new FullCourse(this);
        }
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        for (Grade i : getGrades()) {
            if (i.getPartialScore() >= 3 && i.getExamScore() >=2)
                students.add(i.getStudent());
        }
        return students;
    }
}
