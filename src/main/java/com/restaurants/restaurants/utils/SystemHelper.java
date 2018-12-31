package com.restaurants.restaurants.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.restaurants.restaurants.exceptions.InvalidDateException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceContext;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.codec.binary.Base64;
@Component
public class SystemHelper {

	static final Logger logger = Logger.getLogger(SystemHelper.class);

	private String ZONE_ID = "Asia/Kolkata";

	public String toString(Object obj) { return obj == null ? null : obj.toString(); }

	public String getEncodedString(String param) {
		return new String(Base64.encodeBase64(param.getBytes()));
	}

	public String getDecodedString(String param) {
		return new String(Base64.decodeBase64(param.getBytes()));
	}

	public String getURLEncodedString(String param) throws UnsupportedEncodingException {
		return URLEncoder.encode(param, Constants.ENCODING_FORMAT);
	}

	public String getURLDecodedString(String param) throws UnsupportedEncodingException {
		return URLDecoder.decode(param, Constants.ENCODING_FORMAT);
	}

	public ZoneId getZoneId() {
		return ZoneId.of(ZONE_ID);
	}
	
	public LocalDate getCurrentDate() {
		return LocalDate.now();
	}

	public LocalDateTime getCurrentTime() {
		return LocalDateTime.now().atZone(getZoneId()).toLocalDateTime();
	}

	public String getCurrentDateString() {
		return this.convertDateToString(LocalDate.now());
	}

	public String getCurrentTimeString() {
		return this.convertTimeToString(LocalDateTime.now());
	}

	public Long getTimeInSeconds(LocalDateTime time) {
		return time.atZone( getZoneId()).toEpochSecond();
	}

	public Long getTimeInSeconds(LocalDate date) {
		return date.atStartOfDay().atZone( getZoneId()).toEpochSecond();
	}

	public String convertDateToString(LocalDate dt) {
		return convertDateToString(dt, Constants.SQL_DATE_FORMAT);
	}

	public String convertDateToString(LocalDate dt, String format) {
		return (dt == null) ? null : dt.format(DateTimeFormatter.ofPattern(format));
	}

	public LocalDate convertStringToDate(String date) {
		return (isEmpty(date)) ? null :  LocalDate.parse(date, DateTimeFormatter.ofPattern(Constants.SQL_DATE_FORMAT));
	}

	public LocalDate convertStringToDate(String date, String format) {
		return (isEmpty(date)) ? null :  LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
	}

	public String convertTimeToString() {
		return convertTimeToString(getCurrentTime());
	}

	public String convertTimeToString(LocalDateTime dt) {
		return (dt == null) ? null : getDateTimeFormatter().format(dt);
	}

	public LocalDateTime convertStringToTime(String dt) throws InvalidDateException {
		return isEmpty(dt) ? null : LocalDateTime.parse(dt,getDateTimeFormatter()).atZone(getZoneId()).toLocalDateTime();
	}

	public DateTimeFormatter getDateFormatter() {
		return DateTimeFormatter.ofPattern(Constants.SQL_DATE_FORMAT);
	}

    public DateTimeFormatter getDateTimeFormatter() {
        return new DateTimeFormatterBuilder()
                .appendPattern(Constants.SQL_TIMESTAMP_FORMAT)
                .appendFraction(ChronoField.MILLI_OF_SECOND, 0, 3, true) // min 2 max 3
                .toFormatter();
    }

	public int localDateDiff(LocalDate toDate, LocalDate fromDate) {
		int date = 0;
		LocalDate to = toDate;
		LocalDate from = fromDate;
		if (!isEmpty(from) && !isEmpty(to)) {
		  date = (int) Duration.between(to.atStartOfDay(), from.atStartOfDay()).toDays();
		}
		return date;
	}

	public Object fromJSON(String parm, Type listType) {
		return getGSonBuilder().fromJson(parm, listType);
	}

	public Object fromJSON(String parm, Class<?> cl) {
		return getGSonBuilder().fromJson(parm, cl);
	}

	public String toJSON(Object parm) {
		Gson gson = getGSonBuilder();
		return gson.toJson(parm);
	}

	private Gson getGSonBuilder() {
		return new GsonBuilder().setDateFormat(Constants.SQL_TIMESTAMP_FORMAT)
				.setLongSerializationPolicy(LongSerializationPolicy.STRING)
				.create();
	}

