package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int fromAge, int toAge);

    Collection<Student> findStudentsByFaculty_Id(long id);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    int countStudents();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    int averageAgeOfStudents();

//    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    @Query(value = "SELECT * FROM student ORDER BY id OFFSET (SELECT COUNT(*) FROM student) - 5",
            nativeQuery = true)
    List<Student> last5Students();

}
