package com.example.hotelmanagementsystem.services.impl;

import com.example.hotelmanagementsystem.controllers.LoggingController;
import com.example.hotelmanagementsystem.models.Payment;
import com.example.hotelmanagementsystem.repositories.PaymentRepository;
import com.example.hotelmanagementsystem.services.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private  final PaymentRepository paymentRepository;
    private final LoggingController loggingController;

    public PaymentServiceImpl(PaymentRepository paymentRepository, LoggingController loggingController){
        this.paymentRepository = paymentRepository;
        this.loggingController = loggingController;
    }

    @Override
    public Payment insertPayment(Payment payment) {
        try {
            payment.setCreatedBy(null);
            payment.setModifiedBy(null);
            return paymentRepository.save(payment);
        }
        catch (Exception e)
        {
            loggingController.logger.error(e.getMessage());
        }
        return null;
    }


}
