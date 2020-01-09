package be.sdlg.apps.edcmobile.utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
	public static String getFormattedDate (Date date, Locale locale, boolean time) {
		DateFormat dateFormat;
		if (time) 
			dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);		
		else
			dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
		return dateFormat.format(date);
	}
}
