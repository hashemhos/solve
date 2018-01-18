package com.madfooat.billinquiry.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "Bills")
public class Bills {

    private List<Bill> bills;
    private List<Bill> bills2;
    @XmlElement(name = "bill")
    public List<Bill> getBills2() {
		return bills2;
	}

	public void setBills2(List<Bill> bills2) {
		this.bills2 = bills2;
	}

	@XmlElement(name = "bill")
    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
    
    
}
