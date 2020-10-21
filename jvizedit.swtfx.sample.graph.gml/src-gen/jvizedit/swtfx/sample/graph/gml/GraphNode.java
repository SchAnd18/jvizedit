/**
 */
package jvizedit.swtfx.sample.graph.gml;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.GraphNode#getChildren <em>Children</em>}</li>
 * </ul>
 *
 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getGraphNode()
 * @model
 * @generated
 */
public interface GraphNode extends ShapeNode {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link jvizedit.swtfx.sample.graph.gml.ShapeNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getGraphNode_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<ShapeNode> getChildren();

} // GraphNode
