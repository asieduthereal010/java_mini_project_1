'use client';

import { useState, useEffect } from 'react';
import { useRouter, usePathname } from 'next/navigation';

export default function Dashboard() {
  const [studentData, setStudentData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('overview');
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const router = useRouter();
  const pathname = usePathname();

  // Mock data - replace with actual API calls
  const mockStudentData = {
    id: 'STU001',
    name: 'John Doe',
    email: 'john.doe@university.edu',
    dateOfBirth: '1995-03-15',
    courses: [
      {
        id: 'CS101',
        name: 'Introduction to Computer Science',
        code: 'CS101',
        lecturer: {
          id: 'LEC001',
          name: 'Dr. Sarah Johnson',
          email: 'sarah.johnson@university.edu'
        },
        progress: 75
      },
      {
        id: 'MATH201',
        name: 'Advanced Mathematics',
        code: 'MATH201',
        lecturer: {
          id: 'LEC002',
          name: 'Prof. Michael Chen',
          email: 'michael.chen@university.edu'
        },
        progress: 60
      },
      {
        id: 'ENG101',
        name: 'English Composition',
        code: 'ENG101',
        lecturer: {
          id: 'LEC003',
          name: 'Dr. Emily Davis',
          email: 'emily.davis@university.edu'
        },
        progress: 90
      }
    ],
    fees: {
      totalAmount: 5000.00,
      amountPaid: 3000.00,
      amountOwed: 2000.00
    }
  };

  useEffect(() => {
    // Simulate API call
    setTimeout(() => {
      setStudentData(mockStudentData);
      setLoading(false);
    }, 1000);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('userToken');
    localStorage.removeItem('userData');
    router.push('/login');
  };

  const handleRegisterCourse = () => {
    router.push('/courses/register');
  };

  const handlePayFees = () => {
    router.push('/fees/pay');
  };

  const handleViewFeeHistory = () => {
    router.push('/fees/history');
  };

  const handleViewFeeOverview = () => {
    router.push('/fees/overview');
  };

  const getSidebarContent = () => {
    // Determine sidebar content based on current path
    if (pathname.includes('/fees/pay')) {
      return (
        <div className="space-y-4">
          <h3 className="text-lg font-medium text-gray-900">Quick Actions</h3>
          <div className="space-y-3">
            <button
              onClick={handleViewFeeHistory}
              className="w-full bg-gray-100 hover:bg-gray-200 text-gray-700 py-2 px-4 rounded-md font-medium transition-colors"
            >
              View Payment History
            </button>
          </div>
        </div>
      );
    }

    if (pathname.includes('/fees/history')) {
      return (
        <div className="space-y-4">
          <h3 className="text-lg font-medium text-gray-900">Quick Actions</h3>
          <div className="space-y-3">
            <button
              onClick={handlePayFees}
              className="w-full bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-md text-sm font-medium transition-colors"
            >
              Make New Payment
            </button>
            <button
              onClick={handleViewFeeOverview}
              className="w-full bg-gray-100 hover:bg-gray-200 text-gray-700 px-4 py-2 rounded-md text-sm font-medium transition-colors"
            >
              Fee Overview
            </button>
          </div>
        </div>
      );
    }

    if (pathname.includes('/fees/overview')) {
      return (
        <div className="space-y-4">
          <h3 className="text-lg font-medium text-gray-900">Quick Actions</h3>
          <div className="space-y-3">
            <button
              onClick={handlePayFees}
              className="w-full bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-md text-sm font-medium transition-colors"
            >
              Make Payment
            </button>
            <button
              onClick={handleViewFeeHistory}
              className="w-full bg-gray-100 hover:bg-gray-200 text-gray-700 px-4 py-2 rounded-md text-sm font-medium transition-colors"
            >
              View Payment History
            </button>
          </div>
        </div>
      );
    }

    // Default dashboard sidebar
    return (
      <div className="space-y-4">
        <h3 className="text-lg font-medium text-gray-900">Quick Actions</h3>
        <div className="space-y-3">
          <button
            onClick={handleRegisterCourse}
            className="w-full bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Register for New Course
          </button>
          <button
            onClick={handlePayFees}
            className="w-full bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-md text-sm font-medium transition-colors"
          >
            Pay Fees
          </button>
        </div>
      </div>
    );
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">Loading dashboard...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <div className="flex items-center space-x-4">
              <button
                onClick={() => setSidebarOpen(!sidebarOpen)}
                className="p-2 rounded-md text-gray-400 hover:text-gray-500 hover:bg-gray-100"
              >
                <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
                </svg>
              </button>
              <h1 className="text-xl font-semibold text-gray-900">Student Dashboard</h1>
            </div>
            <div className="flex items-center space-x-4">
              <button
                onClick={handleLogout}
                className="bg-red-600 hover:bg-red-700 text-white px-4 py-2 rounded-md text-sm font-medium transition-colors"
              >
                Logout
              </button>
            </div>
          </div>
        </div>
      </header>

      <div className="flex">
        {/* Left Sidebar */}
        <div className={`fixed left-0 top-16 h-full bg-white shadow-lg border-r border-gray-200 transition-all duration-300 z-10 ${
          sidebarOpen ? 'w-80 translate-x-0' : 'w-80 -translate-x-full'
        }`}>
          <div className="p-6">
            {getSidebarContent()}
          </div>
        </div>

        {/* Main Content */}
        <div className={`flex-1 transition-all duration-300 ${sidebarOpen ? 'ml-80' : 'ml-0'}`}>
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            {/* Welcome Message */}
            <div className="mb-8 text-center">
              <h2 className="text-3xl font-bold text-gray-900">Welcome back, {studentData?.name}!</h2>
            </div>

            {/* Navigation Tabs - Only show on main dashboard */}
            {pathname === '/dashboard' && (
              <div className="bg-white rounded-lg shadow-md mb-8">
                <div className="border-b border-gray-200">
                  <nav className="-mb-px flex space-x-8 px-6">
                    <button
                      onClick={() => setActiveTab('overview')}
                      className={`py-4 px-1 border-b-2 font-medium text-sm ${
                        activeTab === 'overview'
                          ? 'border-indigo-500 text-indigo-600'
                          : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                      }`}
                    >
                      Overview
                    </button>
                    <button
                      onClick={() => setActiveTab('courses')}
                      className={`py-4 px-1 border-b-2 font-medium text-sm ${
                        activeTab === 'courses'
                          ? 'border-indigo-500 text-indigo-600'
                          : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                      }`}
                    >
                      My Courses
                    </button>
                    <button
                      onClick={() => setActiveTab('lecturers')}
                      className={`py-4 px-1 border-b-2 font-medium text-sm ${
                        activeTab === 'lecturers'
                          ? 'border-indigo-500 text-indigo-600'
                          : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                      }`}
                    >
                      My Lecturers
                    </button>
                  </nav>
                </div>

                <div className="p-6">
                  {/* Overview Tab */}
                  {activeTab === 'overview' && (
                    <div className="space-y-6">
                      {/* Fee Summary Card */}
                      <div className="bg-white rounded-lg shadow-md p-6">
                        <h3 className="text-lg font-medium text-gray-900 mb-4">Fee Summary</h3>
                        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                          <div className="text-center">
                            <p className="text-sm text-gray-600">Total Fees</p>
                            <p className="text-2xl font-bold text-gray-900">${studentData?.fees.totalAmount.toFixed(2)}</p>
                          </div>
                          <div className="text-center">
                            <p className="text-sm text-gray-600">Amount Paid</p>
                            <p className="text-2xl font-bold text-green-600">${studentData?.fees.amountPaid.toFixed(2)}</p>
                          </div>
                          <div className="text-center">
                            <p className="text-sm text-gray-600">Outstanding Balance</p>
                            <p className="text-2xl font-bold text-red-600">${studentData?.fees.amountOwed.toFixed(2)}</p>
                          </div>
                        </div>
                        <div className="mt-4">
                          <div className="flex justify-between text-sm text-gray-600 mb-2">
                            <span>Payment Progress</span>
                            <span>{((studentData?.fees.amountPaid / studentData?.fees.totalAmount) * 100).toFixed(1)}%</span>
                          </div>
                          <div className="w-full bg-gray-200 rounded-full h-2">
                            <div
                              className="bg-green-600 h-2 rounded-full transition-all duration-300"
                              style={{ width: `${(studentData?.fees.amountPaid / studentData?.fees.totalAmount) * 100}%` }}
                            ></div>
                          </div>
                        </div>
                      </div>

                      {/* Stats Cards */}
                      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                        <div className="bg-blue-50 rounded-lg p-4">
                          <h4 className="text-lg font-medium text-blue-900">Enrolled Courses</h4>
                          <p className="text-3xl font-bold text-blue-600">{studentData?.courses.length}</p>
                        </div>
                        <div className="bg-green-50 rounded-lg p-4">
                          <h4 className="text-lg font-medium text-green-900">Average Progress</h4>
                          <p className="text-3xl font-bold text-green-600">
                            {Math.round(studentData?.courses.reduce((acc, course) => acc + course.progress, 0) / studentData?.courses.length)}%
                          </p>
                        </div>
                        <div className="bg-red-50 rounded-lg p-4">
                          <h4 className="text-lg font-medium text-red-900">Fees Outstanding</h4>
                          <p className="text-3xl font-bold text-red-600">${studentData?.fees.amountOwed.toFixed(0)}</p>
                        </div>
                      </div>
                    </div>
                  )}

                  {/* Courses Tab */}
                  {activeTab === 'courses' && (
                    <div className="space-y-4">
                      <h3 className="text-lg font-medium text-gray-900 mb-4">My Enrolled Courses</h3>
                      {studentData?.courses.map((course) => (
                        <div key={course.id} className="border border-gray-200 rounded-lg p-4">
                          <div className="flex justify-between items-start">
                            <div>
                              <h4 className="text-lg font-medium text-gray-900">{course.name}</h4>
                              <p className="text-sm text-gray-600">Course Code: {course.code}</p>
                              <p className="text-sm text-gray-600">Lecturer: {course.lecturer.name}</p>
                            </div>
                            <div className="text-right">
                              <div className="text-sm text-gray-600">Progress</div>
                              <div className="text-lg font-medium text-indigo-600">{course.progress}%</div>
                              <div className="w-20 bg-gray-200 rounded-full h-2 mt-1">
                                <div
                                  className="bg-indigo-600 h-2 rounded-full"
                                  style={{ width: `${course.progress}%` }}
                                ></div>
                              </div>
                            </div>
                          </div>
                        </div>
                      ))}
                    </div>
                  )}

                  {/* Lecturers Tab */}
                  {activeTab === 'lecturers' && (
                    <div className="space-y-4">
                      <h3 className="text-lg font-medium text-gray-900 mb-4">My Course Lecturers</h3>
                      {studentData?.courses.map((course) => (
                        <div key={course.lecturer.id} className="border border-gray-200 rounded-lg p-4">
                          <div className="flex justify-between items-center">
                            <div>
                              <h4 className="text-lg font-medium text-gray-900">{course.lecturer.name}</h4>
                              <p className="text-sm text-gray-600">{course.lecturer.email}</p>
                              <p className="text-sm text-indigo-600">Teaching: {course.name}</p>
                            </div>
                            <button className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-md text-sm font-medium transition-colors">
                              Contact
                            </button>
                          </div>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>
            )}

            {/* Page-specific content will be rendered here by child pages */}
            {pathname !== '/dashboard' && (
              <div className="text-center py-12">
                <p className="text-gray-500">Page content will be rendered here</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
} 