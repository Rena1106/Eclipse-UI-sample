package tableview;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class Perspective implements IPerspectiveFactory {
	public static final String ID_PERSPECTIVE = "tableview.perspective";
	@Override
	public void createInitialLayout(IPageLayout layout) {
	

		
		
		layout.setEditorAreaVisible(true);
		/*layout.addView(View.ID,IPageLayout.LEFT, 0.3f, layout.getEditorArea());
		layout.addView(ChangeView.ID, IPageLayout.RIGHT, 0.3f, layout.getEditorArea());*/
		//layout.setFixed(true);
		layout.addView(View.ID, IPageLayout.TOP, 0.2f, IPageLayout.ID_EDITOR_AREA);
		//layout.addView(ChangeView.ID, IPageLayout.BOTTOM, 0.3f, IPageLayout.ID_EDITOR_AREA);
		
		
		
		layout.addShowViewShortcut(View.ID);
		//layout.addShowViewShortcut(ChangeView.ID);
		
	}

}
