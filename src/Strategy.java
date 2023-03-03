import java.util.HashMap;

public interface Strategy {
    abstract Student getBestScore(HashMap<Student, Grade> dictionary);
}
