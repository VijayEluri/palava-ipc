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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

/**
 * Tests {@link IpcCallFilterChain} implementations.
 *
 * @author Willi Schoenborn
 */
public abstract class AbstractIpcCallFilterChainTest {

    /**
     * Provides a unit under testing.
     * 
     * @param filters the filter the new chain should use
     * @param chain the proceeding chain
     * @return a new {@link IpcCallFilterChain}
     */
    protected abstract IpcCallFilterChain unit(List<IpcCallFilter> filters, IpcCallFilterChain chain);
    
    /**
     * Tests {@link IpcCallFilterChain#filter(IpcCall, IpcCommand)} with an empty
     * list of filters.
     * @throws IpcCommandExecutionException if execution failed, should not happen
     */
    @Test
    public void empty() throws IpcCommandExecutionException {
        final List<IpcCallFilter> filters = Collections.emptyList();
        
        final IpcCallFilterChain proceeding = EasyMock.createMock("proceeding", IpcCallFilterChain.class);
        
        final IpcCall call = EasyMock.createMock("call", IpcCall.class);
        final IpcCommand command = EasyMock.createMock("command", IpcCommand.class);
        
        final IpcCallFilterChain unit = unit(filters, proceeding);
        final Map<String, Object> dummyResult = Maps.newHashMap();
        
        EasyMock.expect(proceeding.filter(call, command)).andReturn(dummyResult);
        EasyMock.replay(proceeding, call, command);
        
        final Map<String, Object> result = unit.filter(call, command);
        Assert.assertSame(dummyResult, result);
        EasyMock.verify(proceeding, call, command);
    }
    
    /**
     * Tests {@link IpcCallFilterChain#filter(IpcCall, IpcCommand)} with one filter.
     * 
     * @throws IpcCommandExecutionException if execution failed, should not happen
     */
    @Test
    public void one() throws IpcCommandExecutionException {
        final IpcCallFilter filter = EasyMock.createMock("filter", IpcCallFilter.class);
        final ImmutableList<IpcCallFilter> filters = ImmutableList.of(filter);

        final IpcCallFilterChain proceeding = EasyMock.createMock("proceeding", IpcCallFilterChain.class);
        
        final IpcCall call = EasyMock.createMock("call", IpcCall.class);
        final IpcCommand command = EasyMock.createMock("command", IpcCommand.class);
        
        final IpcCallFilterChain unit = unit(filters, proceeding);
        final Map<String, Object> dummyResult = Maps.newHashMap();

        EasyMock.expect(filter.filter(call, command, unit)).andReturn(dummyResult);
        EasyMock.replay(filter, proceeding, call, command);

        final Map<String, Object> result = unit.filter(call, command);
        Assert.assertSame(dummyResult, result);
        EasyMock.verify(filter, proceeding, call, command);
    }
    
    /**
     * Tests {@link IpcCallFilterChain#filter(IpcCall, IpcCommand)} with three filters.
     * 
     * @throws IpcCommandExecutionException if execution failed, should not happen
     */
    @Test
    public void three() throws IpcCommandExecutionException {
        final IpcCallFilter filter1 = EasyMock.createMock("filter1", IpcCallFilter.class);
        final IpcCallFilter filter2 = EasyMock.createMock("filter2", IpcCallFilter.class);
        final IpcCallFilter filter3 = EasyMock.createMock("filter3", IpcCallFilter.class);
        final ImmutableList<IpcCallFilter> filters = ImmutableList.of(filter1, filter2, filter3);
        
        final IpcCallFilterChain proceeding = EasyMock.createMock("proceeding", IpcCallFilterChain.class);
        
        final IpcCall call = EasyMock.createMock("call", IpcCall.class);
        final IpcCommand command = EasyMock.createMock("command", IpcCommand.class);
        
        final IpcCallFilterChain unit = unit(filters, proceeding);
        final Map<String, Object> dummyResult = Maps.newHashMap();
        
        EasyMock.expect(filter1.filter(call, command, unit)).andAnswer(new IAnswer<Map<String, Object>>() {
            
            @Override
            public Map<String, Object> answer() throws IpcCommandExecutionException {
                return unit.filter(call, command);
            }
            
        });
        
        EasyMock.expect(filter2.filter(call, command, unit)).andAnswer(new IAnswer<Map<String, Object>>() {
            
            @Override
            public Map<String, Object> answer() throws IpcCommandExecutionException {
                return unit.filter(call, command);
            }
            
        });
        
        EasyMock.expect(filter3.filter(call, command, unit)).andAnswer(new IAnswer<Map<String, Object>>() {
            
            @Override
            public Map<String, Object> answer() throws IpcCommandExecutionException {
                return unit.filter(call, command);
            }
            
        });
        
        EasyMock.expect(proceeding.filter(call, command)).andReturn(dummyResult);
        EasyMock.replay(filter1, filter2, filter3, proceeding, call, command);
        
        final Map<String, Object> result = unit.filter(call, command);
        Assert.assertSame(dummyResult, result);
        EasyMock.verify(filter1, filter2, filter3, proceeding, call, command);
    }
    
