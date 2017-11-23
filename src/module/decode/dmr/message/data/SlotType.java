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
package module.decode.dmr.message.data;

import bits.BinaryMessage;
import bits.CorrectedBinaryMessage;
import edac.Golay24;

public class SlotType
{
    private static final int[] MESSAGE_INDEXES = new int[]{122,123,124,125,126,127,128,129,130,131,180,181,182,183,184,
        185,186,187,188,189};

    //Note: extracted message bit index values are offset by +4 from the ICD specified index values
    private static final int[] COLOR_CODE = new int[]{4,5,6,7};
    private static final int[] DATA_TYPE = new int[]{8,9,10,11};

    private CorrectedBinaryMessage mMessage;
    private BinaryMessage mDecodedMessage;
    private boolean mCorrected;

    public SlotType(CorrectedBinaryMessage message)
    {
        mMessage = message;
    }

    /**
     * SlotType message extracted from the transmitted message and Golay(24) error detection and correction complete.
     *
     * Note: this message is extracted with 4 leading/padding bits plus the 20 message bits for an overall length of
     * 24 bits to support use of the existing Golay24 error correction utility.  All bit index values are incremented
     * by an offset of 4 to account for this frame offset.
     */
    private BinaryMessage getDecodedMessage()
    {
        if(mDecodedMessage == null)
        {
            mDecodedMessage = new BinaryMessage(24);

            for(int x = 0; x < MESSAGE_INDEXES.length; x++)
            {
                if(mMessage.get(MESSAGE_INDEXES[x]))
                {
                    mDecodedMessage.set(x + 4);
                }
            }

            mCorrected = Golay24.checkAndCorrect(mDecodedMessage);
        }

        return mDecodedMessage;
    }

    /**
     * Indicates if this message was successfully error detected and corrected
     */
    public boolean isValid()
    {
        return mCorrected;
    }

    /**
     * Color code for this timeslot.
     * @return color code: 0 - 15
     */
    public int getColorCode()
    {
        return getDecodedMessage().getInt(COLOR_CODE);
    }

    /**
     * Data Type for this data message
     */
    public DataType getDataType()
    {
        if(isValid())
        {
            return DataType.fromValue(getDecodedMessage().getInt(DATA_TYPE));
        }

        return DataType.UNKNOWN;
    }
}
