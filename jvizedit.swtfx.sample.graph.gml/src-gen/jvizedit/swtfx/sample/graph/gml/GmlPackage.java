/**
 */
package jvizedit.swtfx.sample.graph.gml;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see jvizedit.swtfx.sample.graph.gml.GmlFactory
 * @model kind="package"
 * @generated
 */
public interface GmlPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "gml";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "jvizedit.swtfx.sample.graph.gml";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "gml";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GmlPackage eINSTANCE = jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl.init();

	/**
	 * The meta object id for the '{@link jvizedit.swtfx.sample.graph.gml.impl.GraphModelRootImpl <em>Graph Model Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jvizedit.swtfx.sample.graph.gml.impl.GraphModelRootImpl
	 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getGraphModelRoot()
	 * @generated
	 */
	int GRAPH_MODEL_ROOT = 0;

	/**
	 * The feature id for the '<em><b>Connections</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_MODEL_ROOT__CONNECTIONS = 0;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_MODEL_ROOT__NODES = 1;

	/**
	 * The number of structural features of the '<em>Graph Model Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_MODEL_ROOT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Graph Model Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_MODEL_ROOT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link jvizedit.swtfx.sample.graph.gml.impl.ConnectionsImpl <em>Connections</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jvizedit.swtfx.sample.graph.gml.impl.ConnectionsImpl
	 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getConnections()
	 * @generated
	 */
	int CONNECTIONS = 1;

	/**
	 * The feature id for the '<em><b>Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTIONS__CONNECTIONS = 0;

	/**
	 * The number of structural features of the '<em>Connections</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTIONS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Connections</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTIONS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link jvizedit.swtfx.sample.graph.gml.impl.NodesImpl <em>Nodes</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jvizedit.swtfx.sample.graph.gml.impl.NodesImpl
	 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getNodes()
	 * @generated
	 */
	int NODES = 2;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODES__NODES = 0;

	/**
	 * The number of structural features of the '<em>Nodes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODES_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Nodes</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODES_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link jvizedit.swtfx.sample.graph.gml.impl.ConnectionImpl <em>Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jvizedit.swtfx.sample.graph.gml.impl.ConnectionImpl
	 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getConnection()
	 * @generated
	 */
	int CONNECTION = 3;

	/**
	 * The feature id for the '<em><b>Src</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__SRC = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__TARGET = 1;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__PATH = 2;

	/**
	 * The number of structural features of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link jvizedit.swtfx.sample.graph.gml.impl.ShapeNodeImpl <em>Shape Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jvizedit.swtfx.sample.graph.gml.impl.ShapeNodeImpl
	 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getShapeNode()
	 * @generated
	 */
	int SHAPE_NODE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_NODE__NAME = 0;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_NODE__X = 1;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_NODE__Y = 2;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_NODE__WIDTH = 3;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_NODE__HEIGHT = 4;

	/**
	 * The number of structural features of the '<em>Shape Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_NODE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Shape Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHAPE_NODE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link jvizedit.swtfx.sample.graph.gml.impl.GraphNodeImpl <em>Graph Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jvizedit.swtfx.sample.graph.gml.impl.GraphNodeImpl
	 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getGraphNode()
	 * @generated
	 */
	int GRAPH_NODE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_NODE__NAME = SHAPE_NODE__NAME;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_NODE__X = SHAPE_NODE__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_NODE__Y = SHAPE_NODE__Y;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_NODE__WIDTH = SHAPE_NODE__WIDTH;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_NODE__HEIGHT = SHAPE_NODE__HEIGHT;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_NODE__CHILDREN = SHAPE_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Graph Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_NODE_FEATURE_COUNT = SHAPE_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Graph Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPH_NODE_OPERATION_COUNT = SHAPE_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link jvizedit.swtfx.sample.graph.gml.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see jvizedit.swtfx.sample.graph.gml.impl.PortImpl
	 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getPort()
	 * @generated
	 */
	int PORT = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NAME = SHAPE_NODE__NAME;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__X = SHAPE_NODE__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__Y = SHAPE_NODE__Y;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__WIDTH = SHAPE_NODE__WIDTH;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__HEIGHT = SHAPE_NODE__HEIGHT;

	/**
	 * The number of structural features of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = SHAPE_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_OPERATION_COUNT = SHAPE_NODE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link jvizedit.swtfx.sample.graph.gml.GraphModelRoot <em>Graph Model Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graph Model Root</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.GraphModelRoot
	 * @generated
	 */
	EClass getGraphModelRoot();

	/**
	 * Returns the meta object for the containment reference '{@link jvizedit.swtfx.sample.graph.gml.GraphModelRoot#getConnections <em>Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connections</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.GraphModelRoot#getConnections()
	 * @see #getGraphModelRoot()
	 * @generated
	 */
	EReference getGraphModelRoot_Connections();

	/**
	 * Returns the meta object for the containment reference '{@link jvizedit.swtfx.sample.graph.gml.GraphModelRoot#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Nodes</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.GraphModelRoot#getNodes()
	 * @see #getGraphModelRoot()
	 * @generated
	 */
	EReference getGraphModelRoot_Nodes();

	/**
	 * Returns the meta object for class '{@link jvizedit.swtfx.sample.graph.gml.Connections <em>Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connections</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.Connections
	 * @generated
	 */
	EClass getConnections();

	/**
	 * Returns the meta object for the containment reference list '{@link jvizedit.swtfx.sample.graph.gml.Connections#getConnections <em>Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Connections</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.Connections#getConnections()
	 * @see #getConnections()
	 * @generated
	 */
	EReference getConnections_Connections();

	/**
	 * Returns the meta object for class '{@link jvizedit.swtfx.sample.graph.gml.Nodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Nodes</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.Nodes
	 * @generated
	 */
	EClass getNodes();

	/**
	 * Returns the meta object for the containment reference list '{@link jvizedit.swtfx.sample.graph.gml.Nodes#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.Nodes#getNodes()
	 * @see #getNodes()
	 * @generated
	 */
	EReference getNodes_Nodes();

	/**
	 * Returns the meta object for class '{@link jvizedit.swtfx.sample.graph.gml.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for the reference '{@link jvizedit.swtfx.sample.graph.gml.Connection#getSrc <em>Src</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Src</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.Connection#getSrc()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Src();

	/**
	 * Returns the meta object for the reference '{@link jvizedit.swtfx.sample.graph.gml.Connection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.Connection#getTarget()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Target();

	/**
	 * Returns the meta object for the attribute list '{@link jvizedit.swtfx.sample.graph.gml.Connection#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Path</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.Connection#getPath()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_Path();

	/**
	 * Returns the meta object for class '{@link jvizedit.swtfx.sample.graph.gml.ShapeNode <em>Shape Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shape Node</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.ShapeNode
	 * @generated
	 */
	EClass getShapeNode();

	/**
	 * Returns the meta object for the attribute '{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.ShapeNode#getName()
	 * @see #getShapeNode()
	 * @generated
	 */
	EAttribute getShapeNode_Name();

	/**
	 * Returns the meta object for the attribute '{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.ShapeNode#getX()
	 * @see #getShapeNode()
	 * @generated
	 */
	EAttribute getShapeNode_X();

	/**
	 * Returns the meta object for the attribute '{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.ShapeNode#getY()
	 * @see #getShapeNode()
	 * @generated
	 */
	EAttribute getShapeNode_Y();

	/**
	 * Returns the meta object for the attribute '{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.ShapeNode#getWidth()
	 * @see #getShapeNode()
	 * @generated
	 */
	EAttribute getShapeNode_Width();

	/**
	 * Returns the meta object for the attribute '{@link jvizedit.swtfx.sample.graph.gml.ShapeNode#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.ShapeNode#getHeight()
	 * @see #getShapeNode()
	 * @generated
	 */
	EAttribute getShapeNode_Height();

	/**
	 * Returns the meta object for class '{@link jvizedit.swtfx.sample.graph.gml.GraphNode <em>Graph Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graph Node</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.GraphNode
	 * @generated
	 */
	EClass getGraphNode();

	/**
	 * Returns the meta object for the containment reference list '{@link jvizedit.swtfx.sample.graph.gml.GraphNode#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.GraphNode#getChildren()
	 * @see #getGraphNode()
	 * @generated
	 */
	EReference getGraphNode_Children();

	/**
	 * Returns the meta object for class '{@link jvizedit.swtfx.sample.graph.gml.Port <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see jvizedit.swtfx.sample.graph.gml.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GmlFactory getGmlFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link jvizedit.swtfx.sample.graph.gml.impl.GraphModelRootImpl <em>Graph Model Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jvizedit.swtfx.sample.graph.gml.impl.GraphModelRootImpl
		 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getGraphModelRoot()
		 * @generated
		 */
		EClass GRAPH_MODEL_ROOT = eINSTANCE.getGraphModelRoot();

		/**
		 * The meta object literal for the '<em><b>Connections</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH_MODEL_ROOT__CONNECTIONS = eINSTANCE.getGraphModelRoot_Connections();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH_MODEL_ROOT__NODES = eINSTANCE.getGraphModelRoot_Nodes();

		/**
		 * The meta object literal for the '{@link jvizedit.swtfx.sample.graph.gml.impl.ConnectionsImpl <em>Connections</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jvizedit.swtfx.sample.graph.gml.impl.ConnectionsImpl
		 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getConnections()
		 * @generated
		 */
		EClass CONNECTIONS = eINSTANCE.getConnections();

		/**
		 * The meta object literal for the '<em><b>Connections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTIONS__CONNECTIONS = eINSTANCE.getConnections_Connections();

		/**
		 * The meta object literal for the '{@link jvizedit.swtfx.sample.graph.gml.impl.NodesImpl <em>Nodes</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jvizedit.swtfx.sample.graph.gml.impl.NodesImpl
		 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getNodes()
		 * @generated
		 */
		EClass NODES = eINSTANCE.getNodes();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODES__NODES = eINSTANCE.getNodes_Nodes();

		/**
		 * The meta object literal for the '{@link jvizedit.swtfx.sample.graph.gml.impl.ConnectionImpl <em>Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jvizedit.swtfx.sample.graph.gml.impl.ConnectionImpl
		 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getConnection()
		 * @generated
		 */
		EClass CONNECTION = eINSTANCE.getConnection();

		/**
		 * The meta object literal for the '<em><b>Src</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__SRC = eINSTANCE.getConnection_Src();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__TARGET = eINSTANCE.getConnection_Target();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION__PATH = eINSTANCE.getConnection_Path();

		/**
		 * The meta object literal for the '{@link jvizedit.swtfx.sample.graph.gml.impl.ShapeNodeImpl <em>Shape Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jvizedit.swtfx.sample.graph.gml.impl.ShapeNodeImpl
		 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getShapeNode()
		 * @generated
		 */
		EClass SHAPE_NODE = eINSTANCE.getShapeNode();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_NODE__NAME = eINSTANCE.getShapeNode_Name();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_NODE__X = eINSTANCE.getShapeNode_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_NODE__Y = eINSTANCE.getShapeNode_Y();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_NODE__WIDTH = eINSTANCE.getShapeNode_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHAPE_NODE__HEIGHT = eINSTANCE.getShapeNode_Height();

		/**
		 * The meta object literal for the '{@link jvizedit.swtfx.sample.graph.gml.impl.GraphNodeImpl <em>Graph Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jvizedit.swtfx.sample.graph.gml.impl.GraphNodeImpl
		 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getGraphNode()
		 * @generated
		 */
		EClass GRAPH_NODE = eINSTANCE.getGraphNode();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPH_NODE__CHILDREN = eINSTANCE.getGraphNode_Children();

		/**
		 * The meta object literal for the '{@link jvizedit.swtfx.sample.graph.gml.impl.PortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see jvizedit.swtfx.sample.graph.gml.impl.PortImpl
		 * @see jvizedit.swtfx.sample.graph.gml.impl.GmlPackageImpl#getPort()
		 * @generated
		 */
		EClass PORT = eINSTANCE.getPort();

	}

} //GmlPackage
