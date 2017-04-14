package analyzer.RefSeq;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by mwads on 2/9/16.
 */
public class RefSeqParser {

    private HashMap<String,String> refSeq; // <"NM_342434", "#0bin\t1name\t2chrom\t3strand\t4txStart\t5txEnd\t6cdsStart\t7cdsEnd\t8exonCount\t9exonStarts\t10exonEnds\t11score\t12name2\t13cdsStartStat\t14cdsEndStat\t15exonFrames"; >
    private HashMap<String,ArrayList<String>> gene2transcript; // < "POTED", <NM_32423,NM_45345>; >

    public RefSeqParser(String refSeqPath){
        this.refSeq = new HashMap<>();
        this.gene2transcript = new HashMap<>();
        parseFile(refSeqPath);

    }

    private void parseFile(String refSeqPath) {
        try {
            @SuppressWarnings("resource")
			Scanner reader = new Scanner(new File(refSeqPath));
            while (reader.hasNextLine()){
                String line = reader.nextLine();
                String[] attributes = line.split("\t");
                if(this.gene2transcript.containsKey(attributes[12])){
                    ArrayList<String> tempVal = this.gene2transcript.get(attributes[12]);
                    tempVal.add(attributes[1]);
                    this.gene2transcript.put(attributes[12],tempVal);
                }
                else{
                    ArrayList<String> val = new ArrayList<>();
                    val.add(attributes[1]);
                    this.gene2transcript.put(attributes[12],val);
                }
                this.refSeq.put(attributes[1],line);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTranscriptNames(String Gene){
        return this.gene2transcript.get(Gene);
    }

    public String getRefSeqData(String transcript){
        return this.refSeq.get(transcript);
    }



}
