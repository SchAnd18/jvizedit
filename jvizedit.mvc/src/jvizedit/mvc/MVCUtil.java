package jvizedit.mvc;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MVCUtil {

	public static <T> Optional<T> findCommonParentOfType(IController c1, IController c2, Class<T> controllerType) {
		final Set<T> allParentEdgeContainersOfC1 = new HashSet<>();
		IController parent = c1.getParent();
		while (parent != null) {
			if (controllerType.isInstance(parent)) {
				allParentEdgeContainersOfC1.add(controllerType.cast(parent));
			}
			parent = parent.getParent();
		}

		parent = c2.getParent();
		while (parent != null) {
			if (allParentEdgeContainersOfC1.contains(parent)) {
				return Optional.of(controllerType.cast(parent));
			}
			parent = parent.getParent();
		}
		return Optional.empty();
	}

}
