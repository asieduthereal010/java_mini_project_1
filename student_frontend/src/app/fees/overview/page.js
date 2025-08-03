'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';

export default function FeeOverviewPage() {
  const [feeData, setFeeData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const router = useRouter();

  // Mock fee data
  const mockFeeData = {
    studentId: 'STU001',
    studentName: 'Nana Asiedu',
    totalAmount: 5000.00,
    amountPaid: 3800.00,
    amountOwed: 1200.00,
    dueDate: '2024-12-31',
    paymentHistory: [
      {
        id: 'PAY001',
        amount: 1500.00,
        date: '2024-01-15',
        description: 'Tuition Fee Payment'
      },
      {
        id: 'PAY002',
        amount: 1000.00,
        date: '2024-02-20',
        description: 'Course Registration Fee'
      },
      {
        id: 'PAY003',
        amount: 500.00,
        date: '2024-03-10',
        description: 'Partial Tuition Payment'
      },
      {
        id: 'PAY004',
        amount: 800.00,
        date: '2024-04-05',
        description: 'Library and Lab Fees'
      }
    ],
    feeBreakdown: [
      {
        category: 'Tuition Fees',
        amount: 3000.00,
        paid: 2500.00,
        remaining: 500.00
      },
      {
        category: 'Course Registration',
        amount: 1000.00,
        paid: 1000.00,
        remaining: 0.00
      },
      {
        category: 'Library Fees',
        amount: 500.00,
        paid: 300.00,
        remaining: 200.00
      },
      {
        category: 'Laboratory Fees',
        amount: 500.00,
        paid: 0.00,
        remaining: 500.00
      }
    ]
  };

  useEffect(() => {
    // Simulate API call
    setTimeout(() => {
      setFeeData(mockFeeData);
      setLoading(false);
    }, 1000);
  }, []);

  const formatCurrency = (amount) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(amount);
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  };

  const getPaymentProgress = () => {
    if (!feeData) return 0;
    return (feeData.amountPaid / feeData.totalAmount) * 100;
  };

  const getDaysUntilDue = () => {
    if (!feeData?.dueDate) return 0;
    const dueDate = new Date(feeData.dueDate);
    const today = new Date();
    const diffTime = dueDate - today;
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return diffDays;
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">Loading fee information...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="mb-8">
          <button
            onClick={() => router.back()}
            className="text-indigo-600 hover:text-indigo-500 mb-4 flex items-center"
          >
            ← Back
          </button>
          <h1 className="text-3xl font-bold text-gray-900">Fee Overview</h1>
          <p className="mt-2 text-gray-600">Complete overview of your fee status and outstanding balance</p>
        </div>

        {error && (
          <div className="mb-6 rounded-md bg-red-50 p-4">
            <div className="text-sm text-red-700">{error}</div>
          </div>
        )}

        {/* Main Fee Summary */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
          {/* Outstanding Balance Card */}
          <div className="bg-white rounded-lg shadow-md p-6 border-l-4 border-red-500">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <svg className="w-8 h-8 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1" />
                </svg>
              </div>
              <div className="ml-4">
                <p className="text-sm font-medium text-gray-600">Outstanding Balance</p>
                <p className="text-2xl font-bold text-red-600">{formatCurrency(feeData?.amountOwed)}</p>
                <p className="text-xs text-gray-500">
                  Due by {formatDate(feeData?.dueDate)} ({getDaysUntilDue()} days remaining)
                </p>
              </div>
            </div>
          </div>

          {/* Total Paid Card */}
          <div className="bg-white rounded-lg shadow-md p-6 border-l-4 border-green-500">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <svg className="w-8 h-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              <div className="ml-4">
                <p className="text-sm font-medium text-gray-600">Total Paid</p>
                <p className="text-2xl font-bold text-green-600">{formatCurrency(feeData?.amountPaid)}</p>
                <p className="text-xs text-gray-500">
                  {getPaymentProgress().toFixed(1)}% of total fees
                </p>
              </div>
            </div>
          </div>

          {/* Total Fees Card */}
          <div className="bg-white rounded-lg shadow-md p-6 border-l-4 border-blue-500">
            <div className="flex items-center">
              <div className="flex-shrink-0">
                <svg className="w-8 h-8 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                </svg>
              </div>
              <div className="ml-4">
                <p className="text-sm font-medium text-gray-600">Total Fees</p>
                <p className="text-2xl font-bold text-blue-600">{formatCurrency(feeData?.totalAmount)}</p>
                <p className="text-xs text-gray-500">Complete fee structure</p>
              </div>
            </div>
          </div>
        </div>

        {/* Payment Progress */}
        <div className="bg-white rounded-lg shadow-md p-6 mb-8">
          <h2 className="text-lg font-medium text-gray-900 mb-4">Payment Progress</h2>
          <div className="mb-4">
            <div className="flex justify-between text-sm text-gray-600 mb-2">
              <span>Payment Progress</span>
              <span>{getPaymentProgress().toFixed(1)}%</span>
            </div>
            <div className="w-full bg-gray-200 rounded-full h-3">
              <div
                className="bg-green-600 h-3 rounded-full transition-all duration-300"
                style={{ width: `${getPaymentProgress()}%` }}
              ></div>
            </div>
          </div>
          <div className="grid grid-cols-2 gap-4 text-sm">
            <div>
              <span className="text-gray-600">Paid:</span>
              <span className="ml-2 font-medium text-green-600">{formatCurrency(feeData?.amountPaid)}</span>
            </div>
            <div>
              <span className="text-gray-600">Remaining:</span>
              <span className="ml-2 font-medium text-red-600">{formatCurrency(feeData?.amountOwed)}</span>
            </div>
          </div>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* Fee Breakdown */}
          <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-lg font-medium text-gray-900 mb-4">Fee Breakdown</h2>
            <div className="space-y-4">
              {feeData?.feeBreakdown.map((item, index) => (
                <div key={index} className="border border-gray-200 rounded-lg p-4">
                  <div className="flex justify-between items-center mb-2">
                    <h3 className="font-medium text-gray-900">{item.category}</h3>
                    <span className="text-sm font-medium text-gray-600">
                      {formatCurrency(item.amount)}
                    </span>
                  </div>
                  <div className="flex justify-between text-sm">
                    <span className="text-green-600">Paid: {formatCurrency(item.paid)}</span>
                    <span className="text-red-600">Remaining: {formatCurrency(item.remaining)}</span>
                  </div>
                  {item.remaining > 0 && (
                    <div className="mt-2">
                      <div className="w-full bg-gray-200 rounded-full h-2">
                        <div
                          className="bg-green-600 h-2 rounded-full"
                          style={{ width: `${(item.paid / item.amount) * 100}%` }}
                        ></div>
                      </div>
                    </div>
                  )}
                </div>
              ))}
            </div>
          </div>

          {/* Recent Payments */}
          <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-lg font-medium text-gray-900 mb-4">Recent Payments</h2>
            <div className="space-y-4">
              {feeData?.paymentHistory.slice(0, 5).map((payment) => (
                <div key={payment.id} className="flex justify-between items-center border-b border-gray-100 pb-3">
                  <div>
                    <p className="font-medium text-gray-900">{payment.description}</p>
                    <p className="text-sm text-gray-500">{formatDate(payment.date)}</p>
                  </div>
                  <span className="font-medium text-green-600">{formatCurrency(payment.amount)}</span>
                </div>
              ))}
            </div>
            <div className="mt-4">
              <button
                onClick={() => router.push('/fees/history')}
                className="text-indigo-600 hover:text-indigo-500 text-sm font-medium"
              >
                View All Payments →
              </button>
            </div>
          </div>
        </div>

        {/* Action Buttons */}
        <div className="mt-8 flex justify-center space-x-4">
          <button
            onClick={() => router.push('/fees/pay')}
            className="bg-indigo-600 hover:bg-indigo-700 text-white px-6 py-3 rounded-md font-medium transition-colors"
          >
            Make Payment
          </button>
          <button
            onClick={() => router.push('/fees/history')}
            className="bg-gray-100 hover:bg-gray-200 text-gray-700 px-6 py-3 rounded-md font-medium transition-colors"
          >
            View Payment History
          </button>
        </div>
      </div>
    </div>
  );
} 