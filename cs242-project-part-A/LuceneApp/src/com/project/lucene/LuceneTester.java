package com.project.lucene;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;


public class LuceneTester {
	
   String indexDir = "/Users/pranshushrivastava/DevData/Lucene/Data/Index";
   String dataDir = "/Users/pranshushrivastava/DevData/Lucene/Data";
   Indexer indexer;
   Searcher searcher;
   static String query = "housewarming";
   public static void main(String[] args) {
      LuceneTester tester;
      try {
         tester = new LuceneTester();
         tester.createIndex();
         
         tester.search(query);
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }

   private void createIndex() throws IOException {
      indexer = new Indexer(indexDir);
      int numIndexed;
      long startTime = System.currentTimeMillis();	
      numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
      long endTime = System.currentTimeMillis();
      indexer.close();
      System.out.println(numIndexed+" tweets indexed, time taken: "
         +(endTime-startTime)+" ms");		
   }

   private void search(String searchQuery) throws IOException, ParseException {
      searcher = new Searcher(indexDir);
      long startTime = System.currentTimeMillis();
      TopDocs hits = searcher.search(searchQuery);
      long endTime = System.currentTimeMillis();
   
      System.out.println(hits.totalHits +
         " tweets found. Time :" + (endTime - startTime) + "ms");
      for(ScoreDoc scoreDoc : hits.scoreDocs) {
         Document doc = searcher.getDocument(scoreDoc);
            //System.out.println("File: "
            //+ doc.get(LuceneConstants.FILE_PATH));
      }
      
      //Searcher ts = new Searcher(query);
      System.out.println("------------------ Query Top Results --------------------");
		int rank = 1;
		for(ScoreDoc scrDoc : hits.scoreDocs) {
			System.out.println("Title = " + searcher.getDocument(scrDoc).get("title"));
			System.out.println("HashTag = #" + searcher.getDocument(scrDoc).get("hashTag"));
			System.out.println("Tweet = " + searcher.getDocument(scrDoc).get("tweet"));
			//System.out.println("Coordinates = " + ts.getDocument(scrDoc).get("coordinates"));
			System.out.println("createdAt = " + searcher.getDocument(scrDoc).get("createdAt"));
			//System.out.println("retweetCount = " + ts.getDocument(scrDoc).get("retweetCount"));
			//System.out.println("replyCount = " + ts.getDocument(scrDoc).get("replyCount"));
			System.out.println("URL = " + searcher.getDocument(scrDoc).get("URL"));
			System.out.println("<== Score and Rank Info ==>");
			System.out.println("Rank = " + rank);
			System.out.println("Score = " + scrDoc.score);
			System.out.println("-----------------------------------------------------");
			rank ++;
      
      //searcher.close();
   }
}}
