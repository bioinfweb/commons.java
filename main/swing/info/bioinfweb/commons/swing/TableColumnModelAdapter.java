package info.bioinfweb.commons.swing;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;



/**
 * <p>An abstract adapter class for receiving events of a <code>TableColumnModel</code>. 
 * The methods in this class are empty. This class exists as convenience for creating 
 * listener objects.</p>
 * <p>Extend this class to create a <code>TableColumnModelListener</code> listener and 
 * override the methods for the events of interest. (If you implement the 
 * <code>TableColumnModelListener</code> interface, you have to define all of the 
 * methods in it. This abstract class defines null methods for them all, so 
 * you can only have to define methods for events you care about.)</p> 
 * @author BenStoever
 *
 */
public abstract class TableColumnModelAdapter implements TableColumnModelListener {
	/* (non-Javadoc)
	 * @see javax.swing.event.TableColumnModelListener#columnAdded(javax.swing.event.TableColumnModelEvent)
	 */
	public void columnAdded(TableColumnModelEvent e) {}

	
	/* (non-Javadoc)
	 * @see javax.swing.event.TableColumnModelListener#columnMarginChanged(javax.swing.event.ChangeEvent)
	 */
	public void columnMarginChanged(ChangeEvent e) {}

	
	/* (non-Javadoc)
	 * @see javax.swing.event.TableColumnModelListener#columnMoved(javax.swing.event.TableColumnModelEvent)
	 */
	public void columnMoved(TableColumnModelEvent e) {}

	
	/* (non-Javadoc)
	 * @see javax.swing.event.TableColumnModelListener#columnRemoved(javax.swing.event.TableColumnModelEvent)
	 */
	public void columnRemoved(TableColumnModelEvent e) {}

	
	/* (non-Javadoc)
	 * @see javax.swing.event.TableColumnModelListener#columnSelectionChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void columnSelectionChanged(ListSelectionEvent e) {}
}
