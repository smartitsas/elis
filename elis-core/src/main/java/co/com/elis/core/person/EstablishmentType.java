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

public enum EstablishmentType {

    AGENCIA(1, "Agencia"),
    ESTABLECIMIENTO_COMERCIO(2, "Establecimiento de comercio"),
    CENTRO_EXPLOTACION_AGRICOLA(3, "Centro de explotación agrícola"),
    CENTRO_EXPLOTACION_ANIMAL(4, "Centro de explotación animal"),
    CENTRO_EXPLOTACION_MINERA(5, "Centro de explotación minera"),
    CENTRO_EXPLOTACION_TRANSFORMACION(6, "Centro de explotación de transformación"),
    CENTRO_EXPLOTACION_SERVICIOS(7, "Centro de explotación de servicios"),
    OFICINA(8, "Oficina"),
    SEDE(9, "Sede"),
    SUCURSAL(10, "Sucursal"),
    CONSULTORIO(11, "Consultorio"),
    ADMINISTRACIONES(12, "Administraciones"),
    SECCIONALES(13, "Seccionales"),
    REGIONALES(14, "Regionales"),
    INTENDENCIAS(15, "Intendencias"),
    LOCAL_O_NEGOCIO(16, "Local o negocio"),
    PUNTO_DE_VENTA(17, "Punto de venta"),
    FABRICA(18, "Fábrica"),
    TALLER(19, "Taller"),
    CANTERA(20, "Cantera"),
    POZO_PETROLEO_GAS(21, "Pozo de Petróleo y Gas"),
    OTRO_LUGAR_EXTRACCION_EXPLOTACION_RECURSOS_NATURALES(22, "Otro lug de tipo de extrac explotación de recursos naturales"),
    OTRO_TIPO_ESTABLECIMIENTO(99, "Otro tipo de establecimiento");

    @Getter
    private final Integer code;

    @Getter
    private final String description;

    @Getter
    private final String typeName;

    private EstablishmentType(Integer code, String description) {
        this.code = code;
        this.description = description;
        this.typeName = "TIPOS ESTABLECIMIENTO:2016";
    }

    public static EstablishmentType fromCode(String code) {

        String sanitizedValue = code.toUpperCase().trim();

        if (sanitizedValue.startsWith("E")) {
            sanitizedValue = sanitizedValue.substring(2);
        }

        for (EstablishmentType value : EstablishmentType.values()) {
            if (value.getCode() == Integer.parseInt(sanitizedValue)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Code: " + code + " is not in the Establishment type list");
    }

    @Override
    public String toString() {
        return "E-" + String.format("%02d", code);
    }

}
