package com.starksoftware.library.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.logging.Logger;

import com.starksoftware.library.abstracts.model.enumeration.FormatoDataEnum;

/**
 * @author Carlos Santos
 */
public class SistemaUtil {
	private static Logger logger = Logger.getLogger(SistemaUtil.class.getName());

	private static final int CPF_LENGTH = 11;
	private static final int CNPJ_LENGTH = 14;
	private static NumberFormat percentFormatter = new DecimalFormat("#0.00");
	private static final NumberFormat currencyNF;
	public static final String CSVSeparator = ";";

	static {
		currencyNF = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
		currencyNF.setMinimumFractionDigits(2);
		currencyNF.setMaximumFractionDigits(2);
	}

	public static String formatCpf(String cpf) {
		if (cpf == null || cpf.length() != CPF_LENGTH) {
			return "";
		}

		StringBuilder cpfFormatado = new StringBuilder(cpf);
		cpfFormatado.insert(9, "-");
		cpfFormatado.insert(6, ".");
		cpfFormatado.insert(3, ".");

		return cpfFormatado.toString();
	}

	public static String formatDate(Date date) {
		return formatDate(date, FormatoDataEnum.DDMMYYYY);
	}

	public static String formatDateTime(Date date) {
		return formatDate(date, FormatoDataEnum.ddMMyyyy_HHmm);
	}

	public static String formatDate(Date date, FormatoDataEnum formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato.toString());
		return date != null ? sdf.format(date) : "";
	}

	public static Date parseDate(String date) throws ParseException {
		return parseDate(date, FormatoDataEnum.DDMMYYYY);
	}

	public static Date parseDateTime(String date) throws ParseException {
		return parseDate(date, FormatoDataEnum.ddMMyyyy_HHmm);
	}

	public static Date parseDate(String date, FormatoDataEnum formato) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(formato.toString());
		return sdf.parse(date);
	}

	public static String formatCurrency(Object value) {
		return value != null ? currencyNF.format(value) : "";
	}

	public static String formatPercent(Object value) {
		return value != null ? percentFormatter.format(value) + "%" : "";
	}

	public static String formatCnpj(String cnpj) {
		if (cnpj == null || cnpj.length() != CNPJ_LENGTH) {
			return "";
		}

		StringBuilder cnpjFormatado = new StringBuilder(cnpj);
		cnpjFormatado.insert(12, "-");
		cnpjFormatado.insert(8, "/");
		cnpjFormatado.insert(5, ".");
		cnpjFormatado.insert(2, ".");

		return cnpjFormatado.toString();
	}

	public static String getBooleanKey(boolean value) {
		return value ? "boolean.true.value" : "boolean.false.value";
	}

	public static Date adjustStartDate(Date startDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date adjustEndDate(Date endDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static String formatNumeric(Object value) {

		return formatNumeric(value, true, true, 2);
	}

	public static BigDecimal dateDifferenceInDays(Date start, Date end) {

		if (start == null || end == null) {
			return BigDecimal.ZERO;
		}

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		double startMiliseconds = startCalendar.getTimeInMillis();

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		double endMiliseconds = endCalendar.getTimeInMillis();

		return new BigDecimal(Math.ceil(endMiliseconds - startMiliseconds) / (1000 * 60 * 60 * 24),
				new MathContext(2, RoundingMode.HALF_UP));

	}

	public static boolean datesInSameMonthAndYear(Date start, Date end) {

		if (start == null || end == null) {
			return false;
		}

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		int startMonth = startCalendar.get(Calendar.MONTH);
		int startYear = startCalendar.get(Calendar.YEAR);

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		int endMonth = endCalendar.get(Calendar.MONTH);
		int endYear = endCalendar.get(Calendar.YEAR);

		return startMonth == endMonth && startYear == endYear;

	}

	public static String formatNumeric(Object value, boolean isMilhar, boolean isDecimal, int decimalDigits) {

		if (value == null) {
			return "";
		}

		DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
		String pattern = new String("{1}0{2}");

		// casa de milhar
		if (isMilhar) {
			pattern = pattern.replace("{1}", "###,###,##");
		} else {
			pattern = pattern.replace("{1}", "");
		}

		if (isDecimal) {

			StringBuilder patterDecimais = new StringBuilder(".");
			for (int i = 0; i < decimalDigits; i++) {
				patterDecimais.append("0");
			}

			pattern = pattern.replace("{2}", patterDecimais);
			formatter = new DecimalFormat(pattern);
			formatter.setRoundingMode(RoundingMode.HALF_DOWN);
		} else {
			pattern = pattern.replace("{2}", "");
			formatter = new DecimalFormat(pattern);
			formatter.setRoundingMode(RoundingMode.DOWN);
		}

		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(new Locale("pt", "BR"));
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator('.');

		formatter.setDecimalFormatSymbols(symbols);

		return formatter.format(value);

	}

	public static boolean isCPF(String CPF) {
		if (CPF == null) {
			return true;
		}
		logger.severe(CPF);
		CPF = CPF.replace(".", "");
		CPF = CPF.replace("-", "");
		CPF = CPF.replace("_", "");
		logger.severe(CPF);
		if (CPF.isEmpty() || CPF.length() > 11) {
			return true;
		}
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = CPF.charAt(i) - 48;
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere
											// numerico

			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = CPF.charAt(i) - 48;
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public final static int Tipo_NUMERICO = 1;
	public final static int Tipo_CURRENCY = 2;
	public final static int Tipo_STRING = 3;
	public final static int Tipo_CNPJ = 4;
	public final static int Tipo_CPF = 5;
	public final static int Tipo_DATA = 6;

	public static String criptografar(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (value != null) {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			byte messageDigest[] = algorithm.digest(value.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			return hexString.toString();
		}
		return null;
	}
	
	static final String AB = "0123456789ABCDEFGHJKMNOPQRSTUVWXYZabcdefghjkmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();
	public static String randomString( int len ){
	   StringBuilder sb = new StringBuilder( len );
	   for( int i = 0; i < len; i++ ) 
	      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
	   return sb.toString();
	}
}
