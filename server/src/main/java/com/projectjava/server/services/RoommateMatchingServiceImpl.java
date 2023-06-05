package com.projectjava.server.services;

import com.projectjava.server.models.entities.Matching;
import com.projectjava.server.models.entities.Student;
import com.projectjava.server.repositories.RoommateMatchingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoommateMatchingServiceImpl implements RoommateMatchingService {
    private final StudentService studentService;
    private final PreferenceService preferenceService;
    private final RoommateMatchingRepository roommateMatchingRepository;
    private List<Student> studentList;
    private Map<Student, Set<Student>> preferences;
    private Map<Student, Student> receivedProposal;
    private Map<Student, Student> sentProposal;
    private Set<Student> noProposalSentPeople;
    private Set<Student> peopleLeftUnmatched;
    private Map<Student, Student> finalMatchings;


    @Autowired
    public RoommateMatchingServiceImpl(StudentService studentService, PreferenceService preferenceService, RoommateMatchingRepository roommateMatchingRepository) {
        this.studentService = studentService;
        this.preferenceService = preferenceService;
        this.roommateMatchingRepository = roommateMatchingRepository;
    }

    public Student getRoommateMatchingOfStudent(Integer studentId) {
        Student student = studentService.getStudent(studentId);
        return roommateMatchingRepository.getRoommateMatchingOfStudent(student);
    }

    public List<Matching> getRoommateMatchings() {
        return roommateMatchingRepository.findAll();
    }

    public void deleteRoommateMatching() {
        roommateMatchingRepository.deleteAll();
    }

    public Integer getRoommateMatchingsCount() {
        return roommateMatchingRepository.getCount();
    }

    @Transactional
    private void saveRoommateMatchings() {
        for (Student student : finalMatchings.keySet()) {
            roommateMatchingRepository.save(new Matching(student, finalMatchings.get(student)));
        }
    }

    private void getFinalMatchings() {
        for (Student student : studentList) {
            if (!preferences.get(student).isEmpty()) {
                finalMatchings.put(student, preferences.get(student).iterator().next());
            } else {
                finalMatchings.put(student, null);
            }
        }
    }


    @Override
    public void generateRoommateMatching() {
        initializeDataStructures();
        preparePreferences();
        runIrvingAlgorithm();
        randomizeRemainingStudents();
        getFinalMatchings();
        saveRoommateMatchings();
    }

    private void initializeDataStructures() {
        preferences = new HashMap<>();
        receivedProposal = new HashMap<>();
        sentProposal = new HashMap<>();
        noProposalSentPeople = new HashSet<>();
        peopleLeftUnmatched = new HashSet<>();
        finalMatchings = new HashMap<>();
    }

    private void preparePreferences() {
        studentList = studentService.getStudents();
        for (Student student : studentService.getStudents()) {
            preferences.put(student, new LinkedHashSet<>(preferenceService.getPreferencesOfStudent(student.getUser_id())));
            Collections.shuffle(studentList);
            Set<Student> preferencesOfStudent = preferences.get(student);
            for (Student preference : studentList) {
                if (preference.getUser_id().equals(student.getUser_id()))
                    continue;
                preferencesOfStudent.add(preference);
            }
        }
    }


    private void runIrvingAlgorithm() {
        runPhase1();
        runPhase2();
        runPhase3();
    }


    private void runPhase1() {
        while (existsPeopleWithNoSentProposals()) {

            Student unmatchedPerson = getFirstUnmatchedPerson();
            Student preferredPerson = getFirstPreferredPerson(unmatchedPerson);

            if (preferredPerson == null) {
                System.out.println("No preferred person for " + unmatchedPerson);
                noProposalSentPeople.remove(unmatchedPerson);
                peopleLeftUnmatched.add(unmatchedPerson);
                sentProposal.put(unmatchedPerson, null);
                continue;
            }
            if (receivedProposal.get(preferredPerson) == null) {
                sentProposal.put(unmatchedPerson, preferredPerson);
                receivedProposal.put(preferredPerson, unmatchedPerson);
                noProposalSentPeople.remove(unmatchedPerson);
            } else if (getPreferenceIndex(preferences.get(preferredPerson), unmatchedPerson) < getPreferenceIndex(preferences.get(preferredPerson), receivedProposal.get(preferredPerson))) {
                rejectSymmetrically(receivedProposal.get(preferredPerson), preferredPerson);
                sentProposal.put(unmatchedPerson, preferredPerson);
                receivedProposal.put(preferredPerson, unmatchedPerson);
                noProposalSentPeople.remove(unmatchedPerson);
            } else {
                rejectSymmetrically(unmatchedPerson, preferredPerson);
            }
        }
    }

    private void runPhase2() {
        for (Student person : preferences.keySet()) {
            Set<Student> preferenceList = preferences.get(person);
            Student currentProposal = receivedProposal.get(person);
            boolean startRemoving = false;
            Iterator<Student> iterator = preferenceList.iterator();
            while (iterator.hasNext()) {
                Student preferredPerson = iterator.next();
                if (startRemoving) {
                    preferences.get(preferredPerson).remove(person);
                    iterator.remove();
                }
                if (preferredPerson.equals(currentProposal)) {
                    startRemoving = true;
                }
            }
        }
    }

    private void runPhase3() {
        Deque<Student> peopleStack = new ArrayDeque<>(preferences.keySet());
        while (!peopleStack.isEmpty()) {
            Student person = peopleStack.removeLast();
            if (preferences.get(person).size() >= 2) {
                List<Student> cycle = new ArrayList<>();
                Student currentPerson = person;
                Set<Student> visited = new HashSet<>();
                while (cycle.isEmpty() || !currentPerson.equals(person)) {
                    if (!visited.add(currentPerson)) {
                        break;
                    }
                    cycle.add(currentPerson);
                    Iterator<Student> iterator = preferences.get(currentPerson).iterator();
                    Student secondPreference = null;
                    if (iterator.hasNext()) {
                        iterator.next();
                        if (iterator.hasNext()) {
                            secondPreference = iterator.next();
                            cycle.add(secondPreference);
                        }
                    }
                    if (secondPreference != null) {
                        List<Student> secondPrefList = new ArrayList<>(preferences.get(secondPreference));
                        currentPerson = secondPrefList.get(secondPrefList.size() - 1);
                    }
                    if (currentPerson.equals(person)) {
                        cycle.add(person);
                    }
                }
                for (int i = cycle.size() - 1; i >= 1; i--) {
                    if (i % 2 == 0) {
                        removePreferencesSymmetrically(cycle.get(i), cycle.get(i - 1));
                    }
                }
                if (preferences.get(person).size() >= 2) {
                    peopleStack.addLast(person);
                }
            }
        }
    }

    private void randomizeRemainingStudents() {
        for (Student person : preferences.keySet()) {
            if (preferences.get(person).isEmpty()) {
                peopleLeftUnmatched.add(person);
            }
        }
        if (!peopleLeftUnmatched.isEmpty()) {
            System.out.println("People left unmatched: " + peopleLeftUnmatched);
        }
        Iterator<Student> iterator = peopleLeftUnmatched.iterator();
        while (iterator.hasNext()) {
            Student person = iterator.next();
            if (preferences.get(person).size() != 0) {
                iterator.remove();
                continue;
            }
            if (sentProposal.get(person) != null && preferences.get(sentProposal.get(person)).size() == 0) {
                addPreferencesSymmetrically(person, sentProposal.get(person));
                iterator.remove();
            }
        }

        iterator = peopleLeftUnmatched.iterator();
        while (!peopleLeftUnmatched.isEmpty()) {
            Student personOne = iterator.next();
            Student personTwo = null;
            iterator.remove();
            if (iterator.hasNext()) {
                personTwo = iterator.next();
                iterator.remove();
            }
            addPreferencesSymmetrically(personOne, personTwo);
        }
    }

    private Student getFirstPreferredPerson(Student unmatchedPerson) {
        Set<Student> preferredPeople = preferences.get(unmatchedPerson);
        if (preferredPeople.size() > 0) {
            return preferredPeople.iterator().next();
        }
        return null;
    }

    private Student getFirstUnmatchedPerson() {
        for (Student unmatchedPerson : preferences.keySet()) {
            if (sentProposal.get(unmatchedPerson) == null) {
                return unmatchedPerson;
            }
        }
        return null;
    }

    private boolean existsPeopleWithNoSentProposals() {
        return noProposalSentPeople.size() > 0;
    }

    private void rejectSymmetrically(Student unmatchedPerson, Student preferredPerson) {
        sentProposal.remove(unmatchedPerson);
        noProposalSentPeople.add(unmatchedPerson);
        removePreferencesSymmetrically(unmatchedPerson, preferredPerson);
    }

    private <T> int getPreferenceIndex(Set<T> set, T element) {
        int index = 0;
        for (T t : set) {
            if (t.equals(element)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private void removePreferencesSymmetrically(Student personOne, Student personTwo) {
        preferences.get(personOne).remove(personTwo);
        preferences.get(personTwo).remove(personOne);
    }

    private void addPreferencesSymmetrically(Student personOne, Student personTwo) {
        if (personOne != null)
            preferences.get(personOne).add(personTwo);
        if (personTwo != null)
            preferences.get(personTwo).add(personOne);
    }

}
