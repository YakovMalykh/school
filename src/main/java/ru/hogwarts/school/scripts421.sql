-- практика написания SQL-запросов

alter table student
    add constraint age_contrains check ( age > 15 );
alter table student alter column name set not null;
alter table student add constraint name_constrains  unique (name);
alter table faculty
    add constraint faculty_name_color_unique unique (name, color);
alter table student alter column age set default '20';
