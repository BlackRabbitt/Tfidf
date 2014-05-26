package Filter;

import cascading.flow.FlowProcess;
import cascading.operation.*;
import cascading.tuple.Fields;
import cascading.tuple.TupleEntry;

/**
 * Created by Sujit on 5/20/14.
 */
public class countTermFilter extends BaseOperation implements Filter{
    String term;
    public countTermFilter(String t) {
        this.term = t;
    }

    @Override
    public boolean isRemove(FlowProcess flowProcess, FilterCall filterCall) {
        TupleEntry arguments = filterCall.getArguments();
        if (this.term.equals(arguments.getString(new Fields("word")))) {
            return false;
        }
        return true;
    }
}
