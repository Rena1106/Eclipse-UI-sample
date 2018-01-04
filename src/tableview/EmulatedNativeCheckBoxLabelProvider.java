package tableview;



import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

public abstract class EmulatedNativeCheckBoxLabelProvider extends OwnerDrawLabelProvider {

    private static final String CHECKED_KEY = "CHECKED";

    private static final String UNCHECK_KEY = "UNCHECKED";

    private Image image;

    public EmulatedNativeCheckBoxLabelProvider(TableViewer viewer) {
        if (JFaceResources.getImageRegistry().getDescriptor(CHECKED_KEY) == null) {
            Shell shell = viewer.getControl().getShell();
            JFaceResources.getImageRegistry().put(UNCHECK_KEY, makeShot(shell, false, viewer));
            JFaceResources.getImageRegistry().put(CHECKED_KEY, makeShot(shell, true, viewer));
        }
    }

    private Image makeShot(Shell shell, boolean type, ColumnViewer viewer) {
        Shell s = new Shell(shell, SWT.NO_TRIM);
        Button b = new Button(s, SWT.CHECK);
        b.setSelection(type);
        Point bsize = b.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        b.setSize(bsize);
        b.setLocation(0, 0);
        s.setSize(bsize);
        s.open();

        GC gc = new GC(b);
        Image image = new Image(shell.getDisplay(), bsize.x, bsize.y);
        gc.copyArea(image, 0, 0);
        gc.dispose();

        s.close();

        return image;
    }

    public Image getImage(Object element) {
        return JFaceResources.getImageRegistry().getDescriptor(isChecked(element) ? CHECKED_KEY : UNCHECK_KEY)
                .createImage();
    }

    @Override
    protected void paint(Event event, Object element) {

        image = getImage(element);
        if (image != null) {
            Rectangle bounds = ((TableItem) event.item)
                    .getBounds(event.index);
            Rectangle imgBounds = image.getBounds();
            bounds.width /= 2;
            bounds.width -= imgBounds.width / 2;
            bounds.height /= 2;
            bounds.height -= imgBounds.height / 2;

            int x = bounds.width > 0 ? bounds.x + bounds.width : bounds.x;
            int y = bounds.height > 0 ? bounds.y + bounds.height : bounds.y;

            event.gc.drawImage(image, x, y);
        }
    }

    protected abstract boolean isChecked(Object element);

    @Override
    protected void measure(Event event, Object element) {
    }
}
