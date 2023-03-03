import java.util.HashMap;
import java.util.Map;

public class BestPartialScore implements Strategy{
    @Override
    // select the student with the highest partial grade
    public Student getBestScore(HashMap<Student, Grade> dictionary) {
        Student bestStudent = null;
        double bestPartialGrade = 0;
        for (Map.Entry<Student, Grade> entry: dictionary.entrySet()) {
            if (entry.getValue().getPartialScore() > bestPartialGrade)
                bestStudent = entry.getKey();
        }
        return bestStudent;
    }
}
