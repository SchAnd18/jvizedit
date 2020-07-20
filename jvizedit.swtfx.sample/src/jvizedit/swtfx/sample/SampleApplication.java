package jvizedit.swtfx.sample;

import java.util.Random;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import jvizedit.swtfx.sample.model.ShapeObject;
import jvizedit.swtfx.sample.model.ShapeType;
import jvizedit.swtfx.sample.model.ShapesRoot;
import jvizedit.swtfx.sample.viewer.ShapeViewerControl;

public class SampleApplication {

	
	public static void main(String...args) {
		
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		
		final ShapeViewerControl svc = new ShapeViewerControl(shell);
		svc.setInput(createInitModel());
		
		shell.setVisible(true);
		
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	
	private static ShapesRoot createInitModel() {
		final Random random = new Random();
		final ShapesRoot root = new ShapesRoot();
		for(int i = 0; i<10; i++) {
			final ShapeObject so = createRandomShapeObject(random, "Shape_" + i);
			root.addShape(so);
		}
		return root;
	}
	
	private static ShapeObject createRandomShapeObject (final Random random, String text) {
		final ShapeObject result = new ShapeObject();
		result.setX(random.nextDouble() * 800);
		result.setY(random.nextDouble() * 600);
		result.setWidth(20 + random.nextDouble() * 100);
		result.setHeight(20 + random.nextDouble() * 100);
		
		final ShapeType st = ShapeType.values()[random.nextInt(ShapeType.values().length)];
		result.setShapeType(st);
		
		final String color = "rgb(" + random.nextInt(255) + "," + random.nextInt(255) + "," + random.nextInt(255)+")";
		result.setColor(color);
		
		result.setText(text);
		return result;
	}
	
}
