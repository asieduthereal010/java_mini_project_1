-- Enhanced Student Management System Schema
-- This schema includes semester-based course management and comprehensive payment tracking

-- Original tables (enhanced)
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

-- New Semester Management Table
CREATE TABLE IF NOT EXISTS semesters (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,  -- e.g., "Fall 2024", "Spring 2025"
  year INTEGER NOT NULL,
  semester_type VARCHAR(20) NOT NULL,  -- "Fall", "Spring", "Summer"
  start_date DATE,
  end_date DATE,
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Enhanced Fees Table
CREATE TABLE IF NOT EXISTS fees (
  id SERIAL PRIMARY KEY,
  student_id TEXT NOT NULL,
  total_amount NUMERIC(10,2) NOT NULL,
  amount_paid NUMERIC(10,2) NOT NULL DEFAULT 0,
  academic_year VARCHAR(9), -- e.g., "2024-2025"
  semester_id INTEGER,
  due_date DATE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
  FOREIGN KEY (semester_id) REFERENCES semesters(id) ON DELETE SET NULL
);

-- New Payments Table for Transaction History
CREATE TABLE IF NOT EXISTS payments (
  id SERIAL PRIMARY KEY,
  student_id TEXT NOT NULL,
  fee_id INTEGER NOT NULL,
  amount NUMERIC(10,2) NOT NULL,
  payment_method VARCHAR(50) NOT NULL, -- 'credit_card', 'bank_transfer', 'cash', etc.
  payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  transaction_id VARCHAR(100) UNIQUE,
  status VARCHAR(20) DEFAULT 'completed', -- 'pending', 'completed', 'failed', 'refunded'
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
  FOREIGN KEY (fee_id) REFERENCES fees(id) ON DELETE CASCADE
);

-- Enhanced Course Enrollments with Semester Tracking
CREATE TABLE IF NOT EXISTS course_enrollments (
  student_id TEXT NOT NULL,
  course_id TEXT NOT NULL,
  semester_id INTEGER NOT NULL,
  enrollment_date DATE DEFAULT CURRENT_DATE,
  status VARCHAR(20) DEFAULT 'active', -- 'active', 'dropped', 'completed'
  grade VARCHAR(2), -- 'A', 'B', 'C', 'D', 'F', 'W' (withdrawal)
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
  FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
  FOREIGN KEY (semester_id) REFERENCES semesters(id) ON DELETE CASCADE,
  CONSTRAINT course_enrollment_pk PRIMARY KEY (student_id, course_id, semester_id)
);

-- Original many-to-many relationships (unchanged)
CREATE TABLE IF NOT EXISTS course_lecturers(
  course_id TEXT NOT NULL,
  lecturer_id TEXT NOT NULL,
  semester_id INTEGER, -- Optional: track which semester lecturer teaches course
  FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
  FOREIGN KEY (lecturer_id) REFERENCES lecturers(id) ON DELETE CASCADE,
  FOREIGN KEY (semester_id) REFERENCES semesters(id) ON DELETE SET NULL,
  CONSTRAINT course_lecturer_pk PRIMARY KEY (course_id, lecturer_id)
);

CREATE TABLE IF NOT EXISTS course_lecturer_assistants(
  course_id TEXT NOT NULL,
  lecturer_assistant_id TEXT NOT NULL,
  semester_id INTEGER, -- Optional: track which semester assistant helps with course
  FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
  FOREIGN KEY (lecturer_assistant_id) REFERENCES teacher_assistants(id) ON DELETE CASCADE,
  FOREIGN KEY (semester_id) REFERENCES semesters(id) ON DELETE SET NULL,
  CONSTRAINT course_lecturer_assistant_pk PRIMARY KEY (course_id, lecturer_assistant_id)
);

-- Indexes for better performance
CREATE INDEX idx_students_email ON students(email);
CREATE INDEX idx_fees_student_id ON fees(student_id);
CREATE INDEX idx_fees_semester_id ON fees(semester_id);
CREATE INDEX idx_payments_student_id ON payments(student_id);
CREATE INDEX idx_payments_fee_id ON payments(fee_id);
CREATE INDEX idx_payments_transaction_id ON payments(transaction_id);
CREATE INDEX idx_course_enrollments_student_id ON course_enrollments(student_id);
CREATE INDEX idx_course_enrollments_semester_id ON course_enrollments(semester_id);
CREATE INDEX idx_semesters_active ON semesters(is_active);

-- Sample data for testing
INSERT INTO semesters (name, year, semester_type, start_date, end_date) VALUES
('Fall 2024', 2024, 'Fall', '2024-09-01', '2024-12-15'),
('Spring 2025', 2025, 'Spring', '2025-01-15', '2025-05-01'),
('Summer 2025', 2025, 'Summer', '2025-06-01', '2025-08-15');

-- Comments explaining the schema
COMMENT ON TABLE semesters IS 'Manages academic semesters and terms';
COMMENT ON TABLE fees IS 'Student fee records with semester tracking';
COMMENT ON TABLE payments IS 'Payment transaction history for audit trail';
COMMENT ON TABLE course_enrollments IS 'Student course enrollments with semester and grade tracking';
COMMENT ON COLUMN payments.payment_method IS 'Method used for payment: credit_card, bank_transfer, cash, etc.';
COMMENT ON COLUMN payments.status IS 'Payment status: pending, completed, failed, refunded';
COMMENT ON COLUMN course_enrollments.status IS 'Enrollment status: active, dropped, completed';
COMMENT ON COLUMN course_enrollments.grade IS 'Final grade: A, B, C, D, F, W (withdrawal)'; 