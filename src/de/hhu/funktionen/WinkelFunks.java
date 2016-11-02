package de.hhu.funktionen;

/**
 * @author Viet Dung Tran <tranvd2010@yahoo.com>
 *
 *         created on 05.07.2014
 */
public final class WinkelFunks {

	/**
	 * Math.toRadians(x)
	 * 
	 * @param x
	 * @return Sinus
	 */
	public static double sinFunk(double x) {
		return Math.sin(x);
	}

	/**
	 * @param x
	 * @return Kosinus
	 */
	public static double cosFunk(double x) {
		return Math.cos(x);
	}

	/**
	 * @param x
	 * @return Tangens mit Null als Rueckgabewert fuer x = k*PI + (PI/2), k eine
	 *         ganze Zahl
	 */
	public static double tanFunk(double x) {
		double a = Math.PI / 2;
		if (x % Math.PI == a)
			return Double.NaN;
		return Math.tan(x);
	}

	/**
	 * @param x
	 * @return Kotangens mit Null als Rueckgabewert fuer x = k*PI, k eine ganze
	 *         Zahl
	 */
	public static double kotanFunk(double x) {
		if (x % Math.PI == 0)
			return Double.NaN;
		return Math.cos(x) / Math.sin(x);
	}

	/**
	 * @param x
	 * @return Arkussinus mit Null als Rueckgabewert fuer |x| >1
	 */
	public static double arsinFunk(double x) {
		if (Math.abs(x) >= 1)
			return Double.NaN;
		return Math.asin(x);
	}

	/**
	 * @param x
	 * @return Arkuskosinus mit Null als Rueckgabewert fuer |x| >1
	 */
	public static double arcosFunk(double x) {
		if (Math.abs(x) >= 1)
			return Double.NaN;
		return Math.acos(x);
	}

	/**
	 * @param x
	 * @return Arkustangens
	 */
	public static double artanFunk(double x) {
		return Math.atan(x);
	}

	/**
	 * @param x
	 * @return Arkuskotangens
	 */
	public static double arkotanFunk(double x) {
		return Math.PI / 2 - Math.atan(x);
	}

}
