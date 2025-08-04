package com.example.demo.services.fees;

import com.example.demo.dtos.FeesDTO;
import com.example.demo.requests.fees.FeeInquiryRequest;

public interface IFeesService {
    public FeesDTO getFeesDetail(FeeInquiryRequest request);
}
