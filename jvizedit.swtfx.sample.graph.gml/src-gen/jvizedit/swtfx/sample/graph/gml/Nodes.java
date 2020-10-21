/**
 */
package jvizedit.swtfx.sample.graph.gml;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Nodes</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.Nodes#getNodes <em>Nodes</em>}</li>
 * </ul>
 *
 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getNodes()
 * @model
 * @generated
 */
public interface Nodes extends EObject {
	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link jvizedit.swtfx.sample.graph.gml.GraphNode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getNodes_Nodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<GraphNode> getNodes();

} // Nodes
