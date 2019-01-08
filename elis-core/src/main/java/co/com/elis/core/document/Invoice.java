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

import co.com.elis.core.util.ResourceInterpolator;
import co.com.elis.core.tax.TaxTotalList;
import co.com.elis.core.item.ItemList;
import co.com.elis.core.tax.TaxTotal;
import co.com.elis.core.tax.TaxType;
import co.com.elis.core.util.ElisEncoder;
import co.com.elis.exception.ElisCoreException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import co.com.elis.core.document.validation.ExportationValidationGroup;
import co.com.elis.core.document.validation.InvoicingRangeValid;
import co.com.elis.core.item.InvoiceItem;
import javax.validation.groups.Default;

@InvoicingRangeValid
public class Invoice extends Document<InvoiceItem> {

    @Valid
    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INV_RANGE")
    private final InvoicingRange invoicingRange;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INV_TYPE")
    private final InvoiceType invoiceType;

    @Getter
    private String cufe;

    /**
     * Constructor used to create an invoice with already calculated data
     *
     * @param invoiceType type of invoice
     * @param header header data
     * @param itemList List of items (detail) of the invoice
     * @param legalMonetaryTotal Totals of invoice
     * @param allowanceCharge if there is a charge or discount should be
     * informed here
     * @param otherData Additional related data
     */
    Invoice(InvoiceType invoiceType, Header header, InvoicingRange invoicingRange, ItemList<InvoiceItem> itemList, TaxTotalList taxTotalList, MonetaryTotal legalMonetaryTotal, OtherRelatedData otherData) {
        super(header, taxTotalList, legalMonetaryTotal, itemList, otherData);
        this.invoiceType = invoiceType;
        this.invoicingRange = invoicingRange;
    }

    private Set<ConstraintViolation<Invoice>> validate() {
        Validator validator = Validation.buildDefaultValidatorFactory().usingContext().messageInterpolator(new ResourceInterpolator()).getValidator();
        Set<ConstraintViolation<Invoice>> results;

        if (invoiceType == InvoiceType.EXPORTATION) {
            results = validator.validate(this, Default.class, ExportationValidationGroup.class);
        } else {
            results = validator.validate(this);
        }
        return results;
    }

    void validateOrThrow() throws ElisCoreException {
        Set<ConstraintViolation<Invoice>> violations = validate();

        if (!violations.isEmpty()) {
            ConstraintViolation<Invoice> violation = violations.iterator().next();
            throw new ElisCoreException(violation.getPropertyPath().toString() + ": " + violation.getMessage());
        }

        getTaxTotalList().validate();
    }

    void calculateCufe() throws ElisCoreException {

        StringBuilder builder = new StringBuilder(getHeader().getDocumentNumber().getFullId());
        builder.append(getHeader().getInvoiceDate().toFormattedDateTime());

        builder.append(getLegalMonetaryTotal().toPlainString());

        TaxTotal taxTotal = getTaxTotalList().getByType(TaxType.IVA).orElseThrow(() -> new ElisCoreException("In order to calculate CUFE total IVA must be present"));
        builder.append(taxTotal.toPlainString());

        taxTotal = getTaxTotalList().getByType(TaxType.ICA).orElseThrow(() -> new ElisCoreException("In order to calculate CUFE ICA must be present"));
        builder.append(taxTotal.toPlainString());

        taxTotal = getTaxTotalList().getByType(TaxType.CONSUMPTION).orElseThrow(() -> new ElisCoreException("In order to calculate CUFE CONSUMPTION must be present"));
        builder.append(taxTotal.toPlainString())
                .append(getHeader().getReceiverParty().getIdentityDocument().getType().getCode())
                .append(getHeader().getReceiverParty().getIdentityDocument().getAccount());

        builder.append(invoicingRange.getTechnicalKey());
        String consolidatedData = builder.toString();
        cufe = ElisEncoder.applyHash(consolidatedData);
    }

    void setCufe(String cufe) {
        this.cufe = cufe;
    }

    @Override
    public DocumentType getType() {
        return DocumentType.INVOICE;
    }

    public boolean isExportation() {
        return invoiceType == InvoiceType.EXPORTATION;
    }

    public boolean isTranscription() {
        return invoiceType == InvoiceType.TRANSCRIPTION;
    }

}
