package de.hhu.parser;
/**
 * @author Viet Dung Tran
 *
 * created on 23.05.2011
 */
public class Konstants{
	
	/**
	 * Name der Funktion mit 2 Parameter ZB: Pow(x,y), lg2(9)
	 */
	public final static String ZWEI_PARAMETER = "log|pow";
	/**
	 * Variable x und besonderen Konstant in der Mathematik zb Pi, E,..
	 */
	public final static String VARIABLE = "X|x|e|pi|-x|-X";
	
	//hier sind name von der Funktion	
	/**
	 * Alles Name von der Funktion wereden hier definiert
	 * <pre>
	 * Definition:
	 * 
	 * log als der Logarithmus von a zur Basis b 		: log2(3)
	 * ln  natuerlicher Logarithmus				 		: ln(3)
	 * log als der Zehnerlogarithmus			 		: lg(3)
	 * 
	 *
	 *</pre>
	 */
	public enum FunkName	{
		
		sin, cos, tan, cot, arctan, arcsin, arccos, arccot,
		shsin, shcos, tanh, coth, arsinh, arcosh, artanh, arcoth,
		sec, csc, arcsec, arccsc,
		sqrt, pow,
		log, ln, lg, 
		abs,
		exp

		}
	/*
	 * 	
		SIN, COS, TAN, COT, ARCTAN, ARCSIN, ARCCOS, ARCCOT,
		SHSIN, SHCOS, TANH, COTH, ARSINH, ARCOSH, ARTANH, ARCOTH,
		SEC, CSC, ARCSEC, ARCCSC,
		SQRT, POW,
		LOG, LN, LG, 
		ABS,
		EXP
	 */
}