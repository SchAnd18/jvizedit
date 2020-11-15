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

	public SwtShapeList(Composite parent) {

		final Group group = new Group(parent, SWT.NONE);
		group.setText("Shape Types");
		group.setLayout(new FillLayout());

		tableViewer = new TableViewer(group);
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new LabelProvider());
		tableViewer.setInput(ShapeType.values());

		final DragSource dragSource = new DragSource(tableViewer.getControl(), DND.DROP_MOVE);
		dragSource.setTransfer(LocalSelectionTransfer.getTransfer(), TextTransfer.getInstance());
		dragSource.addDragListener(new DragSourceAdapter() {

			@Override
			public void dragStart(DragSourceEvent event) {
				event.doit = !tableViewer.getStructuredSelection().isEmpty();
			}

			@Override
			public void dragSetData(DragSourceEvent event) {
				final IStructuredSelection selection = tableViewer.getStructuredSelection();
				LocalSelectionTransfer.getTransfer().setSelection(selection);

				if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
					final String value = selection.getFirstElement().toString();
					event.data = value;
				}
			}

			@Override
			public void dragFinished(DragSourceEvent event) {
				LocalSelectionTransfer.getTransfer().setSelection(null);
			}
		});
	}

}
