/**
 */
package jvizedit.swtfx.sample.graph.gml;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Graph Model Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.GraphModelRoot#getConnections <em>Connections</em>}</li>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.GraphModelRoot#getNodes <em>Nodes</em>}</li>
 * </ul>
 *
 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getGraphModelRoot()
 * @model
 * @generated
 */
public interface GraphModelRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Connections</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connections</em>' containment reference.
	 * @see #setConnections(Connections)
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getGraphModelRoot_Connections()
	 * @model containment="true"
	 * @generated
	 */
	Connections getConnections();

	/**
	 * Sets the value of the '{@link jvizedit.swtfx.sample.graph.gml.GraphModelRoot#getConnections <em>Connections</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Connections</em>' containment reference.
	 * @see #getConnections()
	 * @generated
	 */
	void setConnections(Connections value);

	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference.
	 * @see #setNodes(Nodes)
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getGraphModelRoot_Nodes()
	 * @model containment="true"
	 * @generated
	 */
	Nodes getNodes();

	/**
	 * Sets the value of the '{@link jvizedit.swtfx.sample.graph.gml.GraphModelRoot#getNodes <em>Nodes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Nodes</em>' containment reference.
	 * @see #getNodes()
	 * @generated
	 */
	void setNodes(Nodes value);

} // GraphModelRoot
