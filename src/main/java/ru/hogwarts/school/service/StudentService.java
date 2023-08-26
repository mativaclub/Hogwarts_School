package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student create(Student student) {
        logger.info("Student was created");
        return studentRepository.save(student);
    }

    public Optional<Student> read(long searchById) {
        logger.info("Student was founded");
        return studentRepository.findById(searchById);
    }


    public Student update(Student student) {
        logger.info("Student was updated");
        return studentRepository.save(student);
    }

    public void delete(long deleteById) {
        logger.info("Student was deleted");
        studentRepository.deleteById(deleteById);
    }

    @GetMapping
    public Collection<Student> findStudents() {
        logger.info("All student were founded");
        return studentRepository.findAll();
    }

    public Collection<Student> filterByAge(int age) {
        logger.info("Trying to filter students by age");
        List<Student> allStudents = studentRepository.findAll();
        logger.debug("Size of students is " + allStudents.size());
        return allStudents.stream()
                .filter(el -> el.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> findByAgeBetween(int fromAge, int toAge) {
        logger.info("Trying to filter students from " + fromAge + " to " + toAge);
        if (fromAge < 0 || toAge < 0) {
            logger.error("Age can't be under zero");
        }
//        return studentRepository.findAll().stream()
//                .filter(el -> el.getAge() >= fromAge && el.getAge() <= toAge)
//                .collect(Collectors.toList());
        return studentRepository.findByAgeBetween(fromAge, toAge);
    }

    public Collection<Student> findStudentsByFaculty_Id(long facultyId) {
        logger.info("Trying to filter students by facultyId " + facultyId);
        return studentRepository.findStudentsByFaculty_Id(facultyId);

//        return studentRepository.findAll().stream()
//                .filter(student -> student.getFaculty() != null &&
//                        student.getFaculty().getId().equals(facultyId))
//                .collect(Collectors.toList());
    }

    public int countStudents() {
        logger.info("Trying to count students");
        return studentRepository.countStudents();
    }

    public int averageAgeOfStudents() {
        logger.info("Trying to find average age of students");
        return studentRepository.averageAgeOfStudents();
    }

    public List<Student> last5Students() {
        logger.info("Trying to find last five students");
        return studentRepository.last5Students();
    }

    public List<String> studentsA() {
        logger.info("Getting students name starting with A");
//        return studentRepository.findAll()
//                .stream()
//                .parallel()
//                .filter(el->el.getName().toUpperCase().startsWith("A"))
//                .sorted(Comparator.comparing(Student::getName))
//                .peek(el->el.setName(el.getName().toUpperCase()))
//                .toList();
        return studentRepository.findAll()
                .stream()
                .parallel()
                .map(el -> el.getName().toUpperCase())
                .filter(el -> el.startsWith("A"))
                .sorted()
                .toList();

    }

    public Double averageAge() {
        logger.info("Getting students average age");
        return studentRepository.findAll()
                .stream()
                .parallel()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    public Integer testSpeedStream() {
        int sum = Stream.iterate(1, a -> a +1)
                .limit(100_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b );
        return sum;
    }

    public Integer testSpeedStream2() {
        int sum = IntStream.iterate(1, a -> a +1)
                .limit(100_000_000)
                .reduce(0, (a, b) -> a + b );
        return sum;
    }
}
