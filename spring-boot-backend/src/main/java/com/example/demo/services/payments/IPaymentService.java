package com.example.demo.services.payments;

import com.example.demo.dtos.PaymentDto;
import com.example.demo.requests.payments.PaymentRequest;

public interface IPaymentService {
    PaymentDto createPayment(PaymentRequest request);
}
