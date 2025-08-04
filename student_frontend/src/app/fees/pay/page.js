'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';

export default function FeePaymentPage() {
  const [paymentData, setPaymentData] = useState({
    amount: '',
    paymentMethod: 'credit_card',
    cardNumber: '',
    cardHolderName: '',
    expiryDate: '',
    cvv: ''
  });
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState('');
  const [studentFees, setStudentFees] = useState(null);
  const [studentId, setStudentId] = useState("STU001");
  const [semesterId, setSemesterId] = useState(1);
  const [academicYear, setAcademicYear] = useState("2023-2024");
  const router = useRouter();

  // Mock student fee data
  const mockStudentFees = {
    totalAmount: 5000.00,
    amountPaid: 3000.00,
    amountOwed: 2000.00,
    studentId: 'STU001'
  };

  useEffect(() => {
    // Load student fee data
    async function fetchFeeData(){
      setLoading(true);
      try{
        const res = await fetch(`http://localhost:8080/api/fees/inquiry?studentId=${studentId}&semesterId=${semesterId}&academicYear=${academicYear}`)
        if (!res.ok) {
          console.log(res.statusText);
        }
        const json = await res.json();
        setStudentFees(json.data);
        console.log(json)

      } catch (err) {
        console.log(err);
      } finally {
        setLoading(false);
      }
    }
    fetchFeeData();
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setPaymentData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handlePaymentMethodChange = (method) => {
    setPaymentData(prev => ({
      ...prev,
      paymentMethod: method
    }));
  };

  const validateForm = () => {
    if (!paymentData.amount || parseFloat(paymentData.amount) <= 0) {
      setError('Please enter a valid payment amount');
      return false;
    }
    if (parseFloat(paymentData.amount) > studentFees.amountOwed) {
      setError('Payment amount cannot exceed outstanding balance');
      return false;
    }
    if (paymentData.paymentMethod === 'credit_card') {
      if (!paymentData.cardNumber || !paymentData.cardHolderName || !paymentData.expiryDate || !paymentData.cvv) {
        setError('Please fill in all credit card details');
        return false;
      }
    }
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');
    setSuccess(false);

    if (!validateForm()) {
      setLoading(false);
      return;
    }

    try {
      const paymentPayload = {
        studentId,
        feeId: studentFees.id,
        amount: parseFloat(paymentData.amount),
        paymentMethod: paymentData.paymentMethod,
        paymentDate: new Date().toISOString(),
        transactionId: `TXN${Date.now()}`,
        status: 'completed'
      };

      // Simulate API call
      const response = await fetch('http://localhost:8080/api/payments', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(paymentPayload),
      });

      if (response.ok) {
        setSuccess(true);
        // Reset form
        setPaymentData({
          amount: '',
          paymentMethod: 'credit_card',
          cardNumber: '',
          cardHolderName: '',
          expiryDate: '',
          cvv: ''
        });
        // Update local fee data
        setStudentFees(prev => ({
          ...prev,
          amountPaid: prev.amountPaid + parseFloat(paymentData.amount),
          amountOwed: prev.amountOwed - parseFloat(paymentData.amount)
        }));
      } else {
        setError('Payment failed. Please try again.');
      }
    } catch (err) {
      setError('Network error. Please try again.');
      console.error('Payment error:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-4xl mx-auto">
      {/* Page Header */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">Fee Payment</h1>
        <p className="mt-2 text-gray-600">Make a payment towards your outstanding fees</p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Payment Form */}
        <div className="lg:col-span-2">
          <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-xl font-semibold text-gray-900 mb-6">Payment Details</h2>
            
            {success && (
              <div className="mb-6 rounded-md bg-green-50 p-4">
                <div className="flex">
                  <div className="flex-shrink-0">
                    <svg className="h-5 w-5 text-green-400" viewBox="0 0 20 20" fill="currentColor">
                      <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                    </svg>
                  </div>
                  <div className="ml-3">
                    <h3 className="text-sm font-medium text-green-800">Payment Successful!</h3>
                    <p className="mt-1 text-sm text-green-700">
                      Your payment of ${paymentData.amount} has been processed successfully.
                    </p>
                  </div>
                </div>
              </div>
            )}

            {error && (
              <div className="mb-6 rounded-md bg-red-50 p-4">
                <div className="text-sm text-red-700">{error}</div>
              </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-6">
              {/* Payment Amount */}
              <div>
                <label htmlFor="amount" className="block text-sm font-medium text-gray-700 mb-2">
                  Payment Amount ($)
                </label>
                <input
                  type="number"
                  id="amount"
                  name="amount"
                  step="0.01"
                  min="0.01"
                  max={studentFees?.amountOwed}
                  required
                  className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                  placeholder="Enter amount"
                  value={paymentData.amount}
                  onChange={handleInputChange}
                />
                <p className="mt-1 text-sm text-gray-500">
                  Maximum amount: ${studentFees?.amountOwed.toFixed(2)}
                </p>
              </div>

              {/* Payment Method */}
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Payment Method
                </label>
                <div className="space-y-3">
                  <label className="flex items-center">
                    <input
                      type="radio"
                      name="paymentMethod"
                      value="credit_card"
                      checked={paymentData.paymentMethod === 'credit_card'}
                      onChange={() => handlePaymentMethodChange('credit_card')}
                      className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300"
                    />
                    <span className="ml-3 text-sm text-gray-700">Credit Card</span>
                  </label>
                  <label className="flex items-center">
                    <input
                      type="radio"
                      name="paymentMethod"
                      value="bank_transfer"
                      checked={paymentData.paymentMethod === 'bank_transfer'}
                      onChange={() => handlePaymentMethodChange('bank_transfer')}
                      className="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300"
                    />
                    <span className="ml-3 text-sm text-gray-700">Bank Transfer</span>
                  </label>
                </div>
              </div>

              {/* Credit Card Details */}
              {paymentData.paymentMethod === 'credit_card' && (
                <div className="space-y-4">
                  <div>
                    <label htmlFor="cardNumber" className="block text-sm font-medium text-gray-700 mb-2">
                      Card Number
                    </label>
                    <input
                      type="text"
                      id="cardNumber"
                      name="cardNumber"
                      className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                      placeholder="1234 5678 9012 3456"
                      value={paymentData.cardNumber}
                      onChange={handleInputChange}
                    />
                  </div>
                  <div>
                    <label htmlFor="cardHolderName" className="block text-sm font-medium text-gray-700 mb-2">
                      Cardholder Name
                    </label>
                    <input
                      type="text"
                      id="cardHolderName"
                      name="cardHolderName"
                      className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                      placeholder="John Doe"
                      value={paymentData.cardHolderName}
                      onChange={handleInputChange}
                    />
                  </div>
                  <div className="grid grid-cols-2 gap-4">
                    <div>
                      <label htmlFor="expiryDate" className="block text-sm font-medium text-gray-700 mb-2">
                        Expiry Date
                      </label>
                      <input
                        type="text"
                        id="expiryDate"
                        name="expiryDate"
                        className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                        placeholder="MM/YY"
                        value={paymentData.expiryDate}
                        onChange={handleInputChange}
                      />
                    </div>
                    <div>
                      <label htmlFor="cvv" className="block text-sm font-medium text-gray-700 mb-2">
                        CVV
                      </label>
                      <input
                        type="text"
                        id="cvv"
                        name="cvv"
                        className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
                        placeholder="123"
                        value={paymentData.cvv}
                        onChange={handleInputChange}
                      />
                    </div>
                  </div>
                </div>
              )}

              {/* Submit Button */}
              <button
                type="submit"
                disabled={loading}
                className="w-full bg-indigo-600 hover:bg-indigo-700 text-white py-3 px-4 rounded-md font-medium transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
              >
                {loading ? (
                  <div className="flex items-center justify-center">
                    <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                      <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                      <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                    </svg>
                    Processing Payment...
                  </div>
                ) : (
                  'Process Payment'
                )}
              </button>
            </form>
          </div>
        </div>
        {/* Fee Summary */}
        <div className="lg:col-span-1">
          <div className="bg-white rounded-lg shadow-md p-6">
            <h3 className="text-lg font-medium text-gray-900 mb-4">Fee Summary</h3>
            <div className="space-y-4">
              <div className="flex justify-between">
                <span className="text-gray-600">Total Fees:</span>
                <span className="font-medium">${studentFees?.totalAmount.toFixed(2)}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-600">Amount Paid:</span>
                <span className="font-medium text-green-600">${studentFees?.amountPaid.toFixed(2)}</span>
              </div>
              <div className="border-t pt-4">
                <div className="flex justify-between">
                  <span className="text-lg font-medium text-gray-900">Outstanding Balance:</span>
                  <span className="text-lg font-bold text-red-600">${studentFees?.amountOwed.toFixed(2)}</span>
                </div>
              </div>
              <div className="mt-4">
                <div className="flex justify-between text-sm text-gray-600 mb-2">
                  <span>Payment Progress</span>
                  <span>{((studentFees?.amountPaid / studentFees?.totalAmount) * 100).toFixed(1)}%</span>
                </div>
                <div className="w-full bg-gray-200 rounded-full h-2">
                  <div
                    className="bg-green-600 h-2 rounded-full transition-all duration-300"
                    style={{ width: `${(studentFees?.amountPaid / studentFees?.totalAmount) * 100}%` }}
                  ></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}