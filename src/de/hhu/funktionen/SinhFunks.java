package de.hhu.funktionen;

/**
 * @author Viet Dung Tran <tranvd2010@yahoo.com>
 *
 * created on 05.07.2014
 */
public final class SinhFunks {

	/**
	 * @param x
	 * @return Sinus Hyperbolicus
	 */
	public static double sinhypFunk(double x) {
		return Math.sinh(x);
	}

	/**
	 * @param x
	 * @return Kosinus Hyperbolicus
	 */
	public static double coshypFunk(double x) {
		return Math.cosh(x);
	}

	/**
	 * @param x
	 * @return Tangens Hyperbolicus
	 */
	public static double tanhypFunk(double x) {
		return Math.tanh(x);
	}

	/**
	 * @param x
	 * @return Kotangens Hyperbolicus
	 */
	public static double cotanhypFunk(double x) {
		if (x == 0)
			return Double.NaN;
		return Math.cosh(x) / Math.sinh(x);
	}

	/**
	 * @param x
	 * @return Areasinus Hyperbolicus
	 */
	public static double arsinhypFunk(double x) {
		return Math.log(x + Math.pow((Math.pow(x, 2) + 1), 0.5));
	}

	/**
	 * @param x
	 * @return Areakosinus Hyperbolicus
	 */
	public static double arcoshypFunk(double x) {
		if (x <= 1)
			return Double.NaN;
		return Math.log(x + Math.pow((Math.pow(x, 2) - 1), 0.5));
	}

	/**
	 * @param x
	 * @return Areatangens Hyperbolicus
	 */
	public static double artanhypFunk(double x) {
		if (Math.abs(x) >= 1)
			return Double.NaN;
		return (0.5) * Math.log((1 + x) / (1 - x));
	}

	/**
	 * @param x
	 * @return Areakotangens Hyperbolicus
	 */
	public static double arcotanhypFunk(double x) {
		if (Math.abs(x) <= 1)
			return Double.NaN;
		return (0.5) * Math.log((x + 1) / (x - 1));
	}
}
