package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Avatar;

import javax.transaction.Transactional;
import java.util.Optional;
@Repository
@Transactional
public interface AvatarRepository extends PagingAndSortingRepository<Avatar, Integer> {
    Optional<Avatar> findByStudentId(Long studentId);
}
