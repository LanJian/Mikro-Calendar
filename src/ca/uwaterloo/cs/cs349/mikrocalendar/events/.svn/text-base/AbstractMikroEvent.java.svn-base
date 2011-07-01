package ca.uwaterloo.cs.cs349.mikrocalendar.events;

/**
 * 
 * @author Michael Terry
 */
public abstract class AbstractMikroEvent implements MikroEventModel {
	public boolean equals(Object o) {
		if (o instanceof AbstractMikroEvent) {
			AbstractMikroEvent r = (AbstractMikroEvent)o;
			boolean result = (this.getAuthor().equals(r.getAuthor())
								&& this.getContent().equals(r.getContent())
								&& this.getStatusID() == r.getStatusID()
								&& this.getStartTime().equals(r.getStartTime())
								&& this.getOriginalSubmitTime().equals(r.getOriginalSubmitTime()));
			if (this.getEndTime() == null) {
				result = result && (r.getEndTime() == null);
			} else {
				result = result && this.getEndTime().equals(r.getEndTime());
			}
			return result;
		}
		return false;
	}

	public String toString() {
		return MikroEventUtils.eventToJSON(this).toString();
	}
}