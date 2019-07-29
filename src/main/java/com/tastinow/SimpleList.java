package com.tastinow;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SimpleList {

	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.err.println("Usage: java messageFile nameFile");
			System.exit(-1);
		}
		
		System.out.println("message file: " + args[0]);
		System.out.println("name list file: " + args[1]);

		// property file
		ResourceBundle bundle = ResourceBundle.getBundle("tastinow");
		String account_sid = bundle.getString("ACCOUNT_SID");
		String auth_token = bundle.getString("AUTH_TOKEN");
		String from = bundle.getString("PHONE");
		
		// message
		File file = new File(args[0]);
		String messageStr = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		System.out.println("message: " + messageStr);

		// user list
		String[] HEADERS = { "Cell Number" };
		List<Member> list = new ArrayList<Member>();

		// open the name list
		Reader in = new FileReader(args[1]);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader(HEADERS).withDelimiter(';').withFirstRecordAsHeader()
				.parse(in);

		for (CSVRecord record : records) {
			String phone = record.get("Cell Number");
			list.add(new Member("", phone, ""));
		}
		System.out.println("num of records: " + list.size());

		Twilio.init(account_sid, auth_token);

		for (Member mem : list) {
			System.out.println(mem.getPhone());
			
			try {
			Message message = Message
					.creator(new PhoneNumber(mem.getPhone()), new PhoneNumber(from), messageStr).create();
			System.out.println(message.getSid());
			} catch (Exception e) {
				System.out.println(mem.getPhone() + " has exception!");
				System.out.println(e.getMessage());
			}
		}
	}
}
