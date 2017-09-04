package cjj.demo.test;

public class Depart {

	private String prtID;
	private String ID;
	
	
	public Depart() {
		super();
	}
	public Depart(String prtID, String iD) {
		super();
		this.prtID = prtID;
		ID = iD;
	}
	public String getPrtID() {
		return prtID;
	}
	public void setPrtID(String prtID) {
		this.prtID = prtID;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((prtID == null) ? 0 : prtID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Depart other = (Depart) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (prtID == null) {
			if (other.prtID != null)
				return false;
		} else if (!prtID.equals(other.prtID))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Depart [prtID=" + prtID + ", ID=" + ID + "]";
	}
	
	
}
