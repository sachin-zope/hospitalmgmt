package hospitalmgmt.utils;

import java.util.ArrayList;
import java.util.List;

public class Cache {
	
	private static Cache cache = null;
	
	private Cache() {
	}
	
	public static Cache getInstance() {
		if(cache == null) {
			cache = new Cache();
		}
		
		return cache;
	}
	
	public static List<String> populateDiagnosis() {
		List<String> diagnosis = new ArrayList<String>();
		
		diagnosis.add("Primigravida");
		diagnosis.add("G2P1L1");
		diagnosis.add("Primigravida with CPD");
		diagnosis.add("G2P1L1 with previous LCSC");
		diagnosis.add("P2L2 for tubal ligation");
		diagnosis.add("Incomplete Abortion");
		diagnosis.add("Chronic Cervicitis");
		diagnosis.add("Dysfunctional uterine bleeding");
		diagnosis.add("Uterine prolapse with CRE");
		diagnosis.add("Fibroid uterus");
		diagnosis.add("Infertility for diagnostic laparoscopy");
		diagnosis.add("Ovarian cyst");
		diagnosis.add("Other");
		
		return diagnosis;
	}

}
