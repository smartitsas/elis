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

package co.com.elis.core.person.characterizations;

import co.com.elis.core.person.characterizations.RepresentationType;
import lombok.Getter;

public enum JuridicPersonRepresentationType implements RepresentationType {

    ADMIN_JUDICIAL(1, "Administrador judicial PJ"),
    ADMIN_PRIVADO(2, "Administrador privado PJ"),
    AGENTE_EXCLUSIVO_COLOMBIANO(3, "Agente exclusivo de negocios en Colombia  PJ"),
    APODERADO_ESPECIAL(6, "Apoderado especial PJ"),
    APODERADO_GENERAL(7, "Apoderado general PJ"),
    COMUNERO(9, "Comunero PJ"),
    FACTOR(12, "Factor PJ"),
    FUNCIONARIO_DELEGADO_DEBERES_FORMALES(13, "Funcionario delegado para cumplir deberes formales PJ"),
    LIQUIDADOR(15, "Liquidador PJ"),
    MANDATARIO(16, "Mandatario PJ"),
    REPRESENTANTE_LEGAL_PRINCIPAL(18, "Representante legal principal PJ"),
    REPRESENTANTE_LEGAL_SUPLENTE(19, "Representante legal suplente PJ"),
    SINDICO(20, "Síndico PJ"),
    REPRESENTANTE_ADUANERO(22, "Representante aduanero PJ"),
    LIQUIDADOR_SUPLENTE(23, "Liquidador suplente PJ"),
    DEPOSITARIO_PROVISIONAL(24, "Depositario Provisional PJ"),
    AGENTE_INTERVENTOR(25, "Agente Interventor PJ"),
    AGENTE_ADUANAS(26, "Agente de Aduanas PJ"),
    FUNCIONARIO_OEA(27, "Funcionario delegado para cumplir funciones OEA PJ"),
    OTRO_TIPO_RESPONSABLE(99, "Otro tipo de responsable PJ");

    @Getter
    private final Integer code;

    @Getter
    private final String description;

    @Getter
    private final String typeName;

    private JuridicPersonRepresentationType(Integer code, String description) {
        this.code = code;
        this.description = description;
        this.typeName = "TIPOS REPRESENTACION:2016";
    }

    public static JuridicPersonRepresentationType fromCode(String code) {

        String sanitizedValue = code.toUpperCase().trim();

        if (sanitizedValue.startsWith("R")) {
            sanitizedValue = sanitizedValue.substring(2).replace("-PJ", "");
        }

        for (JuridicPersonRepresentationType value : JuridicPersonRepresentationType.values()) {
            if (value.getCode() == Integer.parseInt(sanitizedValue)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Code: " + code + " is not in the Juridic Person Representation Type type list");
    }

    @Override
    public String toString() {
        return "R-" + String.format("%02d", code) + "-PJ";
    }

}
