package plan_runner.components;

import backtype.storm.Config;
import backtype.storm.topology.TopologyBuilder;
import java.io.Serializable;
import java.util.List;
import plan_runner.expressions.ValueExpression;
import plan_runner.operators.Operator;
import plan_runner.storm_components.StormEmitter;
import plan_runner.storm_components.synchronization.TopologyKiller;


public interface Component extends Serializable, ComponentProperties, StormEmitter {

    public void makeBolts(TopologyBuilder builder,
                       TopologyKiller killer,
                       List<String> allCompNames,
                       Config conf,
                       int partitioningType,
                       int hierarchyPosition);

    public Component setPrintOut(boolean printOut);
    
    //sending the content of the component every 'millis' milliseconds
    public Component setBatchOutputMillis(long millis);

    //this needs to be separately kept, due to Parser.SelectItemsVisitor.ComplexCondition
    //  in short, whether the component uses indexes or expressions
    //     is also dependent on on other component taking part in a join
    public Component setHashIndexes(List<Integer> hashIndexes);
    public Component setHashExpressions(List<ValueExpression> hashExpressions);
    
    public Component addOperator(Operator operator); //add to the end of ChainOperator
    

    // methods necessary for query plan processing
    public void setChild(Component child);

    //method necessary for direct grouping and load balancing:
    //at receiver side:
    public Component setFullHashList(List<String> fullHashList);

}
