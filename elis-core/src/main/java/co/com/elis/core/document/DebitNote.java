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

import co.com.elis.core.item.Discrepancy;
import co.com.elis.core.item.ItemList;
import co.com.elis.core.item.NoteItem;
import co.com.elis.core.tax.TaxTotalList;
import co.com.elis.core.util.ResourceInterpolator;
import co.com.elis.exception.ElisCoreException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class DebitNote extends NoteDocument {

    public DebitNote(Header header, TaxTotalList taxTotalList, MonetaryTotal monetaryTotal, ItemList<NoteItem> itemList, OtherRelatedData otherData, Discrepancy discrepancy) throws ElisCoreException {
        super(header, taxTotalList, monetaryTotal, itemList, otherData, discrepancy);
    }

    private Set<ConstraintViolation<DebitNote>> validate() {
        Validator validator = Validation.buildDefaultValidatorFactory().usingContext().messageInterpolator(new ResourceInterpolator()).getValidator();
        Set<ConstraintViolation<DebitNote>> results;

        results = validator.validate(this);
        return results;
    }

    @Override
    public void validateOrThrow() throws ElisCoreException {
        Set<ConstraintViolation<DebitNote>> violations = validate();

        if (!violations.isEmpty()) {
            ConstraintViolation<DebitNote> violation = violations.iterator().next();
            throw new ElisCoreException(violation.getPropertyPath().toString() + ": " + violation.getMessage());
        }

        getTaxTotalList().validate();
    }

    @Override
    public DocumentType getType() {
        return DocumentType.DEBIT_NOTE;
    }

}
