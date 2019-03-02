package com.yr.util;

import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.BinaryPoint;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleDocValuesField;
import org.apache.lucene.document.DoublePoint;
import org.apache.lucene.document.DoubleRange;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.document.FloatRange;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.IntRange;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.LongRange;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 取数据
 * 
 * @author zxy
 *
 * 2018年6月12日 下午5:30:08
 *
 */
public class LuceneTake {
	
	public Query BinaryDocValField() {
		try {
			QueryParser parser = new QueryParser("bdValuef",new StandardAnalyzer());  
	        Query query = parser.parse("1234");
	        return query;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Query IntField() {
		Query luceneQuery = IntPoint.newRangeQuery("intFiled", 1, 20); // 1 - 20 之间 超出范围会返回 0
	    return luceneQuery;
	}

	public Query StringField() {
		Term term = new Term("string", "邹想云");
	    TermQuery luceneQuery = new TermQuery(term);
	    return luceneQuery;
	}

	public Query FlotField() {
		Query luceneQuery = FloatPoint.newRangeQuery("floatFiled", (float)2.5, (float)75.6);
	    return luceneQuery;
	}

	public Query DoubleField() {
		Query luceneQuery = DoublePoint.newRangeQuery("doubleFiled", 1.2, 75.6);
	    return luceneQuery;
	}
	
	public Query LongField() {
		Query luceneQuery = LongPoint.newRangeQuery("longFiled", 1L, 88L);
	    return luceneQuery;
	}

	public Query DoubleDovValField() {
		Query luceneQuery = DoubleDocValuesField.newSlowExactQuery("ddvf", 52);// 精确的查询  范围查询
	    return luceneQuery;
	}

	public Query FloatDocValField() {
		Term term = new Term("fdvf", "1.2");
	    TermQuery luceneQuery = new TermQuery(term);
	    return luceneQuery;
	}

	public Query SortedNumericDocValField() {
		Term term = new Term("sndvf", "11");
	    TermQuery luceneQuery = new TermQuery(term);
	    return luceneQuery;
	}

	public void SortedSetDocValField() {

	}

	public void SortedDocValField() {
		
	}
	
	public Query StoreField() {
		Term term = new Term("sf", "zxy");
	    TermQuery luceneQuery = new TermQuery(term);
	    return luceneQuery;
//	    try {
//			QueryParser parser = new QueryParser("sf",new StandardAnalyzer()); // 指定对哪个字段检索并且指定使用哪个分词器
//	        Query query = parser.parse("zxy"); // 解析关键词进行分词
//	        return query;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
	}

	public Query BinaryPointTest(){
		byte[] b = {1};
		byte[] p = {9};
		Query luceneQuery = BinaryPoint.newRangeQuery("bp", b, p);
	    return luceneQuery;
	}
	
	public Query DoubleRangeTest(){
		double[] d = {5.2}; // 最小值
		double[] b = {80.6}; // 最大值
		Query luceneQuery = DoubleRange.newWithinQuery("doubleRange", d, b); // 范围之内查询(所存的最小值与最大值都要在所取的范围之内,否者查询不到)
//		Query luceneQuery = DoubleRange.newIntersectsQuery("doubleRange", d, b); // 相交查询
	    return luceneQuery;
	}
	
	public Query FloatRangeTest(){
		float[] f = {(float) 1.1}; // 最小值
		float[] l = {(float) 5.8}; // 最大值
		Query luceneQuery = FloatRange.newIntersectsQuery("floatRange", f, l);
	    return luceneQuery;
	}
	
	public Query IntRangeTest(){
		int[] i = {1};
		int[] r = {99};
	    Query luceneQuery = IntRange.newIntersectsQuery("intRange", i, r);  // 1 - 99 之间可获取
	    return luceneQuery;
	}
	
	public Query LongRangeTest(){
		long[] l = {(long)2};
		long[] r = {(long)88};
		Query luceneQuery = LongRange.newIntersectsQuery("longRange", l, r); // 2 - 88 之间
	    return luceneQuery;
	}
	
	public Query TextField() {
		try {
			QueryParser parser = new QueryParser("content",new IKAnalyzer()); // 指定对哪个字段检索并且指定使用哪个分词器
	        Query query = parser.parse("我是"); // 解析关键词进行分词
	        return query;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	  * 模糊查询
	  * 相近词语的搜索—FuzzyQuery
	  * FuzzyQuery是一种模糊查询，它可以简单地识别两个相近的词语。
	  * @throws Exception
	  */
   public void testFuzzyQuery()throws Exception{
      String searchField="stringValue";
      String indexPath = "G:/lucene_bigDate";
      String q="只是一个字符串";
      Term t=new Term(searchField,q);
      Query query = new FuzzyQuery(t); // FuzzyQuery相近词语查询
      Directory indexDir = FSDirectory.open( Paths.get(indexPath));
      DirectoryReader directoryReader = DirectoryReader.open(indexDir); // 读取索引库索引
      IndexSearcher searcher = new IndexSearcher(directoryReader); // 创建查询索引库核心对象
      TopDocs hits = searcher.search(query, 10);
      System.out.println("匹配 '"+q+"'，总共查询到"+hits.totalHits+"个文档");

      for(ScoreDoc scoreDoc:hits.scoreDocs){
         Document doc = searcher.doc(scoreDoc.doc);
         System.out.println(doc.get("fullPath"));
      }
   }
	
}