package de.hhu.funktionen;

/**
 * @author Viet Dung Tran <tranvd2010@yahoo.com>
 *
 *         created on 05.07.2014
 */
public final class PotenFunks {

	/**
	 * @param x
	 * @return Division mit NULL als Rueckgabewert fuer x =0
	 */
	public static double nDivision(double y, double x) {

		if (x == 0)
			return Double.NaN;
		return y / x;
	}

	/**
	 * @param x
	 * @return Quadratwurzel mit NULL als Rueckgabewert fuer x <0
	 */
	public static double quaWurzel(double x) {

		if (x < 0)
			return Double.NaN;
		return Math.sqrt(x);
	}

	/**
	 * @param basis
	 *            , ex
	 * @return Potenz y^x mit NULL als Rueckgabewert fuer base <0 und ex%2 = 0
	 */
	public static double nExponenten(double basis, double ex) {

		double temp = (1.0 / ex);
		if (basis < 0 && ex != Math.ceil(ex) && temp == Math.ceil(temp)
				&& temp / 2 != Math.ceil(temp / 2))
			return -Math.pow(-basis, ex);
		if (basis < 0 && ex != Math.ceil(ex))
			return Double.NaN;
		return Math.pow(basis, ex);
	}

	/**
	 * @param x
	 * @return Exponentialwert von x zur Basis e = e^x
	 */
	public static double exponenten(double x) {

		return Math.exp(x);
	}
}
