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

package co.com.elis.core.document.builder;

import co.com.elis.core.document.DocumentDate;
import co.com.elis.core.item.Item;
import co.com.elis.core.person.ReceiverParty;
import co.com.elis.core.person.SupplierParty;
import java.util.Collection;

public abstract class MandatoryBuildContext<M extends MandatoryBuildContext, O extends OptionalBuildContext, I extends Item> {

    protected final AbstractBuilder builder;

    public MandatoryBuildContext(AbstractBuilder invoiceBuilder) {
        builder = invoiceBuilder;
    }

    public M setSupplierParty(SupplierParty supplierParty) {
        builder.supplierParty = supplierParty;
        return collectContext();
    }

    public M setReceiverParty(ReceiverParty receiverParty) {
        builder.receiverParty = receiverParty;
        return collectContext();
    }

    public M setCurrency(String currency) {
        builder.currency = currency;
        return collectContext();
    }

    @SuppressWarnings("unchecked")
    public M addItem(I item) {
        builder.itemList.add(item);
        return collectContext();
    }

    @SuppressWarnings("unchecked")
    public M addItems(Collection<I> items) {
        builder.itemList.addAll(items);
        return collectContext();
    }

    public M setDate(DocumentDate invoiceDate) {
        builder.invoiceDate = invoiceDate;
        return collectContext();
    }

    public abstract O withinOptionalSection();

    protected abstract M collectContext();

}
