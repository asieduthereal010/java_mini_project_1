'use client';

import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";
import { useState, useEffect } from 'react';
import { useRouter, usePathname } from 'next/navigation';

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export default function RootLayout({ children }) {
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const [studentData, setStudentData] = useState(null);
  const router = useRouter();
  const pathname = usePathname();

  // Check if user is authenticated
  const isAuthenticated = () => {
    if (typeof window !== 'undefined') {
      return localStorage.getItem('userToken') !== null;
    }
    return false;
  };

  // Check if current page should show the dashboard layout
  const shouldShowDashboardLayout = () => {
    const dashboardPages = ['/dashboard', '/fees', '/courses'];
    return dashboardPages.some(page => pathname?.startsWith(page));
  };

  useEffect(() => {
    // Load student data if authenticated
    if (isAuthenticated() && shouldShowDashboardLayout()) {
      const userData = localStorage.getItem('userData');
      if (userData) {
        setStudentData(JSON.parse(userData));
      }
    }
  }, [pathname]);

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
    if (pathname?.includes('/fees/pay')) {
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

    if (pathname?.includes('/fees/history')) {
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

    if (pathname?.includes('/fees/overview')) {
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

    if (pathname?.includes('/courses/register')) {
      return (
        <div className="space-y-4">
          <h3 className="text-lg font-medium text-gray-900">Quick Actions</h3>
          <div className="space-y-3">
            <button
              onClick={() => router.push('/dashboard')}
              className="w-full bg-gray-100 hover:bg-gray-200 text-gray-700 py-2 px-4 rounded-md font-medium transition-colors"
            >
              Back to Dashboard
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

  // If not authenticated or not a dashboard page, render without navigation
  if (!isAuthenticated() || !shouldShowDashboardLayout()) {
    return (
      <html lang="en">
        <body className={`${geistSans.variable} ${geistMono.variable} antialiased`}>
          {children}
        </body>
      </html>
    );
  }

  return (
    <html lang="en">
      <body className={`${geistSans.variable} ${geistMono.variable} antialiased`}>
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
                  <span
                    role="button"
                    tabIndex={0}
                    onClick={() => router.push('/dashboard')}
                    onKeyPress={e => { if (e.key === 'Enter' || e.key === ' ') router.push('/dashboard'); }}
                    className="text-xl font-semibold text-gray-900 cursor-pointer hover:underline focus:outline-none focus:ring-2 focus:ring-indigo-500"
                  >
                    Student Dashboard
                  </span>
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
                {/* Welcome Message - Only show on main dashboard */}
                {pathname === '/dashboard' && (
                  <div className="mb-8 text-center">
                    <h2 className="text-3xl font-bold text-gray-900">Welcome back, {studentData?.name}!</h2>
                  </div>
                )}
                
                {children}
              </div>
            </div>
          </div>
        </div>
      </body>
    </html>
  );
}
