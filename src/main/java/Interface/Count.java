package Interface;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Sujit on 5/16/14.
 */
public interface Count {
    long countTerm(String t, String _inDocument) throws IOException;

    long NoOfTerms(String _inDocument) throws IOException;

    long NoOfDocument(String _withTerm) throws IOException;
}
