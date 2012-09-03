package org.jdamico.dataobjects;

public class SimData {
	
	private Double distTotalSum = .0;
	private String entityName;
	private long entityId = 0l;
	
	public Double getDistTotalSum() {
		return distTotalSum;
	}
	public void setDistTotalSum(Double distTotalSum) {
		this.distTotalSum = distTotalSum;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public long getEntityId() {
		return entityId;
	}
	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}
	
	public SimData(Double distTotalSum, String entityName, long entityId) {
		super();
		this.distTotalSum = distTotalSum;
		this.entityName = entityName;
		this.entityId = entityId;
	}
	
	

}
