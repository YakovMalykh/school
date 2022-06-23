select student.name, student.age,faculty.name
from student
         inner join faculty on student.faculty_id = faculty.id;

select student.id as student_id, student.name, student.age,avatar.id as avatars_id
from student
         inner join avatar on student.id = avatar.student_id;