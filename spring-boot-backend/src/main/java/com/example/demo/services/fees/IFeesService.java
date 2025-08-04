package com.example.demo.services.fees;

import com.example.demo.models.Fees;
import com.example.demo.requests.fees.FeeInquiryRequest;

public interface IFeesService {
    public Fees getFeesDetail(FeeInquiryRequest request);
}
