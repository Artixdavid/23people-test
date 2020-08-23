package com.app.test.Utils;

import java.security.Key;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class Utils {

	public static final String AUTH = "Authorization";
	public static final Key SECRETKEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	public static final String PREFIXTOKEN = "Bearer ";
	private static final String DATEFORMAT = "yyyy-MM-dd";

	public static boolean validarRut(String rut) {

		try {
			rut = rut.toUpperCase();
			rut = rut.replace(".", "");
			rut = rut.replace("-", "");
			int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

			char dv = rut.charAt(rut.length() - 1);

			int m = 0, s = 1;
			for (; rutAux != 0; rutAux /= 10) {
				s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
			}
			if (dv == (char) (s != 0 ? s + 47 : 75)) {
				return true;
			}

		} catch (java.lang.NumberFormatException e) {
		} catch (Exception e) {
		}
		return false;
	}

	public static boolean validarFecha(String date) {
		try {
			DateFormat df = new SimpleDateFormat(DATEFORMAT);
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static Date formatearFecha(String date) {
		try {
			return new SimpleDateFormat(DATEFORMAT).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatearRut(String rut) {

		String res = rut.substring(rut.length() - 1,rut.length());
		return rut + "-" + res;

	}
}
