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

import de.cosmocode.collections.utility.UtilityMap;

/**
 * Argument map providing useful methods for type checking
 * and converting.
 *
 * @author Willi Schoenborn
 * @author Tobias Sarnowski
 */
public interface IpcArguments extends UtilityMap<String, Object> {

    /**
     * Checks whether all specified keys exist in these arguments.
     *
     * @param keys the required keys
     * @throws IpcArgumentsMissingException if any key is missing
     */
    void require(String... keys) throws IpcArgumentsMissingException;

}
