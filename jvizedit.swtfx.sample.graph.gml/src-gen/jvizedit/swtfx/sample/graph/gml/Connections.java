/**
 */
package jvizedit.swtfx.sample.graph.gml;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connections</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.Connections#getConnections <em>Connections</em>}</li>
 * </ul>
 *
 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getConnections()
 * @model
 * @generated
 */
public interface Connections extends EObject {
	/**
	 * Returns the value of the '<em><b>Connections</b></em>' containment reference list.
	 * The list contents are of type {@link jvizedit.swtfx.sample.graph.gml.Connection}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connections</em>' containment reference list.
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getConnections_Connections()
	 * @model containment="true"
	 * @generated
	 */
	EList<Connection> getConnections();

} // Connections
