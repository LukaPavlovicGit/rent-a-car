package raf.rentacar.reservationservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ReservationServiceApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void dateDifference() {

		SimpleDateFormat sdf
				= new SimpleDateFormat(
				"yyyy-MM-dd");
		// Try Block
		try {
			// parse method is used to parse
			// the text from a string to
			// produce the date
			Date d1 =  sdf.parse("2022-9-21");
			Date d2 =  sdf.parse("2022-10-31");

			java.sql.Date sqlDateD1 = new java.sql.Date(d1.getTime());
			java.sql.Date sqlDateD2 = new java.sql.Date(d2.getTime());

			// Calculate time difference
			// in milliseconds
			long difference_In_Time
					= sqlDateD2.getTime() - sqlDateD1.getTime();

			long difference_In_Days
					= (difference_In_Time
					/ (1000 * 60 * 60 * 24))
					% 365;


			System.out.print(
					"Difference "
							+ "between two dates is: ");

			System.out.println(difference_In_Days + " days");

			Date dateBefore = sdf.parse("2022-9-21");
			Date dateAfter = sdf.parse("2022-10-31");

			long dateBeforeInMs = dateBefore.getTime();
			long dateAfterInMs = dateAfter.getTime();

			long timeDiff = Math.abs(dateAfterInMs - dateBeforeInMs);

			long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

			System.out.println(" The number of days between dates: " + daysDiff);

			double d = 13;
			System.out.println(d);
			DecimalFormat df = new DecimalFormat("0.00");
			df.setRoundingMode(RoundingMode.DOWN);

			BigDecimal a = new BigDecimal(d).setScale(2,RoundingMode.HALF_DOWN);
			BigDecimal b = new BigDecimal(100).setScale(2,RoundingMode.HALF_DOWN);
			BigDecimal c = a.divide(b);
			System.out.println(a.divide(b));


			// Catch the Exception
		} catch (ParseException e) {
				e.printStackTrace();
		}
	}

}
