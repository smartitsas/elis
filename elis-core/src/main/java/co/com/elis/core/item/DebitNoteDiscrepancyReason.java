/**********************************************************************************************
 *
 * ELectronic Invoicing System Community Core library
 * Copyright (C) 2017-2018. Smart IT S.A.S. <smartit.net.co>
 *
 * This file is licensed under the GNU Affero General Public License version 3 as published by
 * the Free Software Foundation.
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * You should have received a copy of the GNU Affero General Public License.  If not, please
 * visit <http://www.gnu.org/licenses/agpl-3.0.html>.
 *
 **********************************************************************************************/

package co.com.elis.core.item;

import java.util.Objects;
import lombok.Getter;

public enum DebitNoteDiscrepancyReason implements DiscrepancyReason {

    /**
     * 1- INTERESTS
     */
    INTERESTS(1),
    /**
     * 2- EXPENSES TO CARGE
     */
    EXPENSES_TO_CHARGE(2),
    /**
     * 3- CHANGE OF VALUE
     */
    CHANGE_OF_VALUE(3);

    @Getter
    private final Integer code;

    private DebitNoteDiscrepancyReason(int code) {
        this.code = code;
    }

    public static DebitNoteDiscrepancyReason fromCode(Integer code) {
        for (DebitNoteDiscrepancyReason value : DebitNoteDiscrepancyReason.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Code " + code + " is not in the Debit Note discrepancy reasons ");
    }

}
