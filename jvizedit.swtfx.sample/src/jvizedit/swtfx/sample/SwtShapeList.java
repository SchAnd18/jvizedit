package jvizedit.swtfx.sample;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import jvizedit.swtfx.sample.model.ShapeType;

public class SwtShapeList {

	final TableViewer tableViewer;

	public SwtShapeList(final Composite parent) {

		final Group group = new Group(parent, SWT.NONE);
		group.setText("Shape Types");
		group.setLayout(new FillLayout());

		this.tableViewer = new TableViewer(group);
		this.tableViewer.setContentProvider(new ArrayContentProvider());
		this.tableViewer.setLabelProvider(new LabelProvider());
		this.tableViewer.setInput(ShapeType.values());

		final DragSource dragSource = new DragSource(this.tableViewer.getControl(), DND.DROP_MOVE);
		dragSource.setTransfer(LocalSelectionTransfer.getTransfer(), TextTransfer.getInstance());
		dragSource.addDragListener(new DragSourceAdapter() {

			@Override
			public void dragStart(final DragSourceEvent event) {
				event.doit = !SwtShapeList.this.tableViewer.getStructuredSelection().isEmpty();
			}

			@Override
			public void dragSetData(final DragSourceEvent event) {
				final IStructuredSelection selection = SwtShapeList.this.tableViewer.getStructuredSelection();
				LocalSelectionTransfer.getTransfer().setSelection(selection);

				if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
					final String value = selection.getFirstElement().toString();
					event.data = value;
				}
			}

			@Override
			public void dragFinished(final DragSourceEvent event) {
				LocalSelectionTransfer.getTransfer().setSelection(null);
			}
		});
	}

}
