-- Active: 1669031617602@@127.0.0.1@3306@course
-- Работа по заполнению
INSERT INTO trainingprogram
  (trainingProgramName,started,ended,guest_id,trainer_id)
VALUES
  ("lacus", "2024-01-30 19", "2025-09-01 03", 26, 74),
  ("justo", "2024-07-18 12", "2025-06-01 07", 37, 74),
  ("eu", "2024-07-16 20", "2025-08-01 02", 9, 66),
  ("metus", "2024-08-17 04", "2025-05-23 08", 22, 76),
  ("vitae", "2024-02-22 03", "2025-02-02 11", 20, 77),
  ("pede", "2024-07-27 07", "2025-07-18 12", 13, 75),
  ("Donec", "2024-06-01 10", "2025-03-20 09", 37, 75),
  ("mauris,", "2024-02-06 19", "2025-10-06 12", 4, 82),
  ("sed", "2024-10-13 10", "2025-02-25 09", 19, 91),
  ("Donec", "2024-07-26 19", "2025-05-21 13", 32, 78),
  ("Nullam", "2024-09-12 18", "2025-02-06 02", 9, 60),
  ("rhoncus", "2024-10-14 21", "2025-06-07 10", 25, 83),
  ("convallis", "2024-09-15 16", "2025-01-07 06", 21, 88),
  ("sed", "2024-12-08 21", "2025-09-25 21", 23, 68),
  ("blandit", "2024-04-11 10", "2025-08-27 14", 28, 70),
  ("aliquet,", "2024-11-11 18", "2025-04-07 10", 4, 78),
  ("Donec", "2024-08-29 06", "2025-07-22 09", 4, 86),
  ("scelerisque", "2024-04-12 02", "2025-12-08 00", 27, 94),
  ("non", "2024-06-24 23", "2025-07-21 12", 1, 82),
  ("varius", "2024-02-22 16", "2025-06-22 08", 30, 73);

-- Образец работы с соединениями
select
  Tr.trainingProgramName,
  Tr.started,
  Tr.ended,
  G.fullName as Гость,
  T.fullname as Тренер
from
  trainingprogram Tr
  inner join trainer T ON TR.trainer_id = T.trainer_id
  inner join guest G on Tr.guest_id = G.guest_id;

-- Показ столбцов и их тип
-- describe timetable;

insert into timetable
  (datetime, dayOfWeek, guest_id)
values
  ("2024-12-16", "Понедельник", 1),
  ("2024-12-16", "Понедельник", 2),
  ("2024-12-16", "Понедельник", 3),
  ("2024-12-17", "Вторник", 4),
  ("2024-12-17", "Вторник", 5),
  ("2024-12-17", "Вторник", 6),
  ("2024-12-18", "Среда", 7),
  ("2024-12-18", "Среда", 8),
  ("2024-12-18", "Среда", 9),
  ("2024-12-19", "Четверг", 10),
  ("2024-12-19", "Четверг", 11),
  ("2024-12-19", "Четверг", 12),
  ("2024-12-20", "Пятница", 13),
  ("2024-12-20", "Пятница", 14),
  ("2024-12-20", "Пятница", 15),
  ("2024-12-21", "Суббота", 16),
  ("2024-12-21", "Суббота", 17),
  ("2024-12-21", "Суббота", 18),
  ("2024-12-22", "Воскресенье", 19),
  ("2024-12-22", "Воскресенье", 20),
  ("2024-12-22", "Воскресенье", 21);

-- Показ количества гостей в каждый день недели
SELECT
  datetime,
  dayOfWeek,
  count(guest_id) as Количество_гостей
FROM timetable

group by dayOfWeek;

alter table exercises
  rename COLUMN exercises_id to exercise_id;

select cast(T.datetime as date), T.dayofweek, G.fullname
from timetable T inner join guest G on T.guest_id = G.guest_id;

create trigger l1 on Ученики
for update as BEGIN
  print 'Таблица изменена'
End;

INSERT INTO guest
  (fullName,birth,hostel,course,classGroup,trainer_id)
values
  ('фцвфцв','2023-01-11','фцвф',1,'фцвф',12);
