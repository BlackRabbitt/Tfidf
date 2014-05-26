package Function;

import cascading.flow.FlowProcess;
import cascading.operation.BaseOperation;
import cascading.operation.Function;
import cascading.operation.FunctionCall;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntry;

/**
 * Created by Sujit on 5/20/14.
 */
public class toLowerFunction extends BaseOperation implements Function {

    public toLowerFunction() {
        super(new Fields("line"));
    }
    @Override
    public void operate(FlowProcess flowProcess, FunctionCall functionCall) {
        TupleEntry arguments = functionCall.getArguments();
        functionCall.getOutputCollector().add(Tuple.parse(arguments.getString(new Fields("line")).toLowerCase()));
    }
}