	public boolean getSystemProperty(String param, String value) {
		String auth = System.getProperty(param);
		return !(auth != null && auth.equalsIgnoreCase(value));
	}

	public boolean isExpired(LocalDateTime expiryTime) {

		if (expiryTime == null || expiryTime.toString().equals(Constants.TIMESTAMP_NULL))
			return false; // if expiry days is null, then no expiration verification

		return (expiryTime.isBefore(LocalDateTime.now()));
	}

	public String generateNonce() throws NoSuchAlgorithmException {
		String nonce;
		// Initialize SecureRandom
		// This is a lengthy operation, to be done only upon
		// initialization of the application
		SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

		// generate a random number
		String randomNum = new Integer(prng.nextInt()).toString();

		// get its digest
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		byte[] result = sha.digest(randomNum.getBytes());

		nonce = randomNum + hexEncode(result);

		return nonce;
	}

	private String hexEncode(byte[] aInput) {
		StringBuilder result = new StringBuilder();
		char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f'};
		for (int idx = 0; idx < aInput.length; ++idx) {
			byte b = aInput[idx];
			result.append(digits[(b & 0xf0) >> 4]);
			result.append(digits[b & 0x0f]);
		}
		return result.toString();
	}

	public String getFinancialYear(LocalDate date) {

		int year = date.getYear();
		int month = date.getMonthValue() + 1;

		String strDateFormat = "yy";
		DateTimeFormatter sdf = DateTimeFormatter.ofPattern(strDateFormat);
		String y = sdf.format(date);
		Integer yr = Integer.parseInt(y);
		String financialYear;
		if (month < 4) {
			financialYear = (year - 1) + "-" + yr;
		} else {
			financialYear = year + "-" + (yr + 1);
		}

		return financialYear;
	}


	private static final String[] specialNames = {
			"",
			" Thousand",
			" Million",
			" Billion",
			" Trillion",
			" Quadrillion",
			" Quintillion"
	};

	private static final String[] tensNames = {
			"",
			" Ten",
			" Twenty",
			" Thirty",
			" Forty",
			" Fifty",
			" Sixty",
			" Seventy",
			" Eighty",
			" Ninety"
	};

	private static final String[] numNames = {
			"",
			" One",
			" Two",
			" Three",
			" Four",
			" Five",
			" Six",
			" Seven",
			" Eight",
			" Nine",
			" Ten",
			" Eleven",
			" Twelve",
			" Thirteen",
			" Fourteen",
			" Fifteen",
			" Sixteen",
			" Seventeen",
			" Eighteen",
			" Nineteen"
	};

	private String convertLessThanOneThousand(int number) {
		String current = Constants.EMPTY_STRING;

		if (number % 100 < 20) {
			current = numNames[number % 100];
			number /= 100;
		} else {
			current = numNames[number % 10];
			number /= 10;

			current = tensNames[number % 10] + current;
			number /= 10;
		}
		if (number == 0)
			return current;

		String ret = numNames[number] + " Hundred";
		if (current.length() > 0)
			ret += " and" + current;

		return ret;
	}

	public String convert(int number) {

		if (number == 0) {
			return "zero";
		}

		String prefix = Constants.EMPTY_STRING;

		if (number < 0) {
			number = -number;
			prefix = "negative";
		}

		String current = Constants.EMPTY_STRING;
		int place = 0;

		do {
			int n = number % 1000;
			if (n != 0) {
				String s = convertLessThanOneThousand(n);
				current = s + specialNames[place] + current;
			}
			place++;
			number /= 1000;
		} while (number > 0);

		return (prefix + current).trim();
	}

	public String currencyNumberToWord(String number) {
		String sPaise = Constants.EMPTY_STRING;
		String[] split = number.split("\\.");
		int rupees = Integer.parseInt(split[0]);
		String sRupees = this.convert(rupees);
		if (rupees > 1) {
			sRupees += " Rupees";
		} else {
			sRupees += " Rupee";
		}

		if (split.length == 2) {
			int paise = Integer.parseInt(number.split("\\.")[1]);
			if (paise != 0) {
				sPaise += " and ";
				sPaise += this.convert(paise);
				sPaise += " Paise";
			}
		}

		return sRupees + sPaise + " Only.";
	}

