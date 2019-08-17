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
package co.com.elis.core.tax;

import co.com.elis.core.util.ResourceInterpolator;
import co.com.elis.core.tax.validation.ContainsDefaultTaxes;
import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

public class TaxTotalList {

    @Valid
    @ContainsDefaultTaxes(message = "ELIS_CORE_VAL_TAX_SUBTAXES")
    private final List<TaxTotal> taxTotals;

    private TaxTotalList() {
        taxTotals = new ArrayList<>();
    }

    public TaxTotalList(List<TaxTotal> taxTotals) {
        this.taxTotals = taxTotals;
    }

    //TODO: Limit this call
    public boolean add(TaxTotal taxTotal) {
        return taxTotals.add(taxTotal);
    }

    public Optional<TaxTotal> getByType(TaxType type) {
        return taxTotals.stream().filter(p -> p.getType().equals(type)).findFirst();
    }

    public TaxTotal get(int index) {
        return taxTotals.get(index);
    }

    public Iterator<TaxTotal> iterator() {
        return taxTotals.iterator();
    }

    public Stream<TaxTotal> stream() {
        return taxTotals.stream();
    }

    public int size() {
        return taxTotals.size();
    }

    public boolean isEmpty() {
        return taxTotals.isEmpty();
    }

    public List<Tax> toTaxList() {
        return taxTotals.stream().flatMap(t -> t.getTaxSubtotals().stream()).collect(Collectors.toList());
    }

    public BigDecimal getCalculatedTotal() {
        return taxTotals.stream()
                .map(TaxTotal::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static TaxTotalList buildTotalList(Tax... subtotals) throws ElisCoreException {
        return buildTotalList(false, false, Arrays.asList(subtotals));
    }

    public static TaxTotalList buildTotalList(boolean includeMissing, boolean isRetained, Tax... subtotals) throws ElisCoreException {
        return buildTotalList(includeMissing, isRetained, Arrays.asList(subtotals));
    }

    public static TaxTotalList buildTotalList(Collection<Tax> subtotals) throws ElisCoreException {
        return buildTotalList(false, false, subtotals);
    }

    public static TaxTotalList buildTotalList(boolean includeMissing, boolean isRetained, Collection<Tax> subtotals) throws ElisCoreException {
        List<TaxTotal> totalsList = new LinkedList<>();

        //group by type
        Map<TaxType, List<Tax>> typeGroup = subtotals.stream().collect(groupingBy(Tax::getType));

        for (Map.Entry<TaxType, List<Tax>> typeGroupEntry : typeGroup.entrySet()) {
            //group by percentage within each type
            Map<BigDecimal, List<Tax>> percentageGroup;
            percentageGroup = typeGroupEntry.getValue().stream().collect(groupingBy(Tax::getPercentage));

            //each list in percentageMap (percentage category) must be converted into one taxSubtotal since they have the same type and percentage
            List<Tax> finalList = agregateSubtotalsByPercentage(percentageGroup);
            totalsList.add(new TaxTotal(finalList, isRetained));
        }

        if (includeMissing) {
            addZerosTo(totalsList, isRetained);
        }
        return new TaxTotalList(totalsList);
    }

    /**
     * Adds every element of List<TaxSubtotal> to get ONE TaxSubtotal PER
     * Percentage value.
     *
     * @param mapList Map containing every List of subtotals classified by
     * Percentage value
     * @return simple list with ONE subtotal per Percentage
     */
    private static List<Tax> agregateSubtotalsByPercentage(Map<BigDecimal, List<Tax>> mapList) throws ElisCoreException {
        List<Tax> result = new ArrayList<>(mapList.size());

        for (Map.Entry<BigDecimal, List<Tax>> percentageCategory : mapList.entrySet()) {
            Tax sum = percentageCategory.getValue()
                    .stream().reduce((a, b) -> a.add(b))
                    .orElseThrow(() -> new ElisCoreException("Error agreggating subtotals"));
            result.add(sum);
        }

        return result;
    }

    private static void addZerosTo(List<TaxTotal> totalsList, boolean isRetained) {
        for (TaxType type : TaxType.values()) {
            Optional<TaxTotal> result = totalsList.stream()
                    .filter(t -> t.getType() == type)
                    .findAny();

            if (!result.isPresent()) {
                Tax tax = Tax.createWithZeros(type).build();
                totalsList.add(new TaxTotal(type, BigDecimal.ZERO, isRetained, Arrays.asList(tax)));
            }
        }
    }

    public Set<ConstraintViolation<TaxTotalList>> validate() {
        Validator validator = Validation
                .buildDefaultValidatorFactory()
                .usingContext()
                .messageInterpolator(new ResourceInterpolator())
                .getValidator();
        return validator.validate(this);
    }

}
