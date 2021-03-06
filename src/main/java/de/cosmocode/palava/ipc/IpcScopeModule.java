/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.palava.ipc;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;

/**
 * Binds the three custom scopes.
 * <ol>
 *   <li>{@link IpcCallScoped}</li>
 *   <li>{@link IpcConnectionScoped}</li>
 *   <li>{@link IpcSessionScoped}</li>
 * </ol>
 * 
 * And in addition binds the corresponding providers for
 * {@link IpcCall}, {@link IpcConnection} and {@link IpcSession}.
 *
 * @author Willi Schoenborn
 * @author Tobias Sarnowski
 */
public final class IpcScopeModule implements Module {

    @Override
    public void configure(Binder binder) {
        final IpcCallScope callScope = new ThreadLocalIpcCallScope();
        binder.bindScope(IpcCallScoped.class, callScope);
        binder.bind(IpcCallScope.class).toInstance(callScope);
        binder.bind(IpcCall.class).annotatedWith(Current.class).toProvider(IpcCallScope.class);

        final IpcConnectionScope connectionScope = new DefaultIpcConnectionScope();
        binder.bindScope(IpcConnectionScoped.class, connectionScope);
        binder.bind(IpcConnectionScope.class).toInstance(connectionScope);

        final IpcSessionScope sessionScope = new IpcSessionScope();
        binder.bindScope(IpcSessionScoped.class, sessionScope);
        binder.bind(IpcSessionScope.class).toInstance(sessionScope);
    }
    
    /**
     * Provides the current connection.
     *
     * @since 2.0
     * @param call the current call
     * @return the current connection
     */
    @Provides
    @Current
    IpcConnection getCurrentConnection(@Current IpcCall call) {
        return call.getConnection();
    }

    /**
     * Provides the current session.
     *
     * @since 2.0
     * @param connection the current connection
     * @return the current session
     */
    @Provides
    @Current
    IpcSession getCurrentSession(@Current IpcConnection connection) {
        return connection.getSession();
    }
    
    /**
     * Provides the current arguments provided by {@link IpcCall#getArguments()} of the
     * {@link Current} {@link IpcCall}.
     *
     * @since 1.9.1
     * @param call the current call
     * @return the current arguments
     */
    @Provides
    @Current
    IpcArguments getCurrentArguments(@Current IpcCall call) {
        return call.getArguments();
    }
    
}
