import java.util.HashMap;
import java.util.Map;

public class BestExamScore implements Strategy{
    @Override
    // select the student with the highest exam grade
    public Student getBestScore(HashMap<Student, Grade> dictionary) {
        Student bestStudent = null;
        double bestExamGrade = 0;
        for (Map.Entry<Student, Grade> entry: dictionary.entrySet()) {
            if (entry.getValue().getExamScore() > bestExamGrade)
                bestStudent = entry.getKey();
        }
        return bestStudent;
    }
}
