'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import styles from './dashboard.module.css';

export default function DashboardClient() {
  const [activeTab, setActiveTab] = useState('students');
  const [students, setStudents] = useState([]);
  const [lecturerTA, setLecturerTA] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const router = useRouter();

  useEffect(() => {
    const fetchData = async () => {
      try {
        setLoading(true);
        setError(null);
        
        // Fetch students data
        const studentsResponse = await fetch('http://localhost:8080/dashboard/students');
        if (!studentsResponse.ok) {
          throw new Error('Failed to fetch students data');
        }
        const studentsData = await studentsResponse.json();
        
        // Fetch lecturer-TA data
        const lecturerTAResponse = await fetch('http://localhost:8080/dashboard/lecturers');
        if (!lecturerTAResponse.ok) {
          throw new Error('Failed to fetch lecturer-TA data');
        }
        const lecturerTAData = await lecturerTAResponse.json();
        
        setStudents(studentsData);
        setLecturerTA(lecturerTAData);
      } catch (err) {
        console.error('Error fetching data:', err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleLogout = () => {
    // TODO: Implement actual logout
    router.push('/login');
  };

  if (loading) {
    return (
      <div className={styles.container}>
        <nav className={styles.nav}>
          <div className={styles.navContainer}>
            <div className={styles.navContent}>
              <div className={styles.navLeft}>
                <div className={styles.logo}>
                    School Management System
                </div>
              </div>
              <div>
                <button
                  onClick={handleLogout}
                  className={styles.logoutButton}
                >
                  <span>Logout</span>
                </button>
              </div>
            </div>
          </div>
        </nav>
        <main className={styles.main}>
          <div className={styles.content}>
            <div className={styles.card}>
              <div className={styles.cardHeader}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
                  <div style={{
                    width: '24px',
                    height: '24px',
                    border: '3px solid #f3f4f6',
                    borderTop: '3px solid #3b82f6',
                    borderRadius: '50%',
                    animation: 'spin 1s linear infinite'
                  }}></div>
                  <h2 className={styles.cardTitle} style={{ margin: 0 }}>Loading Dashboard Data</h2>
                </div>
              </div>
              <div style={{ 
                padding: '40px 20px', 
                textAlign: 'center',
                color: '#6b7280'
              }}>
                <p style={{ margin: '0 0 16px 0', fontSize: '16px' }}>
                  Fetching the latest information...
                </p>
                <div style={{
                  width: '200px',
                  height: '4px',
                  backgroundColor: '#f3f4f6',
                  borderRadius: '2px',
                  margin: '0 auto',
                  overflow: 'hidden'
                }}>
                  <div style={{
                    width: '60%',
                    height: '100%',
                    backgroundColor: '#3b82f6',
                    borderRadius: '2px',
                    animation: 'pulse 2s ease-in-out infinite'
                  }}></div>
                </div>
              </div>
            </div>
          </div>
        </main>
        <style jsx>{`
          @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
          }
          @keyframes pulse {
            0%, 100% { opacity: 1; }
            50% { opacity: 0.5; }
          }
        `}</style>
      </div>
    );
  }

  if (error) {
    return (
      <div className={styles.container}>
        <nav className={styles.nav}>
          <div className={styles.navContainer}>
            <div className={styles.navContent}>
              <div className={styles.navLeft}>
                <div className={styles.logo}>
                    School Management System
                </div>
              </div>
              <div>
                <button
                  onClick={handleLogout}
                  className={styles.logoutButton}
                >
                  <span>Logout</span>
                </button>
              </div>
            </div>
          </div>
        </nav>
        <main className={styles.main}>
          <div className={styles.content}>
            <div className={styles.card}>
              <div className={styles.cardHeader}>
                <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
                  <div style={{
                    width: '24px',
                    height: '24px',
                    borderRadius: '50%',
                    backgroundColor: '#fef2f2',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center'
                  }}>
                    <span style={{ color: '#dc2626', fontSize: '16px', fontWeight: 'bold' }}>!</span>
                  </div>
                  <h2 className={styles.cardTitle} style={{ margin: 0, color: '#dc2626' }}>Connection Error</h2>
                </div>
              </div>
              <div style={{ 
                padding: '32px 20px', 
                textAlign: 'center'
              }}>
                <div style={{
                  width: '64px',
                  height: '64px',
                  margin: '0 auto 24px auto',
                  borderRadius: '50%',
                  backgroundColor: '#fef2f2',
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                  border: '2px solid #fecaca'
                }}>
                  <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="#dc2626" strokeWidth="2">
                    <circle cx="12" cy="12" r="10"></circle>
                    <line x1="15" y1="9" x2="9" y2="15"></line>
                    <line x1="9" y1="9" x2="15" y2="15"></line>
                  </svg>
                </div>
                <h3 style={{ 
                  margin: '0 0 12px 0', 
                  fontSize: '18px', 
                  fontWeight: '600',
                  color: '#1f2937'
                }}>
                  Unable to Load Data
                </h3>
                <p style={{ 
                  margin: '0 0 24px 0', 
                  fontSize: '14px',
                  color: '#6b7280',
                  maxWidth: '400px',
                  marginLeft: 'auto',
                  marginRight: 'auto'
                }}>
                  {error}
                </p>
                <div style={{ display: 'flex', gap: '12px', justifyContent: 'center' }}>
                  <button 
                    onClick={() => window.location.reload()} 
                    style={{
                      padding: '10px 20px',
                      backgroundColor: '#3b82f6',
                      color: 'white',
                      border: 'none',
                      borderRadius: '6px',
                      fontSize: '14px',
                      fontWeight: '500',
                      cursor: 'pointer',
                      transition: 'background-color 0.2s'
                    }}
                    onMouseOver={(e) => e.target.style.backgroundColor = '#2563eb'}
                    onMouseOut={(e) => e.target.style.backgroundColor = '#3b82f6'}
                  >
                    Try Again
                  </button>
                  <button 
                    onClick={() => router.push('/dashboard')} 
                    style={{
                      padding: '10px 20px',
                      backgroundColor: '#f3f4f6',
                      color: '#374151',
                      border: 'none',
                      borderRadius: '6px',
                      fontSize: '14px',
                      fontWeight: '500',
                      cursor: 'pointer',
                      transition: 'background-color 0.2s'
                    }}
                    onMouseOver={(e) => e.target.style.backgroundColor = '#e5e7eb'}
                    onMouseOut={(e) => e.target.style.backgroundColor = '#f3f4f6'}
                  >
                    Refresh Page
                  </button>
                </div>
              </div>
            </div>
          </div>
        </main>
      </div>
    );
  }

  return (
    <div className={styles.container}>
      <nav className={styles.nav}>
        <div className={styles.navContainer}>
          <div className={styles.navContent}>
            <div className={styles.navLeft}>
              <div className={styles.logo}>
                  School Management System
              </div>
            </div>
            <div>
              <button
                onClick={handleLogout}
                className={styles.logoutButton}
              >
                <span>Logout</span>
              </button>
            </div>
          </div>
        </div>
      </nav>

      <main className={styles.main}>
        <div className={styles.content}>
          <div className={styles.tabContainer}>
            <nav className={styles.tabNav}>
              <button
                onClick={() => setActiveTab('students')}
                className={`${styles.tabButton} ${activeTab === 'students' ? styles.active : ''}`}
              >
                <span>Student Fees</span>
              </button>
              <button
                onClick={() => setActiveTab('lecturers')}
                className={`${styles.tabButton} ${activeTab === 'lecturers' ? styles.active : ''}`}
              >
                <span>Lecturer-TA Relationships</span>
              </button>
            </nav>
          </div>

          <div>
            {activeTab === 'students' ? (
              <div className={styles.card}>
                <div className={styles.cardHeader}>
                  <h2 className={styles.cardTitle}>Student Fee Overview</h2>
                </div>
                <div className="overflow-x-auto">
                  <table className={styles.table}>
                    <thead className={styles.tableHeader}>
                      <tr>
                        <th className={styles.tableHeaderCell}>
                          Student ID
                        </th>
                        <th className={styles.tableHeaderCell}>
                          Student Name
                        </th>
                        <th className={styles.tableHeaderCell}>
                          Courses
                        </th>
                        <th className={styles.tableHeaderCell}>
                          Fees Owed
                        </th>
                        <th className={styles.tableHeaderCell}>
                          Fees Paid
                        </th>
                        <th className={styles.tableHeaderCell}>
                          Balance
                        </th>
                      </tr>
                    </thead>
                    <tbody className={styles.tableBody}>
                      {students.map((student) => (
                        <tr key={student.id} className={styles.tableRow}>
                          <td className={styles.tableCell}>
                            <div className={styles.userName}>{student.id}</div>
                          </td>
                          <td className={styles.tableCell}>
                            <div className={styles.userName}>{student.name}</div>
                          </td>
                          <td className={styles.tableCell}>
                            <div className={styles.tagContainer}>
                              {student.courses?.filter(Boolean).map((course, index) => (
                                <span
                                  key={index}
                                  className={`${styles.tag} ${styles.course}`}
                                >
                                  {course}
                                </span>
                              ))}
                            </div>
                          </td>
                          <td className={styles.tableCell}>
                            <div className={styles.balance}>
                              GH₵{student.feesOwed.toLocaleString()}
                            </div>
                          </td>
                          <td className={styles.tableCell}>
                            <div className={styles.balance}>
                              GH₵{student.feesPaid.toLocaleString()}
                            </div>
                          </td>
                          <td className={styles.tableCell}>
                            <div className={`${styles.balance} ${
                              student.feesOwed - student.feesPaid > 0 
                                ? styles.negative 
                                : styles.positive
                            }`}>
                              GH₵{(student.feesOwed - student.feesPaid).toLocaleString()}
                            </div>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            ) : (
              <div className={styles.card}>
                <div className={styles.cardHeader}>
                  <h2 className={styles.cardTitle}>Lecturer-TA Assignments</h2>
                </div>
                <div className="overflow-x-auto">
                  <table className={styles.table}>
                    <thead className={styles.tableHeader}>
                      <tr>
                        <th className={styles.tableHeaderCell}>
                          Lecturer
                        </th>
                        <th className={styles.tableHeaderCell}>
                          Teaching Assistants
                        </th>
                        <th className={styles.tableHeaderCell}>
                          Courses
                        </th>
                      </tr>
                    </thead>
                    <tbody className={styles.tableBody}>
                      {lecturerTA.map((item) => (
                        <tr key={item.id} className={styles.tableRow}>
                          <td className={styles.tableCell}>
                            <div className={styles.userName}>{item.lecturer}</div>
                          </td>
                          <td className={styles.tableCell}>
                            <div className={styles.tagContainer}>
                              {item.teachingAssistants?.filter(Boolean).map((ta, index) => (
                                <span
                                  key={index}
                                  className={`${styles.tag} ${styles.ta}`}
                                >
                                  {ta}
                                </span>
                              ))}
                            </div>
                          </td>
                          <td className={styles.tableCell}>
                            <div className={styles.tagContainer}>
                              {item.courses?.filter(Boolean).map((course, index) => (
                                <span
                                  key={index}
                                  className={`${styles.tag} ${styles.lecturer}`}
                                >
                                  {course}
                                </span>
                              ))}
                            </div>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            )}
          </div>
        </div>
      </main>
    </div>
  );
} 