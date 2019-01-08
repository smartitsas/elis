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

import co.com.elis.core.document.ExchangeRate;
import co.com.elis.core.document.allowance.AllowanceCharge;
import co.com.elis.core.document.delivery.Delivery;
import co.com.elis.core.document.reference.Reference;
import co.com.elis.core.item.Item;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class OptionalBuildContext<M extends MandatoryBuildContext, O extends OptionalBuildContext, I extends Item> {

    protected final AbstractBuilder<M, I> builder;

    protected OptionalBuildContext(AbstractBuilder<M, I> builder) {
        this.builder = builder;
    }

    public O addAllowanceCharge(AllowanceCharge allowanceCharge) {
        builder.allowanceCharge.add(allowanceCharge);
        return collectContext();
    }

    public O setDelivery(Delivery delivery) {
        builder.delivery = delivery;
        return collectContext();
    }

    public O addReference(Reference reference) {
        builder.referenceList.add(reference);
        return collectContext();
    }

    public O addReferences(Reference... reference) {
        builder.referenceList.addAll(Arrays.asList(reference));
        return collectContext();
    }

    public O addReferences(Collection<Reference> references) {
        builder.referenceList.addAll(references);
        return collectContext();
    }

    public O addAdditionalNote(String additionalNote) {
        builder.additionalNotes.add(additionalNote);
        return collectContext();
    }

    public O addAdditionalNotes(List<String> notes) {
        builder.additionalNotes.addAll(notes);
        return collectContext();
    }

    public O setExchangeRate(ExchangeRate exchangeRate) {
        builder.exchangeRate = exchangeRate;
        return collectContext();
    }

    protected abstract O collectContext();

}
