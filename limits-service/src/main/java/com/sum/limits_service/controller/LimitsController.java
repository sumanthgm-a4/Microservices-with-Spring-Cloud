package com.sum.limits_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sum.limits_service.bean.Limits;
import com.sum.limits_service.config.Config;

@RestController
public class LimitsController {

    @Autowired
    private Config config;
	
	@GetMapping("/limits")
	public Limits retrieveLimits() {
		// return new Limits(1,1000);

        return new Limits(config.getMinimum(), config.getMaximum());
	}
}
