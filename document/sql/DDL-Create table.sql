-- Tạo database
CREATE DATABASE casestudy_md4_c10;
-- Sử dụng database
USE casestudy_md4_c10;

-- Tạo bảng roles
CREATE TABLE roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) UNIQUE NOT NULL
);

-- Tạo bảng users
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role_id INT,   
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE SET NULL
);


CREATE TABLE subjects (
    id INT PRIMARY KEY AUTO_INCREMENT,
    subject_name ENUM(
        'MATHEMATICS', 
        'VIETNAMESE_LANGUAGE', 
        'ENGLISH', 
        'SCIENCE', 
        'HISTORY', 
        'GEOGRAPHY', 
        'ETHICS', 
        'PHYSICAL_EDUCATION', 
        'MUSIC', 
        'ART', 
        'COMPUTER_SCIENCE'
    ) NOT NULL
);

-- Tạo bảng teachers
CREATE TABLE teachers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    gender ENUM('MALE', 'FEMALE') NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20) UNIQUE,
    subject_id INT,
    user_id INT UNIQUE,  -- Mỗi giáo viên chỉ có 1 tài khoản user
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE SET NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tạo bảng classes
CREATE TABLE classes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    class_name VARCHAR(50) UNIQUE NOT NULL,
    teacher_id INT UNIQUE,  -- Mỗi lớp chỉ có một giáo viên chủ nhiệm
    grade_level ENUM('GRADE_1', 'GRADE_2', 'GRADE_3', 'GRADE_4', 'GRADE_5') NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE SET NULL
);

-- Tạo bảng students
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    gender ENUM('MALE', 'FEMALE') NOT NULL,
    address TEXT,
    class_id INT,
    parent_contact VARCHAR(50),
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE SET NULL
);

CREATE TABLE schedules (
    id INT PRIMARY KEY AUTO_INCREMENT,
    class_id INT NOT NULL,
    subject_id INT NOT NULL,
    teacher_id INT NOT NULL,
    day_of_week ENUM('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY') NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE
);

-- Tạo bảng grades (Điểm số)
CREATE TABLE grades (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    subject_id INT NOT NULL,
    teacher_id INT NOT NULL,
    score FLOAT NOT NULL CHECK (score BETWEEN 0 AND 10), -- Điểm từ 0 đến 10
    exam_type ENUM('MIDTERM', 'FINAL', 'QUIZ', 'HOMEWORK') NOT NULL,
    date DATE NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE CASCADE
);

-- Tạo bảng attendance (Điểm danh)
CREATE TABLE attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    schedule_id INT NOT NULL,
    status ENUM('PRESENT', 'ABSENT', 'LATE') NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
	FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE
);

select * from roles;
select * from users;
select * from subjects;
select * from teachers;
select * from classes;
select * from students;
select * from schedules;
select * from grades;