package com.madfooat.billinquiry;

import com.madfooat.billinquiry.domain.Bill;
import com.madfooat.billinquiry.domain.Bills;
import com.madfooat.billinquiry.exceptions.InvalidBillInquiryResponse;
import java.io.StringReader;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XMLParseBillInquiryResponse implements ParseBillInquiryResponse {

	@Override
	public List<Bill> parse(String billerResponse) throws InvalidBillInquiryResponse {
		Bills bills = null;

		try {

			JAXBContext context = JAXBContext.newInstance(Bills.class);
			Unmarshaller un = context.createUnmarshaller();
			StringReader reader = new StringReader(billerResponse);
			bills = (Bills) un.unmarshal(reader);

			for (Bill b : bills.getBills()) {

				b.setDueDate(new Date(100));

				  if (!b.equals(null)) {
				 
				if (!(b.getDueAmount().scale() <= 3)) {
					throw new InvalidBillInquiryResponse();
				}


				// Checked all requirements
				if (b.getDueDate().compareTo((new Date())) < 0 || b.getDueAmount().compareTo(b.getFees()) > 0
						&& b.getDueAmount().scale() <= 3 && b.getFees().scale() <= 3) {

					System.out.println( "XML -" + b.toString());
				}

				else {
					System.out.println("else1 XML -" + b.toString());
					throw new InvalidBillInquiryResponse();
				}

				 }

				 else {
				 System.out.println("else2 XML -" + b.toString());
				 throw new InvalidBillInquiryResponse();
				 }

			}

		} catch (JAXBException e) {

			throw new InvalidBillInquiryResponse();
		}

		return bills.getBills();
	}
}
