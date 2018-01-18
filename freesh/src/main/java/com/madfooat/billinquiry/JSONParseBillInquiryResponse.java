package com.madfooat.billinquiry;

import com.madfooat.billinquiry.domain.Bill;
import com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParseBillInquiryResponse implements ParseBillInquiryResponse {
	@Override
	public List<Bill> parse(String billerResponse) throws InvalidBillInquiryResponse {

		Bill bi = new Bill();

		try {
			JSONArray array = new JSONArray(billerResponse);

			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObj = array.getJSONObject(i);

				try {
					// due date should not be future date and exist
					if (jsonObj.has("dueDate") && jsonObj.getString("dueDate").compareTo(new Date().toString()) < 0) {
						bi.setDueDate(new SimpleDateFormat("dd-MM-yyyy").parse(jsonObj.getString("dueDate")));
					} else {
						throw new InvalidBillInquiryResponse();
					}

				} catch (ParseException e) {
					e.printStackTrace();
				}

				// due Amount should exist  valid format in Jordainian Dinar (?.xxx) by 1000 fulus 
				if (jsonObj.has("dueAmount")  && jsonObj.getBigDecimal("dueAmount").scale() <= 3    ) {

					bi.setDueAmount(jsonObj.getBigDecimal("dueAmount"));
					 
					 
				} else {
					throw new InvalidBillInquiryResponse();
				}

				// Fees is optional, and incase its thier it should be less than Amount & valid format in Jordainian Dinar (?.xxx) by 1000 fulus 
				if (jsonObj.has("fees")
						&& jsonObj.getBigDecimal("fees").compareTo(jsonObj.getBigDecimal("dueAmount")) < 0 && jsonObj.getBigDecimal("fees").scale() <= 3 ) {
					BigDecimal fees = jsonObj.getBigDecimal("fees");
					bi.setFees(fees);
				} else {
					bi.setFees(BigDecimal.ZERO);
				}

				System.out.println("JSON - " + bi.toString());

			}

		} catch (JSONException e) {
			throw new InvalidBillInquiryResponse();
		}

		List<Bill> list2= new ArrayList<Bill>();
		list2.add(bi);
		list2.add(bi);

		return list2;// return List not null?
	}
}
