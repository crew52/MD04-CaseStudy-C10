USE casestudy_md4_c10;

INSERT INTO roles (role_name) VALUES ('ADMIN'), ('TEACHER');

-- Giả sử id của ADMIN là 1 và TEACHER là 2 trong bảng roles
INSERT INTO users (username, password, role_id) VALUES 
('admin_user', 'admin_password_hash', 1),  -- Admin
('teacher1', 'teacher1_password_hash', 2), -- Teacher 1
('teacher2', 'teacher2_password_hash', 2), -- Teacher 2
('teacher3', 'teacher3_password_hash', 2); -- Teacher 3|

INSERT INTO users (username, password, role_id) VALUES 
('sarah_davis', 'sarah_davis_password_hash', 2),  -- Teacher 4
('david_wilson', 'david_wilson_password_hash', 2),  -- Teacher 5
('jessica_miller', 'jessica_miller_password_hash', 2),  -- Teacher 6
('james_anderson', 'james_anderson_password_hash', 2),  -- Teacher 7
('laura_martinez', 'laura_martinez_password_hash', 2),  -- Teacher 8
('robert_taylor', 'robert_taylor_password_hash', 2),  -- Teacher 9
('anna_thomas', 'anna_thomas_password_hash', 2);  -- Teacher 10

INSERT INTO subjects (subject_name) VALUES
('MATHEMATICS'),
('VIETNAMESE_LANGUAGE'),
('ENGLISH'),
('SCIENCE'),
('HISTORY'),
('GEOGRAPHY'),
('ETHICS'),
('PHYSICAL_EDUCATION'),
('MUSIC'),
('ART'),
('COMPUTER_SCIENCE');

INSERT INTO teachers (name, dob, gender, email, phone, subject_id, user_id) VALUES
('John Smith', '1985-06-15', 'MALE', 'john.smith@example.com', '1234567890', 1, 2),
('Emily Johnson', '1990-03-22', 'FEMALE', 'emily.johnson@example.com', '1234567891', 2, 3),
('Michael Brown', '1982-09-10', 'MALE', 'michael.brown@example.com', '1234567892', 3, 4),
('Sarah Davis', '1988-07-19', 'FEMALE', 'sarah.davis@example.com', '1234567893', 4, 5),
('David Wilson', '1980-11-30', 'MALE', 'david.wilson@example.com', '1234567894', 5, 6),
('Jessica Miller', '1992-04-05', 'FEMALE', 'jessica.miller@example.com', '1234567895', 6, 7),
('James Anderson', '1979-08-21', 'MALE', 'james.anderson@example.com', '1234567896', 7, 8),
('Laura Martinez', '1987-12-14', 'FEMALE', 'laura.martinez@example.com', '1234567897', 8, 9),
('Robert Taylor', '1983-02-28', 'MALE', 'robert.taylor@example.com', '1234567898', 9, 10),
('Anna Thomas', '1991-05-17', 'FEMALE', 'anna.thomas@example.com', '1234567899', 10, 11);

INSERT INTO classes (class_name, teacher_id, grade_level) VALUES 
('Class 1A', 1, 'GRADE_1'),  -- Giáo viên ID 1 chủ nhiệm lớp 1A
('Class 2B', 2, 'GRADE_2'),  -- Giáo viên ID 2 chủ nhiệm lớp 2B
('Class 3C', 3, 'GRADE_3');  -- Giáo viên ID 3 chủ nhiệm lớp 3C

INSERT INTO students (name, dob, gender, address, class_id, parent_contact) VALUES 
-- Lớp 1A (Class ID 1)
('Alice Johnson', '2018-05-12', 'FEMALE', '123 Maple St', 1, '123-456-7890'),
('Bob Smith', '2018-07-19', 'MALE', '456 Oak St', 1, '987-654-3210'),
('Charlie Brown', '2018-09-22', 'MALE', '789 Pine St', 1, '555-123-4567'),
('Daisy Evans', '2018-11-05', 'FEMALE', '159 Birch St', 1, '111-222-3333'),
('Ethan White', '2018-12-30', 'MALE', '753 Cedar St', 1, '444-555-6666'),
('Fiona Green', '2018-06-14', 'FEMALE', '951 Elm St', 1, '777-888-9999'),
('George Adams', '2018-04-01', 'MALE', '357 Walnut St', 1, '222-333-4444'),

-- Lớp 2B (Class ID 2)
('Hannah Carter', '2017-03-11', 'FEMALE', '852 Willow St', 2, '999-000-1111'),
('Ian Turner', '2017-05-22', 'MALE', '753 Maple St', 2, '888-777-6666'),
('Jack Wilson', '2017-07-18', 'MALE', '852 Oak St', 2, '555-444-3333'),
('Kylie Martinez', '2017-09-03', 'FEMALE', '357 Pine St', 2, '333-222-1111'),
('Liam Johnson', '2017-11-29', 'MALE', '159 Cedar St', 2, '777-666-5555'),
('Mia Thompson', '2017-02-14', 'FEMALE', '456 Birch St', 2, '444-333-2222'),
('Noah Scott', '2017-08-08', 'MALE', '951 Elm St', 2, '222-111-0000'),

-- Lớp 3C (Class ID 3)
('Olivia Baker', '2016-01-17', 'FEMALE', '357 Walnut St', 3, '999-888-7777'),
('Patrick Davis', '2016-04-05', 'MALE', '753 Willow St', 3, '666-555-4444'),
('Quinn Harris', '2016-06-23', 'FEMALE', '852 Maple St', 3, '111-000-9999'),
('Ryan Clark', '2016-09-11', 'MALE', '159 Oak St', 3, '333-222-1111'),
('Sophia Lewis', '2016-11-28', 'FEMALE', '357 Pine St', 3, '777-888-9999'),
('Tyler Rodriguez', '2016-12-31', 'MALE', '951 Cedar St', 3, '444-555-6666');


