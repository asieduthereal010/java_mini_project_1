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

  const getDueStatus = () => {
    const daysUntilDue = getDaysUntilDue();
    if (daysUntilDue < 0) {
      return { status: 'overdue', color: 'text-red-600', bgColor: 'bg-red-100' };
    } else if (daysUntilDue <= 7) {
      return { status: 'urgent', color: 'text-orange-600', bgColor: 'bg-orange-100' };
    } else if (daysUntilDue <= 30) {
      return { status: 'due-soon', color: 'text-yellow-600', bgColor: 'bg-yellow-100' };
    } else {
      return { status: 'on-time', color: 'text-green-600', bgColor: 'bg-green-100' };
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center py-12">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">Loading fee overview...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="text-center py-12">
        <div className="text-red-600 mb-4">{error}</div>
        <button
          onClick={() => window.location.reload()}
          className="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-md"
        >
          Try Again
        </button>
      </div>
    );
  }

  const dueStatus = getDueStatus();

  return (
    <div>
      {/* Page Header */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">Fee Overview</h1>
        <p className="mt-2 text-gray-600">Complete overview of your academic fees and payment status</p>
      </div>

      {/* Outstanding Balance Card */}
      <div className="bg-white rounded-lg shadow-md p-6 mb-8">
        <div className="flex items-center justify-between">
          <div>
            <h2 className="text-2xl font-bold text-gray-900">Outstanding Balance</h2>
            <p className="text-4xl font-bold text-red-600 mt-2">{formatCurrency(feeData.amountOwed)}</p>
            <p className="text-sm text-gray-600 mt-1">Due by {formatDate(feeData.dueDate)}</p>
          </div>
          <div className={`px-4 py-2 rounded-full ${dueStatus.bgColor}`}>
            <span className={`text-sm font-medium ${dueStatus.color}`}>
              {dueStatus.status === 'overdue' && 'Overdue'}
              {dueStatus.status === 'urgent' && 'Due Soon'}
              {dueStatus.status === 'due-soon' && 'Due This Month'}
              {dueStatus.status === 'on-time' && 'On Time'}
            </span>
          </div>
        </div>
        
        {/* Payment Progress */}
        <div className="mt-6">
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
          <div className="flex justify-between text-sm text-gray-600 mt-2">
            <span>Paid: {formatCurrency(feeData.amountPaid)}</span>
            <span>Total: {formatCurrency(feeData.totalAmount)}</span>
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Fee Breakdown */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <h3 className="text-lg font-medium text-gray-900 mb-4">Fee Breakdown</h3>
          <div className="space-y-4">
            {feeData.feeBreakdown.map((item, index) => (
              <div key={index} className="border-b border-gray-200 pb-4 last:border-b-0">
                <div className="flex justify-between items-start mb-2">
                  <h4 className="font-medium text-gray-900">{item.category}</h4>
                  <span className="text-sm font-medium text-gray-900">{formatCurrency(item.amount)}</span>
                </div>
                <div className="flex justify-between text-sm text-gray-600">
                  <span>Paid: {formatCurrency(item.paid)}</span>
                  <span>Remaining: {formatCurrency(item.remaining)}</span>
                </div>
                <div className="mt-2">
                  <div className="w-full bg-gray-200 rounded-full h-2">
                    <div
                      className="bg-green-600 h-2 rounded-full"
                      style={{ width: `${(item.paid / item.amount) * 100}%` }}
                    ></div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Recent Payments */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <h3 className="text-lg font-medium text-gray-900 mb-4">Recent Payments</h3>
          <div className="space-y-4">
            {feeData.paymentHistory.slice(0, 5).map((payment) => (
              <div key={payment.id} className="flex justify-between items-center border-b border-gray-200 pb-4 last:border-b-0">
                <div>
                  <p className="font-medium text-gray-900">{payment.description}</p>
                  <p className="text-sm text-gray-600">{formatDate(payment.date)}</p>
                </div>
                <span className="text-lg font-medium text-green-600">{formatCurrency(payment.amount)}</span>
              </div>
            ))}
          </div>
          {feeData.paymentHistory.length > 5 && (
            <div className="mt-4 text-center">
              <button
                onClick={() => router.push('/fees/history')}
                className="text-indigo-600 hover:text-indigo-500 text-sm font-medium"
              >
                View All Payments →
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
} 