package com.example.demo.requests.fees;

import lombok.Data;

@Data
public class FeeInquiryRequest {
    private String studentId;
    private Long semesterId;
    private String academicYear;
}
