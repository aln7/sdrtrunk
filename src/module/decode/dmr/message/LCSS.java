/*******************************************************************************
 * sdrtrunk
 * Copyright (C) 2014-2017 Dennis Sheirer
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 ******************************************************************************/
package module.decode.dmr.message;

public enum LCSS
{
    /**
     * Link Control(LC) Single Fragment -or- CSBK Signalling First Fragment
     */
    SINGLE_FRAGMENT,

    /**
     * LC First Fragment
     */
    FIRST_FRAGMENT,

    /**
     * LC or CSBK Last Fragment
     */
    LAST_FRAGMENT,

    /**
     * LC or CSBK Continuation Fragment
     */
    CONTINUATION_FRAGMENT,

    /**
     * Unknown Fragment
     */
    UNKNOWN;

    public static LCSS fromValue(int value)
    {
        if(0 <= value && value <= 4)
        {
            return LCSS.values()[value];
        }

        return UNKNOWN;
    }
}