    /**
     * Tests {@link IpcCallFilterChain#filter(IpcCall, IpcCommand)} with three filters
     * of which the first intercepts the call.
     * 
     * @throws IpcCommandExecutionException if filtering failed, should not happen
     */
    @Test
    public void first() throws IpcCommandExecutionException {
        final IpcCallFilter filter1 = EasyMock.createMock("filter1", IpcCallFilter.class);
        final IpcCallFilter filter2 = EasyMock.createMock("filter2", IpcCallFilter.class);
        final IpcCallFilter filter3 = EasyMock.createMock("filter3", IpcCallFilter.class);
        final ImmutableList<IpcCallFilter> filters = ImmutableList.of(filter1, filter2, filter3);

        final IpcCallFilterChain proceeding = EasyMock.createMock("proceeding", IpcCallFilterChain.class);

        final IpcCall call = EasyMock.createMock("call", IpcCall.class);
        final IpcCommand command = EasyMock.createMock("command", IpcCommand.class);
        
        final IpcCallFilterChain unit = unit(filters, proceeding);
        final Map<String, Object> dummyResult = Maps.newHashMap();
        
        EasyMock.expect(filter1.filter(call, command, unit)).andReturn(dummyResult);
        EasyMock.replay(filter1, filter2, filter3, proceeding, call, command);

        final Map<String, Object> result = unit.filter(call, command);
        Assert.assertSame(dummyResult, result);
        EasyMock.verify(filter1, filter2, filter3, proceeding, call, command);
    }

    /**
     * Tests {@link IpcCallFilterChain#filter(IpcCall, IpcCommand)} with three filters
     * of which the last intercepts the call.
     * 
     * @throws IpcCommandExecutionException if filtering failed, should not happen
     */
    @Test
    public void last() throws IpcCommandExecutionException {
        final IpcCallFilter filter1 = EasyMock.createMock("filter1", IpcCallFilter.class);
        final IpcCallFilter filter2 = EasyMock.createMock("filter2", IpcCallFilter.class);
        final IpcCallFilter filter3 = EasyMock.createMock("filter3", IpcCallFilter.class);
        final ImmutableList<IpcCallFilter> filters = ImmutableList.of(filter1, filter2, filter3);

        final IpcCallFilterChain proceeding = EasyMock.createMock("proceeding", IpcCallFilterChain.class);

        final IpcCall call = EasyMock.createMock("call", IpcCall.class);
        final IpcCommand command = EasyMock.createMock("command", IpcCommand.class);
        
        final IpcCallFilterChain unit = unit(filters, proceeding);
        final Map<String, Object> dummyResult = Maps.newHashMap();
        
        EasyMock.expect(filter1.filter(call, command, unit)).andReturn(dummyResult);
        EasyMock.replay(filter1, filter2, filter3, proceeding, call, command);

        final Map<String, Object> result = unit.filter(call, command);
        Assert.assertSame(dummyResult, result);
        EasyMock.verify(filter1, filter2, filter3, proceeding, call, command);
    }
    
    /**
     * Tests {@link IpcCallFilterChain#filter(IpcCall, IpcCommand)} with a {@link RuntimeException}.
     * 
     * @throws IpcCommandExecutionException if filtering failed, should not happen
     */
    @Test(expected = RuntimeException.class)
    public void runtime() throws IpcCommandExecutionException {
        final IpcCallFilter filter = EasyMock.createMock("filter", IpcCallFilter.class);
        final ImmutableList<IpcCallFilter> filters = ImmutableList.of(filter);
        
        final IpcCallFilterChain proceeding = EasyMock.createMock("proceeding", IpcCallFilterChain.class);
        
        final IpcCall call = EasyMock.createMock("call", IpcCall.class);
        final IpcCommand command = EasyMock.createMock("command", IpcCommand.class);

        final IpcCallFilterChain unit = unit(filters, proceeding);
        
        EasyMock.expect(filter.filter(call, command, unit)).andThrow(new RuntimeException());
        EasyMock.replay(filter, proceeding, call, command);
        
        unit.filter(call, command);
        EasyMock.verify(filter, proceeding, call, command);
    }
    
    /**
     * Tests {@link IpcCallFilterChain#filter(IpcCall, IpcCommand)} with an {@link IpcCommandExecutionException}.
     * 
     * @throws IpcCommandExecutionException if filtering failed, should not happen
     */
    @Test(expected = IpcCommandExecutionException.class)
    public void checked() throws IpcCommandExecutionException {
        final IpcCallFilter filter = EasyMock.createMock("filter", IpcCallFilter.class);
        final ImmutableList<IpcCallFilter> filters = ImmutableList.of(filter);
        
        final IpcCallFilterChain proceeding = EasyMock.createMock("proceeding", IpcCallFilterChain.class);
        
        final IpcCall call = EasyMock.createMock("call", IpcCall.class);
        final IpcCommand command = EasyMock.createMock("command", IpcCommand.class);

        final IpcCallFilterChain unit = unit(filters, proceeding);
        
        final IpcCommandExecutionException exception = new IpcCallFilterException(
            new IllegalStateException("Should happen")
        );
        EasyMock.expect(filter.filter(call, command, unit)).andThrow(exception);
        EasyMock.replay(filter, proceeding, call, command);
        
        unit.filter(call, command);
        EasyMock.verify(filter, proceeding, call, command);
    }
    
}
