import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;

public class ParserTest {
	
	
	
	@Test
	public void test1() throws Exception {
		String input = "add dinner FROM 1234 01 january 03";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd MMMM yy");
		Date date = sdf.parse("1234 01 january 03");
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test2() throws Exception {
		String input = "add dinner FROM 12.34 01 january 03";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM yy");
		Date date = sdf.parse("12.34 01 january 03");
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}

	@Test
	public void test3() throws Exception {
		String input = "add dinner FROM 01 january";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM");
		Date date = sdf.parse("01 january");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		date = cal.getTime();
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test4() throws Exception {
		String input = "add dinner FROM 01-01";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
		Date date = sdf.parse("01-01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		date = cal.getTime();
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test5() throws Exception {
		String input = "add dinner FROM 7pm 01 jan 2003";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("hha dd MMMM yyyy");
		Date date = sdf.parse("7pm 01 jan 2003");
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test6() throws Exception {
		String input = "add dinner AT 7pm today";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("hha");
		Date date = sdf.parse("7pm");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		date = cal.getTime();
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test7() throws Exception {
		String input = "add dinner AT 11:31pm wednesday";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
		Date date = sdf.parse("11:31pm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, Calendar.getInstance().get(Calendar.DATE));
		cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		Calendar now = Calendar.getInstance();
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		int HOUR_OF_DAY = cal.get(Calendar.HOUR_OF_DAY);
		int MINUTE = cal.get(Calendar.MINUTE);
		int NOW_HOUR_OF_DAY = now.get(Calendar.HOUR_OF_DAY);
		int NOW_MINUTE = now.get(Calendar.MINUTE);

		weekday = cal.get(Calendar.DAY_OF_WEEK);
		
		if (weekday != Calendar.WEDNESDAY) {
			int days = (Calendar.WEDNESDAY - weekday) % 7;
			cal.add(Calendar.DAY_OF_YEAR, days);
		} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
			cal.add(Calendar.DAY_OF_YEAR, 7);
		}
		date = cal.getTime();
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}

	@Test
	public void test8() throws Exception {
		String input = "add dinner FROM 1131 today";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Date date = sdf.parse("1131");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		date = cal.getTime();
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test9() throws Exception {
		String input = "add dinner FROM 730pm today";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("hmma");
		Date date = sdf.parse("730pm");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		date = cal.getTime();
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test10() throws Exception {
		String input = "add dinner FROM 0730pm today";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("hhmma");
		Date date = sdf.parse("0730pm");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		date = cal.getTime();
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void testcmd1() throws Exception {
		String input = "add dinner FROM 12.34 01 january 03";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		assertEquals(CommandDetails.COMMANDS.ADD ,Parser.parseCommandType(strTokens));
	}
	
	@Test
	public void testcmd2() throws Exception {
		String input = "delete 2";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		assertEquals(CommandDetails.COMMANDS.DELETE ,Parser.parseCommandType(strTokens));
	}
	
	@Test
	public void testID() throws Exception {
		String input = "2";
		int result = 2;
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));

		assertEquals(result ,Parser.parseID(strTokens));
	}
	
	@Test
	public void testend1() throws Exception {
		String input = "add dinner BY 01 january";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM");
		Date date = sdf.parse("01 january");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		date = cal.getTime();
		assertEquals(date ,Parser.parseEndDate(strTokens));
	}
	
	@Test
	public void testend2() throws Exception {
		String input = "add dinner BY 01-01";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
		Date date = sdf.parse("01-01");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		date = cal.getTime();
		assertEquals(date ,Parser.parseEndDate(strTokens));
	}
	
	@Test
	public void testend3() throws Exception {
		String input = "add dinner BY 7pm 01 jan 2003";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("hha dd MMMM yyyy");
		Date date = sdf.parse("7pm 01 jan 2003");
		
		assertEquals(date ,Parser.parseEndDate(strTokens));
	}
	
	@Test
	public void testend4() throws Exception {
		String input = "add dinner BY today";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Date date = sdf.parse("2359");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		date = cal.getTime();
		
		System.out.println(date);
		
		assertEquals(date ,Parser.parseEndDate(strTokens));
	}
	
}
