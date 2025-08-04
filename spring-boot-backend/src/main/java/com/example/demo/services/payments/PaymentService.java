package com.example.demo.services.payments;

import com.example.demo.dtos.FeesDTO;
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

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService{
    private final PaymentsRepository paymentsRepository;
    private final StudentRepository studentRepository;
    private final FeeRepository feeRepository;
    private final ModelMapper modelMapper;

    @Override
    public PaymentDto createPayment(PaymentRequest request) {
        Students student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("No student exists by that ID"));

        Fees fee = feeRepository.findById(request.getFeeId())
                .orElseThrow(() -> new ResourceNotFoundException("No fee by that Id exits"));
        fee.setAmountPaid(fee.getAmountPaid().add(request.getAmount()));
        feeRepository.save(fee);

        Payments payment = new Payments();
        payment.setPaymentDate(request.getPaymentDate().atStartOfDay());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setFees(fee);
        payment.setStudent(student);
        payment.setAmount(request.getAmount());
        payment.setStatus(request.getStatus());
        payment.setTransactionId(request.getTransactionId());

        paymentsRepository.save(payment);
        FeesDTO feesDTO = modelMapper.map(fee, FeesDTO.class);
        feesDTO.setAmountOwed(fee.getTotalAmount().subtract(fee.getAmountPaid()));
        PaymentDto paymentDto = modelMapper.map(payment, PaymentDto.class);
        paymentDto.setFees(feesDTO);
        return paymentDto;
    }
}
