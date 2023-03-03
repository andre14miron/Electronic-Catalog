import java.util.ArrayList;
import java.util.Comparator;

public class Group<Student> extends ArrayList<Student> {
    private Assistant assistant;
    private String ID;
    private Comparator<Student> comp;

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        this(ID, assistant);
        this.comp = comp;
    }

    public Group(String ID, Assistant assistant) {
        this.ID = ID;
        this.assistant = assistant;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public Assistant getAssistant() {
        return assistant;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setComp(Comparator<Student> comp) {
        this.comp = comp;
    }

    public Comparator<Student> getComp() {
        return comp;
    }
}
