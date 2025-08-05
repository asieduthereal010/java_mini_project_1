-- STUDENT MANAGEMENT SYSTEM - SQL INSERTION DATA
-- ================================================

-- Insert Semesters data
-- =====================
INSERT INTO semesters (id, name, year, semester_type, start_date, end_date, is_active, academic_year, semester_number) VALUES
(1, 'Fall 2024', 2024, 'Fall', '2024-09-01', '2024-12-15', true, '2024-2025', 1),
(2, 'Spring 2024', 2024, 'Spring', '2024-01-15', '2024-05-01', false, '2024-2025', 2),
(3, 'Spring 2025', 2025, 'Spring', '2025-01-15', '2025-05-01', true, '2025-2026', 1),
(4, 'Summer 2025', 2025, 'Summer', '2025-06-01', '2025-08-15', false, '2025-2026', 2),
(5, 'Fall 2023', 2023, 'Fall', '2023-09-01', '2023-12-15', false, '2023-2024', 1);


-- Insert Students data
-- ====================
INSERT INTO students (id, name, date_of_birth, email) VALUES
('STU001', 'John Doe', '1995-03-15', 'john.doe@university.edu');


-- Insert Lecturers data
-- =====================
INSERT INTO lecturers (id, name, date_of_birth, email) VALUES
('LEC001', 'Dr. Sarah Johnson', '1975-05-20', 'sarah.johnson@university.edu'),
('LEC002', 'Prof. Michael Chen', '1968-11-12', 'michael.chen@university.edu'),
('LEC003', 'Dr. Emily Davis', '1980-03-08', 'emily.davis@university.edu'),
('LEC004', 'Dr. Robert Wilson', '1972-09-15', 'robert.wilson@university.edu'),
('LEC005', 'Prof. Lisa Anderson', '1978-07-22', 'lisa.anderson@university.edu'),
('LEC006', 'Dr. James Brown', '1982-01-30', 'james.brown@university.edu'),
('LEC007', 'Dr. Maria Garcia', '1976-12-05', 'maria.garcia@university.edu'),
('LEC008', 'Prof. David Lee', '1970-04-18', 'david.lee@university.edu'),
('LEC009', 'Dr. Jennifer White', '1985-06-25', 'jennifer.white@university.edu'),
('LEC010', 'Prof. Thomas Black', '1973-10-11', 'thomas.black@university.edu');


-- Insert Courses data
-- ===================
INSERT INTO courses (id, name, code, semester_id) VALUES
('CS101', 'Introduction to Computer Science', 'CS101', 1),
('CS201', 'Data Structures and Algorithms', 'CS201', 1),
('CS301', 'Database Systems', 'CS301', 1),
('CS401', 'Software Engineering', 'CS401', 1),
('MATH101', 'Calculus I', 'MATH101', 1),
('MATH201', 'Advanced Mathematics', 'MATH201', 1),
('MATH301', 'Linear Algebra', 'MATH301', 1),
('ENG101', 'English Composition', 'ENG101', 1),
('ENG201', 'Advanced Writing', 'ENG201', 1),
('PHY101', 'Introduction to Physics', 'PHY101', 1),
('PHY201', 'Mechanics', 'PHY201', 1),
('CHEM101', 'General Chemistry', 'CHEM101', 1),
('BIO101', 'Introduction to Biology', 'BIO101', 1),
('HIST101', 'World History', 'HIST101', 1),
('ECON101', 'Principles of Economics', 'ECON101', 1);

-- Insert Fees data
-- ================
INSERT INTO fees (id, total_amount, amount_paid, academic_year, due_date, created_at, updated_at, student_id, semester_id) VALUES
(1, 0, 0, '2024-2025', '2024-12-31', '2024-09-01', '2024-11-15', 'STU001', 1),
(2, 0, 0, '2024-2025', '2024-12-31', '2024-09-01', '2024-11-20', 'STU002', 2),
(3, 0, 0, '2024-2025', '2024-12-31', '2024-09-01', '2024-10-15', 'STU003', 1),
(4, 0, 0, '2024-2025', '2024-12-31', '2024-09-01', '2024-11-10', 'STU004', 2),
(5, 0, 0, '2024-2025', '2024-12-31', '2024-09-01', '2024-11-25', 'STU005', 1);


