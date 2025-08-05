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

  // Generate academic year options from 2020 to 2025
  const academicYearOptions = [];
  for (let year = 2020; year <= 2025; year++) {
    academicYearOptions.push(`${year}-${year + 1}`);
  }

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
          <h3 className="text-lg font-medium text-[#BA8F4A] text-center">Quick Actions</h3>
          <div className="space-y-0">
            <button
              onClick={handleViewFeeHistory}
              className="w-full text-white py-3 px-4 font-medium transition-colors flex items-center gap-2 hover:bg-white/10"
            >
              {/* Clock icon */}
              <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
              View Payment History
            </button>
            <div className="border-t border-white/20"></div>
          </div>
        </div>
      );
    }

    if (pathname?.includes('/fees/history')) {
      return (
        <div className="space-y-4">
          <h3 className="text-lg font-medium text-[#BA8F4A] text-center">Quick Actions</h3>
          <div className="space-y-0">
            <button
              onClick={handlePayFees}
              className="w-full text-white py-3 px-4 font-medium transition-colors flex items-center gap-2 hover:bg-white/10"
            >
              {/* Credit Card icon */}
              <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 9V7a2 2 0 00-2-2H9a2 2 0 00-2 2v2M5 11h14M5 15h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2z" /></svg>
              Make New Payment
            </button>
            <div className="border-t border-white/20"></div>
            <button
              onClick={handleViewFeeOverview}
              className="w-full text-white py-3 px-4 font-medium transition-colors flex items-center gap-2 hover:bg-white/10"
            >
              {/* Document icon */}
              <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M7 7h10M7 11h10M7 15h6" /></svg>
              Fee Overview
            </button>
            <div className="border-t border-white/20"></div>
          </div>
        </div>
      );
    }

    if (pathname?.includes('/fees/overview')) {
      return (
        <div className="space-y-4">
          <h3 className="text-lg font-medium text-[#BA8F4A] text-center">Quick Actions</h3>
          <div className="space-y-0">
            <button
              onClick={handlePayFees}
              className="w-full text-white py-3 px-4 font-medium transition-colors flex items-center gap-2 hover:bg-white/10"
            >
              {/* Credit Card icon */}
              <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 9V7a2 2 0 00-2-2H9a2 2 0 00-2 2v2M5 11h14M5 15h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2z" /></svg>
              Make Payment
            </button>
            <div className="border-t border-white/20"></div>
            <button
              onClick={handleViewFeeHistory}
              className="w-full text-white py-3 px-4 font-medium transition-colors flex items-center gap-2 hover:bg-white/10"
            >
              {/* Clock icon */}
              <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
              View Payment History
            </button>
            <div className="border-t border-white/20"></div>
          </div>
        </div>
      );
    }

    if (pathname?.includes('/courses/register')) {
      return (
        <div className="space-y-4">
          <h3 className="text-lg font-medium text-[#BA8F4A] text-center">Quick Actions</h3>
          <div className="space-y-0">
            <button
              onClick={handlePayFees}
              className="w-full text-white py-3 px-4 font-medium transition-colors flex items-center gap-2 hover:bg-white/10"
            >
              {/* Credit Card icon */}
              <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 9V7a2 2 0 00-2-2H9a2 2 0 00-2 2v2M5 11h14M5 15h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2z" /></svg>
              Pay Fees
            </button>
            <div className="border-t border-white/20"></div>
          </div>
        </div>
      );
    }

    // Default dashboard sidebar
    return (
      <div className="space-y-4">
        <h3 className="text-lg font-medium text-[#BA8F4A] text-center">Quick Actions</h3>
        <div className="space-y-0">
          <button
            onClick={handleRegisterCourse}
            className="w-full text-white py-3 px-4 font-medium transition-colors flex items-center gap-2 hover:bg-white/10"
          >
            {/* Academic Cap icon */}
            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 14l9-5-9-5-9 5 9 5zm0 0v6m0 0H6m6 0h6" /></svg>
            Register for New Course
          </button>
          <div className="border-t border-white/20"></div>
          <button
            onClick={handlePayFees}
            className="w-full text-white py-3 px-4 font-medium transition-colors flex items-center gap-2 hover:bg-white/10"
          >
            {/* Credit Card icon */}
            <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 9V7a2 2 0 00-2-2H9a2 2 0 00-2 2v2M5 11h14M5 15h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2z" /></svg>
            Pay Fees
          </button>
          <div className="border-t border-white/20"></div>
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
        <div className="min-h-screen bg-[#F8F8F8]">
          {/* Header */}
          <header className="bg-[#0F2A4A] shadow-sm sticky top-0 z-20">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
              <div className="flex justify-between items-center h-16">
                <button
                  onClick={() => setSidebarOpen(!sidebarOpen)}
                  className="p-2 rounded-md text-[#BA8F4A] hover:text-white hover:bg-[#0F2A4A]/80"
                >
                  <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
                  </svg>
                </button>
                
                <div className="flex items-center space-x-4">
                  <img 
                    src="/UG_LOGO_SHORT1.png" 
                    alt="University of Ghana Logo" 
                    className="h-10 w-auto"
                  />
                  <span
                    role="button"
                    tabIndex={0}
                    onClick={() => router.push('/dashboard')}
                    onKeyPress={e => { if (e.key === 'Enter' || e.key === ' ') router.push('/dashboard'); }}
                    className="text-xl font-semibold text-[#BA8F4A] cursor-pointer hover:underline focus:outline-none focus:ring-2 focus:ring-[#BA8F4A]"
                  >
                    Student Dashboard
                  </span>
                </div>

                <button
                  onClick={handleLogout}
                  className="text-[#DC3545] hover:text-[#BA8F4A] font-medium transition-colors cursor-pointer"
                >
                  Logout
                </button>
              </div>
            </div>
          </header>

          <div className="flex">
            {/* Left Sidebar */}
            <div className={`fixed left-0 top-16 h-[calc(100vh-4rem)] bg-[#0A1F3A] shadow-lg border-r border-[#BA8F4A] transition-all duration-300 z-10 ${
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
                    <h2 className="text-3xl font-bold text-[#153D70]">Welcome back, {studentData?.name}!</h2>
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
