package Class;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sujit on 5/16/14.
 */
public class TFidf implements Interface.TFidf {
    private Count cnt = new Count();

    @Override
    public float calculateTf(String _forT, String _inDocument) throws IOException {
        float noOfTimesTAppear, noOfTermsInDoc;
        noOfTimesTAppear = cnt.countTerm(_forT, _inDocument);
        noOfTermsInDoc = cnt.NoOfTerms(_inDocument);
        return noOfTimesTAppear/noOfTermsInDoc;
    }

    @Override
    public double calculateIdf(String _forT) throws IOException {
        String dirPath = "src/main/resources/Documents";
        long totalNoOfDocument = new File(dirPath).listFiles().length;
        long noOfDocumentWithTermT = cnt.NoOfDocument(_forT);
        return Math.log(totalNoOfDocument / noOfDocumentWithTermT);
    }

    @Override
    public double calculateTfidf(String _forT, String _inDocument) throws IOException {
        return calculateTf(_forT, _inDocument) * calculateIdf(_forT);
    }
}
