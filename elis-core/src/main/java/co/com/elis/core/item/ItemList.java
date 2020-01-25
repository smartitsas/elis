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
package co.com.elis.core.item;

import co.com.elis.core.tax.Tax;
import co.com.elis.core.withold.WithHold;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemList<I extends Item> implements Iterable<I> {

    private final List<I> internalCollection;

    public ItemList() {
        internalCollection = new ArrayList<>();
    }

    @Override
    public Iterator<I> iterator() {
        return internalCollection.iterator();
    }

    public boolean isEmpty() {
        return internalCollection.isEmpty();
    }

    public void add(I item) {
        internalCollection.add(item);
    }

    public int size() {
        return internalCollection.size();
    }

    public BigDecimal getCalculatedTotal() {
        return internalCollection.stream()
                .map(Item::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Tax> getTaxes() {
        return internalCollection.stream()
                .flatMap(i -> i.getTaxes().stream())
                .collect(Collectors.toList());
    }

    public List<WithHold> getWithHolds() {
        return internalCollection.stream()
                .flatMap(i -> i.getWithHolds().stream())
                .collect(Collectors.toList());
    }

    public Stream<I> stream() {
        return internalCollection.stream();
    }

    public I get(int index) {
        return internalCollection.get(index);
    }

    public void addAll(Collection<I> items) {
        internalCollection.addAll(items);
    }

}
