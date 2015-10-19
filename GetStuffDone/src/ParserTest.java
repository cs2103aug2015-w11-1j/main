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
	/////////
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
		String input = "add dinner FROM 12.34 01 january 03";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM yy");
		Date date = sdf.parse("12.34 01 january 03");
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test5() throws Exception {
		String input = "add dinner FROM 12.34 01 january 03";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM yy");
		Date date = sdf.parse("12.34 01 january 03");
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test6() throws Exception {
		String input = "add dinner FROM 12.34 01 january 03";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM yy");
		Date date = sdf.parse("12.34 01 january 03");
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test7() throws Exception {
		String input = "add dinner FROM 12.34 01 january 03";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM yy");
		Date date = sdf.parse("12.34 01 january 03");
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test8() throws Exception {
		String input = "add dinner FROM 12.34 01 january 03";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM yy");
		Date date = sdf.parse("12.34 01 january 03");
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test9() throws Exception {
		String input = "add dinner FROM 12.34 01 january 03";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM yy");
		Date date = sdf.parse("12.34 01 january 03");
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test10() throws Exception {
		String input = "add dinner FROM 12.34 01 january 03";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM yy");
		Date date = sdf.parse("12.34 01 january 03");
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test11() throws Exception {
		String input = "add dinner FROM 12.34 01 january 03";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM yy");
		Date date = sdf.parse("12.34 01 january 03");
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
	
	@Test
	public void test12() throws Exception {
		String input = "add dinner FROM 12.34 01 january 03";
		ArrayList<String> strTokens = new ArrayList<String>(Arrays.asList(input.split(" ")));
		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM yy");
		Date date = sdf.parse("12.34 01 january 03");
		
		assertEquals(date ,Parser.parseStartDate(strTokens));
	}
}
