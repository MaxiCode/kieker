/**
 */
package kieker.analysisteetime.model.analysismodel.assembly.impl;

import java.lang.reflect.InvocationTargetException;

import kieker.analysisteetime.model.analysismodel.assembly.AssemblyComponent;
import kieker.analysisteetime.model.analysismodel.assembly.AssemblyOperation;
import kieker.analysisteetime.model.analysismodel.assembly.AssemblyPackage;
import kieker.analysisteetime.model.analysismodel.assembly.AssemblyRoot;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link kieker.analysisteetime.model.analysismodel.assembly.impl.AssemblyComponentImpl#getSignature <em>Signature</em>}</li>
 *   <li>{@link kieker.analysisteetime.model.analysismodel.assembly.impl.AssemblyComponentImpl#getAssemblyOperations <em>Assembly Operations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AssemblyComponentImpl extends MinimalEObjectImpl.Container implements AssemblyComponent {
	/**
	 * The default value of the '{@link #getSignature() <em>Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected static final String SIGNATURE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSignature() <em>Signature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignature()
	 * @generated
	 * @ordered
	 */
	protected String signature = SIGNATURE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAssemblyOperations() <em>Assembly Operations</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssemblyOperations()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, AssemblyOperation> assemblyOperations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AssemblyComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssemblyPackage.Literals.ASSEMBLY_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSignature(String newSignature) {
		String oldSignature = signature;
		signature = newSignature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssemblyPackage.ASSEMBLY_COMPONENT__SIGNATURE, oldSignature, signature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, AssemblyOperation> getAssemblyOperations() {
		if (assemblyOperations == null) {
			assemblyOperations = new EcoreEMap<String,AssemblyOperation>(AssemblyPackage.Literals.ESTRING_TO_ASSEMBLY_OPERATION_MAP_ENTRY, EStringToAssemblyOperationMapEntryImpl.class, this, AssemblyPackage.ASSEMBLY_COMPONENT__ASSEMBLY_OPERATIONS);
		}
		return assemblyOperations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssemblyRoot getAssemblyRoot() {
		org.eclipse.emf.ecore.EObject container = this.eContainer();
		if (container != null) {
			org.eclipse.emf.ecore.EObject containerContainer = container.eContainer();
			if (containerContainer != null) {
				return (AssemblyRoot) containerContainer ;
			}
		}
		return null;
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssemblyPackage.ASSEMBLY_COMPONENT__ASSEMBLY_OPERATIONS:
				return ((InternalEList<?>)getAssemblyOperations()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AssemblyPackage.ASSEMBLY_COMPONENT__SIGNATURE:
				return getSignature();
			case AssemblyPackage.ASSEMBLY_COMPONENT__ASSEMBLY_OPERATIONS:
				if (coreType) return getAssemblyOperations();
				else return getAssemblyOperations().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AssemblyPackage.ASSEMBLY_COMPONENT__SIGNATURE:
				setSignature((String)newValue);
				return;
			case AssemblyPackage.ASSEMBLY_COMPONENT__ASSEMBLY_OPERATIONS:
				((EStructuralFeature.Setting)getAssemblyOperations()).set(newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AssemblyPackage.ASSEMBLY_COMPONENT__SIGNATURE:
				setSignature(SIGNATURE_EDEFAULT);
				return;
			case AssemblyPackage.ASSEMBLY_COMPONENT__ASSEMBLY_OPERATIONS:
				getAssemblyOperations().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AssemblyPackage.ASSEMBLY_COMPONENT__SIGNATURE:
				return SIGNATURE_EDEFAULT == null ? signature != null : !SIGNATURE_EDEFAULT.equals(signature);
			case AssemblyPackage.ASSEMBLY_COMPONENT__ASSEMBLY_OPERATIONS:
				return assemblyOperations != null && !assemblyOperations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case AssemblyPackage.ASSEMBLY_COMPONENT___GET_ASSEMBLY_ROOT:
				return getAssemblyRoot();
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (signature: ");
		result.append(signature);
		result.append(')');
		return result.toString();
	}

} //AssemblyComponentImpl
