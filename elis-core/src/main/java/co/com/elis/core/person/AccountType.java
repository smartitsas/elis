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

package co.com.elis.core.person;

import lombok.Getter;

public enum AccountType {

    /**
     * 11 - CIVIL REGISTRATION
     */
    CIVIL_REGISTRATION("11"),
    /**
     * +
     * 12 - IDENTITY CARD
     */
    IDENTITY_CARD("12"),
    /**
     * 42 - FOREING IDENTIFICATION DOCUMENT
     */
    FOREING_IDENTIFICATION_DOCUMENT("42"),
    /**
     * 13 - CITIZENSHIP CARD
     */
    CITIZENSHIP_CARD("13"),
    /**
     * 21 - FOREING CARD
     */
    FOREING_CARD("21"),
    /**
     * 22 - CITIZENSHIP FOREING CARD
     */
    CITIZENSHIP_FOREING_CARD("22"),
    /**
     * 31 - NIT
     */
    NIT("31"),
    /**
     * 41 - PASSPORT
     */
    PASSPORT("41"),
    /**
     * R-00-PN - NATURAL PERSON NOT REQUIRED TO REGISTER IN THE RUT
     */
    NOT_REQUIRED_TO_REGISTER_IN_THE_RUT_PN("R-00-PN"),
    /**
     * 91 - NUIP
     */
    NUIP("91");

    @Getter
    private final String code;

    private AccountType(String code) {
        this.code = code;
    }

    public static AccountType fromCode(String code) {
        for (AccountType value : AccountType.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Code: " + code + " is not in the AccountType list");
    }
}
