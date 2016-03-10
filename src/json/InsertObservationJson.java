package json;

public class InsertObservationJson {
	
	public String type ="";
	public String SamplingFOIName ="";
	public String SamplingFOIIdentifier ="";
	public String ProcedureID ="";
	public String ProcedureName ="";
	public String ObservationPhenomenonTime ="";
	public String ObservedFOIName ="";
	public String ObservedFOIIdentifier ="";

	Observations observations = new Observations();
	ObservationFOI2 observationFOI = new ObservationFOI2();
	
	public String RelatedFeature_Role ="";
	public String RelatedFeature_Target ="";
	
	public String Metadata_Keyword ="";
	public String Metadata_ResponsiblePartyContact ="";
	public String Metadata_HREF ="";

}
