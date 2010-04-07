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

/**
 * Static constant holder class for context specific key names.
 *
 * @author Willi Schoenborn
 */
public final class Context {
    
    public static final String PREFIX = "context.";

    public static final String LOCALE = PREFIX + "locale";
    
    public static final String REQUEST_URI = PREFIX + "requestUri";
    
    public static final String REFERER = PREFIX + "referer";
    
    public static final String REMOTE_ADDRESS = PREFIX + "remoteAddress";
    
    public static final String USER_AGENT = PREFIX + "userAgent";

    private Context() {
        
    }
    
}
