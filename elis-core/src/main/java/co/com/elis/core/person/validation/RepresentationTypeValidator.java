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

package co.com.elis.core.person.validation;

import co.com.elis.core.person.DIANCharacterizationData;
import co.com.elis.core.person.characterizations.RepresentationType;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RepresentationTypeValidator implements ConstraintValidator<ConsistentSamePersonGroup, DIANCharacterizationData> {

    @Override
    public boolean isValid(DIANCharacterizationData representationType, ConstraintValidatorContext context) {

        if (!representationType.getRepresentationTypes().iterator().hasNext()) {
            return true;
        }

        Class<? extends RepresentationType> typeClass;

        typeClass = representationType.getRepresentationTypes().iterator().next().getClass();

        return representationType.getRepresentationTypes()
                .stream()
                .noneMatch(r -> typeClass != r.getClass());
    }

}
