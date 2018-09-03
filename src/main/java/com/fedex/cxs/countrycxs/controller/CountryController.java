/**
 * 
 */
package com.fedex.cxs.countrycxs.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fedex.cxs.countrycxs.service.GetCountryService;
import com.fedex.cxs.countrycxs.vo.Country;

/**
 * @author KrishnaPC
 *
 */
@RestController
@RequestMapping(value = "/countries/v3")
public class CountryController {

	private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

	@Autowired
	private GetCountryService getCountryService;

	@GetMapping("/")
	public List<Country> countries() {
		return getCountryService.getAllCountries();
	}

	/*
	 * @RequestMapping(value = "/", method = RequestMethod.GET) public String
	 * countries1() { return "test"; }
	 */
}