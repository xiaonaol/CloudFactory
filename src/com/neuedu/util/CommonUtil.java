package com.neuedu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * 一般工具类
 * 
 * @author koala
 *
 */
public class CommonUtil {

	public final static String DATE_FORMATE_TYPE_NOTIME = "yyyy-MM-dd";
	public final static String DATE_FORMATE_TYPE_TIME = "yyyy-MM-dd  HH:mm:ss";
	public final static String DATE_FORMATE_CODE = "yyyyMMddHHmmss";
	
	public static String codeGenerator(){
		Date d = new Date();
		return date2Str(d, new SimpleDateFormat(DATE_FORMATE_CODE) )+ (int)((d.getTime())&0xfffL);
	}
	
	/**
	 * 计算两个时间相差分钟数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int calculateDateSubtract(Date d1 , Date d2){
		return (int)(d1.getTime() - d2.getTime())/1000/60;
	}
	
	/**
	 * 针对传入时间， 增加分钟数
	 * @param d
	 * @param min
	 * @return
	 */
	public static Date datePlus (Date d , String min){
		return new Date(d.getTime()+Integer.parseInt(min)*60*1000);
	}
	
	/**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
           Pattern pattern = Pattern.compile("[0-9]*");
           Matcher isNum = pattern.matcher(str);
           if( !isNum.matches() ){
               return false;
           }
           return true;
    }

	/**
	 * 汉字转换拼音首字母
	 * @param chinese
	 * @return
	 */
	public static char chinese2Char(String chinese) {
		String pinyinStr = "";
		char[] newChar = chinese.toCharArray(); // 转为单个字符
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		if (newChar[0] > 128) {
			try {
				pinyinStr += PinyinHelper.toHanyuPinyinStringArray(newChar[0], defaultFormat)[0].charAt(0);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		} else {
			pinyinStr += newChar[0];
		}
		return pinyinStr.toCharArray()[0];
	}
	
	/**
	 * 年龄计算
	 * 
	 * @param birthDay
	 * @return
	 */
	public static int calculatAge(Date birthDay) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			System.out.println("日期：" + birthDay + " 在当前时间前");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		return age;
	}

	/**
	 * 字符类型转日期类型
	 * 
	 * @param strDate
	 * @param sdf
	 * @return
	 */
	public static Date str2Date(String strDate, SimpleDateFormat sdf) {
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 日期类型转字符串类型
	 * 
	 * @param date
	 * @param sdf
	 * @return
	 */
	public static String date2Str(Date date, SimpleDateFormat sdf) {
		return sdf.format(date);
	}
	
}