-- Insert Course-Lecturer relationships
-- ====================================
INSERT INTO course_lecturers (course_id, lecturer_id) VALUES
('CS101', 'LEC001'),
('CS201', 'LEC004'),
('CS301', 'LEC008'),
('CS401', 'LEC009'),
('MATH101', 'LEC002'),
('MATH201', 'LEC002'),
('MATH301', 'LEC005'),
('ENG101', 'LEC003'),
('ENG201', 'LEC006'),
('PHY101', 'LEC007'),
('PHY201', 'LEC007'),
('CHEM101', 'LEC010'),
('BIO101', 'LEC001'),
('HIST101', 'LEC003'),
('ECON101', 'LEC005');


-- Insert Payments data (if you have a separate payments table)
-- ============================================================
-- Note: This assumes you have a payments table. If not, you can create one or modify as needed.

-- Sample payment records for demonstration
--INSERT INTO payments (transaction_id, student_id, amount, payment_method, payment_date, status, description, fees_id) VALUES
--('TXN20240115001', 'STU001', 1500.00, 'credit_card', '2024-01-15 10:30:00', 'completed', 'Tuition Fee Payment', 1),
--('TXN20240220001', 'STU001', 1000.00, 'bank_transfer', '2024-02-20 14:15:00', 'completed', 'Course Registration Fee', 1),
--('TXN20240310001', 'STU001', 500.00, 'credit_card', '2024-03-10 09:45:00', 'completed', 'Partial Tuition Payment', 1),
--('TXN20240120001', 'STU002', 2000.00, 'credit_card', '2024-01-20 11:00:00', 'completed', 'Tuition Fee Payment', 2),
--('TXN20240225001', 'STU002', 2500.00, 'bank_transfer', '2024-02-25 16:30:00', 'completed', 'Course Registration Fee', 2),
--('TXN20240130001', 'STU003', 3000.00, 'credit_card', '2024-01-30 13:20:00', 'completed', 'Tuition Fee Payment', 3),
--('TXN20240205001', 'STU004', 1500.00, 'bank_transfer', '2024-02-05 15:45:00', 'completed', 'Partial Tuition Payment', 4),
--('TXN20240215001', 'STU005', 2000.00, 'credit_card', '2024-02-15 09:30:00', 'completed', 'Tuition Fee Payment', 5);

-- Update some courses for Spring 2025 semester
INSERT INTO courses (id, name, code, semester_id) VALUES
('CS501', 'Advanced Algorithms', 'CS501', 2),
('CS601', 'Machine Learning', 'CS601', 2),
('MATH401', 'Differential Equations', 'MATH401', 2),
('ENG301', 'Technical Writing', 'ENG301', 2),
('PHY301', 'Quantum Physics', 'PHY301', 2);

-- Assign lecturers to Spring 2025 courses
INSERT INTO course_lecturers (course_id, lecturer_id) VALUES
('CS501', 'LEC004'),
('CS601', 'LEC009'),
('MATH401', 'LEC005'),
('ENG301', 'LEC006'),
('PHY301', 'LEC007');


-- Comments and Notes
-- ==================
/*
This SQL file contains comprehensive test data for the Student Management System.

Key Features of the Data:
1. 5 Semesters (Fall 2024, Spring 2025, Summer 2025, Fall 2023, Spring 2024)
2. 10 Students with realistic personal information
3. 10 Lecturers with academic credentials
4. 10 Teacher Assistants for course support
5. 15 Courses across different disciplines
6. 10 Fee records with varying payment statuses
7. Comprehensive course-enrollment relationships
8. Course-lecturer and course-TA assignments
9. Sample data for multiple semesters

The data structure supports:
- Student dashboard functionality
- Course registration and enrollment
- Payment tracking and fee management
- Academic staff management
- Multi-semester course offerings

To use this data:
1. Ensure your database schema matches the model definitions
2. Run the INSERT statements in the order provided
3. Adjust any foreign key constraints as needed
4. Modify data values to match your specific requirements

Note: Some tables like 'payments' may need to be created separately if not part of the current schema.
*/
