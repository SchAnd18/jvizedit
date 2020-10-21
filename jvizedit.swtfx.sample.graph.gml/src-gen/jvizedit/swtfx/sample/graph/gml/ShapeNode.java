/**
 */
package jvizedit.swtfx.sample.graph.gml;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shape Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getName <em>Name</em>}</li>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getX <em>X</em>}</li>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getY <em>Y</em>}</li>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getWidth <em>Width</em>}</li>
 *   <li>{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getHeight <em>Height</em>}</li>
 * </ul>
 *
 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getShapeNode()
 * @model abstract="true"
 * @generated
 */
public interface ShapeNode extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getShapeNode_Name()
	 * @model unique="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(double)
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getShapeNode_X()
	 * @model unique="false"
	 * @generated
	 */
	double getX();

	/**
	 * Sets the value of the '{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(double value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(double)
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getShapeNode_Y()
	 * @model unique="false"
	 * @generated
	 */
	double getY();

	/**
	 * Sets the value of the '{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(double value);

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(double)
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getShapeNode_Width()
	 * @model unique="false"
	 * @generated
	 */
	double getWidth();

	/**
	 * Sets the value of the '{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(double value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(double)
	 * @see jvizedit.swtfx.sample.graph.gml.GmlPackage#getShapeNode_Height()
	 * @model unique="false"
	 * @generated
	 */
	double getHeight();

	/**
	 * Sets the value of the '{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(double value);

} // ShapeNode
