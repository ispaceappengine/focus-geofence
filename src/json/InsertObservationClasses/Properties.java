
package json.InsertObservationClasses;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Properties {

    public String SamplingFOIName;
    public String SamplingFOIIdentifier;
    public String ProcedureID;
    public String ProcedureName;
    public String ObservationPhenomenonTime;
    public String ObservedFOIName;
    public String ObservedFOIIdentifier;
    public List<Observation> Observations = new ArrayList<Observation>();
    public RelatedFeature RelatedFeature;
    public Metadata Metadata;
    public ObservationFOI ObservationFOI;

}
