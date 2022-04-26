import java.util.Calendar;
import java.util.GregorianCalendar;

public class Data {
	
	private GregorianCalendar c;
	
	public Data(){
		c = new GregorianCalendar();
	}
	
	public Data(int g, int m, int a){
		c = new GregorianCalendar(a,m-1,g);
	}
	
	
	//this - d
	public long getDifference(Data d){
		int diff=0;
		long cm = c.getTimeInMillis();
		long dm = d.c.getTimeInMillis();
		long diffm = cm - dm;
		diff = (int) (diffm / (1000));
		return diff;
	}
	
	public boolean equals(Object obj){
		if(obj == null) return false;
		
		if(!(obj instanceof Data)) return false;
		
		return c.equals((GregorianCalendar)obj);
		
	}
	
	public String toString(){
		int anno = c.get(Calendar.YEAR);
		int mese = c.get(Calendar.MONTH) + 1;
		int giorno = c.get(Calendar.DATE);
		int ora = c.get(Calendar.HOUR_OF_DAY);
		int minuto = c.get(Calendar.MINUTE);
		int secondo = c.get(Calendar.SECOND);
		return ora+":"+minuto+":"+secondo+"_"+giorno+":"+mese+":"+anno;
	}

}