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

import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class IdentityDocument {

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_PERSON_DOC_ACCOUNT")
    private final String account;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_PERSON_DOC_TYPE")
    private final AccountType type;

    @Getter
    private final Long verificationDigit;

    @Getter
    private final List<String> additionalAccounts;
    
    
    /**
     * Creates a new IdentityDocument with the optional verificationDigit
     *
     * @param account NIT number, DNI number or account number as given by the
     * country registration service
     * @param verificationDigit optional verification digit as given by the
     * country registration service
     * @param type Type of account as given by the country registration service
     * @see AccountType
     */
    public IdentityDocument(String account, Long verificationDigit, AccountType type) {
        this.account = account;
        this.type = type;
        this.verificationDigit = verificationDigit;
        this.additionalAccounts = Collections.emptyList();
    }
    
    /**
     * Creates a new IdentityDocument with the optional verificationDigit
     *
     * @param account NIT number, DNI number or account number as given by the
     * country registration service
     * @param verificationDigit optional verification digit as given by the
     * country registration service
     * @param type Type of account as given by the country registration service
     * @param additionalAccounts AdditionalAccounts of this document
     * @see AccountType
     */
    public IdentityDocument(String account, Long verificationDigit, AccountType type, List<String> additionalAccounts) {
        this.account = account;
        this.type = type;
        this.verificationDigit = verificationDigit;
        this.additionalAccounts = additionalAccounts;
    }    
    

    /**
     * Creates a new IdentityDocument
     *
     * @param account NIT number, DNI number or account number as given by the
     * country registration service
     * @param type Type of account as given by the country registration service
     * @see AccountType
     */
    public IdentityDocument(String account, AccountType type) {
        this.account = account;
        this.type = type;
        this.verificationDigit = null;
        this.additionalAccounts = Collections.emptyList();
    }

}
