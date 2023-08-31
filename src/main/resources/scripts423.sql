SELECT s.name, s.age, f.name AS faculty_name
FROM student AS s
         LEFT JOIN faculty AS f ON s.faculty_id = f.id;
SELECT *
FROM student AS s
         INNER JOIN avatar AS a ON s.id = a.student_id;