	public List<Object> getObjectsList(List<?> list) {
		List<Object> objects = new ArrayList<>();
		objects.addAll(list);
		return objects;
	}
/***************
	public String generateChecksum(String path) throws Exception {

		MessageDigest md = MessageDigest.getInstance("SHA1");
		FileInputStream fis = new FileInputStream(path);
		byte[] dataBytes = new byte[1024];

		int nread = 0;

		while ((nread = fis.read(dataBytes)) != -1) {
			md.update(dataBytes, 0, nread);
		}

		byte[] mdbytes = md.digest();

		// convert the byte to hex format
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		fis.close();

		return sb.toString();
	}
*/

	public String getDoubleValueForAPK(String arg) {
		String ret = "0.00";
		if (arg != null && arg.trim().length() > 0 && !".".equals(arg)) {
			try {
				ret = String.format("%.2f", Double.parseDouble(arg));
			} catch (Exception e) {

			}
		}
		return ret;
	}

	public String getLeftJustified(String arg, int length) {
		String ret = Constants.EMPTY_STRING;
		if (arg != null) {

			if (arg.length() > length) {
				ret = arg.substring(0, length);
			} else {
				ret = new StringBuffer(arg).append(getPaddingString(length - arg.length())).toString();
			}
		} else {
			ret = getPaddingString(length);
		}
		return ret;
	}

	public String getRightJustified(String arg, int length) {
		String ret = Constants.EMPTY_STRING;
		if (arg != null) {

			if (arg.length() > length) {
				ret = arg.substring(0, length);
			} else {
				ret = new StringBuffer(getPaddingString(length - arg.length())).append(arg).toString();
			}
		} else {
			ret = getPaddingString(length);
		}
		return ret;
	}

	public String getCenterJustified(String arg, int length) {
		String ret = Constants.EMPTY_STRING;
		if (arg != null) {

			ret = new StringBuffer(getPaddingString((length - arg.length()) / 2)).append(arg).
					append(getPaddingString((length - arg.length()) / 2)).toString();
		} else {
			ret = getPaddingString(length);
		}
		return ret;
	}

	public String getPaddingString(int length) {
		StringBuffer sb = new StringBuffer();
		while (length > 0) {
			sb.append(Constants.SINGLE_SPACE);
			length--;
		}
		return sb.toString();
	}

	public String getLikeQueryString(String str) {
		final String var = isEmpty(str) ? Constants.EMPTY_STRING : str.trim().replace(Constants.PERCENTAGE, Constants.ESCAPE_PERCENTAGE).toUpperCase();
		return var + Constants.PERCENTAGE;
	}

	public String getContainLikeQueryString(String str) {
		final String var = isEmpty(str) ? Constants.EMPTY_STRING :  Constants.PERCENTAGE + str.trim().replace(Constants.PERCENTAGE, Constants.ESCAPE_PERCENTAGE).toUpperCase();
		return var + Constants.PERCENTAGE;
	}

	public String getTrimmedUpperCaseNoSpace(String batch) {
		batch = isEmpty(batch) ? Constants.EMPTY_STRING : batch;
		return batch.trim().replace(" ", Constants.EMPTY_STRING).toUpperCase();
	}

	public String getTrimmedUpperCase(String batch) {
		batch = isEmpty(batch) ? Constants.EMPTY_STRING : batch;
		return batch.trim().toUpperCase();
	}

	public String getCommaSeparatedCurrencyValue(Double value) {
		if (value < 1000) {
			return format("###.##", value);
		} else {
			double hundreds = value % 1000;
			int other = (int) (value / 1000);
			return format(",##.##", other) + ',' + format("000.##", hundreds);
		}
	}

	public String getCommaSeparatedTotalValue(Long value) {
		if (value < 1000) {
			return format("###", value);
		} else {
			double hundreds = value % 1000;
			int other = (int) (value / 1000);
			return format(",##", other) + ',' + format("000", hundreds);
		}
	}

	private String format(String pattern, Object value) {
		return new DecimalFormat(pattern).format(value);
	}

	public boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public boolean isEmpty(Object obj) {
		return obj == null;
	}

	public boolean isNull(Object obj) {
		return obj == null;
	}

