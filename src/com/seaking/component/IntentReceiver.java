/* SAAF: A static analyzer for APK files.
 * Copyright (C) 2013  syssec.rub.de
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.seaking.component;

import java.util.Set;

import com.seaking.mapper.IntentFilterInterface;


/**
 * An element in the AndroidManifest.xml, that may define Intent-Filters
 * 
 * @author Tilman Bender <tilman.bender@rub.de>
 *
 */
public interface IntentReceiver {
	
	public Set<IntentFilterInterface> getIntentFilters();
	
	public void addIntentFilter(IntentFilterInterface filter);

}