INSERT INTO schedules (class_id, subject_id, teacher_id, day_of_week, date, start_time, end_time) VALUES
-- Lớp 1A
(1, 1, 1, 'MONDAY', '2024-03-18', '08:00:00', '09:00:00'),
(1, 2, 2, 'TUESDAY', '2024-03-19', '09:00:00', '10:00:00'),
(1, 3, 3, 'WEDNESDAY', '2024-03-20', '10:00:00', '11:00:00'),
(1, 4, 4, 'THURSDAY', '2024-03-21', '13:00:00', '14:00:00'),
(1, 5, 5, 'FRIDAY', '2024-03-22', '14:00:00', '15:00:00'),
(1, 6, 6, 'MONDAY', '2024-03-25', '08:00:00', '09:00:00'),
(1, 7, 7, 'TUESDAY', '2024-03-26', '09:00:00', '10:00:00'),

-- Lớp 2B
(2, 1, 1, 'MONDAY', '2024-03-18', '10:00:00', '11:00:00'),
(2, 2, 2, 'TUESDAY', '2024-03-19', '11:00:00', '12:00:00'),
(2, 3, 3, 'WEDNESDAY', '2024-03-20', '13:00:00', '14:00:00'),
(2, 4, 4, 'THURSDAY', '2024-03-21', '14:00:00', '15:00:00'),
(2, 5, 5, 'FRIDAY', '2024-03-22', '08:00:00', '09:00:00'),
(2, 6, 6, 'MONDAY', '2024-03-25', '09:00:00', '10:00:00'),
(2, 7, 7, 'TUESDAY', '2024-03-26', '10:00:00', '11:00:00'),

-- Lớp 3C
(3, 1, 1, 'MONDAY', '2024-03-18', '13:00:00', '14:00:00'),
(3, 2, 2, 'TUESDAY', '2024-03-19', '14:00:00', '15:00:00'),
(3, 3, 3, 'WEDNESDAY', '2024-03-20', '08:00:00', '09:00:00'),
(3, 4, 4, 'THURSDAY', '2024-03-21', '09:00:00', '10:00:00'),
(3, 5, 5, 'FRIDAY', '2024-03-22', '10:00:00', '11:00:00'),
(3, 6, 6, 'MONDAY', '2024-03-25', '11:00:00', '12:00:00');


INSERT INTO grades (student_id, subject_id, teacher_id, score, exam_type, date) VALUES
-- Điểm cho học sinh của lớp 1A
(1, 1, 1, 8.5, 'MIDTERM', '2024-03-10'),
(1, 2, 2, 7.0, 'FINAL', '2024-03-15'),
(2, 1, 1, 9.0, 'QUIZ', '2024-03-05'),
(2, 3, 3, 6.5, 'HOMEWORK', '2024-03-12'),
(3, 4, 4, 7.8, 'MIDTERM', '2024-03-10'),
(3, 5, 5, 8.2, 'FINAL', '2024-03-15'),

-- Điểm cho học sinh của lớp 2B
(4, 2, 2, 6.9, 'MIDTERM', '2024-03-11'),
(4, 3, 3, 7.5, 'QUIZ', '2024-03-07'),
(5, 1, 1, 9.2, 'FINAL', '2024-03-14'),
(5, 2, 2, 5.8, 'HOMEWORK', '2024-03-09'),
(6, 3, 3, 8.0, 'MIDTERM', '2024-03-10'),
(6, 4, 4, 7.1, 'FINAL', '2024-03-15'),

-- Điểm cho học sinh của lớp 3C
(7, 5, 5, 9.5, 'MIDTERM', '2024-03-10'),
(7, 6, 6, 8.8, 'QUIZ', '2024-03-06'),
(8, 2, 2, 7.3, 'FINAL', '2024-03-14'),
(8, 3, 3, 6.9, 'HOMEWORK', '2024-03-08'),
(9, 1, 1, 8.7, 'MIDTERM', '2024-03-11'),
(9, 4, 4, 7.6, 'FINAL', '2024-03-15'),
(10, 5, 5, 9.0, 'QUIZ', '2024-03-07');

INSERT INTO attendance (student_id, schedule_id, status) VALUES
-- Điểm danh cho lớp 1A
(1, 1, 'PRESENT'),
(2, 1, 'PRESENT'),
(3, 2, 'ABSENT'),
(4, 2, 'LATE'),
(5, 3, 'PRESENT'),

-- Điểm danh cho lớp 2B
(6, 4, 'PRESENT'),
(7, 4, 'LATE'),
(8, 5, 'PRESENT'),
(9, 5, 'ABSENT'),
(10, 6, 'PRESENT'),

-- Điểm danh cho lớp 3C
(11, 7, 'PRESENT'),
(12, 7, 'PRESENT'),
(13, 8, 'ABSENT'),
(14, 8, 'LATE'),
(15, 9, 'PRESENT'),
(16, 9, 'PRESENT'),
(17, 10, 'LATE'),
(18, 10, 'PRESENT'),
(19, 11, 'ABSENT'),
(20, 11, 'PRESENT');

SELECT * FROM roles;
SELECT * FROM users;
SELECT * FROM subjects;
SELECT * FROM subjects;
SELECT * FROM teachers;
SELECT * FROM users;
SELECT * FROM classes;
SELECT * FROM students;
SELECT * FROM schedules;
SELECT * FROM grades;
SELECT * FROM attendance;



