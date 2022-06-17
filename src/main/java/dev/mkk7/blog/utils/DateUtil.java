package dev.mkk7.blog.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class DateUtil {
	private static class TIME_MAXIMUM {
		public static final int SEC = 60;
		public static final int MIN = 60;
		public static final int HOUR = 24;
		public static final int DAY = 30;
		public static final int MONTH = 12;
	}
	
	public static String dateFormat(Date date) {
		long curTime = System.currentTimeMillis();
		long creDate = date.getTime();
		long diffTime = (curTime - creDate) / 1000;
		String msg = "";
		
		if(diffTime < TIME_MAXIMUM.SEC) {
			msg = diffTime + "초 전";
		} else if((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) { // (diffTime = diffTime / 60) < min 
			msg = diffTime + "분 전";
		} else if((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) { // (diffTime = diffTime / 60) < hour 
																		// 여기서 diffTime은 위 if 문에서 60으로 한번 나누어진 상태
			msg = diffTime + "시간 전";
		} else {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
			msg = simpleDateFormat.format(creDate);
		}
		
		return msg;
		
	}
}
