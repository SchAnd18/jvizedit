package jvizedit.mvc.content;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jvizedit.mvc.IEdgeContainer;
import jvizedit.mvc.IEdgeController;
import jvizedit.mvc.content.core.IContentHandler;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.mvc.content.core.IContentUpdate;

public class EdgeUpdater {

	
	public void update(final Set<IEdgeContainer> updatedEdgeContainers, final Set<IEdgeContainer> removedEdgeContainers, final IContentManager contentManager) {
		
		
		final Map<IEdgeContainer, ContainerUpdate> contaienrToUpdate = new HashMap<>(); // all edge updtaes 
		final Map<IEdgeController,IEdgeContainer> edgeControllerToParent = new HashMap<>(); // all used (added + reused) edges with parent
		{ 
			Set<IEdgeContainer> invalidatedEdgeContainers = updatedEdgeContainers;
			while(!invalidatedEdgeContainers.isEmpty()) {
				final Set<IEdgeContainer> furtherInvalidatedContainers = new HashSet<>();
				for(IEdgeContainer ec: invalidatedEdgeContainers) {
					final EdgeUpdateContext euc = new EdgeUpdateContext(contentManager);
					
					final ContainerUpdate update = new ContainerUpdate(ec, euc);
					contaienrToUpdate.put(ec, update);
					
					
					for(IEdgeController reused: euc.getReusedControllers()) {
						furtherInvalidatedContainers.add(reused.getParent());
					}
					
					for(IEdgeController added: update.getUpdate().getAdded()) {
						final IEdgeContainer prevEc = edgeControllerToParent.put(added, ec);
						if(prevEc != null) {
							throw new RuntimeException("Edge is provided by multiple parents!");
						}
					}
				}
				//make sure that no one is update twice
				furtherInvalidatedContainers.removeAll(contaienrToUpdate.keySet());
				invalidatedEdgeContainers = furtherInvalidatedContainers;
			}
		}
		
		
		// determine all removed edges
		final Set<IEdgeController> removedEdgeControllers = new HashSet<>(); {
			for(final IEdgeContainer rec: removedEdgeContainers) {
				final IContentHandler<IEdgeController> ch = rec.getEdgeConentHandler();
				if(ch == null) {
					continue;
				}
				removedEdgeControllers.addAll(ch.getContent());
			}
			for(final ContainerUpdate update: contaienrToUpdate.values()) {
				removedEdgeControllers.addAll(update.getUpdate().getRemoved());
			}
			for(final ContainerUpdate update: contaienrToUpdate.values()) {
				removedEdgeControllers.removeAll(update.getUpdate().getAdded());
			}
		}
		
		// perform container Updates
		contaienrToUpdate.values().forEach(ContainerUpdate::runUpdate);
		
		
		// dispose removed edges
		removedEdgeControllers.forEach(IEdgeController::dispose);
		
		// update visual of used edges
		for(Entry<IEdgeController,IEdgeContainer> e: edgeControllerToParent.entrySet()) {
			e.getKey().updateView(e.getValue());
		}
		
	}
	
	private static class ContainerUpdate {
		
		private final IEdgeContainer container;
		private final IContentUpdate<IEdgeController> update;
		
		public ContainerUpdate(final IEdgeContainer container, final EdgeUpdateContext updateContext) {
			container.getEdgeConentHandler().updateContent(updateContext);
			update = container.getEdgeConentHandler().getLastUpdate();
			this.container = container;
		}
		
		public IContentUpdate<IEdgeController> getUpdate() {
			return update;
		}
		
		public void runUpdate() {
			container.updateEdges();
		}
		
	}
}
