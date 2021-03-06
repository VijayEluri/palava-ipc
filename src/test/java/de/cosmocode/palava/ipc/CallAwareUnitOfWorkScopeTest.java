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

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.cosmocode.palava.scope.AbstractUnitOfWorkScopeTest;
import de.cosmocode.palava.scope.DelegateThreadLocalUnitOfWorkScopeModule;
import de.cosmocode.palava.scope.DestroyStrategyModule;

/**
 * Tests {@link ConnectionAwareUnitOfWorkScope}.
 *
 * @author Willi Schoenborn
 */
public final class CallAwareUnitOfWorkScopeTest extends AbstractUnitOfWorkScopeTest {

    @Override
    public Injector unit() {
        return Guice.createInjector(
            new CallAwareUnitOfWorkScopeModule(),
            new IpcScopeModule(),
            new DelegateThreadLocalUnitOfWorkScopeModule(),
            new DestroyStrategyModule()
        );
    }

}
