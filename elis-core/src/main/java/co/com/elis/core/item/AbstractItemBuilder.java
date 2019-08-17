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

import co.com.elis.exception.ElisCoreException;
import java.math.BigDecimal;
import java.util.Set;
import javax.validation.ConstraintViolation;

public class AbstractItemBuilder {

    protected Integer position;
    protected String code;
    protected String description;
    protected String units;
    protected BigDecimal unitaryValue;
    protected BigDecimal quantity;
    protected boolean validate;

    public AbstractItemBuilder() {
        this.validate = true;
    }

    protected void validateOrThrow(Item item) throws ElisCoreException {
        if (validate) {
            Set<ConstraintViolation<Item>> violations = item.validate();
            if (!violations.isEmpty()) {
                throw new ElisCoreException("Error en item " + item.toDescriptiveId() + ": " + violations.iterator().next().getMessage());
            }
        }
    }

    public abstract static class MandatoryContext<B extends AbstractItemBuilder, T extends MandatoryContext> {

        protected final B builder;

        public MandatoryContext(B builder) {
            this.builder = builder;
        }

        /**
         * Assigns position of the item
         *
         * @param position Id or position of the item
         * @return Chaining Builder
         */
        public T setPosition(Integer position) {
            builder.position = position;
            return collectContext();
        }

        public T setUnitaryValue(BigDecimal unitaryValue) {
            builder.unitaryValue = unitaryValue;
            return collectContext();
        }

        public T setUnitaryValue(double unitaryValue) {
            builder.unitaryValue = BigDecimal.valueOf(unitaryValue).setScale(4);
            return collectContext();
        }

        public T setQuantity(BigDecimal quantity) {
            builder.quantity = quantity;
            return collectContext();
        }

        public T setQuantity(double quantity) {
            builder.quantity = BigDecimal.valueOf(quantity);
            return collectContext();
        }

        public T setUnits(String units) {
            builder.units = units;
            return collectContext();
        }

        protected abstract T collectContext();

    }

    public abstract static class OptionalContext<B extends AbstractItemBuilder, T extends OptionalContext> {

        protected final B builder;

        public OptionalContext(B builder) {
            this.builder = builder;
        }

        /**
         * Assigns code to the item to be building
         *
         * @param code Code of the item
         * @return Chaining Builder
         */
        public T setCode(String code) {
            builder.code = code;
            return collectContext();
        }

        public T setDescription(String description) {
            builder.description = description;
            return collectContext();
        }

        public T disableValidations() {
            builder.validate = false;
            return collectContext();
        }

        protected abstract T collectContext();
    }

}
