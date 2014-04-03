package info.bioinfweb.commons.swing;


import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;



public class RelativeRangeModel extends DefaultBoundedRangeModel 
    implements BoundedRangeModel {
	
	
	private double maximumFactor = 10;
	

  /**
   * Constructs an instance of this class with a maximum factor of 10.
   */
  public RelativeRangeModel() {
		super();
		calculateMaximum(getValue());
	}


	public RelativeRangeModel(int value, double maximumFactor) {
		super();
		this.maximumFactor = maximumFactor;
		setValue(value);
	}


	private void calculateMaximum(int newValue) {
		setMaximum((int)Math.round(
				Math.max(Integer.MAX_VALUE, getMaximumFactor() * newValue)));
  }
	
	
	public double getMaximumFactor() {
		return maximumFactor;
	}


	/**
	 * Sets the current maximum factor and recalculates the maximum.
	 * @param maxFactor - the new maximum factor
	 */
	public void setMaximumFactor(double maxFactor) {
		this.maximumFactor = maxFactor;
		calculateMaximum(getValue());
	}


	/**
	 * Sets the value of this model. The minimum is decreased if the current minimum is
	 * greater than the given value. The maximum is recalculated from the current maximum
	 * factor.
	 * @param value - the new value (Must be greater or equal zero.) 
	 */
	@Override
	public void setValue(int value) {
		if (value > 0) {
			if (getMinimum() > value) {
				setMinimum(value);
			}
			calculateMaximum(value);
			super.setValue(value);
		}
	}
}
