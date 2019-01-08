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

import java.time.LocalDate;
import lombok.Getter;

public class AffectedInvoice {

    @Getter
    private final DocumentNumber documentNumber;

    @Getter
    private final LocalDate issueDate;

    @Getter
    private final String cufe;

    /**
     * Creates an affected invoice to be used inside a Note using a document
     * Number
     *
     * @param documentNumber Number (prefix, consecutive) of the affected
     * invoice
     * @param issueDate IssueDate of the affected invoice
     * @param cufe CUFE of the affected invoice
     * @see DocumentNumber
     */
    public AffectedInvoice(DocumentNumber documentNumber, LocalDate issueDate, String cufe) {
        this.documentNumber = documentNumber;
        this.issueDate = issueDate;
        this.cufe = cufe;
    }

    /**
     * Creates an affected invoice to be used inside a Note
     *
     * @param prefix Prefix of the affected invoice
     * @param consecutive Consecutive of the affected invoice
     * @param issueDate IssueDate of the affected invoice
     * @param cufe CUFE of the affected invoice
     * @see DocumentNumber
     */
    public AffectedInvoice(String prefix, Long consecutive, LocalDate issueDate, String cufe) {
        this(new DocumentNumber(prefix, consecutive), issueDate, cufe);
    }

}
