'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';

export default function CourseRegistrationPage() {
  const [availableCourses, setAvailableCourses] = useState([]);
  const [selectedCourses, setSelectedCourses] = useState([]);
  const [semesters, setSemesters] = useState([]);
  const [selectedSemester, setSelectedSemester] = useState('');
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const router = useRouter();

  // Mock data - replace with actual API calls
  const mockSemesters = [
    { id: 1, name: 'Fall 2024', year: 2024, semester_type: 'Fall', is_active: true },
    { id: 2, name: 'Spring 2025', year: 2025, semester_type: 'Spring', is_active: true },
    { id: 3, name: 'Summer 2025', year: 2025, semester_type: 'Summer', is_active: false }
  ];

  const mockAvailableCourses = [
    {
      id: 'CS201',
      name: 'Data Structures and Algorithms',
      code: 'CS201',
      credits: 3,
      description: 'Advanced programming concepts and algorithm analysis',
      lecturer: {
        id: 'LEC004',
        name: 'Dr. Robert Wilson',
        email: 'robert.wilson@university.edu'
      },
      capacity: 30,
      enrolled: 25,
      semester_id: 1,
      fee: 1500.00
    },
    {
      id: 'MATH301',
      name: 'Linear Algebra',
      code: 'MATH301',
      credits: 4,
      description: 'Vector spaces, linear transformations, and eigenvalues',
      lecturer: {
        id: 'LEC005',
        name: 'Prof. Lisa Anderson',
        email: 'lisa.anderson@university.edu'
      },
      capacity: 25,
      enrolled: 20,
      semester_id: 1,
      fee: 1200.00
    },
    {
      id: 'ENG201',
      name: 'Advanced Writing',
      code: 'ENG201',
      credits: 3,
      description: 'Advanced composition and rhetoric',
      lecturer: {
        id: 'LEC006',
        name: 'Dr. James Brown',
        email: 'james.brown@university.edu'
      },
      capacity: 20,
      enrolled: 15,
      semester_id: 1,
      fee: 1000.00
    },
    {
      id: 'PHY101',
      name: 'Introduction to Physics',
      code: 'PHY101',
      credits: 4,
      description: 'Fundamental principles of physics',
      lecturer: {
        id: 'LEC007',
        name: 'Dr. Maria Garcia',
        email: 'maria.garcia@university.edu'
      },
      capacity: 35,
      enrolled: 30,
      semester_id: 1,
      fee: 1400.00
    }
  ];

  useEffect(() => {
    // Simulate API calls
    const fetchData = async () => {
      try {
        // Fetch semesters
        setSemesters(mockSemesters);
        setSelectedSemester(mockSemesters[0]?.id || '');
        
        // Fetch available courses
        setAvailableCourses(mockAvailableCourses);
        setLoading(false);
      } catch (err) {
        setError('Failed to load course data');
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleCourseSelection = (courseId) => {
    setSelectedCourses(prev => {
      if (prev.includes(courseId)) {
        return prev.filter(id => id !== courseId);
      } else {
        return [...prev, courseId];
      }
    });
  };

  const handleSemesterChange = (semesterId) => {
    setSelectedSemester(semesterId);
    setSelectedCourses([]); // Clear selections when semester changes
  };

  const getSelectedCoursesData = () => {
    return availableCourses.filter(course => selectedCourses.includes(course.id));
  };

  const calculateTotalFee = () => {
    return getSelectedCoursesData().reduce((total, course) => total + course.fee, 0);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    setError('');
    setSuccess('');

    if (selectedCourses.length === 0) {
      setError('Please select at least one course');
      setSubmitting(false);
      return;
    }

    try {
      // Simulate API call
      const registrationData = {
        studentId: localStorage.getItem("studentId"), // Get from auth context
        semesterId: parseInt(selectedSemester),
        courses: selectedCourses.map(courseId => ({
          courseId,
          enrollmentDate: new Date().toISOString().split('T')[0]
        }))
      };

      const response = await fetch('/api/courses/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(registrationData),
      });

      if (response.ok) {
        setSuccess('Course registration successful!');
        setSelectedCourses([]);
        // Optionally redirect to dashboard after a delay
        setTimeout(() => {
          router.push('/dashboard');
        }, 2000);
      } else {
        const errorData = await response.json();
        setError(errorData.message || 'Registration failed. Please try again.');
      }
    } catch (err) {
      setError('Network error. Please try again.');
      console.error('Registration error:', err);
    } finally {
      setSubmitting(false);
    }
  };

  const getAvailableCoursesForSemester = () => {
    return availableCourses.filter(course => course.semester_id === parseInt(selectedSemester));
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center py-12">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">Loading available courses...</p>
        </div>
      </div>
    );
  }

  return (
    <div>
      {/* Page Header with Back Button */}
      <div className="mb-8 flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Course Registration</h1>
          <p className="mt-2 text-gray-600">Select courses for the upcoming semester</p>
        </div>
        <button
          type="button"
          onClick={() => router.push('/dashboard')}
          className="bg-gray-200 hover:bg-gray-300 text-gray-800 px-4 py-2 rounded-md text-sm font-medium transition-colors"
        >
          Back
        </button>
      </div>

      {error && (
        <div className="mb-6 rounded-md bg-red-50 p-4">
          <div className="text-sm text-red-700">{error}</div>
        </div>
      )}

      {success && (
        <div className="mb-6 rounded-md bg-green-50 p-4">
          <div className="text-sm text-green-700">{success}</div>
        </div>
      )}

      <form onSubmit={handleSubmit}>
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Course Selection */}
          <div className="lg:col-span-2">
            <div className="bg-white rounded-lg shadow-md p-6">
              <h2 className="text-xl font-semibold text-gray-900 mb-6">Available Courses</h2>
              
              {/* Semester Selection */}
              <div className="mb-6">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Select Semester
                </label>
                <select
                  value={selectedSemester}
                  onChange={(e) => handleSemesterChange(e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                >
                  {semesters.map((semester) => (
                    <option key={semester.id} value={semester.id}>
                      {semester.name} {!semester.is_active && '(Not Active)'}
                    </option>
                  ))}
                </select>
              </div>

              {/* Course List */}
              <div className="space-y-4">
                {getAvailableCoursesForSemester().map((course) => (
                  <div key={course.id} className="border border-gray-200 rounded-lg p-4">
                    <div className="flex items-start space-x-4">
                      <input
                        type="checkbox"
                        id={course.id}
                        checked={selectedCourses.includes(course.id)}
                        onChange={() => handleCourseSelection(course.id)}
                        className="mt-1 h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded"
                      />
                      <div className="flex-1">
                        <div className="flex justify-between items-start">
                          <div>
                            <h3 className="text-lg font-medium text-gray-900">{course.name}</h3>
                            <p className="text-sm text-gray-600">Course Code: {course.code}</p>
                            <p className="text-sm text-gray-600">Credits: {course.credits}</p>
                            <p className="text-sm text-gray-600 mt-2">{course.description}</p>
                            <p className="text-sm text-gray-600 mt-1">
                              Lecturer: {course.lecturer.name}
                            </p>
                          </div>
                          <div className="text-right">
                            <p className="text-lg font-medium text-gray-900">${course.fee.toFixed(2)}</p>
                            <p className="text-sm text-gray-600">
                              {course.enrolled}/{course.capacity} enrolled
                            </p>
                            <div className="mt-1">
                              <div className="w-20 bg-gray-200 rounded-full h-2">
                                <div
                                  className="bg-indigo-600 h-2 rounded-full"
                                  style={{ width: `${(course.enrolled / course.capacity) * 100}%` }}
                                ></div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>

              {getAvailableCoursesForSemester().length === 0 && (
                <div className="text-center py-8">
                  <p className="text-gray-500">No courses available for the selected semester.</p>
                </div>
              )}
            </div>
          </div>

          {/* Registration Summary */}
          <div className="lg:col-span-1">
            <div className="bg-white rounded-lg shadow-md p-6">
              <h3 className="text-lg font-medium text-gray-900 mb-4">Registration Summary</h3>
              
              {selectedCourses.length === 0 ? (
                <p className="text-gray-500">No courses selected</p>
              ) : (
                <div className="space-y-4">
                  <div>
                    <p className="text-sm font-medium text-gray-700">Selected Courses:</p>
                    <ul className="mt-2 space-y-2">
                      {getSelectedCoursesData().map((course) => (
                        <li key={course.id} className="flex justify-between text-sm">
                          <span className="text-gray-600">{course.code}</span>
                          <span className="font-medium">${course.fee.toFixed(2)}</span>
                        </li>
                      ))}
                    </ul>
                  </div>
                  
                  <div className="border-t pt-4">
                    <div className="flex justify-between">
                      <span className="text-lg font-medium text-gray-900">Total Fee:</span>
                      <span className="text-lg font-bold text-indigo-600">
                        ${calculateTotalFee().toFixed(2)}
                      </span>
                    </div>
                  </div>

                  <button
                    type="submit"
                    disabled={submitting}
                    className="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-3 px-4 rounded-md font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                  >
                    {submitting ? (
                      <div className="flex items-center justify-center">
                        <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                          <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                          <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                        </svg>
                        Registering...
                      </div>
                    ) : (
                      'Register for Selected Courses'
                    )}
                  </button>
                </div>
              )}
            </div>
          </div>
        </div>
      </form>
    </div>
  );
} 