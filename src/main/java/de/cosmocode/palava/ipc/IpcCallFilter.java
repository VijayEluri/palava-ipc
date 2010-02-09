/**
 * palava - a java-php-bridge
 * Copyright (C) 2007-2010  CosmoCode GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.cosmocode.palava.ipc;

import java.util.Map;

/**
 * A filter can be configured to get executed on specified call, 
 * usually requesting specified {@link IpcCommand}s and runs before, after or instead
 * of the requested command.
 *
 * @author Willi Schoenborn
 */
public interface IpcCallFilter {

    /**
     * Execute this filter. This may result in proceeding the given chain or in returning 
     * a probably cached content.
     * 
     * @param call the incoming call
     * @param command the command scheduled to process the call
     * @param chain the proceeding chain
     * @return the generated content
     * @throws IpcCallFilterException if filtering failed
     */
    Map<String, Object> filter(IpcCall call, IpcCommand command, IpcCallFilterChain chain) 
        throws IpcCallFilterException;
    
}
