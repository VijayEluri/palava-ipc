package de.cosmocode.palava.ipc;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

/**
 * Static utility class for {@link IpcCallFilter}s and {@link IpcCallFilterChain}.
 *
 * @author Willi Schoenborn
 */
public final class Filtering {

    private static final Logger LOG = LoggerFactory.getLogger(Filtering.class);

    private Filtering() {
        
    }
    
    /**
     * Composes a {@link Predicate} and an {@link IpcCallFilter} which executes the specified
     * filter if and only if the specified predicate returns true for a given command.
     * 
     * @param predicate the predicate which decides whether the specified filter should be applied
     *        to an incoming call.
     * @param filter the backed filter
     * @return a composed filter which skips filter execution if the specified predicate does not apply
     */
    public static IpcCallFilter compose(Predicate<? super IpcCommand> predicate, IpcCallFilter filter) {
        return new ComposedIpcCallFilterDefinition(predicate, filter);
    }
    
    /**
     * Composes a {@link Predicate} and an {@link IpcCallFilter}.
     *
     * @author Willi Schoenborn
     */
    private static final class ComposedIpcCallFilterDefinition implements IpcCallFilter {

        private final Predicate<? super IpcCommand> predicate;
        
        private final IpcCallFilter filter;
        
        public ComposedIpcCallFilterDefinition(Predicate<? super IpcCommand> predicate, IpcCallFilter filter) {
            this.predicate = Preconditions.checkNotNull(predicate, "Predicate");
            this.filter = Preconditions.checkNotNull(filter, "Filter");
        }
        
        @Override
        public Map<String, Object> filter(IpcCall call, IpcCallFilterChain chain) throws IpcCallFilterException {
            if (predicate.apply(call.command())) {
                LOG.debug("Filtering {} using {}", call, filter);
                return filter.filter(call, chain);
            } else {
                LOG.debug("Skipping filter executing of {} for {}", filter, call);
                return chain.filter(call);
            }
        }
        
        @Override
        public String toString() {
            return String.format("Filtering.compose(%s, %s)", filter, predicate);
        }
        
    }
    
}
