'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';

export default function LoginPage() {
  const [formData, setFormData] = useState({
    id: '',
    password: ''
  });
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');
  const router = useRouter();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  // const handleSubmit = async (e) => {
  //   e.preventDefault();
  //   setIsLoading(true);
  //   setError('');
  //
  //   try {
  //     // Check for dummy credentials
  //     if (formData.id === 'STU001' && formData.password === '1234') {
  //       // Simulate successful login
  //       const mockUserData = {
  //         id: 'STU001',
  //         name: 'Nana Asiedu',
  //         email: 'nana@student.com',
  //         role: 'student'
  //       };
  //
  //       // Store user data in localStorage
  //       localStorage.setItem('userToken', 'dummy-token-12345');
  //       localStorage.setItem('userData', JSON.stringify(mockUserData));
  //       localStorage.setItem("studentId", mockUserData.id);
  //
  //       // Redirect to dashboard
  //       router.push('/dashboard');
  //     } else {
  //       // Invalid credentials
  //       setError('Invalid email or password. Please use nana@student.com / 1234');
  //     }
  //   } catch (err) {
  //     setError('Network error. Please try again.');
  //     console.error('Login error:', err);
  //   } finally {
  //     setIsLoading(false);
  //   }
  // };


  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setError('');

    if (formData.id === 'STU001' && formData.password === '1234') {
      // Simulate successful login
      const mockUserData = {
        id: 'STU001',
        name: 'Nana Asiedu',
        email: 'nana@student.com',
        role: 'student'
      };

      // Store user data in localStorage
      localStorage.setItem('userToken', 'dummy-token-12345');
      localStorage.setItem('userData', JSON.stringify(mockUserData));
      localStorage.setItem("studentId", mockUserData.id);

      // Redirect to dashboard
      router.push('/dashboard');
      setIsLoading(false);
      return
    }

    try{
      const res = await fetch("http://localhost:8080/api/auth/login", {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      })

      if (!res.ok) {
        console.log(res.statusText);
      }

      const json = await res.json();
      console.log(json);

      if (!json.success) {
        setError(json.error);
      }
      else {
        localStorage.setItem('userToken', 'dummy-token-12345');
        localStorage.setItem('userData', JSON.stringify(json.data));
        localStorage.setItem("studentId", json.data.id);

        // Redirect to dashboard
        router.push('/dashboard');
      }


    } catch (err){
      console.log(err);
    }
    finally {
      setIsLoading(false);
    }
  }
  return (
    <div className="min-h-screen flex items-center justify-center bg-[#F8F8F8] py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8">
        <div>
          <img
            src="/UG_LOGO_SHORT1.png"
            alt="University of Ghana Logo"
            className="mx-auto h-20 w-auto mb-4"
          />
          <h2 className="mt-6 text-center text-3xl font-extrabold text-[#153D70]">
            Student Login
          </h2>
          <p className="mt-2 text-center text-sm text-[#666666]">
            Sign in to your student account
          </p>
        </div>

        <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
          <div className="rounded-md shadow-sm -space-y-px">
            <div>
              <label htmlFor="email" className="sr-only">
                Student Id
              </label>
              <input
                id="id"
                name="id"
                type="id"
                autoComplete="id"
                required
                className="appearance-none rounded-none relative block w-full px-3 py-2 border border-[#999999] placeholder-[#999999] text-[#333333] rounded-t-md focus:outline-none focus:ring-2 focus:ring-[#153D70] focus:border-[#153D70] focus:z-10 sm:text-sm bg-white"
                placeholder="Enter student Id"
                value={formData.id}
                onChange={handleInputChange}
              />
            </div>
            <div>
              <label htmlFor="password" className="sr-only">
                Password
              </label>
              <input
                id="password"
                name="password"
                type="password"
                autoComplete="current-password"
                required
                className="appearance-none rounded-none relative block w-full px-3 py-2 border border-[#999999] placeholder-[#999999] text-[#333333] rounded-b-md focus:outline-none focus:ring-2 focus:ring-[#153D70] focus:border-[#153D70] focus:z-10 sm:text-sm bg-white"
                placeholder="Password"
                value={formData.password}
                onChange={handleInputChange}
              />
            </div>
          </div>

          {error && (
            <div className="rounded-md bg-[#DC3545] bg-opacity-10 p-4">
              <div className="text-sm text-[#DC3545]">
                {error}
              </div>
            </div>
          )}

          <div>
            <button
              type="submit"
              disabled={isLoading}
              className="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-[#153D70] hover:bg-[#BA8F4A] focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-[#153D70] disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
            >
              {isLoading ? (
                <div className="flex items-center">
                  <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  Signing in...
                </div>
              ) : (
                'Sign in'
              )}
            </button>
          </div>

          <div className="flex items-center justify-between">
            <div className="text-sm">
              <a href="#" className="font-medium text-[#007BFF] hover:text-[#153D70]">
                Forgot your password?
              </a>
            </div>
            <div className="text-sm">
              <a href="/register" className="font-medium text-[#007BFF] hover:text-[#153D70]">
                Don't have an account?
              </a>
            </div>
          </div>
        </form>
        <div className="mt-4 p-3 bg-[#F8F8F8] border border-[#153D70] rounded-md">
          <p className="text-xs text-[#153D70] text-center">
            <strong>Demo Credentials:</strong><br />
            Id: STU001<br />
            Password: 1234
          </p>
        </div>
      </div>
    </div>
  );
} 