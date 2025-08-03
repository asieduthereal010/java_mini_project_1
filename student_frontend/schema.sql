CREATE TABLE IF NOT EXISTS students (
  id TEXT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  date_of_birth DATE NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS lecturers (
  id TEXT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  date_of_birth DATE NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS courses(
  id TEXT PRIMARY KEY, 
  name VARCHAR(100) NOT NULL,
  code VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS teacher_assistants(
  id TEXT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  date_of_birth DATE NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS fees (
  id SERIAL PRIMARY KEY,
  student_id TEXT NOT NULL,
  total_amount NUMERIC(10,2) NOT NULL,
  amount_paid NUMERIC(10,2) NOT NULL DEFAULT 0,
  FOREIGN KEY (student_id) REFERENCES students(id)
);

/*
  THE TABLES BELOW ALL ASSUME A MANY TO MANY RELATIONSHIP!
*/
CREATE TABLE IF NOT EXISTS course_enrollments (
  student_id TEXT NOT NULL,
  course_id TEXT NOT NULL,
  FOREIGN KEY (student_id) REFERENCES students(id),
  FOREIGN KEY (course_id) REFERENCES courses(id),
  CONSTRAINT course_enrollment_pk PRIMARY KEY (student_id, course_id)
);

CREATE TABLE IF NOT EXISTS course_lecturers(
  course_id TEXT NOT NULL,
  lecturer_id TEXT NOT NULL,
  FOREIGN KEY (course_id) REFERENCES courses(id),
  FOREIGN KEY (lecturer_id) REFERENCES lecturers(id),
  CONSTRAINT course_lecturer_pk PRIMARY KEY (course_id, lecturer_id)
);

CREATE TABLE IF NOT EXISTS course_lecturer_assistants(
  course_id TEXT NOT NULL,
  lecturer_assistant_id TEXT NOT NULL,
  FOREIGN KEY (course_id) REFERENCES courses(id),
  FOREIGN KEY (lecturer_assistant_id) REFERENCES teacher_assistants(id),
  CONSTRAINT course_lecturer_assistant_pk PRIMARY KEY (course_id, lecturer_assistant_id)
);
