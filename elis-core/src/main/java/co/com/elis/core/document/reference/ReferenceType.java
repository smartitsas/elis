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
package co.com.elis.core.document.reference;

import java.util.Arrays;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ReferenceType {

    BILLING_REFERENCE(null, "FACTURA"),
    CONTRACT_REFERENCE(null, "CONTRATO"),
    ORDER_REFERENCE("OC", "ORDEN DE COMPRA"),
    RECEIPT_REFERENCE(null, "RECIBO"),
    DESPATCH_REFERENCE(null, "GUIA"),
    PURCHASE_ORDER("15-C", "ORDEN DE PEDIDO");

    @Getter
    private final String code;

    @Getter
    private final String description;

    public static Stream<ReferenceType> getAdditionalTypesAsStream() {
        return Arrays.asList(ReferenceType.values())
                .stream()
                .filter(ref -> ref != ReferenceType.ORDER_REFERENCE)
                .filter(ref -> ref != ReferenceType.BILLING_REFERENCE)
                .filter(ref -> ref != ReferenceType.DESPATCH_REFERENCE)
                .filter(ref -> ref != ReferenceType.RECEIPT_REFERENCE);
    }

}
