/**
 */
package jvizedit.swtfx.sample.graph.gml;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.Connection#getSrc <em>Src</em>}</li>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.Connection#getTarget <em>Target</em>}</li>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.Connection#getPath <em>Path</em>}</li>
 * </ul>
 *
 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getConnection()
 * @model
 * @generated
 */
public interface Connection extends EObject {
	/**
	 * Returns the value of the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Src</em>' reference.
	 * @see #setSrc(ShapeNode)
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getConnection_Src()
	 * @model
	 * @generated
	 */
	ShapeNode getSrc();

	/**
	 * Sets the value of the '{@link jvizedit.swtfx.sample.graph.gml.Connection#getSrc <em>Src</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Src</em>' reference.
	 * @see #getSrc()
	 * @generated
	 */
	void setSrc(ShapeNode value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(ShapeNode)
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getConnection_Target()
	 * @model
	 * @generated
	 */
	ShapeNode getTarget();

	/**
	 * Sets the value of the '{@link jvizedit.swtfx.sample.graph.gml.Connection#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(ShapeNode value);

	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Double}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute list.
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getConnection_Path()
	 * @model unique="false"
	 * @generated
	 */
	EList<Double> getPath();

} // Connection
