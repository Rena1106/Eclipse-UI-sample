package tableview;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.ViewPart;

import dialogs.AddPersonDialog;
import edit.FirstNameEditingSupport;
import edit.MarriedEditingSupport;
//import edit.MarriedEditingSupport;
import model.ModelProvider;
import model.Person;

public class View extends ViewPart {
	public static final String ID = "tableviewer.view";

	@Inject
	IWorkbench workbench;

	private TableViewer viewer;

	// static fields to hold the images

	private Button btnNewButton;

	private UISynchronize sync;


	@Override
	public void createPartControl(Composite parent) {
		System.out.println("createPartControl of view");
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		Label searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText("Search");
		final Text searchText = new Text(parent, SWT.BORDER | SWT.SEARCH);
		searchText.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		createViewer(parent);

		btnNewButton = new Button(parent, SWT.PUSH);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ModelProvider persons = ModelProvider.INSTANCE;
				AddPersonDialog dialog = new AddPersonDialog(getViewSite().getShell());
				dialog.open();
				if (dialog.getPerson() != null) {
					persons.getPersons().add(dialog.getPerson());
					System.out.println("change view");
					viewer.refresh();
					// updating the display in the View
					// System.out.println("partid"+part.ID);
					// part.refresh();
				}

			}
		});
		btnNewButton.setText("Add");

		Button btnDelete = new Button(parent, SWT.PUSH);
		btnDelete.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				/* DeletePerson dp = new DeletePerson(); */
				ISelection selection = viewer.getSelection();
				if (selection != null && selection instanceof IStructuredSelection) {
					List<Person> persons = ModelProvider.INSTANCE.getPersons();
					IStructuredSelection sel = (IStructuredSelection) selection;

					for (Iterator<Person> iterator = sel.iterator(); iterator.hasNext();) {
						Person person = iterator.next();
						persons.remove(person);
					}
					viewer.refresh();
				}
			}
		});
		btnDelete.setText("Delete");

		// make selection available via SelectionProvider
		// getSite().setSelectionProvider(viewer);

	}

	// used to update the viewer
	public void refresh() {
		viewer.refresh();
	}

	private void createViewer(Composite parent) {

		System.out.print("create viewer enter");
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(ModelProvider.INSTANCE.getPersons());
		// make the selection available to other views
		getSite().setSelectionProvider(viewer);

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);
	}

	public TableViewer getViewer() {
		return viewer;
	}

	// create columns for table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { "FirstName", "LastName", "Gender", "Married" };
		int[] bounds = { 100, 100, 100, 100 };

		// first column for the first name
		TableViewerColumn colFirst = createTableViewerColumn(titles[0], bounds[0], 0);
		colFirst.setLabelProvider(new CellLabelProvider() {

			@Override
			public void update(ViewerCell cell) {
				cell.setText(((Person) cell.getElement()).getFirstName());

			}

		});
		colFirst.setEditingSupport(new FirstNameEditingSupport(viewer));

		// second column for last name
		TableViewerColumn colSecond = createTableViewerColumn(titles[1], bounds[1], 1);
		colSecond.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Person p = (Person) element;
				return p.getLastName();
			}
		});

		// third column for gender
		TableViewerColumn colThird = createTableViewerColumn(titles[2], bounds[2], 2);
		colThird.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Person p = (Person) element;
				return p.getGender();
			}
		});

		// forth column for married
		TableViewerColumn colForth = createTableViewerColumn(titles[3], bounds[3], 3);
		colForth.setLabelProvider(new EmulatedNativeCheckBoxLabelProvider(viewer) {

			@Override
			protected boolean isChecked(Object element) {
				if (((Person) element).isMarried()) {
					return true;
				} else {
					return false;
				}

			}
		});
		colForth.setEditingSupport(new MarriedEditingSupport(viewer));
	}

	// The third parameter is currently not used
	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	/*
	 * private List<String> createInitialDataModel() { return Arrays.asList("One",
	 * "Two", "Three"); }
	 */
}