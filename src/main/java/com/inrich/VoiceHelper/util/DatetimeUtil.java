package com.inrich.VoiceHelper.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DatetimeUtil
{
    public DatetimeUtil()
    {
    }

    //取年月日，形式为yyyymmdd
//    public static String getDate()
//    {
//        Calendar cal = Calendar.getInstance();
//        String year = Integer.toString(cal.get(Calendar.YEAR));
//        String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
//        String date = Integer.toString(cal.get(Calendar.DATE));
//
//        StringBuffer sb = new StringBuffer(year);
//        if (month.length() == 1)
//        {
//            sb.append("0").append(month);
//        }
//        else
//        {
//            sb.append(month);
//        }
//
//        if (date.length() == 1)
//        {
//            sb.append("0").append(date);
//        }
//        else
//        {
//            sb.append(date);
//        }
//
//        return sb.toString();
//    }

    /**
     * 
     * 取时分秒，形式为hhmmss
     * 
     * @return
     */
    public static String getTime()
    {
        Calendar cal = Calendar.getInstance();
        String hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        String minute = Integer.toString(cal.get(Calendar.MINUTE));
        String second = Integer.toString(cal.get(Calendar.SECOND));

        StringBuffer sb = new StringBuffer("");

        if (hour.length() == 1)
        {
            sb.append("0").append(hour);
        }
        else
        {
            sb.append(hour);
        }

        if (minute.length() == 1)
        {
            sb.append("0").append(minute);
        }
        else
        {
            sb.append(minute);
        }

        if (second.length() == 1)
        {
            sb.append("0").append(second);
        }
        else
        {
            sb.append(second);
        }

        return sb.toString();
    }

    
    /**
     * 
     * 取年月日时分秒，形式为yyyymmdd-hhmmss
     * 
     * @return
     */
    public static String getDateTime()
    {
        Calendar cal = Calendar.getInstance();
        String year = Integer.toString(cal.get(Calendar.YEAR));
        String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
        String date = Integer.toString(cal.get(Calendar.DATE));
        String hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        String minute = Integer.toString(cal.get(Calendar.MINUTE));
        String second = Integer.toString(cal.get(Calendar.SECOND));

        StringBuffer sb = new StringBuffer(year);

        if (month.length() == 1)
        {
            sb.append("0").append(month);
        }
        else
        {
            sb.append(month);
        }

        if (date.length() == 1)
        {
            sb.append("0").append(date);
        }
        else
        {
            sb.append(date);
        }

        if (hour.length() == 1)
        {
            sb.append("-0").append(hour);
        }
        else
        {
            sb.append("-"+hour);
        }

        if (minute.length() == 1)
        {
            sb.append("0").append(minute);
        }
        else
        {
            sb.append(minute);
        }

        if (second.length() == 1)
        {
            sb.append("0").append(second);
        }
        else
        {
            sb.append(second);
        }

        return sb.toString();
    }
    
    
    /**
     * 
     * 取年月日时分秒，形式为mmddhhmmss
     * 
     * @return
     */
    public static String getMonthTime()
    {
        Calendar cal = Calendar.getInstance();
        String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
        String date = Integer.toString(cal.get(Calendar.DATE));
        String hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        String minute = Integer.toString(cal.get(Calendar.MINUTE));
        String second = Integer.toString(cal.get(Calendar.SECOND));

        StringBuffer sb = new StringBuffer();

        if (month.length() == 1)
        {
            sb.append("0").append(month);
        }
        else
        {
            sb.append(month);
        }

        if (date.length() == 1)
        {
            sb.append("0").append(date);
        }
        else
        {
            sb.append(date);
        }

        if (hour.length() == 1)
        {
            sb.append("0").append(hour);
        }
        else
        {
            sb.append(hour);
        }

        if (minute.length() == 1)
        {
            sb.append("0").append(minute);
        }
        else
        {
            sb.append(minute);
        }

        if (second.length() == 1)
        {
            sb.append("0").append(second);
        }
        else
        {
            sb.append(second);
        }

        return sb.toString();
    }
    
    /**
    sql日期型转换为带时间的字符串
     **/
    public static String toDateTimeStr(java.sql.Date dDate) {
    	if (dDate == null) {
    		return null;
    	}
    	else {
    		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(dDate);
    	}
    }

    /**
		普通日期型转换为带时间的字符串
     **/
    public static String toDateTimeStr(java.util.Date dDate) {
    	if (dDate == null) {
    		return null;
    	}
    	else {
    		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(dDate);
    	}
    }

    /**
     * "20120828" -> "2012-08-28"
     * @param date
     * @return
     */
    public static String addMinus(String date)
    {
    	StringBuffer sb = new StringBuffer();
    	
    	sb.append(date.substring(0, 4));
    	sb.append("-");
    	sb.append(date.substring(4, 6));
    	sb.append("-");
    	sb.append(date.substring(6, 8));
    	
    	return sb.toString();
    }
    
    /**
     * "2011/8/31 0:00:00" 转成 "20110831000000"
     * @param inDate
     * @return
     */
    public static String slashToFlat(String inDate)
    {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = null;
		
		try
		{
			date = sdf.parse(inDate);
		}
		catch(ParseException e) 
		{
			throw new RuntimeException(e);
		}
		
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
    }
    
    public static Date parseFlat(String inDate)
    {
		try
		{
			return new SimpleDateFormat("yyyyMMddHHmmss").parse(inDate);
		}
		catch(ParseException e) 
		{
			throw new RuntimeException(e);
		}
    }
    
    public static Date parseFlat(String flatDateFormat, String inDate)
    {
		try
		{
			if(inDate != null && !inDate.trim().equals(""))
			{
				return new SimpleDateFormat(flatDateFormat).parse(inDate);
			}
			else
			{
				return null;
			}
		}
		catch(ParseException e) 
		{
			throw new RuntimeException(e);
		}
    }

    
	  /**
	   * 取当前日期 yyyymmdd
	   * @return
	   */
	  public static String getDate()
	  {
		  String DATE_FORMAT = "yyyyMMdd";
		  
		  SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		  Calendar cal = Calendar.getInstance(); // today
		  
		  return sdf.format(cal.getTime());
	  }
//	  public static String getDate2()
//	  {
//		  String DATE_FORMAT = "yyyy-MM-dd";
//		  
//		  SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//		  Calendar cal = Calendar.getInstance(); // today
//		  
//		  return sdf.format(cal.getTime());
//	  }
	  
	  /**
	   * 
	   * @param dateFormat "yyyyMMdd"、"yyyy-MM-dd"
	   * @return
	   */
	  public static String getDate(String dateFormat)
	  {
		  SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		  Calendar cal = Calendar.getInstance(); // today
		  
		  return sdf.format(cal.getTime());
	  }

	  
	  /**
	   * 取前一天日期 yyyymmdd
	   * @return
	   */
	  public static String getLastDate()
	  {
		  String DATE_FORMAT = "yyyyMMdd";
		  
		  SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		  Calendar cal = Calendar.getInstance(); // today
		  cal.add(Calendar.DATE, -1);
		  
		  return sdf.format(cal.getTime());
	  }
	  /**
	   * 取前一天日期 yyyymmdd
	   * @return
	   */
	  public static String getLastDate(String inDateStr)
	  {
		  //
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 
		  Calendar cal = Calendar.getInstance();
		  Date inDate;
		try {
			inDate = sdf.parse(inDateStr);
			cal.setTime(inDate);
			cal.add(Calendar.DATE, -1);
			 return sdf.format(cal.getTime());
			  
		} catch (ParseException e) {
			e.printStackTrace();
		    throw new RuntimeException(e);
		}
	  }
	  
	  public static String format(java.util.Date dDate,String ft)
	  {
		  try
		  {
			  return (new SimpleDateFormat(ft)).format(dDate);
		  }
		  catch(Exception e)
		  {
			  
		  }
		  return "";
	  }

	  /**
	   * 获取当前月份
	   * @return
	   */
	  public static int getMonth()
	  {
		  Calendar cal = Calendar.getInstance();
		  
		  return cal.get(Calendar.MONTH)+1;
	  }
	  public static int getDayInMonth(String dateStr,String format){
		  SimpleDateFormat sdf = new SimpleDateFormat(format);
			 
		  Calendar cal = Calendar.getInstance();
		  try {
			cal.setTime(sdf.parse(dateStr));
			 return cal.get(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
			 throw new RuntimeException(e);
		}
		 
		  
		 
		  
	  }
	  public static int getMonth(String dateStr,String format)
	  {
		  SimpleDateFormat sdf = new SimpleDateFormat(format);
			 
		  Calendar cal = Calendar.getInstance();
		  try {
			cal.setTime(sdf.parse(dateStr));
			 return cal.get(Calendar.MONTH)+1;
		} catch (ParseException e) {
			e.printStackTrace();
			 throw new RuntimeException(e);
		}
	  }
	  /**
	   * 获取当前年份
	   * @return
	   */
	  public static int getCurrYear()
	  {
		  Calendar cal = Calendar.getInstance(); // today

		  return cal.get(Calendar.YEAR);
	  }
	  public static int getYear(String dateStr,String format)
	  {
		  SimpleDateFormat sdf = new SimpleDateFormat(format);
			 
		  Calendar cal = Calendar.getInstance();
		  try {
			cal.setTime(sdf.parse(dateStr));
			 return cal.get(Calendar.YEAR);
		} catch (ParseException e) {
			e.printStackTrace();
			 throw new RuntimeException(e);
		}
	  }
	  
	  /**
	   * 获取去年年份
	   * @return
	   */
	  public static int getLastYear()
	  {
		  Calendar cal = Calendar.getInstance(); // today
		  cal.add(Calendar.YEAR, -1);

		  return cal.get(Calendar.YEAR);
	  }
	  public static int getLastYear(String dateStr,String format)
	  {
		  SimpleDateFormat sdf = new SimpleDateFormat(format);
			 
		  Calendar cal = Calendar.getInstance();
		  try {
			cal.setTime(sdf.parse(dateStr));
			cal.add(Calendar.YEAR, -1);
			 return cal.get(Calendar.YEAR);
		} catch (ParseException e) {
			e.printStackTrace();
			 throw new RuntimeException(e);
		}
	  }
	  public static int getNextYear(String dateStr,String format)
	  {
		  SimpleDateFormat sdf = new SimpleDateFormat(format);
			 
		  Calendar cal = Calendar.getInstance();
		  try {
			cal.setTime(sdf.parse(dateStr));
			cal.add(Calendar.YEAR, 1);
			 return cal.get(Calendar.YEAR);
		} catch (ParseException e) {
			e.printStackTrace();
			 throw new RuntimeException(e);
		}
	  }
	  /**
	   * 在inDate上增减月数后得出日期
	   * @param inDate yyyymmdd
	   * @return
	   */
	  public static String getDateByAddSubMonth(String inDateStr, int monthAmt)
	  {
		  java.util.Date inDate = null;
		  
		  try
		  {
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			  inDate = sdf.parse(inDateStr);
			  Calendar cal = Calendar.getInstance();
			  cal.setTime(inDate);
			  cal.add(Calendar.MONTH, monthAmt);
			  
			  return sdf.format(cal.getTime());
		  }
		  catch(ParseException e) 
		  {
			  throw new RuntimeException(e);
		  }

	  }
	  public static String getDateByAddSubDay(String inDateStr, int monthAmt)
	  {
		  java.util.Date inDate = null;
		  
		  try
		  {
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			  inDate = sdf.parse(inDateStr);
			  Calendar cal = Calendar.getInstance();
			  cal.setTime(inDate);
			  cal.add(Calendar.DATE, monthAmt);
			  
			  return sdf.format(cal.getTime());
		  }
		  catch(ParseException e) 
		  {
			  throw new RuntimeException(e);
		  }

	  }
	  public static String getDateByAddSubDay(Date inDate, int monthAmt)
	  {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(inDate);
		  cal.add(Calendar.DATE, monthAmt);
		  
		  return sdf.format(cal.getTime());
	  }
	  /**
	   * 在inDate上增减月数后得出日期
	   * @param inDate yyyy-mm-dd
	   * @return
	   */
	  public static String getDateByAddMonth(Date inDate, int monthAmt)
	  {
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 
			  Calendar cal = Calendar.getInstance();
			  cal.setTime(inDate);
			  cal.add(Calendar.MONTH, monthAmt);
			  
			  return sdf.format(cal.getTime());

	  }
	  
	  
	  public static void main(String[] args)
	  {
		  System.out.println(getMonth());
		  System.out.println(getLastYear());
		  System.out.println(getDateByAddSubMonth("20151201", 1));
	  }
	  public static Date getSmaller(Date a,Date b ){
			if(a.getTime()>b.getTime()){
				return b;
			}else{
				return a;
			}
		}
	  public static Date getBigger(Date a,Date b ){
			if(a.getTime()>b.getTime()){
				return a;
			}else{
				return b;
			}
		}
}