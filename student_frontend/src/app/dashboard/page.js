'use client';

import { useState, useEffect } from 'react';

export default function Dashboard() {
  const [studentData, setStudentData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('overview');

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

  // useEffect(() => {
  //   // Simulate API call
  //   setTimeout(() => {
  //     setStudentData(mockStudentData);
  //     setLoading(false);
  //   }, 1000);
  // }, []);

  useEffect(() =>{
    async function loadStudentData() {
      setLoading(true);
      const studentId = localStorage.getItem("studentId")
      try{
        const res = await fetch("http://localhost:8080/api/student/dashboard?studentId=" + studentId);
        if (!res.ok){
            console.log("Didn't work")
        }
        const data = await res.json();
        console.log(data);
        setStudentData(data.data)
      }
      catch(err){
        console.log(err);
      }
      finally{
        setLoading(false);
      }
    }
    loadStudentData();
  }, [])

  if (loading) {
    return (
      <div className="flex items-center justify-center py-12">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">Loading dashboard...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="bg-[#F8F8F8] min-h-screen">
      {/* Navigation Tabs */}
      <div className="bg-white rounded-lg shadow-md mb-8">
        <div className="border-b border-[#999999]">
          <nav className="-mb-px flex space-x-8 px-6">
            <button
              onClick={() => setActiveTab('overview')}
              className={`py-4 px-1 border-b-2 font-medium text-sm transition-colors ${
                activeTab === 'overview'
                  ? 'border-[#153D70] text-[#153D70]'
                  : 'border-transparent text-[#666666] hover:text-[#153D70] hover:border-[#BA8F4A]'
              }`}
            >
              Overview
            </button>
            <button
              onClick={() => setActiveTab('courses')}
              className={`py-4 px-1 border-b-2 font-medium text-sm transition-colors ${
                activeTab === 'courses'
                  ? 'border-[#153D70] text-[#153D70]'
                  : 'border-transparent text-[#666666] hover:text-[#153D70] hover:border-[#BA8F4A]'
              }`}
            >
              My Courses
            </button>
            <button
              onClick={() => setActiveTab('lecturers')}
              className={`py-4 px-1 border-b-2 font-medium text-sm transition-colors ${
                activeTab === 'lecturers'
                  ? 'border-[#153D70] text-[#153D70]'
                  : 'border-transparent text-[#666666] hover:text-[#153D70] hover:border-[#BA8F4A]'
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
                <h3 className="text-lg font-medium text-[#153D70] mb-4">Fee Summary</h3>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                  <div className="text-center">
                    <p className="text-sm text-[#666666]">Total Fees</p>
                    <p className="text-2xl font-bold text-[#153D70]">${studentData?.fees.totalAmount.toFixed(2)}</p>
                  </div>
                  <div className="text-center">
                    <p className="text-sm text-gray-600">Amount Paid</p>
                    <p className="text-2xl font-bold text-green-600">${studentData?.fees.amountPaid.toFixed(2)}</p>
                  </div>
                  <div className="text-center">
                    <p className="text-sm text-[#666666]">Outstanding Balance</p>
                    <p className="text-2xl font-bold text-[#DC3545]">${studentData?.fees.amountOwed.toFixed(2)}</p>
                  </div>
                </div>
                <div className="mt-4">
                  <div className="flex justify-between text-sm text-[#666666] mb-2">
                    <span>Payment Progress</span>
                    <span>{((studentData?.fees.amountPaid / studentData?.fees.totalAmount) * 100).toFixed(1)}%</span>
                  </div>
                  <div className="w-full bg-[#999999] rounded-full h-2">
                    <div
                      className="bg-[#28A745] h-2 rounded-full transition-all duration-300"
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
              <h3 className="text-lg font-medium text-[#153D70] mb-4">My Enrolled Courses</h3>
              {studentData?.courses.map((course) => (
                <div key={course.id} className="border border-[#999999] rounded-lg p-4 bg-white">
                  <div>
                    <h4 className="text-lg font-medium text-[#153D70]">{course.name}</h4>
                    <p className="text-sm text-[#666666]">Course Code: {course.code}</p>
                    <p className="text-sm text-[#666666]">Lecturer: {course.lecturer.name}</p>
                  </div>
                </div>
              ))}
            </div>
          )}

          {/* Lecturers Tab */}
          {activeTab === 'lecturers' && (
            <div className="space-y-4">
              <h3 className="text-lg font-medium text-[#153D70] mb-4">My Course Lecturers</h3>
              {studentData?.courses.map((course) => (
                <div key={course.lecturer.id} className="border border-[#999999] rounded-lg p-4 bg-white">
                  <div className="flex justify-between items-center">
                    <div>
                      <h4 className="text-lg font-medium text-[#153D70]">{course.lecturer.name}</h4>
                      <p className="text-sm text-[#666666]">{course.lecturer.email}</p>
                      <p className="text-sm text-[#BA8F4A]">Teaching: {course.name}</p>
                    </div>
                    {/*<button className="bg-[#153D70] hover:bg-[#BA8F4A] text-white px-4 py-2 rounded-md text-sm font-medium transition-colors">*/}
                    {/*  Contact*/}
                    {/*</button>*/}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
} 