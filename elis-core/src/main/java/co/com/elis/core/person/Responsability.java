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
package co.com.elis.core.person;

import lombok.Getter;

//named by DIAN as: "Section 53 of RUT" (why they use a non-autocontained number??)
public enum Responsability {

    /**
     * R-00-PN CLIENTES_DEL_EXTERIOR
     */
    CLIENTES_DEL_EXTERIOR(0, "Clientes del Exterior", null),
    /**
     * R-12-PN FACTOR_PN
     */
    FACTOR_PN(12, "Factor PN", "PN"),
    /**
     * R-16-PN MANDATARIO_PN
     */
    MANDATARIO_PN(16, "Mandatario PN", "PN"),
    /**
     * R-25-PN AGENTE_INTERVENTOR_PN
     */
    AGENTE_INTERVENTOR_PN(25, "Agente Interventor PN", "PN"),
    /**
     * R-99-PN NO_RESPONSABLE_PN
     */
    NO_RESPONSABLE_PN(99, "No responsable PN", "PN"),
    /**
     * R-06-PJ APODERADO_ESPECIAL_PJ
     */
    APODERADO_ESPECIAL_PJ(6, "Apoderado especial PJ", "PJ"),
    /**
     * R-07-PJ APODERADO_GENERAL_PJ
     */
    APODERADO_GENERAL_PJ(7, "Apoderado general PJ", "PJ"),
    /**
     * R-12-PJ FACTOR_PJ
     */
    FACTOR_PJ(12, "Factor PJ", "PJ"),
    /**
     * R-16-PJ MANDATARIO_PJ
     */
    MANDATARIO_PJ(16, "Mandatario PJ", "PJ"),
    /**
     * R-99-PJ OTRO_TIPO_RESPONSABLE_PJ
     */
    OTRO_TIPO_RESPONSABLE_PJ(99, "Otro tipo responsable PJ", "PJ");

    @Getter
    private final Integer code;

    @Getter
    private final String description;

    @Getter
    private final String typeName;

    private final String personType;

    private Responsability(Integer code, String description, String personType) {
        this.code = code;
        this.description = description;
        this.typeName = "6.2.7";
        this.personType = personType;
    }

    public static Responsability fromCode(String code) {

        String sanitizedValue = code.toUpperCase().trim();

        if (sanitizedValue.startsWith("R")) {
            sanitizedValue = sanitizedValue.substring(2);
        }

        for (Responsability value : Responsability.values()) {

            if (value.getCode() == Integer.parseInt(sanitizedValue)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Code: " + code + " is not in the Responsabilities list (section 53 of RUT)");
    }

    @Override
    public String toString() {
        return "R-" + String.format("%02d", code)+"-"+personType;
    }

}
