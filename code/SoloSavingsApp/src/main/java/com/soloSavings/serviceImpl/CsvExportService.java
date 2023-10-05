package com.soloSavings.serviceImpl;

import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.soloSavings.model.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CsvExportService {
	
	    public void exportToCsv(Optional<Transaction> transactions, String filePath) throws IOException {
	        try (FileWriter writer = new FileWriter(filePath)) {
	            // Write CSV header
	            writer.append("Transaction ID,User ID,Source,Transaction Type,Amount,Transaction Date\n");

	            // Check if transactions is present and write data if available
	            transactions.ifPresent(transaction -> {
	                try {
						writer.append(String.format("%d,%d,%s,%s,%.2f,%s\n",
						        transaction.getTransaction_id(),
						        transaction.getUser_id(),
						        transaction.getSource(),
						        transaction.getTransaction_type(),
						        transaction.getAmount(),
						        transaction.getTransaction_date()
						));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            });
	        }
	    }
}

	

	 

