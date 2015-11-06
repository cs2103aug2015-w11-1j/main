package parser;

import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import commandDetail.CommandDetails;

public class ParserTest {

	/*************************************************************************************************
	 ************************************* START DATE FORMAT TEST ************************************
	 *************************************************************************************************/
	
	@Test
	public void startTest1() throws Exception {
		String input = "add dinner FROM 1234 01 january 23";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd MMMM yy");
		Date date = sdf.parse("1234 01 january 23");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest2() throws Exception {
		String input = "add dinner FROM 12.34 01 january 23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM yy");
		Date date = sdf.parse("12.34 01 january 23");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest3() throws Exception {
		String input = "add dinner FROM 12:34 01 january 23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd MMMM yy");
		Date date = sdf.parse("12:34 01 january 23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest4() throws Exception {
		String input = "add dinner FROM 1234am 01 january 23";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd MMMM yy");
		Date date = sdf.parse("1234am 01 january 23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest5() throws Exception {
		String input = "add dinner FROM 7pm 01 jan 2023";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd MMMM yy");
		Date date = sdf.parse("7pm 01 jan 2023");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest7() throws Exception {
		String input = "add dinner AT 7.30pm 01 january 16";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd MMMM yy");
		Date date = sdf.parse("7.30pm 01 january 16");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest8() throws Exception {
		String input = "add dinner AT 7:30pm 01 january 16";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd MMMM yy");
		Date date = sdf.parse("7:30pm 01 january 16");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest9() throws Exception {
		String input = "add dinner AT 01 january 16";

		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yy");
		Date date = sdf.parse("01 january 16");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest10() throws Exception {
		String input = "add dinner AT 1330 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd MM yy");
		Date date = sdf.parse("1330 01 01 18");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest11() throws Exception {
		String input = "add dinner AT 13.30 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MM yy");
		Date date = sdf.parse("13.30 01 01 18");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest12() throws Exception {
		String input = "add dinner AT 13:30 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd MM yy");
		Date date = sdf.parse("13:30 01 01 18");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest13() throws Exception {
		String input = "add dinner AT 1230pm 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd MM yy");
		Date date = sdf.parse("1230pm 01 01 18");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest14() throws Exception {
		String input = "add dinner AT 11pm 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd MM yy");
		Date date = sdf.parse("11pm 01 01 18");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest15() throws Exception {
		String input = "add dinner AT 130pm 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd MM yy");
		Date date = sdf.parse("130pm 01 01 18");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest16() throws Exception {
		String input = "add dinner AT 11.30pm 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd MM yy");
		Date date = sdf.parse("11.30pm 01 01 18");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest17() throws Exception {
		String input = "add dinner AT 11:30pm 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd MM yy");
		Date date = sdf.parse("11:30pm 01 01 18");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest18() throws Exception {
		String input = "add dinner AT 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yy");
		Date date = sdf.parse("01 01 18");

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest19() throws Exception {
		String input = "add dinner AT 1234 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd MMMM");
		Date date = sdf.parse("1234 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest20() throws Exception {
		String input = "add dinner AT 12.34 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM");
		Date date = sdf.parse("12.34 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest21() throws Exception {
		String input = "add dinner AT 12:34 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd MMMM");
		Date date = sdf.parse("12:34 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest22() throws Exception {
		String input = "add dinner AT 11pm 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd MMMM");
		Date date = sdf.parse("11pm 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest23() throws Exception {
		String input = "add dinner AT 530pm 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd MMMM");
		Date date = sdf.parse("530pm 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest24() throws Exception {
		String input = "add dinner AT 530pm 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd MMMM");
		Date date = sdf.parse("530pm 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest25() throws Exception {
		String input = "add dinner AT 05.30pm 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd MMMM");
		Date date = sdf.parse("05.30pm 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest26() throws Exception {
		String input = "add dinner AT 05:30pm 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd MMMM");
		Date date = sdf.parse("05:30pm 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest27() throws Exception {
		String input = "add dinner AT 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM");
		Date date = sdf.parse("01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest28() throws Exception {
		String input = "add dinner AT 1334 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd MM");
		Date date = sdf.parse("1334 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest29() throws Exception {
		String input = "add dinner AT 13.34 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MM");
		Date date = sdf.parse("13.34 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest30() throws Exception {
		String input = "add dinner AT 13:34 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd MM");
		Date date = sdf.parse("13:34 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest31() throws Exception {
		String input = "add dinner AT 1230am 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd MM");
		Date date = sdf.parse("1230am 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest32() throws Exception {
		String input = "add dinner AT 12pm 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd MM");
		Date date = sdf.parse("12pm 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest33() throws Exception {
		String input = "add dinner AT 130pm 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd MM");
		Date date = sdf.parse("130pm 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest34() throws Exception {
		String input = "add dinner AT 10.30pm 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd MM");
		Date date = sdf.parse("10.30pm 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest35() throws Exception {
		String input = "add dinner AT 10:30pm 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd MM");
		Date date = sdf.parse("10:30pm 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest36() throws Exception {
		String input = "add dinner AT 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("dd MM");
		Date date = sdf.parse(" 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest37() throws Exception {
		String input = "add dinner AT 2359 01/december/23";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MMMM/yy");
		Date date = sdf.parse("2359 01/december/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest38() throws Exception {
		String input = "add dinner AT 23.59 01/december/23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd/MMMMM/yy");
		Date date = sdf.parse("23.59 01/december/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest39() throws Exception {
		String input = "add dinner AT 23:59 01/december/23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MMMMM/yy");
		Date date = sdf.parse("23:59 01/december/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest40() throws Exception {
		String input = "add dinner AT 1130pm 01/december/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd/MMMM/yy");
		Date date = sdf.parse("1130pm 01/december/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest41() throws Exception {
		String input = "add dinner AT 12am 01/december/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd/MMMM/yy");
		Date date = sdf.parse("12am 01/december/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest42() throws Exception {
		String input = "add dinner AT 130am 01/december/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd/MMMM/yy");
		Date date = sdf.parse("130am 01/december/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest43() throws Exception {
		String input = "add dinner AT 11.30am 01/december/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd/MMMM/yy");
		Date date = sdf.parse("11.30am 01/december/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest44() throws Exception {
		String input = "add dinner AT 11:30am 01/december/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd/MMMM/yy");
		Date date = sdf.parse("11:30am 01/december/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest45() throws Exception {
		String input = "add dinner AT 01/december/23";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yy");
		Date date = sdf.parse("01/december/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest46() throws Exception {
		String input = "add dinner AT 2359 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MM/yy");
		Date date = sdf.parse("2359 01/01/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest47() throws Exception {
		String input = "add dinner AT 23.59 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd/MM/yy");
		Date date = sdf.parse("23.59 01/01/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest48() throws Exception {
		String input = "add dinner AT 23:59 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yy");
		Date date = sdf.parse("23:59 01/01/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest49() throws Exception {
		String input = "add dinner AT 1230pm 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd/MM/yy");
		Date date = sdf.parse("1230pm 01/01/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest50() throws Exception {
		String input = "add dinner AT 12am 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd/MM/yy");
		Date date = sdf.parse("12am 01/01/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest51() throws Exception {
		String input = "add dinner AT 130am 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd/MM/yy");
		Date date = sdf.parse("130am 01/01/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest52() throws Exception {
		String input = "add dinner AT 11.30am 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd/MM/yy");
		Date date = sdf.parse("11.30am 01/01/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest53() throws Exception {
		String input = "add dinner AT 11:30am 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd/MM/yy");
		Date date = sdf.parse("11:30am 01/01/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest54() throws Exception {
		String input = "add dinner AT 11/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		Date date = sdf.parse("11/01/23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest55() throws Exception {
		String input = "add dinner AT 1330 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MMMM");
		Date date = sdf.parse("1330 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest56() throws Exception {
		String input = "add dinner AT 14.30 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd/MMMM");
		Date date = sdf.parse("14.30 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest57() throws Exception {
		String input = "add dinner AT 14:30 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MMMM");
		Date date = sdf.parse("14:30 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest58() throws Exception {
		String input = "add dinner AT 1230pm 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd/MMMM");
		Date date = sdf.parse("1230pm 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest59() throws Exception {
		String input = "add dinner AT 12pm 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd/MMMM");
		Date date = sdf.parse("12pm 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest60() throws Exception {
		String input = "add dinner AT 130pm 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd/MMMM");
		Date date = sdf.parse("130pm 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest61() throws Exception {
		String input = "add dinner AT 11.30pm 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd/MMMM");
		Date date = sdf.parse("11.30pm 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest62() throws Exception {
		String input = "add dinner AT 11:30pm 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd/MMMM");
		Date date = sdf.parse("11:30pm 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest63() throws Exception {
		String input = "add dinner AT 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM");
		Date date = sdf.parse("11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest64() throws Exception {
		String input = "add dinner AT 1230 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MM");
		Date date = sdf.parse("1230 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest65() throws Exception {
		String input = "add dinner AT 1230 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MM");
		Date date = sdf.parse("1230 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest66() throws Exception {
		String input = "add dinner AT 12.30 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd/MM");
		Date date = sdf.parse("12.30 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest67() throws Exception {
		String input = "add dinner AT 12:30 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM");
		Date date = sdf.parse("12:30 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest68() throws Exception {
		String input = "add dinner AT 1130pm 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd/MM");
		Date date = sdf.parse("1130pm 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest69() throws Exception {
		String input = "add dinner AT 11pm 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd/MM");
		Date date = sdf.parse("11pm 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest70() throws Exception {
		String input = "add dinner AT 130pm 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd/MM");
		Date date = sdf.parse("130pm 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest71() throws Exception {
		String input = "add dinner AT 11.30pm 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd/MM");
		Date date = sdf.parse("11.30pm 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest72() throws Exception {
		String input = "add dinner AT 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
		Date date = sdf.parse("11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest73() throws Exception {
		String input = "add dinner AT 130pm 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd/MM");
		Date date = sdf.parse("130pm 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest74() throws Exception {
		String input = "add dinner AT 2250 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd-MMMM-yy");
		Date date = sdf.parse("2250 11-april-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest75() throws Exception {
		String input = "add dinner AT 22.50 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd-MMMM-yy");
		Date date = sdf.parse("22.50 11-april-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest76() throws Exception {
		String input = "add dinner AT 22:50 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MMMM-yy");
		Date date = sdf.parse("22:50 11-april-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest77() throws Exception {
		String input = "add dinner AT 1150pm 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd-MMMM-yy");
		Date date = sdf.parse("1150pm 11-april-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest78() throws Exception {
		String input = "add dinner AT 11pm 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd-MMMM-yy");
		Date date = sdf.parse("11pm 11-april-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest79() throws Exception {
		String input = "add dinner AT 156pm 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd-MMMM-yy");
		Date date = sdf.parse("156pm 11-april-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest80() throws Exception {
		String input = "add dinner AT 11.56pm 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd-MMMM-yy");
		Date date = sdf.parse("11.56pm 11-april-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest81() throws Exception {
		String input = "add dinner AT 11:56pm 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd-MMMM-yy");
		Date date = sdf.parse("11:56pm 11-april-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest82() throws Exception {
		String input = "add dinner AT 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yy");
		Date date = sdf.parse("11-april-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest83() throws Exception {
		String input = "add dinner AT 22.30 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd-MM-yy");
		Date date = sdf.parse("22.30 11-05-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest84() throws Exception {
		String input = "add dinner AT 22:30 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yy");
		Date date = sdf.parse("22:30 11-05-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest85() throws Exception {
		String input = "add dinner AT 1130pm 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd-MM-yy");
		Date date = sdf.parse("1130pm 11-05-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest86() throws Exception {
		String input = "add dinner AT 11pm 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd-MM-yy");
		Date date = sdf.parse("11pm 11-05-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest87() throws Exception {
		String input = "add dinner AT 137pm 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd-MM-yy");
		Date date = sdf.parse("137pm 11-05-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest88() throws Exception {
		String input = "add dinner AT 11.37pm 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd-MM-yy");
		Date date = sdf.parse("11.37pm 11-05-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest89() throws Exception {
		String input = "add dinner AT 11:37pm 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd-MM-yy");
		Date date = sdf.parse("11:37pm 11-05-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest90() throws Exception {
		String input = "add dinner AT 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
		Date date = sdf.parse("11-05-23");
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest92() throws Exception {
		String input = "add dinner AT 23.59 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd-MMMM");
		Date date = sdf.parse("23.59 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest93() throws Exception {
		String input = "add dinner AT 23:59 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MMMM");
		Date date = sdf.parse("23:59 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest94() throws Exception {
		String input = "add dinner AT 1130pm 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd-MMMM");
		Date date = sdf.parse("1130pm 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest95() throws Exception {
		String input = "add dinner AT 11pm 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd-MMMM");
		Date date = sdf.parse("11pm 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest96() throws Exception {
		String input = "add dinner AT 130pm 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd-MMMM");
		Date date = sdf.parse("130pm 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest97() throws Exception {
		String input = "add dinner AT 11.30pm 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd-MMMM");
		Date date = sdf.parse("11.30pm 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest98() throws Exception {
		String input = "add dinner AT 11:30pm 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd-MMMM");
		Date date = sdf.parse("11:30pm 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest99() throws Exception {
		String input = "add dinner AT 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM");
		Date date = sdf.parse("11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest100() throws Exception {
		String input = "add dinner AT 2359 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd-MM");
		Date date = sdf.parse("2359 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest101() throws Exception {
		String input = "add dinner AT 23.59 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd-MM");
		Date date = sdf.parse("23.59 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest102() throws Exception {
		String input = "add dinner AT 23:59 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM");
		Date date = sdf.parse("23:59 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest103() throws Exception {
		String input = "add dinner AT 1134pm 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd-MM");
		Date date = sdf.parse("1134pm 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest104() throws Exception {
		String input = "add dinner AT 11pm 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd-MM");
		Date date = sdf.parse("11pm 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest105() throws Exception {
		String input = "add dinner AT 130pm 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd-MM");
		Date date = sdf.parse("130pm 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest106() throws Exception {
		String input = "add dinner AT 11.30pm 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd-MM");
		Date date = sdf.parse("11.30pm 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest107() throws Exception {
		String input = "add dinner AT 11:30pm 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd-MM");
		Date date = sdf.parse("11:30pm 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest108() throws Exception {
		String input = "add dinner AT 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
		Date date = sdf.parse("11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest109() throws Exception {
		String input = "add dinner AT 1159pm";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma");
		Date date = sdf.parse("1159pm");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	private Date setDefaultMonthYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		cal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
		date = cal.getTime();
		return date;
	}

	@Test
	public void startTest110() throws Exception {
		String input = "add dinner AT 11pm";

		SimpleDateFormat sdf = new SimpleDateFormat("hha");
		Date date = sdf.parse("11pm");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest111() throws Exception {
		String input = "add dinner AT 959pm";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma");
		Date date = sdf.parse("959pm");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest112() throws Exception {
		String input = "add dinner AT 11.59pm";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma");
		Date date = sdf.parse("11.59pm");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest113() throws Exception {
		String input = "add dinner AT 11:59pm";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
		Date date = sdf.parse("11:59pm");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest114() throws Exception {
		String input = "add dinner AT 23.59";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
		Date date = sdf.parse("23.59");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest115() throws Exception {
		String input = "add dinner AT 23:59";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = sdf.parse("23:59");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest117() throws Exception {
		String input = "add dinner AT 2359";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Date date = sdf.parse("2359");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest118() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Date date = sdf.parse("0000");
		String input = "add dinner AT Monday";

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		cal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
		int days = (Calendar.MONDAY - cal.get(Calendar.DAY_OF_WEEK)) % 7;
		if (days < 0) {
			days = days + 7;
		}
		cal.add(Calendar.DAY_OF_YEAR, days);

		date = cal.getTime();
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest119() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Date date = sdf.parse("1200pm");
		String input = "add dinner AT 1200 friday";

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		cal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));

		Calendar now = Calendar.getInstance();
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		int HOUR_OF_DAY = cal.get(Calendar.HOUR_OF_DAY);
		int MINUTE = cal.get(Calendar.MINUTE);
		int NOW_HOUR_OF_DAY = now.get(Calendar.HOUR_OF_DAY);
		int NOW_MINUTE = now.get(Calendar.MINUTE);

		if (weekday != Calendar.FRIDAY) {
			int days = (Calendar.FRIDAY - weekday) % 7;
			if (days < 0) {
				days = days + 7;
			}
			cal.add(Calendar.DAY_OF_YEAR, days);
		} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
			cal.add(Calendar.DAY_OF_YEAR, 7);
		}
		date = cal.getTime();
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test
	public void startTest120() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Date date = sdf.parse("0000");
		String input = "add dinner AT TOMORROW";

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		cal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
		cal.add(Calendar.DAY_OF_YEAR, 1);

		date = cal.getTime();
		assertEquals(date, Parser.parse(input).getStartDate());
	}

	/*************************************************************************************************
	 ************************************* END DATE FORMAT TEST **************************************
	 *************************************************************************************************/
	
	public void endTest1() throws Exception {
		String input = "add dinner on 1234 01 december 23";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd MMMM yy");
		Date date = sdf.parse("1234 01 december 23");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest2() throws Exception {
		String input = "add dinner on 12.34 01 december 23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM yy");
		Date date = sdf.parse("12.34 01 december 23");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest3() throws Exception {
		String input = "add dinner on 12:34 01 december 23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd MMMM yy");
		Date date = sdf.parse("12:34 01 december 23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest4() throws Exception {
		String input = "add dinner on 1234am 01 december 23";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd MMMM yy");
		Date date = sdf.parse("1234am 01 december 23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest5() throws Exception {
		String input = "add dinner on 7pm 01 jan 2023";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd MMMM yy");
		Date date = sdf.parse("7pm 01 jan 2023");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest7() throws Exception {
		String input = "add dinner on 7.30pm 01 december 15";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd MMMM yy");
		Date date = sdf.parse("7.30pm 01 december 15");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest8() throws Exception {
		String input = "add dinner on 7:30pm 01 december 15";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd MMMM yy");
		Date date = sdf.parse("7:30pm 01 december 15");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest9() throws Exception {
		String input = "add dinner on 01 december 15";

		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yy");
		Date date = sdf.parse("01 december 15");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest10() throws Exception {
		String input = "add dinner on 1330 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd MM yy");
		Date date = sdf.parse("1330 01 01 18");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest11() throws Exception {
		String input = "add dinner on 13.30 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MM yy");
		Date date = sdf.parse("13.30 01 01 18");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest12() throws Exception {
		String input = "add dinner on 13:30 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd MM yy");
		Date date = sdf.parse("13:30 01 01 18");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest13() throws Exception {
		String input = "add dinner on 1230pm 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd MM yy");
		Date date = sdf.parse("1230pm 01 01 18");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest14() throws Exception {
		String input = "add dinner on 11pm 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd MM yy");
		Date date = sdf.parse("11pm 01 01 18");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest15() throws Exception {
		String input = "add dinner on 130pm 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd MM yy");
		Date date = sdf.parse("130pm 01 01 18");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest16() throws Exception {
		String input = "add dinner on 11.30pm 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd MM yy");
		Date date = sdf.parse("11.30pm 01 01 18");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest17() throws Exception {
		String input = "add dinner on 11:30pm 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd MM yy");
		Date date = sdf.parse("11:30pm 01 01 18");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest18() throws Exception {
		String input = "add dinner on 01 01 18";

		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yy");
		Date date = sdf.parse("01 01 18");

		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest19() throws Exception {
		String input = "add dinner on 1234 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd MMMM");
		Date date = sdf.parse("1234 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	private Date setDefaultYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		date = cal.getTime();
		return date;
	}

	@Test
	public void endTest20() throws Exception {
		String input = "add dinner on 12.34 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MMMM");
		Date date = sdf.parse("12.34 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest21() throws Exception {
		String input = "add dinner on 12:34 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd MMMM");
		Date date = sdf.parse("12:34 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest22() throws Exception {
		String input = "add dinner on 11pm 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd MMMM");
		Date date = sdf.parse("11pm 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest23() throws Exception {
		String input = "add dinner on 530pm 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd MMMM");
		Date date = sdf.parse("530pm 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest24() throws Exception {
		String input = "add dinner on 530pm 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd MMMM");
		Date date = sdf.parse("530pm 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest25() throws Exception {
		String input = "add dinner on 05.30pm 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd MMMM");
		Date date = sdf.parse("05.30pm 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest26() throws Exception {
		String input = "add dinner on 05:30pm 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd MMMM");
		Date date = sdf.parse("05:30pm 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest27() throws Exception {
		String input = "add dinner on 01 december";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd MMMM");
		Date date = sdf.parse("2359 01 december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest28() throws Exception {
		String input = "add dinner on 1334 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd MM");
		Date date = sdf.parse("1334 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest29() throws Exception {
		String input = "add dinner on 13.34 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd MM");
		Date date = sdf.parse("13.34 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest30() throws Exception {
		String input = "add dinner on 13:34 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd MM");
		Date date = sdf.parse("13:34 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest31() throws Exception {
		String input = "add dinner on 1230am 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd MM");
		Date date = sdf.parse("1230am 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest32() throws Exception {
		String input = "add dinner on 12pm 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd MM");
		Date date = sdf.parse("12pm 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest33() throws Exception {
		String input = "add dinner on 130pm 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd MM");
		Date date = sdf.parse("130pm 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest34() throws Exception {
		String input = "add dinner on 10.30pm 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd MM");
		Date date = sdf.parse("10.30pm 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest35() throws Exception {
		String input = "add dinner on 10:30pm 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd MM");
		Date date = sdf.parse("10:30pm 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest36() throws Exception {
		String input = "add dinner on 01 12";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd MM");
		Date date = sdf.parse("2359 01 12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest37() throws Exception {
		String input = "add dinner on 2359 01/january/23";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MMMM/yy");
		Date date = sdf.parse("2359 01/january/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest38() throws Exception {
		String input = "add dinner on 23.59 01/january/23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd/MMMMM/yy");
		Date date = sdf.parse("23.59 01/january/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest39() throws Exception {
		String input = "add dinner on 23:59 01/january/23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MMMMM/yy");
		Date date = sdf.parse("23:59 01/january/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest40() throws Exception {
		String input = "add dinner on 1130pm 01/january/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd/MMMM/yy");
		Date date = sdf.parse("1130pm 01/january/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest41() throws Exception {
		String input = "add dinner on 12am 01/january/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd/MMMM/yy");
		Date date = sdf.parse("12am 01/january/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest42() throws Exception {
		String input = "add dinner on 130am 01/january/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd/MMMM/yy");
		Date date = sdf.parse("130am 01/january/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest43() throws Exception {
		String input = "add dinner on 11.30am 01/january/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd/MMMM/yy");
		Date date = sdf.parse("11.30am 01/january/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest44() throws Exception {
		String input = "add dinner on 11:30am 01/january/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd/MMMM/yy");
		Date date = sdf.parse("11:30am 01/january/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest45() throws Exception {
		String input = "add dinner on 01/january/23";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yy");
		Date date = sdf.parse("01/january/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest46() throws Exception {
		String input = "add dinner on 2359 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MM/yy");
		Date date = sdf.parse("2359 01/01/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest47() throws Exception {
		String input = "add dinner on 23.59 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd/MM/yy");
		Date date = sdf.parse("23.59 01/01/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest48() throws Exception {
		String input = "add dinner on 23:59 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yy");
		Date date = sdf.parse("23:59 01/01/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest49() throws Exception {
		String input = "add dinner on 1230pm 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd/MM/yy");
		Date date = sdf.parse("1230pm 01/01/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest50() throws Exception {
		String input = "add dinner on 12am 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd/MM/yy");
		Date date = sdf.parse("12am 01/01/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest51() throws Exception {
		String input = "add dinner on 130am 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd/MM/yy");
		Date date = sdf.parse("130am 01/01/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest52() throws Exception {
		String input = "add dinner on 11.30am 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd/MM/yy");
		Date date = sdf.parse("11.30am 01/01/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest53() throws Exception {
		String input = "add dinner on 11:30am 01/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd/MM/yy");
		Date date = sdf.parse("11:30am 01/01/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest54() throws Exception {
		String input = "add dinner on 11/01/23";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		Date date = sdf.parse("11/01/23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest55() throws Exception {
		String input = "add dinner on 1330 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MMMM");
		Date date = sdf.parse("1330 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest56() throws Exception {
		String input = "add dinner on 14.30 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd/MMMM");
		Date date = sdf.parse("14.30 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest57() throws Exception {
		String input = "add dinner on 14:30 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MMMM");
		Date date = sdf.parse("14:30 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest58() throws Exception {
		String input = "add dinner on 1230pm 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd/MMMM");
		Date date = sdf.parse("1230pm 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest59() throws Exception {
		String input = "add dinner on 12pm 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd/MMMM");
		Date date = sdf.parse("12pm 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest60() throws Exception {
		String input = "add dinner on 130pm 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd/MMMM");
		Date date = sdf.parse("130pm 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest61() throws Exception {
		String input = "add dinner on 11.30pm 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd/MMMM");
		Date date = sdf.parse("11.30pm 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest62() throws Exception {
		String input = "add dinner on 11:30pm 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd/MMMM");
		Date date = sdf.parse("11:30pm 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest63() throws Exception {
		String input = "add dinner on 11/december";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MMMM");
		Date date = sdf.parse("2359 11/december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest64() throws Exception {
		String input = "add dinner on 1230 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MM");
		Date date = sdf.parse("1230 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest65() throws Exception {
		String input = "add dinner on 1230 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MM");
		Date date = sdf.parse("1230 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest66() throws Exception {
		String input = "add dinner on 12.30 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd/MM");
		Date date = sdf.parse("12.30 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest67() throws Exception {
		String input = "add dinner on 12:30 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM");
		Date date = sdf.parse("12:30 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest68() throws Exception {
		String input = "add dinner on 1130pm 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd/MM");
		Date date = sdf.parse("1130pm 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest69() throws Exception {
		String input = "add dinner on 11pm 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd/MM");
		Date date = sdf.parse("11pm 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest70() throws Exception {
		String input = "add dinner on 130pm 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd/MM");
		Date date = sdf.parse("130pm 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest71() throws Exception {
		String input = "add dinner on 11.30pm 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd/MM");
		Date date = sdf.parse("11.30pm 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest72() throws Exception {
		String input = "add dinner on 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd/MM");
		Date date = sdf.parse("2359 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest73() throws Exception {
		String input = "add dinner on 130pm 11/12";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd/MM");
		Date date = sdf.parse("130pm 11/12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest74() throws Exception {
		String input = "add dinner on 2250 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd-MMMM-yy");
		Date date = sdf.parse("2250 11-april-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest75() throws Exception {
		String input = "add dinner on 22.50 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd-MMMM-yy");
		Date date = sdf.parse("22.50 11-april-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest76() throws Exception {
		String input = "add dinner on 22:50 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MMMM-yy");
		Date date = sdf.parse("22:50 11-april-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest77() throws Exception {
		String input = "add dinner on 1150pm 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd-MMMM-yy");
		Date date = sdf.parse("1150pm 11-april-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest78() throws Exception {
		String input = "add dinner on 11pm 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd-MMMM-yy");
		Date date = sdf.parse("11pm 11-april-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest79() throws Exception {
		String input = "add dinner on 156pm 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd-MMMM-yy");
		Date date = sdf.parse("156pm 11-april-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest80() throws Exception {
		String input = "add dinner on 11.56pm 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd-MMMM-yy");
		Date date = sdf.parse("11.56pm 11-april-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest81() throws Exception {
		String input = "add dinner on 11:56pm 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd-MMMM-yy");
		Date date = sdf.parse("11:56pm 11-april-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest82() throws Exception {
		String input = "add dinner on 11-april-23";

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yy");
		Date date = sdf.parse("11-april-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest83() throws Exception {
		String input = "add dinner on 22.30 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd-MM-yy");
		Date date = sdf.parse("22.30 11-05-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest84() throws Exception {
		String input = "add dinner on 22:30 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yy");
		Date date = sdf.parse("22:30 11-05-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest85() throws Exception {
		String input = "add dinner on 1130pm 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd-MM-yy");
		Date date = sdf.parse("1130pm 11-05-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest86() throws Exception {
		String input = "add dinner on 11pm 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd-MM-yy");
		Date date = sdf.parse("11pm 11-05-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest87() throws Exception {
		String input = "add dinner on 137pm 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd-MM-yy");
		Date date = sdf.parse("137pm 11-05-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest88() throws Exception {
		String input = "add dinner on 11.37pm 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd-MM-yy");
		Date date = sdf.parse("11.37pm 11-05-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest89() throws Exception {
		String input = "add dinner on 11:37pm 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd-MM-yy");
		Date date = sdf.parse("11:37pm 11-05-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest90() throws Exception {
		String input = "add dinner on 11-05-23";

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
		Date date = sdf.parse("11-05-23");
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest92() throws Exception {
		String input = "add dinner on 23.59 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd-MMMM");
		Date date = sdf.parse("23.59 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest93() throws Exception {
		String input = "add dinner on 23:59 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MMMM");
		Date date = sdf.parse("23:59 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest94() throws Exception {
		String input = "add dinner on 1130pm 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd-MMMM");
		Date date = sdf.parse("1130pm 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest95() throws Exception {
		String input = "add dinner on 11pm 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd-MMMM");
		Date date = sdf.parse("11pm 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest96() throws Exception {
		String input = "add dinner on 130pm 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd-MMMM");
		Date date = sdf.parse("130pm 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest97() throws Exception {
		String input = "add dinner on 11.30pm 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd-MMMM");
		Date date = sdf.parse("11.30pm 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest98() throws Exception {
		String input = "add dinner on 11:30pm 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd-MMMM");
		Date date = sdf.parse("11:30pm 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest99() throws Exception {
		String input = "add dinner on 11-december";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd-MMMM");
		Date date = sdf.parse("2359 11-december");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest100() throws Exception {
		String input = "add dinner BY 2359 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd-MM");
		Date date = sdf.parse("2359 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest101() throws Exception {
		String input = "add dinner on 23.59 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm dd-MM");
		Date date = sdf.parse("23.59 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest102() throws Exception {
		String input = "add dinner BY 23:59 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM");
		Date date = sdf.parse("23:59 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest103() throws Exception {
		String input = "add dinner on 1134pm 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma dd-MM");
		Date date = sdf.parse("1134pm 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest104() throws Exception {
		String input = "add dinner on 11pm 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("hha dd-MM");
		Date date = sdf.parse("11pm 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest105() throws Exception {
		String input = "add dinner on 130pm 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma dd-MM");
		Date date = sdf.parse("130pm 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest106() throws Exception {
		String input = "add dinner on 11.30pm 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma dd-MM");
		Date date = sdf.parse("11.30pm 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest107() throws Exception {
		String input = "add dinner on 11:30pm 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma dd-MM");
		Date date = sdf.parse("11:30pm 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest108() throws Exception {
		String input = "add dinner on 11-12";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm dd-MM");
		Date date = sdf.parse("2359 11-12");
		date = setDefaultYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest109() throws Exception {
		String input = "add dinner on 1159pm";

		SimpleDateFormat sdf = new SimpleDateFormat("hhmma");
		Date date = sdf.parse("1159pm");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest110() throws Exception {
		String input = "add dinner on 11pm";

		SimpleDateFormat sdf = new SimpleDateFormat("hha");
		Date date = sdf.parse("11pm");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest111() throws Exception {
		String input = "add dinner on 959pm";

		SimpleDateFormat sdf = new SimpleDateFormat("hmma");
		Date date = sdf.parse("959pm");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest112() throws Exception {
		String input = "add dinner on 11.59pm";

		SimpleDateFormat sdf = new SimpleDateFormat("hh.mma");
		Date date = sdf.parse("11.59pm");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest113() throws Exception {
		String input = "add dinner on 11:59pm";

		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
		Date date = sdf.parse("11:59pm");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest114() throws Exception {
		String input = "add dinner on 23.59";

		SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
		Date date = sdf.parse("23.59");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest115() throws Exception {
		String input = "add dinner on 23:59";

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = sdf.parse("23:59");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest117() throws Exception {
		String input = "add dinner on 2359";

		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Date date = sdf.parse("2359");
		date = setDefaultMonthYear(date);
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest118() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Date date = sdf.parse("2359");
		String input = "add dinner on Monday";

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		cal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
		int days = (Calendar.MONDAY - cal.get(Calendar.DAY_OF_WEEK)) % 7;
		if (days < 0) {
			days = days + 7;
		}
		cal.add(Calendar.DAY_OF_YEAR, days);

		date = cal.getTime();
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest119() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Date date = sdf.parse("1200pm");
		String input = "add dinner By 1200 sunday";

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		cal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));

		Calendar now = Calendar.getInstance();
		int weekday = cal.get(Calendar.DAY_OF_WEEK);
		int HOUR_OF_DAY = cal.get(Calendar.HOUR_OF_DAY);
		int MINUTE = cal.get(Calendar.MINUTE);
		int NOW_HOUR_OF_DAY = now.get(Calendar.HOUR_OF_DAY);
		int NOW_MINUTE = now.get(Calendar.MINUTE);

		if (weekday != Calendar.SUNDAY) {
			int days = (Calendar.SUNDAY - weekday) % 7;
			if (days < 0) {
				days = days + 7;
			}
			cal.add(Calendar.DAY_OF_YEAR, days);
		} else if (HOUR_OF_DAY < NOW_HOUR_OF_DAY || (HOUR_OF_DAY == NOW_HOUR_OF_DAY && MINUTE < NOW_MINUTE)) {
			cal.add(Calendar.DAY_OF_YEAR, 7);
		}
		date = cal.getTime();
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	@Test
	public void endTest120() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Date date = sdf.parse("2359");
		String input = "add dinner TO TOMORROW";

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		cal.set(Calendar.DAY_OF_YEAR, Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
		cal.add(Calendar.DAY_OF_YEAR, 1);

		date = cal.getTime();
		assertEquals(date, Parser.parse(input).getDeadline());
	}

	/*************************************************************************************************
	 **************************************** COMMAND TEST *******************************************
	 *************************************************************************************************/
	
	@Test
	public void testCmd() throws Exception {
		String input = "add dinner FROM 12.34 01 january 23";
		assertEquals(CommandDetails.COMMANDS.ADD, Parser.parse(input).getCommand());
	}

	@Test
	public void testDefaultCmd() throws Exception {
		String input = "dinner FROM 12.34 01 january 23";
		assertEquals(CommandDetails.COMMANDS.ADD, Parser.parse(input).getCommand());
	}

	@Test
	public void testCmdWhiteSpace() throws Exception {
		String input = "dinner FROM 12.34 01 january 23";
		assertEquals(CommandDetails.COMMANDS.ADD, Parser.parse(input).getCommand());
	}
	
	/*************************************************************************************************
	 **************************************** ID TEST ************************************************
	 *************************************************************************************************/
	
	@Test
	public void testID() throws Exception {
		String input = "delete 2";
		int result = 2;

		assertEquals(result, Parser.parse(input).getID());
	}

	@Test
	public void testIDWhiteSpace() throws Exception {
		String input = "delete                  2";
		int result = 2;
		assertEquals(result, Parser.parse(input).getID());
	}
	
	/*************************************************************************************************
	 **************************************** DESCRIPTION TEST ***************************************
	 *************************************************************************************************/
	
	@Test
	public void testDescription() throws Exception {
		String input = "add dinner ";
		String result = "dinner";
		assertEquals(result, Parser.parse(input).getDescription());
	}

	@Test
	public void testDescriptionEscapeChar() throws Exception {
		String input = "add dinner /at gardens /by the bay";
		String result = "dinner at gardens by the bay";
		assertEquals(result, Parser.parse(input).getDescription());
	}
	
	@Test
	public void testDescriptionWhiteSpace() throws Exception {
		String input = "add               dinner                   /at gardens        /by the bay          ";
		String result = "dinner at gardens by the bay";
		assertEquals(result, Parser.parse(input).getDescription());
	}
	
	/*************************************************************************************************
	 **************************************** RECURRING TEST *****************************************
	 *************************************************************************************************/
	
	@Test
	public void testRecurring() throws Exception {
		String input = "add dinner by today daily";
		String result = "DAILY";
		assertEquals(result, Parser.parse(input).getRecurring());
	}

	@Test
	public void testRecurringEnd() throws Exception {
		String input = "add dinner by today daily";
		SimpleDateFormat format = new SimpleDateFormat("HHmm dd/MM/yyyy");
		Date date = format.parse("2359 31/12/8089");
		assertEquals(date, Parser.parse(input).getEndingDate());
	}

	@Test
	public void testRecurringEnd2() throws Exception {
		String input = "add dinner by today daily ending 31/12";
		SimpleDateFormat format = new SimpleDateFormat("HHmm dd/MM/yyyy");
		Date date = format.parse("2359 31/12/2015");
		assertEquals(date, Parser.parse(input).getEndingDate());
	}

	/*************************************************************************************************
	 **************************************** EXCEPTION TEST *****************************************
	 *************************************************************************************************/
	
	@Test(expected = InvalidParametersException.class)
	public void emptyDescription() throws Exception {
		String input = "add At tomorrow";
		assertEquals(input, Parser.parse(input).getDescription());
	}
	
	@Test(expected = InvalidTimeDateInputException.class)
	public void timeNotSupported() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		Date date = sdf.parse("2359");
		String input = "add dinner At 123456";

		assertEquals(date, Parser.parse(input).getStartDate());
	}

	@Test(expected = InvalidTimeDateInputException.class)
	public void endedTask() throws Exception {
		String input = "add dinner from tomorrow to today";
		assertEquals(input, Parser.parse(input));
	}
	
	@Test(expected = InvalidTimeDateInputException.class)
	public void startBeforeEnd() throws Exception {
		String input = "add dinner from 1 jan to 6 jun";
		assertEquals(input, Parser.parse(input));
	}
	
	@Test(expected = InvalidTimeDateInputException.class)
	public void endBeforeEnding() throws Exception {
		String input = "add dinner from today  to 21 dec 2018 daily ending tomorrow";
		assertEquals(input, Parser.parse(input));
	}
	
	@Test(expected = InvalidTimeDateInputException.class)
	public void startAfterEnding() throws Exception {
		String input = "add dinner from 21 jan 2017  to 21 dec 2018 daily ending today";
		assertEquals(input, Parser.parse(input));
	}
	
	@Test(expected = InvalidParametersException.class)
	public void noRecurringInterval() throws Exception {
		String input = "add dinner by today ending sunday";
		assertEquals(input, Parser.parse(input));
	}
	
	@Test(expected = NumberFormatException.class)
	public void taskNumberInvalid() throws Exception {
		String input = "delete 2grbvwerf";
		assertEquals("", Parser.parse(input).getStartDate());
	}

	@Test(expected = InvalidParametersException.class)
	public void taskNumberMissing() throws Exception {
		String input = "delete ";
		assertEquals("", Parser.parse(input).getStartDate());
	}
	
}
