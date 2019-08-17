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

package co.com.elis.core.document;

import co.com.elis.core.person.ReceiverParty;
import co.com.elis.core.person.SupplierParty;
import co.com.elis.core.software.Software;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public class Header {

    @Valid
    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INV_SUPPLIER")
    private final SupplierParty supplierParty;

    @Valid
    @Getter
    @NotNull(message = "ELIS_CORE_VAL_SOFTWARE")
    private final Software software;

    @Valid
    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INV_CUSTOMER")
    private final ReceiverParty receiverParty;

    @Valid
    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INV_ISSUE_DATE")
    private final DocumentDate documentDate;

    @Valid
    @Getter
    @NotNull(message = "ELIS_CORE_VAL_DOC_NUMBER")
    private final DocumentNumber documentNumber;

    @Getter
    @Valid
    private ValidityPeriod validityPeriod;

    public Header(SupplierParty supplierParty, Software software, ReceiverParty receiverParty, DocumentDate invoiceDate, DocumentNumber documentNumber) {
        this.supplierParty = supplierParty;
        this.receiverParty = receiverParty;
        this.documentDate = invoiceDate;
        this.documentNumber = documentNumber;
        this.software = software;
    }

    public Header(SupplierParty supplierParty, Software software, ReceiverParty receiverParty, DocumentDate invoiceDate, DocumentNumber documentNumber, ValidityPeriod validityPeriod) {
        this.supplierParty = supplierParty;
        this.software = software;
        this.receiverParty = receiverParty;
        this.documentDate = invoiceDate;
        this.documentNumber = documentNumber;
        this.validityPeriod = validityPeriod;
    }

}
