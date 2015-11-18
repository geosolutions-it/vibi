package it.geosolutions.geobatch.destination.datamigration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class UpdaterFeatures implements Cloneable {
	
	private static final XStream xstream = new XStream();

	static {
		xstream.alias("features", UpdaterFeatures.class);	
		xstream.alias("feature", UpdaterFeature.class);	
		xstream.addImplicitCollection(UpdaterFeatures.class, "features");
	}
	
	private List<UpdaterFeature> features = new ArrayList<UpdaterFeature>();
	
	
	public List<UpdaterFeature> getFeatures() {
		return features;
	}

	public void setFeatures(List<UpdaterFeature> features) {
		this.features = features;
	}

	public static UpdaterFeatures fromXML(InputStream inputXML) {
		return (UpdaterFeatures) xstream.fromXML(inputXML);		
	}
	
	public class UpdaterFeature implements Cloneable {
		
		private Boolean target;
		private String featureName;
		private String featureParentRelation;
		
		public Boolean getTarget() {
			return target;
		}
		public void setTarget(Boolean target) {
			this.target = target;
		}
		public String getFeatureName() {
			return featureName;
		}
		public void setFeatureName(String featureName) {
			this.featureName = featureName;
		}
		public String getFeatureParentRelation() {
			return featureParentRelation;
		}
		public void setFeatureParentRelation(String featureParentRelation) {
			this.featureParentRelation = featureParentRelation;
		}
		
		
	}
	
}
