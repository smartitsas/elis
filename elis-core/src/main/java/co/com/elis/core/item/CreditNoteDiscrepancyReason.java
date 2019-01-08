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

public enum CreditNoteDiscrepancyReason implements DiscrepancyReason {

    /**
     * 1- RETUNR OF PRODUCT OR NOT TAKING A SERVICE
     */
    RETURN_OF_PRODUCT_OR_SERVICE(1),
    /**
     * 2- INVOICE VOIDING
     */
    INVOICE_VOIDING(2),
    /**
     * 3- TOTAL REDUCTION APPLIED
     */
    TOTAL_REDUCTION_APPLIED(3),
    /**
     * 4- TOTAL DISCOUNT APPLIED
     */
    TOTAL_DISCOUNT_APPLIED(4),
    /**
     * 5- RECISSION FOR LACK OF REQUIREMENTS
     */
    RESCISSION_NULLITY_LACK_REQUIREMENTS(5),
    /**
     * 6- OTHER REASON
     */
    OTHERS(6);

    @Getter
    private final Integer code;

    private CreditNoteDiscrepancyReason(int code) {
        this.code = code;
    }

    public static CreditNoteDiscrepancyReason fromCode(Integer code) {
        for (CreditNoteDiscrepancyReason value : CreditNoteDiscrepancyReason.values()) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Code " + code + " is not in the Debit Note discrepancy reasons ");
    }

}
