package de.hhu.funktionen;

/**
 * @author Viet Dung Tran <tranvd2010@yahoo.com>
 *
 *         created on 05.07.2014
 */
public final class KansFunks {

	/**
	 * @param x
	 * @return Sekans Null als Rueckgabewert fuer k*PI + (PI/2), k eine ganze
	 *         Zahl
	 */
	public static double sekFunk(double x) {
		double a = (Double) Math.PI / 2;
		if (x % Math.PI == a)
			return Double.NaN;
		return 1.0 / Math.cos(x);
	}

	/**
	 * @param x
	 * @return Kosekans mit NULL als Rueckgabewert fuer k*PI
	 */
	public static double kosekFunk(double x) {
		if (x % Math.PI == 0)
			return Double.NaN;
		return 1.0 / Math.sin(x);
	}

	/**
	 * @param x
	 * @return Arkussekans
	 */
	public static double arsekFunk(double x) {
		if (x > -1 && x < 1)
			return Double.NaN;
		return Math.acos(1 / x);
	}

	/**
	 * @param x
	 * @return Arkuskosekans
	 */
	public static double arkosekFunk(double x) {
		if (x > -1 && x < 1)
			return Double.NaN;
		return Math.asin(1 / x);
	}
}
