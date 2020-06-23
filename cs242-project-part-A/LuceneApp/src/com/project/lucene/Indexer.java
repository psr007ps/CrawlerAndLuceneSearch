package com.project.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;




public class Indexer {

   private IndexWriter writer;

   @SuppressWarnings("deprecation")
public Indexer(String indexDirectoryPath) throws IOException {
      //this directory will contain the indexes
      Directory indexDirectory = 
         FSDirectory.open(new File(indexDirectoryPath));

      //create the indexer
      writer = new IndexWriter(indexDirectory, 
         new StandardAnalyzer(Version.LUCENE_36),true, 
         IndexWriter.MaxFieldLength.UNLIMITED);
   }

   public void close() throws CorruptIndexException, IOException {
      writer.close();
   }

   /*private Document getDocument(File file) throws IOException {
      Document document = new Document();

      //index file contents
      Field contentField = new Field(LuceneConstants.CONTENTS, new FileReader(file));
      //index file name
      Field fileNameField = new Field(LuceneConstants.FILE_NAME,
         file.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED);
      //index file path
      Field filePathField = new Field(LuceneConstants.FILE_PATH,
         file.getCanonicalPath(),Field.Store.YES,Field.Index.NOT_ANALYZED);

      document.add(contentField);
      document.add(fileNameField);
      document.add(filePathField);

      return document;
   }*/
   
   
   private Document getDocument(String tweetRecord) throws IOException {
	      Document document = new Document();
	      int[] indexList = new int[9];
	      indexList[0] = tweetRecord.indexOf(" Tweet:");
	      indexList[1] = tweetRecord.indexOf(" Coordinates:");
	      indexList[2] = tweetRecord.indexOf(" Date:");
	      indexList[3] = tweetRecord.indexOf(" RetweetCount:");
	      indexList[4] = tweetRecord.indexOf(" ReplyCount:");
	      indexList[5] = tweetRecord.indexOf(" FavoriteCount:");
	      indexList[6] = tweetRecord.indexOf(" URL:");
	      indexList[7] = tweetRecord.indexOf(" Title:");
	      /* Logic to parse the tweet and add fields to the document */
	      document.add(new Field("hashTag", tweetRecord.substring(9,indexList[0]),Field.Store.YES,Field.Index.ANALYZED));
	      document.add(new Field("tweet", 	tweetRecord.substring(indexList[0] + 7,indexList[1]),Field.Store.YES,Field.Index.ANALYZED));
	      document.add(new Field("coordinates", tweetRecord.substring(indexList[1] + 13,indexList[2]),Field.Store.YES,Field.Index.NO));
	      document.add(new Field("createdAt", tweetRecord.substring(indexList[2] + 7,indexList[3]),Field.Store.YES,Field.Index.NO));
	      document.add(new Field("retweetCount", tweetRecord.substring(indexList[3] + 14,indexList[4]),Field.Store.YES,Field.Index.NO));
	      document.add(new Field("replyCount", tweetRecord.substring(indexList[4] + 12,indexList[5]),Field.Store.YES,Field.Index.NO));
	      document.add(new Field("FavoriteCount", tweetRecord.substring(indexList[5] + 15,indexList[6]),Field.Store.YES,Field.Index.NO));
	      document.add(new Field("URL", tweetRecord.substring(indexList[6] + 5,indexList[7]),Field.Store.YES,Field.Index.NO));
	      document.add(new Field("title", tweetRecord.substring(indexList[7] + 7,tweetRecord.length()),Field.Store.YES,Field.Index.NO));
	      return document;
	   } 
   
   
   
   

   private void indexFile(File file) throws IOException {
      /*System.out.println("Indexing "+file.getCanonicalPath());
      Document document = getDocument(file);
      writer.addDocument(document);
      */
      
      BufferedReader reader;
	  try {
		   reader = new BufferedReader(new FileReader(file));
		   String line = reader.readLine();
		   System.out.println(line);
		   // Logic to read each line from the file and generate index
		   while(line != null) { 
			   Document document = getDocument(line);
			   writer.addDocument(document);
			   line = reader.readLine();
			   //System.out.println(line);
		   }
		   reader.close();
	  }
	  catch(Exception e) {
		  //System.out.println("Error -- " + e);
	  }
   }
   
   
  
   
   
   

   public int createIndex(String dataDirPath, FileFilter filter) 
      throws IOException {
      //get all files in the data directory
      File[] files = new File(dataDirPath).listFiles();

      for (File file : files) {
         if(!file.isDirectory()
            && !file.isHidden()
            && file.exists()
            && file.canRead()
            && filter.accept(file)
         ){
            indexFile(file);
         }
      }
      return writer.numDocs();
   }
}






