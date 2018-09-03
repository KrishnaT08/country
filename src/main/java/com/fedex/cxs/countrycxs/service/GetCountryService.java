/**
 * 
 */
package com.fedex.cxs.countrycxs.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fedex.cxs.countrycxs.vo.Country;

/**
 * @author KrishnaPC
 *
 */
@Service
public class GetCountryService {

	private static final Logger logger = LoggerFactory.getLogger(GetCountryService.class);

	 public List<Country> getAllCountries() {
		 	logger.debug("get the countries...");
	        return createList();
	    }

	private List<Country> createList() {
		
		List<Country> countryList = new ArrayList<Country>();
		
		countryList.add(new Country("US","USA"));
		countryList.add(new Country("IN", "India"));
		return countryList;
	}
}
