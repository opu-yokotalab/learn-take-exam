import java.util.*;
import java.util.Timer;
import java.text.*;
import javax.swing.*;


class MyTimer extends TimerTask {
	JLabel label = new JLabel();
	boolean f = false;
	public void run(){
		String delim = f ? ":" : " ";
		f = !f;
		String str = "hh" + delim +"mm" + delim + "ss";
		SimpleDateFormat sf = new SimpleDateFormat(str);
		GregorianCalendar cal = new GregorianCalendar();
		label.setText(sf.format(cal.getTime()));
	}
}