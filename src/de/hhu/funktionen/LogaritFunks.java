package de.hhu.funktionen;

/**
 * @author Viet Dung Tran <tranvd2010@yahoo.com>
 *
 * created on 05.07.2014
 */
public final class LogaritFunks {
	
	/**
	 * @param x
	 * @return naturliche Logarithmus mit NULL als Rueckgabewert fuer x <0
	 */
	public static double logaNa( double x ) {
		if(x<=0)
			return Double.NaN;
		return Math.log( x );
	}
	
	
	/**
	 * logb(x) = loga(x) / loga(b)
	 * @param x , basis
	 * @return Logarithmus basis mit NULL als Rueckgabewert fuer x <0 oder basis =1 oder basis <=0
	 */
	public static double logaBasis( double basis,  double x ) {
		if(x<=0 || basis <=0 || basis == 1.0)
			return Double.NaN;
		return Math.log( x )/Math.log(basis);
	}
	public static double logaZehn( double x ) {
		if(x<=0)
			return Double.NaN;
		return Math.log10(x);
	}
	

}
