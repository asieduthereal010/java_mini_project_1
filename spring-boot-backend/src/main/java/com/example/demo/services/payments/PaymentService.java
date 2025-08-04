package com.example.demo.services.payments;

import com.example.demo.dtos.PaymentDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.StudentNotFoundException;
import com.example.demo.models.Fees;
import com.example.demo.models.Payments;
import com.example.demo.models.Students;
import com.example.demo.repositories.FeeRepository;
import com.example.demo.repositories.PaymentsRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.requests.payments.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService{
    private final PaymentsRepository paymentsRepository;
    private final StudentRepository studentRepository;
    private final FeeRepository feeRepository;
    private final ModelMapper modelMapper;
    private static final AtomicInteger number = new AtomicInteger(0);

    @Override
    public PaymentDto createPayment(PaymentRequest request) {
        Students student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("No student exists by that ID"));

        Fees fee = feeRepository.findById(request.getFeeId())
                .orElseThrow(() -> new ResourceNotFoundException("No fee by that Id exits"));
        fee.setAmount_paid(fee.getAmount_paid().add(request.getAmount()));
        feeRepository.save(fee);

        String transactionId = String.format("TXN-%010d", number.incrementAndGet());

        Payments payment = new Payments();
        payment.setPaymentDate(request.getTimestamp());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setFee(fee);
        payment.setStudent(student);
        payment.setAmount(request.getAmount());
        payment.setStatus(request.getStatus());
        payment.setTransactionId(transactionId);

        paymentsRepository.save(payment);
        return modelMapper.map(payment, PaymentDto.class);
    }
}
