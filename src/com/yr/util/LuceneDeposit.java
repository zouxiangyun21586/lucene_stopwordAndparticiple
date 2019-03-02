package com.yr.util;

import org.apache.lucene.document.BinaryDocValuesField;
import org.apache.lucene.document.BinaryPoint;
import org.apache.lucene.document.DoubleDocValuesField;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.DoubleRange;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatDocValuesField;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.FloatRange;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.IntRange;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.LongRange;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.SortedNumericDocValuesField;
import org.apache.lucene.document.SortedSetDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.util.BytesRef;

/**
 * 存数据
 * @author zxy
 *
 * 2018年6月12日 下午5:29:54
 *
 */
public class LuceneDeposit {
	
	public Field BinaryDocValField() {
		BinaryDocValuesField bdvf = new BinaryDocValuesField("bdValuef",new BytesRef("1234".getBytes()));
		return bdvf;
	}

	public Field IntField() {
		IntPoint intFiled = new IntPoint("intFiled", 10);
		return intFiled;
	}

	public StringField StringField() {
		StringField sf = new StringField("string", "邹想云", Field.Store.YES);
		return sf;
	}

	public Field FloatFiled(){
		FloatPoint floatFiled = new FloatPoint("floatFiled", (float)21.5); // bug : 不能存入两个值
		return floatFiled;
	}

	public Field DoubleField() {
		DoublePoint doubleFiled = new DoublePoint("doubleFiled", 2.2);
		return doubleFiled;
	}

	public Field LongField() {
		LongPoint doubleFiled = new LongPoint("longFiled", 62L); // 超出范围会抛异常
		return doubleFiled;
	}
	
	public Field DoubleDovValField() {
		DoubleDocValuesField ddv = new DoubleDocValuesField("ddvf", 5.2);
		return ddv;
	}

	public Field FloatDocValField() {
		FloatDocValuesField fvf = new FloatDocValuesField("fdvf", (float) 1.2);
		return fvf;
	}
	
	public Field SortedNumericDocValField() {
		SortedNumericDocValuesField sndvf = new SortedNumericDocValuesField("sndvf", 11);
		return sndvf;
	}

	public Field SortedSetDocValField() {
		BytesRef bytes = null;
		SortedSetDocValuesField ssdvf = new SortedSetDocValuesField("ssdvf", bytes);
		return ssdvf;
	}
	
	public Field SortedDocValField() {
		BytesRef bytes = null;
		SortedDocValuesField sdvf = new SortedDocValuesField("sdvf", bytes);
		return sdvf;
	}

	public Field StoreField() {
		StoredField sf = new StoredField("sf", "zxy");
		return sf;
	}

	public Field BinaryPointTest(){
		byte[] b = {2};
		byte[] p = {5};
		BinaryPoint bp = new BinaryPoint("bp", b, p);
		return bp;
	}
	
	public Field DoubleRangeTest(){
		double[] d = {6.2}; // 最小值
		double[] b = {62.4}; // 最大值
		DoubleRange dr = new DoubleRange("doubleRange", d, b);
		return dr;
	}
	
	public Field FloatRangeTest(){
		float[] f = {(float) 0.2}; // 最小值
		float[] l = {(float) 99.1}; // 最大值
		FloatRange dr = new FloatRange("floatRange", f, l);
		return dr;
	}
	
	public Field IntRangeTest(){ // 存入的范围要在取值的范围之内
		int[] i = {1};
		int[] r = {10};
		Field ir = new IntRange("intRange", i, r);
		return ir;
	}
	
	public Field LongRangeTest(){
		long[] l = {(long)5};
		long[] r = {(long)56};
		LongRange lr = new LongRange("longRange", l, r);
		return lr;
	}
	
	public Field textField() {
		try {
			Field content = new TextField("content", "我是中国人",Field.Store.YES);
	        return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
}
