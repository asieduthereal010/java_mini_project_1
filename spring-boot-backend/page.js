import { query } from '@/lib/db';
import DashboardClient from './DashboardClient';

async function getStudents() {
  try {
    const students = await query(`
      SELECT 
        s.id,
        s.name,
        array_agg(c.name) as courses,
        COALESCE(f.total_amount, 0) as fees_owed,
        COALESCE(f.amount_paid, 0) as fees_paid
      FROM students s
      LEFT JOIN course_enrollments ce ON s.id = ce.student_id
      LEFT JOIN courses c ON ce.course_id = c.id
      LEFT JOIN fees f ON s.id = f.student_id
      GROUP BY s.id, s.name, f.total_amount, f.amount_paid
    `);
    return students;
  } catch (error) {
    console.error('Error fetching students:', error);
    return [];
  }
}

async function getLecturerTA() {
  try {
    const lecturerTA = await query(`
      SELECT 
        l.id,
        l.name as lecturer,
        array_agg(DISTINCT ta.name) as teaching_assistants,
        array_agg(DISTINCT c.name) as courses
      FROM lecturers l
      LEFT JOIN course_lecturers cl ON l.id = cl.lecturer_id
      LEFT JOIN courses c ON cl.course_id = c.id
      LEFT JOIN course_lecturer_assistants cla ON c.id = cla.course_id
      LEFT JOIN teacher_assistants ta ON cla.lecturer_assistant_id = ta.id
      GROUP BY l.id, l.name
    `);
    return lecturerTA;
  } catch (error) {
    console.error('Error fetching lecturer-TA relationships:', error);
    return [];
  }
}

export default async function Dashboard() {
  const students = await getStudents();
  const lecturerTA = await getLecturerTA();

  return <DashboardClient students={students} lecturerTA={lecturerTA} />;
} 