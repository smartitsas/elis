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

import co.com.elis.core.item.Item;
import co.com.elis.core.item.ItemList;
import co.com.elis.core.tax.TaxTotalList;
import co.com.elis.exception.ElisCoreException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;

public abstract class Document<I extends Item> {

    @Getter
    @Valid
    @NotNull(message = "ELIS_CORE_VAL_INV_HEADER")
    private final Header header;

    @Getter
    @Valid
    private final OtherRelatedData otherData;

    @Getter
    @Valid
    @NotNull(message = "ELIS_CORE_VAL_INV_TAXTOTAL")
    private final TaxTotalList taxTotalList;

    @Getter
    @Valid
    @NotNull(message = "ELIS_CORE_VAL_INV_TOTAL")
    private final MonetaryTotal legalMonetaryTotal;

    @Getter
    @Valid
    @NotNull(message = "ELIS_CORE_VAL_INV_ITEMS")
    private final ItemList<I> itemList;

    @Getter
    @NotNull(message = "ELIS_CORE_VAL_SOFTWARE_SECURITY_CODE")
    private final String securityCode;

    public Document(Header header, TaxTotalList taxTotalList, MonetaryTotal monetaryTotal, ItemList<I> itemList, OtherRelatedData otherData) throws ElisCoreException {
        this.header = header;
        this.taxTotalList = taxTotalList;
        this.legalMonetaryTotal = monetaryTotal;
        this.otherData = otherData;
        this.itemList = itemList;
        this.securityCode = header.getSoftware().calculateSecurityCode(header.getDocumentNumber());

    }

    public DocumentNumber getDocumentNumber() {
        return header.getDocumentNumber();
    }

    public abstract DocumentType getType();

    public abstract String getQR();

}
