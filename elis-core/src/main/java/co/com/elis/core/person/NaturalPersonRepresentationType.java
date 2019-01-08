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

package co.com.elis.core.person;

import lombok.Getter;

public enum NaturalPersonRepresentationType implements RepresentationType {

    NO_REQUIERE_RUT(0, "No obligado a registrarse en el RUT PN"),
    ADMIN_JUDICIAL(1, "Administrador judicial PN"),
    ADMIN_PRIVADO(2, "Administrador privado PN"),
    AGENTE_EXCLUSIVO_COLOMBIANO(3, "Agente exclusivo de negocios en Colombia PN"),
    AGENTE_OFICIO(4, "Agente oficioso PN"),
    ALBACEA(5, "Albacea PN"),
    APODERADO_ESPECIAL(6, "Apoderado especial PN"),
    APODERADO_GENERAL(7, "Apoderado general PN"),
    ASIGNATARIO(8, "Asignatario PN"),
    COMUNERO(9, "Comunero PN"),
    CURADOR(10, "Curador PN"),
    DONATARIO(11, "Donatario PN"),
    FACTOR(12, "Factor PN"),
    HEREDERO_CON_ADMI0N_BIENES(14, "Heredero con administración de bienes PN"),
    LIQUIDADOR(15, "Liquidador PN"),
    MANDATARIO(16, "Mandatario PN"),
    PADRE(17, "Padre PN"),
    TUTOR(21, "Tutor PN"),
    REPRESENTANTE_ADUANERO(22, "Representante aduanero PN"),
    LIQUIDADOR_SUPLENTE(23, "Liquidador suplente PN"),
    DEPOSITARIO_PROVISIONAL(24, "Depositario Provisional PN"),
    AGENTE_INTERVENTOR(25, "Agente Interventor PN"),
    HEREDERO_DESIGNADO(28, "Heredero designado PN"),
    PERSONA_NATURAL_SIN_RESIDENCIA_COLOMBIANA(29, "Persona Natural sin residencia en Colombia con EP PN"),
    OTRO_TIPO_RESPONSABLE(99, "Otro tipo de responsable PN");

    @Getter
    private final Integer code;

    @Getter
    private final String description;

    @Getter
    private final String typeName;

    private NaturalPersonRepresentationType(Integer code, String description) {
        this.code = code;
        this.description = description;
        this.typeName = "TIPOS REPRESENTACIÓN:2016";
    }

    public static NaturalPersonRepresentationType fromCode(String code) {

        String sanitizedValue = code.toUpperCase().trim();

        if (sanitizedValue.startsWith("R")) {
            sanitizedValue = sanitizedValue.substring(2).replace("-PN", "");
        }

        for (NaturalPersonRepresentationType value : NaturalPersonRepresentationType.values()) {
            if (value.getCode() == Integer.parseInt(sanitizedValue)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Code: " + code + " is not in the Natural Person Representation Type type list");
    }

    @Override
    public String toString() {
        return "R-" + String.format("%02d", code) + "PN";
    }

}
