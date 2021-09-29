package com.cg.Customer.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.cg.Customer.dto.LoanDetails;
import com.cg.Customer.entity.Loan;

@Component
public class LoanUtil {

	public LoanDetails toDetails(Loan loan) {
		return new LoanDetails(loan.getApplicationid(), loan.getDate(), loan.getApplyamount(), loan.getApproveamount(),
				loan.isLandverify(), loan.isFinanceverify(), loan.isAdminapprove(), loan.getStatus(), loan.getId());
	}

	public List<LoanDetails> toDetails(List<Loan> loans) {
		List<LoanDetails> loanList = new ArrayList<>();
		for (Loan loan : loans) {
			LoanDetails details = toDetails(loan);
			loanList.add(details);
		}
		return loanList;
	}

}
