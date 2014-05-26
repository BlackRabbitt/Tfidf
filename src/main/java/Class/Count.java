package Class;

import Filter.countTermFilter;
import Function.toLowerFunction;
import cascading.flow.FlowDef;
import cascading.flow.local.LocalFlowConnector;
import cascading.operation.Function;
import cascading.operation.regex.RegexGenerator;
import cascading.pipe.Each;
import cascading.pipe.Pipe;
import cascading.pipe.assembly.CountBy;
import cascading.scheme.local.TextDelimited;
import cascading.tap.Tap;
import cascading.tap.local.FileTap;
import cascading.tuple.Fields;

import java.io.*;

/**
 * Created by Sujit on 5/16/14.
 */
public class Count implements Interface.Count {
    @Override
    public long countTerm(String t, String _inDocument) throws IOException {
        Long countedValue = Long.valueOf(0);
        Pipe count = new Pipe("Count");
        String tempFile = "src/test/resources/temp.file";
        String countDocumentOutput = "src/test/resources/count.file";
        Tap inTap = new FileTap(new TextDelimited(new Fields("line")), _inDocument);
        Tap tokenizeTap = new FileTap(new TextDelimited(new Fields("word")), tempFile);
        Tap countTap = new FileTap(new TextDelimited(new Fields("word", "count")), countDocumentOutput);

        // tokenize line into words
        tokenize(inTap, tokenizeTap);

        // count words
        count = new CountBy(count, new Fields("word"), new Fields("count"));

        // use filter to filter out the tuple that is not the term
        count = new Each(count, new countTermFilter(t));

        FlowDef flowDef = new FlowDef().addSource(count, tokenizeTap).addSink(count, countTap).addTail(count);
        new LocalFlowConnector().connect(flowDef).complete();

        // read output file and return the count only.
        BufferedReader bf = new BufferedReader(new FileReader(countDocumentOutput));
        String lines = null;
        while ((lines = bf.readLine()) != null) {
            String[] counted = lines.split("\t");
            countedValue = Long.valueOf(counted[1]);
        }
        return countedValue;
    }

    @Override
    public long NoOfTerms(String _inDocument) throws IOException {
        Pipe count = new Pipe("Count");
        String tempFile = "src/test/resources/temp.file";
        String totalCountDocumentOutput = "src/test/resources/totalCount.file";
        Tap inTap = new FileTap(new TextDelimited(new Fields("line")), _inDocument);
        Tap tokenizeTap = new FileTap(new TextDelimited(new Fields("word")), tempFile);
        Tap totalCountTap = new FileTap(new TextDelimited(new Fields("word", "count")), totalCountDocumentOutput);
        // tokenize line into words
        tokenize(inTap, tokenizeTap);
        count = new CountBy(count, new Fields("word"), new Fields("count"));
        FlowDef flowDef = new FlowDef().addSource(count, tokenizeTap).addSink(count, totalCountTap).addTail(count);
        new LocalFlowConnector().connect(flowDef).complete();

        // read output file and calculate total count
        BufferedReader bf = new BufferedReader(new FileReader(totalCountDocumentOutput));
        String lines = null;
        int totalCount = 0;
        while ((lines = bf.readLine()) != null) {
            String[] counted = lines.split("\t");
            totalCount += Integer.parseInt(counted[1]);
        }
        return totalCount;
    }

    @Override
    public long NoOfDocument(String _withTerm) throws IOException {
        String dirPath = "src/main/resources/Documents";
        File file[] = new File(dirPath).listFiles();
        long countFileWithTerm = 0;
        for (File each_file : file) {
            if (0 != (countTerm(_withTerm, each_file.toString()))) {
                countFileWithTerm += 1;
            }
        }
        return countFileWithTerm;
    }

    public static void tokenize(Tap sourceTap, Tap sinkTap) {
        Pipe tokenize = new Pipe("tokenize");
        String regex = "((\\b[^\\s]+\\b)((?<=\\.\\w).)?)";
        Function split = new RegexGenerator(new Fields("word"), regex);
        tokenize = new Each(tokenize, new toLowerFunction(), Fields.RESULTS);
        tokenize = new Each(tokenize, new Fields("line"), split);
        FlowDef flowDef = new FlowDef().addSource(tokenize, sourceTap).addTail(tokenize).addSink(tokenize, sinkTap);
        new LocalFlowConnector().connect(flowDef).complete();
    }
}
