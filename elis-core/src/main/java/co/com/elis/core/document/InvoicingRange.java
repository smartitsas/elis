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
package co.com.elis.core.document;

import co.com.elis.core.util.ResourceInterpolator;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;

public class InvoicingRange {

    @Getter
    @Size(max = 4, message = "ELIS_CORE_VAL_PREFIX_SIZE")
    @Pattern(regexp = "[A-Za-z0-9]+", message = "ELIS_CORE_VAL_PREFIX")
    private final String prefix;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INVOICING_RANGE_TECH_KEY")
    private final String technicalKey;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INVOICING_RANGE_CONSECUTIVE_FROM")
    private final Long fromConsecutive;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INVOICING_RANGE_CONSECUTIVE_TO")
    private final Long toConsecutive;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INVOICING_RANGE_RESOLUTION")
    private final BigDecimal authorizationNumber;

    @Valid
    @Getter
    @NotNull(message = "ELIS_CORE_VAL_INV_INVOICING_PERIOD")
    private final InvoicingRangePeriod authorizationPeriod;

    private InvoicingRange(String technicalKey, BigDecimal authorizationNumber, String prefix, Long fromConsecutive, Long toConsecutive, InvoicingRangePeriod invoicingPeriod) {
        this.technicalKey = technicalKey;
        this.fromConsecutive = fromConsecutive;
        this.toConsecutive = toConsecutive;
        this.authorizationNumber = authorizationNumber;
        this.prefix = prefix;
        this.authorizationPeriod = invoicingPeriod;
    }

    public static class InvoiceRangeBuilder {

        private String prefix;
        private Long fromConsecutive;
        private Long toConsecutive;
        private BigDecimal authorizationNumber;
        private String technicalKey;
        private InvoicingRangePeriod invoicingPeriod;

        /**
         * Technical key given by DIAN prior registration (located in the muisca
         * portal).
         *
         * Details can be found in "Guia del facturador electronico" or in
         * "Anexo 6 Servicio de Consulta Rangos Numeracion Facturacion" if you'd
         * like to use the DIAN webservice
         *
         * @param technicalKey Value corresponding to the technical key of the
         * invoice range to build
         * @return Builder to chain object construction
         */
        public InvoiceRangeBuilder withTechnicalKey(String technicalKey) {
            this.technicalKey = technicalKey;
            return this;
        }

        /**
         * Resolution key given by DIAN prior registration (located in the
         * muisca portal), same as Resolution Number.
         *
         * Details can be found in "Guia del facturador electronico"
         *
         * @param authorizationNumber Value corresponding to the resolution
         * number of the invoice range to build
         * @return Builder to chain object construction
         */
        public InvoiceRangeBuilder withAuthorizationNumber(BigDecimal authorizationNumber) {
            this.authorizationNumber = authorizationNumber;
            return this;
        }

        /**
         * Prefix registered in DIAN (located in the muisca portal).
         *
         * Details can be found in "Guia del facturador electronico"
         *
         * @param prefix Value corresponding to the prefix of the invoice range
         * to build
         * @return Builder to chain object construction
         */
        public InvoiceRangeBuilder withPrefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        /**
         * Consecutive registered in DIAN (located in the muisca portal).
         * Details can be found in "Guia del facturador electronico"
         *
         * @param fromNumber Value corresponding to the starting id (of the
         * first invoice) within the range
         * @param toNumber Value corresponding to the ending id (of the last
         * invoice) within the range
         * @return Builder to chain object construction
         */
        public InvoiceRangeBuilder withConsecutiveRange(Long fromNumber, Long toNumber) {
            this.fromConsecutive = fromNumber;
            this.toConsecutive = toNumber;
            return this;
        }

        /**
         * Invoicing period in DIAN (located in the muisca portal).
         *
         * Details can be found in "Guia del facturador electronico"
         *
         * @param invoicingPeriod Value corresponding to the valid invoicing
         * period registered in DIAN for the current range
         * @return Builder to chain object construction
         */
        public InvoiceRangeBuilder withInvoicingPeriod(InvoicingRangePeriod invoicingPeriod) {
            this.invoicingPeriod = invoicingPeriod;
            return this;
        }

        /**
         * Builds an InvoicingRange object using the arguments used in the
         * builder
         *
         * @return
         * @throws ElisCoreException
         */
        public InvoicingRange build() throws ElisCoreException {
            InvoicingRange invoicinRange = new InvoicingRange(technicalKey, authorizationNumber, prefix, fromConsecutive, toConsecutive, invoicingPeriod);

            validateOrThrow(invoicinRange);
            return invoicinRange;
        }

        private void validateOrThrow(InvoicingRange invoicinRange) throws ElisCoreException {
            Validator validator = Validation.buildDefaultValidatorFactory().usingContext().messageInterpolator(new ResourceInterpolator()).getValidator();
            Set<ConstraintViolation<InvoicingRange>> results = validator.validate(invoicinRange);

            if (!results.isEmpty()) {
                ConstraintViolation<InvoicingRange> violation = results.iterator().next();
                throw new ElisCoreException(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }

        }

    }

}
