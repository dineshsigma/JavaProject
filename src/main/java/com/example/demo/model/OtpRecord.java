package com.example.demo.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class OtpRecord implements Serializable {

    private String otp;

    private int retries;

    private long lockUntilEpoch;
}