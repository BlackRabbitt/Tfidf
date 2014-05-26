package Interface;

import java.io.IOException;

/**
 * Created by Sujit on 5/16/14.
 */
public interface TFidf {
    float calculateTf(String _forT, String _inDocument) throws IOException;

    double calculateIdf(String _forT) throws IOException;

    double calculateTfidf(String _forT, String _inDocument) throws IOException;
}
