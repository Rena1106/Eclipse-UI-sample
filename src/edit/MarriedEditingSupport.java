package edit;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import model.Person;

public class MarriedEditingSupport extends EditingSupport {

	private final TableViewer viewer;

	public MarriedEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new CheckboxCellEditor(null, SWT.CHECK | SWT.READ_ONLY);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		Person person = (Person) element;
		System.out.println("married editing return");
		return person.isMarried();
		
	}

	@Override
	protected void setValue(Object element, Object value) {
		Person pers = (Person) element;
		
		pers.setMarried((Boolean)value);
		System.out.println(pers.isMarried());
		viewer.update(element, null);
		
	}

}
