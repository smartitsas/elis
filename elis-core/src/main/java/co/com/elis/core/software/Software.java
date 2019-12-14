/** ********************************************************************************************
 *
 * ELectronic Invoicing System Community Core library
 * Copyright (C) 2017-2018. Smart IT S.A.S. <smartit.net.co>
 *
 * This file is licensed under the GNU Affero General Public License version 3
 * as published by the Free Software Foundation.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 * You should have received a copy of the GNU Affero General Public License. If
 * not, please visit <http://www.gnu.org/licenses/agpl-3.0.html>.
 *
 *********************************************************************************************
 */
package co.com.elis.core.software;

import co.com.elis.core.document.CalculatedCreditNoteBuilder;
import co.com.elis.core.document.CalculatedDebitNoteBuilder;
import co.com.elis.core.document.PopulatedCreditNoteBuilder;
import co.com.elis.core.document.CalculatedInvoiceBuilder;
import co.com.elis.core.document.DocumentNumber;
import co.com.elis.core.document.InvoicingRange;
import co.com.elis.core.document.InvoicingRange.InvoiceRangeBuilder;
import co.com.elis.core.document.PopulatedDebitNoteBuilder;
import co.com.elis.core.document.PopulatedInvoiceBuilder;
import co.com.elis.core.item.ItemCalculationBuilder;
import co.com.elis.core.item.ItemPopulationBuilder;
import co.com.elis.core.person.PersonBuilder;
import co.com.elis.exception.ElisCoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import lombok.Getter;

@Getter
public class Software {

    @NotNull(message = "ELIS_CORE_VAL_SOFTWARE_ID")
    private final String id;

    @NotNull(message = "ELIS_CORE_VAL_SOFTWARE_NIT")
    private final Long nit;

    @NotNull(message = "ELIS_CORE_VAL_SOFTWARE_NAME")
    private final String name;

    private final String pin;

    private final Environment environment;

    /**
     * Creates a new software Object
     *
     * @param id Id of the software as registered in DIAN
     * @param nit NIT of the registree company
     * @param name Name of the software
     * @param pin PIN of the software as registered in DIAN
     */
    public Software(String id, Long nit, String name, String pin, Environment environment) {
        this.id = id;
        this.nit = nit;
        this.pin = pin;
        this.name = name;
        this.environment = environment;
    }

    /**
     * Creates a new software Object
     *
     * @param id Id of the software as registered in DIAN
     * @param nit NIT of the registree company
     */
    public Software(String id, Long nit, Environment environment) {
        this.id = id;
        this.nit = nit;
        this.name = null;
        this.pin = null;
        this.environment = environment;
    }

    /**
     * Creates an invoice range builder configured for this software. Values to
     * build an invoice range must be the same registered in DIAN muisca portal.
     * all fields are mandatory.
     *
     * @return An invoice builder asociated to this software
     */
    public InvoiceRangeBuilder createInvoicingRangeAs() {
        return new InvoicingRange.InvoiceRangeBuilder();
    }

    public CalculatedInvoiceBuilder calculateInvoiceAs() {
        return new CalculatedInvoiceBuilder(this);
    }

    /**
     * Creates an InvoiceBuilder to generate an invoice by assigning all of it's
     * fields
     *
     * @return
     */
    public PopulatedInvoiceBuilder populateInvoiceAs() {
        return new PopulatedInvoiceBuilder(this);
    }

    /**
     * Creates a CreditNoteBuilder to generate a creditNote by assigning all of
     * it's fields
     *
     * @return
     */
    public PopulatedCreditNoteBuilder populateCreditNoteAs() {
        return new PopulatedCreditNoteBuilder(this);
    }

    /**
     * Creates a CreditNoteBuilder to generate a creditNote by calculating based
     * on the item
     *
     * @return
     */
    public CalculatedCreditNoteBuilder calculateCreditNoteAs() {
        return new CalculatedCreditNoteBuilder(this);
    }

    /**
     * Creates a CreditNoteBuilder to generate a creditNote by assigning all of
     * it's fields
     *
     * @return
     */
    public PopulatedDebitNoteBuilder populateDebitNoteAs() {
        return new PopulatedDebitNoteBuilder(this);
    }

    /**
     * Creates a CreditNoteBuilder to generate a creditNote by calculating based
     * on the item
     *
     * @return
     */
    public CalculatedDebitNoteBuilder calculateDebitNoteAs() {
        return new CalculatedDebitNoteBuilder(this);
    }

    /**
     * Creates an item builder that allows to CALCULATE an item using minimum
     * data.
     *
     * @return Item builder
     * @see ItemCalculationBuilder
     *
     * public ItemCalculationBuilder calculateItemAs() { return new
     * ItemCalculationBuilder(); }
     */
    /**
     * Creates an item builder that allows to POPULATE an item by settng all
     * data fields.
     *
     * @return Item builder
     * @see ItemCalculationBuilder
     */
    public ItemPopulationBuilder populateItemAs() {
        return new ItemPopulationBuilder();
    }

    public PersonBuilder getPersonBuilder() {
        return new PersonBuilder();
    }

    public String calculateSecurityCode(DocumentNumber documentNumber) throws ElisCoreException {
        try {
            String seed = id + pin + documentNumber.getFullId();
            MessageDigest digester = MessageDigest.getInstance("SHA-384");

            return DatatypeConverter.printHexBinary(digester.digest(seed.getBytes())).toLowerCase();

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Software.class.getName()).log(Level.SEVERE, null, ex);
            throw new ElisCoreException("Error obtaining hashing function: SHA-384 for security code.", ex);
        }
    }

}
