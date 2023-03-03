import java.util.HashMap;
import java.util.Map;

public class BestTotalScore implements Strategy{
    @Override
    // select the student with the highest total grade
    public Student getBestScore(HashMap<Student, Grade> dictionary) {
        Student bestStudent = null;
        double bestTotalGrade = 0;
        for (Map.Entry<Student, Grade> entry: dictionary.entrySet()) {
            if (entry.getValue().getTotal() > bestTotalGrade)
                bestStudent = entry.getKey();
        }
        return bestStudent;
    }
}
