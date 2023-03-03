import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) {

        JSONParser jsonParser = new JSONParser();

        try {
            // read JSON file
            Object obj =  jsonParser.parse(new FileReader("input.json"));
            JSONObject jsonObject = (JSONObject) obj;

            // get the courses and add them to the catalog
            JSONArray coursesList = (JSONArray) jsonObject.get("courses");
            for (Object course : coursesList)
                parseCourseObject((JSONObject) course);

            // get the exam scores and add them to the list of unvalidated exam grades
            JSONArray examScoresList = (JSONArray) jsonObject.get("examScores");
            for (Object examScore : examScoresList)
                parseExamScoresObject((JSONObject) examScore);

            // get the partial scores and add them to the list of unvalidated partial grades
            JSONArray partialScoresList = (JSONArray) jsonObject.get("partialScores");
            for (Object partialScore : partialScoresList)
                parsePartialScoresObject((JSONObject) partialScore);

            // Testare normala
            Catalog catalog = Catalog.getCatalog();
            ScoreVisitor scoreVisitor = ScoreVisitor.getScoreVisitor();
            Assistant assist1 = catalog.verifyAssistant(new Assistant("Horia", "Ghita"));
            assist1.accept(scoreVisitor);

            Course course = catalog.getCourse("Paradigme de programare");
            course.makeBackup();

            System.out.println("Grades backup :" + course.getGrades());

            Teacher teacher1 = catalog.verifyTeacher(new Teacher("Sonia", "Lupu"));
            teacher1.accept(scoreVisitor);
            System.out.println("Grades before undo :" + course.getGrades());
            System.out.println("Promovated Students :" + course.getGraduatedStudents());
            System.out.println("Best Student :" + course.getBestStudent());
            course.undo();
            System.out.println("Grades after undo :" + course.getGrades());


            //Testare interfata grafica
            // Student Gigel Frone
            /*
            Catalog catalog = Catalog.getCatalog();
            Student student = catalog.verifyStudent(new Student("Gigel", "Frone"));
            StudentPage studentPage = new StudentPage(student);

            // Assistant Andrei Georgescu
            Assistant assist = catalog.verifyAssistant(new Assistant("Andrei", "Georgescu"));
            TeacherAssistantPage assistantPage = new TeacherAssistantPage(assist);

            // Teacher Ion Mihalache
            Teacher teacher = catalog.verifyTeacher(new Teacher("Ion", "Mihalache"));
            TeacherAssistantPage teacherPage = new TeacherAssistantPage(teacher);

            // Parent Daniel Frone
            Parent p1 = catalog.verifyParent(new Parent("Daniel", "Frone"));
            catalog.addObserver(p1);
            ParentPage parentPage = new ParentPage(p1);

             */


        } catch(Exception e) {
            System.out.println("Error");
        }
    }

    private static void parseCourseObject(JSONObject object)
    {
        Course course;
        Teacher teacher;
        Strategy strategy;
        HashSet<Assistant> assistants = new HashSet<>();
        HashMap<String, Group> groups = new HashMap<>();
        String nameStrategy, courseName;
        Catalog catalog = Catalog.getCatalog();

        // get the strategy of the course
        nameStrategy = (String) object.get("strategy");
        strategy = StrategyFactory.getStrategy(nameStrategy);

        // get the name of the course
        courseName = (String) object.get("name");

        // get and create Teacher
        JSONObject objTeacher = (JSONObject) object.get("teacher");
        teacher = parseTeacherObject(objTeacher);
        teacher = catalog.verifyTeacher(teacher);

        // get and create assistants
        JSONArray assistantsList = (JSONArray) object.get("assistants");
        for (Object assist : assistantsList) {
            Assistant assistant = parseAssistantObject((JSONObject) assist);
            assistant = catalog.verifyAssistant(assistant);
            assistants.add(assistant);
        }

        // get groups
        JSONArray groupsList = (JSONArray) object.get("groups");
        for (Object obj : groupsList) {
            JSONObject groupObj = (JSONObject) obj;

            // get the ID of the group
            String ID = (String) groupObj.get("ID");

            // get the assistant of the group
            JSONObject assistObj = (JSONObject) groupObj.get("assistant");
            Assistant assistant = parseAssistantObject(assistObj);
            assistant = catalog.verifyAssistant(assistant);

            // create the group
            Group group = new Group(ID, assistant);

            // add the students to the group
            JSONArray studentsList = (JSONArray) groupObj.get("students");
            for (Object j : studentsList) {
                Student student = parseStudentObject((JSONObject) j);
                student = catalog.verifyStudent(student);
                group.add(student);
            }

            // add the new group to the groups of the course
            groups.put(ID, group);
        }

        // get type
        String type = (String) object.get("type");

        // create course
        if (type.equals("FullCourse"))
            course = new FullCourse.FullCourseBuilder(courseName, teacher, strategy)
                    .groups(groups)
                    .assistants(assistants)
                    .build();
        else
            course = new PartialCourse.PartialCourseBuilder(courseName, teacher, strategy)
                    .groups(groups)
                    .assistants(assistants)
                    .build();

        // add the course to the catalog
        catalog.addCourse(course);
    }

    // create a student from a json object
    private static Student parseStudentObject(JSONObject object) {
        Student student;
        Parent mother, father;
        String firstName, lastName;

        // create the student
        firstName = (String) object.get("firstName");
        lastName = (String) object.get("lastName");
        student = (Student) UserFactory.getUser("student", firstName, lastName);

        // create the mother of the student and set
        JSONObject objMother = (JSONObject) object.get("mother");
        if (objMother != null) {
            firstName = (String) objMother.get("firstName");
            lastName = (String) objMother.get("lastName");
            mother = (Parent) UserFactory.getUser("parent", firstName, lastName);
            student.setMother(mother);
        }

        // create the father of the student and set
        JSONObject objFather = (JSONObject) object.get("father");
        if (objFather != null) {
            firstName = (String) objFather.get("firstName");
            lastName = (String) objFather.get("lastName");
            father = (Parent) UserFactory.getUser("parent", firstName, lastName);
            student.setFather(father);
        }

        return student;
    }

    // create a teacher from a json object
    private static Teacher parseTeacherObject(JSONObject object) {
        Teacher teacher;
        String firstName, lastName;

        firstName = (String) object.get("firstName");
        lastName = (String) object.get("lastName");
        teacher = (Teacher) UserFactory.getUser("teacher", firstName, lastName);

        return teacher;
    }

    // create an assistant from a json object
    private static Assistant parseAssistantObject(JSONObject object) {
        Assistant assistant;
        String firstName, lastName;

        firstName = (String) object.get("firstName");
        lastName = (String) object.get("lastName");
        assistant = (Assistant) UserFactory.getUser("assistant", firstName, lastName);

        return assistant;
    }

    // parse the exam grades
    private static void parseExamScoresObject(JSONObject object) {
        String courseName;
        Teacher teacher;
        Student student;
        Number examScore;
        Catalog catalog = Catalog.getCatalog();
        ScoreVisitor scoreVisitor = ScoreVisitor.getScoreVisitor();

        // get Teacher
        JSONObject teacherObj = (JSONObject) object.get("teacher");
        teacher = parseTeacherObject(teacherObj);
        teacher = catalog.verifyTeacher(teacher);

        // get Student
        JSONObject studentObj = (JSONObject) object.get("student");
        student = parseStudentObject(studentObj);
        student = catalog.verifyStudent(student);

        // get course
        courseName = (String) object.get("course");

        // get exam score
        examScore = (Number) object.get("grade");

        // add the exam grade to be validated
        scoreVisitor.addExamGrade(teacher, student, courseName, examScore.doubleValue());
    }

    // parse the partial grades
    private static void parsePartialScoresObject(JSONObject object) {
        String courseName;
        Assistant assistant;
        Student student;
        Number partialScore;
        Catalog catalog = Catalog.getCatalog();
        ScoreVisitor scoreVisitor = ScoreVisitor.getScoreVisitor();

        // get Assistant
        JSONObject assistObj = (JSONObject) object.get("assistant");
        assistant = parseAssistantObject(assistObj);
        assistant = catalog.verifyAssistant(assistant);

        // get Student
        JSONObject studentObj = (JSONObject) object.get("student");
        student = parseStudentObject(studentObj);
        student = catalog.verifyStudent(student);

        // get course
        courseName = (String) object.get("course");

        // get partial score
        partialScore = (Number) object.get("grade");

        // add the partial grade to be validated
        scoreVisitor.addPartialGrade(assistant, student, courseName, partialScore.doubleValue());
    }
}