	public Double round(Double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		Long factor = (long) Math.pow(10, places);
		value = value * factor;
		Long tmp = Math.round(value);
		return (Double.valueOf(tmp) / factor);
	}

	public LocalDate calculateDOB(String dateType, String dateValue) {

		LocalDate date = LocalDate.now();
		if (("Y").equals(dateType)) {
			String[] yearMonth = dateValue.split("\\.");

			Integer years = Integer.valueOf(yearMonth[0]);
			if (yearMonth.length > 1) {
				Double monthDays = Double.valueOf("0." + yearMonth[1]) * 12;
				years += (monthDays.intValue());
			}
			date = date.minusYears(years);

		} else if (("M").equals(dateType)) {
			date = date.minusMonths(Integer.valueOf(dateValue)+ 1);
		} else if (("D").equals(dateType)) {
			date = date.minusDays(Integer.valueOf(dateValue));
		}

		return date;
	}

	public boolean isPhoneNumberFormat(String value) {
		boolean ret = false;
		if (value != null && value.length() >= 10) {
			ret = true;
			char[] array = value.toCharArray();
			for (int i = 0; i < value.length(); i++) {
				if (!Character.isDigit(array[i])) {
					ret = false;
					break;
				}
			}
		}
		return ret;
	}

	public String getNextSequence(String mnemonic, String code, String number, LocalDate date) {
		String financialYear = getFinancialYear(date);
		String prefix = mnemonic + "/" + code + "/" + financialYear + "/";
		String serial = "0";
		if (number != null) {
			String[] split = number.split("/");
			if (financialYear.equalsIgnoreCase(split[2])) {
				serial = split[3];
			}
		}
		return prefix + getNextSequence(serial);
	}

	private String getNextSequence(String number) {
		Long lNum = Long.parseLong(number) + 1;
		return String.format("%08d", lNum);
	}

	public boolean doesHavePropertyAccess(Long accessLevel, Long property) {
		ArrayList partition = new ArrayList();
		long tmp;
		if (accessLevel == 0) {
			partition.add(new Long(0));
		}

		for (int i = 0; i < 32; i++) {
			long mask = 1L << i;
			tmp = (mask & accessLevel);
			if (tmp != 0) partition.add(new Long(tmp));
		}
		return partition.contains(property);
	}

	public LocalDate getDaysAdjustedDate(int days) {
		return getDaysAdjustedDate(getCurrentDate(), days);
	}

	public LocalDate getDaysAdjustedDate(LocalDate date, int days) {
		return days < 0 ? date.minusDays(days * -1) : date.plusDays(days);
	}

	public String getExceptionTrace(Exception e) {
		String trace;
		try {
			trace = e.getMessage() + "->" + e.getStackTrace()[0].getFileName() + "->" + e.getStackTrace()[0].getClassName() + "-> " + e.getStackTrace()[0].getMethodName() + "->" + e.getStackTrace()[0].getLineNumber();
		} catch (Exception e1) {
			String msg = (e != null) ? e.toString() : "null";
			trace = "Error while getting exception trace -> " + msg;
		}
		return trace;
	}

	public String getAgeValue(Double obj){
        DecimalFormat format = new DecimalFormat("0.#");
        return format.format(obj);
    }

	public LocalDate getThirtyDays() {
		return LocalDate.now().plusDays(30);
	}

	public String getUpdateQuery(String tableName, List<String> fieldNames) {
		return  "UPDATE " + tableName + " SET " + getUpdateFields(fieldNames) + ", last_updated_by = :last_updated_by, last_updated_timestamp = :last_updated_timestamp";
	}

	private String getUpdateFields(List<String> fields) {
		StringBuilder fieldNames = new StringBuilder(fields.get(0)).append(" = :").append(fields.get(0));
		for (int i = 1; i < fields.size(); i++) fieldNames.append(", ").append(fields.get(i)).append(" = :").append(fields.get(i));
		return fieldNames.toString();
	}

	public Long getRandom(List<Long> ids) {
    	Long id = null;
		if (ids != null && !ids.isEmpty()) {
			Random random = new Random();
			id = ids.get(random.nextInt(ids.size()));
		}
		return id;
	}

	public String getName(String first, String last) {
		return getNameValue(first, last, Constants.SPACE_DELIMITER);
	}

