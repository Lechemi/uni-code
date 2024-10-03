import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Uni {
    public final Map<Subject, List<Studente>> liveLessons = new HashMap<>();
    public final Map<Subject, List<Studente>> remoteLessons = new HashMap<>();

    public Uni() {
        for (Subject subject : Subject.values()) {
            liveLessons.put(subject, new ArrayList<>(subject.capacity()));
            remoteLessons.put(subject, new ArrayList<>());
        }
    }

    public List<String> getSubjectList() {
        List<String> subjects = new ArrayList<>();
        for (Subject subject : liveLessons.keySet()) {
            subjects.add(subject.subjectName() + " (" + subject + ")");
        }
        return subjects;
    }

    // Elenco studenti prenotati in presenza data materia
    public List<String> getBookedStudentsLive(Subject subject) {
        List<String> students = new ArrayList<>();
        List<Studente> booked = liveLessons.get(subject);
        for (int i = 0; i < booked.size(); i++) {
            students.add(booked.get(i).toString() + " ; posto: " + (i + 1));
        }
        return students;
    }

    // Elenco studenti prenotati a distanza data materia
    public List<String> getBookedStudentsRemote(Subject subject) {
        List<String> students = new ArrayList<>();
        for (Studente student : remoteLessons.get(subject)) {
            students.add(student.toString());
        }
        return students;
    }

    // Posti in aula data materia
    public int freeSpots(Subject subject) {
        return subject.capacity() - liveLessons.get(subject).size();
    }

    // Prenotazione posto in aula
    public int bookLiveLesson(Subject subject, Studente student) {
        List<Studente> booked = liveLessons.get(subject);
        if (booked.contains(student))
            return -1;

        booked.add(student);
        return booked.indexOf(student) + 1;
    }

    // Prenotazione lezione a distanza
    public boolean bookRemoteLesson(Subject subject, Studente student) {
        List<Studente> booked = remoteLessons.get(subject);
        if (booked.contains(student))
            return false;

        booked.add(student);
        return true;
    }

}
