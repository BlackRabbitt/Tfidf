package TestCase;

import Class.Count;
import Class.TFidf;
import cascading.flow.FlowDef;
import cascading.flow.local.LocalFlowConnector;
import cascading.scheme.local.TextDelimited;
import cascading.tap.Tap;
import cascading.tap.local.FileTap;
import cascading.tuple.Fields;
import org.junit.Test;
import utils.Assert;

import java.io.IOException;

/**
 * Created by Sujit on 5/16/14.
 */
public class TestCase {
    @Test
    public void test_tokenize() throws IOException {
        String inDocument = "src/test/resources/input_file.txt";
        String outDocument = "src/test/resources/output_tokenize_file.txt";
        String expectedDocument = "src/test/resources/expected_tokenize_file.txt";

        Tap sourceTap = new FileTap(new TextDelimited(new Fields("line")), inDocument);
        Tap sinkTap = new FileTap(new TextDelimited(new Fields("word")), outDocument);

        Count.tokenize(sourceTap, sinkTap);
        Assert.assertFiles(outDocument, expectedDocument);
    }

    @Test
    public void test_countTerm() throws IOException {
        String inDocument = "src/test/resources/input_file.txt";
        String outDocument = "src/test/resources/output_count.txt";
        long aCount = new Count().countTerm("over", inDocument);
        junit.framework.Assert.assertEquals(3, aCount);
        long sCount = new Count().countTerm("sujit", inDocument);
        junit.framework.Assert.assertEquals(0, sCount);
    }

    @Test
    public void test_NoOfTerms() throws IOException {
        String inDocument = "src/test/resources/input_file.txt";
        long tCount = new Count().NoOfTerms(inDocument);
        junit.framework.Assert.assertEquals(24, tCount);
    }

    @Test
    public void test_calculateTf() throws IOException {
        String inDocument = "src/test/resources/input_tf.txt";
        float tf = new TFidf().calculateTf("brown", inDocument);
        junit.framework.Assert.assertEquals(Float.valueOf(1/9), tf);
    }

    @Test
    public void test_calculateIdf() throws IOException {
        double tfIdf = new TFidf().calculateIdf("test");
        junit.framework.Assert.assertEquals(Math.log(2), tfIdf);
    }

    @Test
    public void test_calculateTfidf() throws  IOException {
        String inFile = "src/test/resources/input";
        double tfIdf = new TFidf().calculateTfidf("test", inFile);
        junit.framework.Assert.assertEquals((((float)2/(float)17) * Math.log(2)), tfIdf);
    }
}