	public String getName(String first, String middle, String last) {
		return getName(first, middle, last, Constants.SPACE_DELIMITER);
	}

	public String getNameValue(String first, String last, String delimiter) {
		String name = Constants.EMPTY_STRING;
		if (!isEmpty(first)) name = name.concat(first);
		if (!isEmpty(last)) {
			if(!isEmpty(name)) name = name.concat(delimiter);
			name = name.concat(last);
		}
		return name;
	}

	public String getName(String first, String middle, String last, String delimiter) {
		String name = Constants.EMPTY_STRING;
		if (!isEmpty(first)) name = name.concat(first);
		if (!isEmpty(middle)) {
			if(!isEmpty(name)) name = name.concat(delimiter);
			name = name.concat(middle);
		}
		if (!isEmpty(first)) {
			if(!isEmpty(name)) name = name.concat(delimiter);
			name = name.concat(last);
		}
		return name;
	}

	public <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> list = new ArrayList<>();
		if (iterable != null) {
			for (T anIterable : iterable) {
				list.add(anIterable);
			}
		}
		return list;
	}

	public String generate16DigitRandom(String prefix) {
		Random rand = new Random();

		long x = (long)(rand.nextDouble()*100000000000000L);

		return prefix + String.format("%014d", x);
	}



	public int getNumberOfQuarters() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH) / 3;
	}

	public LocalDate getBeginOfYear() {
		return LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
	}

	public int getNumberOfQuarters(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) / 3;
	}

	public int getNumberOfQuarters(Date fromDate, Date toDate) {
		return getNumberOfMonths(fromDate, toDate) / 3;
	}

	public int getNumberOfMonths(Date fromDate, Date toDate) {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.setTime(fromDate);
		to.setTime(toDate);
		int years = to.get(Calendar.YEAR) - from.get(Calendar.YEAR);
		return years * 12 + to.get(Calendar.MONTH) - from.get(Calendar.MONTH);
	}

	public int getNumberOfDays(LocalDate fromDate, LocalDate toDate) {
		return (int) Duration.between(fromDate.atStartOfDay(), toDate.atStartOfDay()).toDays();
	}

	public LocalDate convertToLocalDate(LocalDateTime dateToConvert) {
		return dateToConvert.atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public int getAllowedValue(int value, int maxValue) {
		return value > maxValue ? maxValue : value;
	}

	public boolean doesContain (int [] list, int value) {
		boolean ret = false;
		for (int aList : list) {
			if (aList == value) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	public Double getNegativeValue(Double value) { return value * -1; }

	public int getNegativeValue(int value) { return value * -1; }

	public String getFileContentAsString(String fileLocation) throws IOException {

		Path path = Paths.get(fileLocation);

		return java.nio.file.Files.lines(path).collect(Collectors.joining());

	}

	public byte[] getBytes(String str) throws IOException {
		File file = new File(str);
		// File length
		int size = (int) file.length();
		if (size > Integer.MAX_VALUE) {
			System.out.println("File is too large");
		}
		byte[] bytes = new byte[size];
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		int read = 0;
		int numRead = 0;
		while (read < bytes.length && (numRead = dis.read(bytes, read, bytes.length - read)) >= 0) {
			read = read + numRead;
		}
		dis.close();

		return bytes;
	}

	public String getDomainName(String email) {
		return email.substring(email.indexOf("@") + 1, email.lastIndexOf("."));
	}


	public int getCompensationDays(String compensationCycle) {
		int days;
		if (isEmpty(compensationCycle)) {
			days = 30;
		} else {
			switch (compensationCycle) {
				case "D": days = 1; break;
				case "W": days = 7; break;
				case "F": days = 15; break;
				case "M": days = 30; break;
				default: days = 30;
			}
		}
		return days;
	}

	public Double getNameMatchPercentage(String payload, String name) {
		Double retVal;
		Double score = 0.0;

		String[] nameSplit = name.split(" ");
		String[] payloadSplit = payload.split(" ");

		for (String val : nameSplit) {
			for (String payloadVal : payloadSplit) {
				if (val.equalsIgnoreCase(payloadVal)) {
					score++;
					break;
				}
			}
		}

		retVal = (score/payloadSplit.length) * 100;
		return retVal;
	}
}
