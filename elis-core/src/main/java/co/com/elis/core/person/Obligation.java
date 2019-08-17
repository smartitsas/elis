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
public enum Obligation {

    /**
     * O-1 APORTE_ESPECIAL_ADMIN_JUSTICIA
     */
//    APORTE_ESPECIAL_ADMIN_JUSTICIA(1, "Aporte especial para la administración de justicia"),
    /**
     * O-2 GRAVAMEN_MOVIMIENTO_FINANCIERO
     */
//    GRAVAMEN_MOVIMIENTO_FINANCIERO(2, "Gravamen a los movimientos financieros"),
    /**
     * O-3 IMPUESTO_PATRIMONIO
     */
//    IMPUESTO_PATRIMONIO(3, "Impuesto al patrimonio"),
    /**
     * O-4 IMPUESTO_RENTA_REGIMEN_ESPECIAL
     */
//    IMPUESTO_RENTA_REGIMEN_ESPECIAL(4, "Impuesto sobre la renta y complementario régimen tributario especial"),
    /**
     * O-5 IMPUESTO_RENTA_REGIMEN_ORDINARIO
     */
//    IMPUESTO_RENTA_REGIMEN_ORDINARIO(5, "Impuesto sobre la renta y complementario régimen ordinario"),
    /**
     * O-6 INGRESOS_PATRIMONIO
     */
    INGRESOS_PATRIMONIO(6, "Ingresos y patrimonio"),
    /**
     * O-7 RETENCION_FUENTE_TITULO_RENTA
     */
    RETENCION_FUENTE_TITULO_RENTA(7, "Retención en la fuente a título de renta"),
    /**
     * O-8 RETENCION_TIMBRE_NACIONAL
     */
    RETENCION_TIMBRE_NACIONAL(8, "Retención timbre nacional"),
    /**
     * O-9 RETENCION_FUENTE_IVA
     */
    RETENCION_FUENTE_IVA(9, "Retención en la fuente en el impuesto sobre las ventas"),
    /**
     * O-10 USUARIO_ADUANERO
     */
    //    USUARIO_ADUANERO(10, "Usuario aduanero"),
    /**
     * O-11 VENTAS_REGIMEN_COMUN
     */
    //    VENTAS_REGIMEN_COMUN(11, "Ventas régimen común"),
    /**
     * O-12 VENTAS_REGIMEN_SIMPLIFICADO
     */
    //    VENTAS_REGIMEN_SIMPLIFICADO(12, "Ventas régimen simplificado"),
    /**
     * O-13 GRAN_CONTRIBUYENTE
     */
    GRAN_CONTRIBUYENTE(13, "Gran contribuyente"),
    /**
     * O-14 INFORMANTE_EXOGENA
     */
    INFORMANTE_EXOGENA(14, "Informante de exógena"),
    /**
     * O-15 AUTORRETENEDOR
     */
    AUTORRETENEDOR(15, "Autorretenedor"),
    /**
     * O-16 OBLIGACION_FACTURAR_INGRESOS_BIENES_SERVICIOS_EXCLUIDOS
     */
    OBLIGACION_FACTURAR_INGRESOS_BIENES_SERVICIOS_EXCLUIDOS(16, "Obligación de facturar por ingresos de bienes y/o servicios excluidos"),
    /**
     * O-17 PROFESIONALES_COMPRA_VENTA_DIVISAS
     */
    PROFESIONALES_COMPRA_VENTA_DIVISAS(17, "Profesionales de compra y venta de divisas"),
    /**
     * O-18 PRECIOS_TRANSFERENCIA
     */
    //    PRECIOS_TRANSFERENCIA(18, "Precios de Transferencia"),
    /**
     * O-19 PRODUCTOR_EXPORTADOR_BIENES_EXENTOS
     */
    PRODUCTOR_EXPORTADOR_BIENES_EXENTOS(19, "Productor y/o exportador de bienes exentos"),
    /**
     * O-20 OBTENCION_NIT
     */
    //    OBTENCION_NIT(20, "Obtención NIT"),
    /**
     * O-21 DECLARAR_INGRESO_SALIDA_DIVISAS
     */
    //    DECLARAR_INGRESO_SALIDA_DIVISAS(21, "Declarar el ingreso o salida del país de divisas o moneda legal colombiana"),
    /**
     * O-22 OBLIGADO_DEBERES_FORMALES_DE_TERCEROS
     */
    OBLIGADO_DEBERES_FORMALES_DE_TERCEROS(22, "Obligado a cumplir deberes formales a nombre de terceros"),
    /**
     * O-23 AGENTE_RETENEDOR_IVA
     */
    AGENTE_RETENEDOR_IVA(23, "Agente de retención en el impuesto sobre las ventas"),
    /**
     * O-24 DECLARACION_INFORMATIVA_CONSOLIDADA_PRECIOS_TRANSFERENCIA
     */
    //    DECLARACION_INFORMATIVA_CONSOLIDADA_PRECIOS_TRANSFERENCIA(24, "Declaración Informativa Consolidada Precios de transferencia"),
    /**
     * O-26 DECLARACION_INFORMATIVA_INDIVIDUAL_PRECIOS_TRASNFERENCIA
     */
    //    DECLARACION_INFORMATIVA_INDIVIDUAL_PRECIOS_TRASNFERENCIA(26, "Declaración Informativa Individual Precios de transferencia"),
    /**
     * O-32 IMPUESTO_NACIONAL_GASOLINA_ACPM
     */
    IMPUESTO_NACIONAL_GASOLINA_ACPM(32, "Impuesto Nacional a la Gasolina y al ACPM"),
    /**
     * O-33 IMPUESTO_NACIONAL_CONSUMO
     */
    IMPUESTO_NACIONAL_CONSUMO(33, "Impuesto Nacional al consumo"),
    /**
     * O-34 REGIMEN_SIMPLIFICADO_IMPUESTO_CONSUMO
     */
    REGIMEN_SIMPLIFICADO_IMPUESTO_CONSUMO(34, "Régimen simplificado impuesto nacional consumo rest y bares"),
    /**
     * O-35 IMPUESTO_CREE
     */
    //    IMPUESTO_CREE(35, "Impuesto sobre la renta para la equidad CREE"),
    /**
     * O-36 ESTABLECIMIENTO_PERMANENTE
     */
    ESTABLECIMIENTO_PERMANENTE(36, "Establecimiento Permanente"),
    /**
     * O-37 OBLIGADO_FACTURA_ELECTRONICA_MODELO_2242
     */
    OBLIGADO_FACTURA_ELECTRONICA_MODELO_2242(37, "Obligado a Facturar Electrónicamente Modelo 2242"),
    /**
     * O-38 FACTURA_ELECTRONICA_VOLUNTARIA_MODELO_2242
     */
    FACTURA_ELECTRONICA_VOLUNTARIA_MODELO_2242(38, "Facturación Electrónica Voluntaria Modelo 2242"),
    /**
     * O-39 PROVEEDOR_SERVICIOS_TECNOLOGICOS_MODELO_2242
     */
    PROVEEDOR_SERVICIOS_TECNOLOGICOS_MODELO_2242(39, "Proveedor de Servicios Tecnológicos PST Modelo 2242"),
    /**
     * O-40 IMPUESTO_RIQUEZA
     */
    //    IMPUESTO_RIQUEZA(40, "Impuesto a la Riqueza"),
    /**
     * O-41 DECLARACION_ANUAL_ACTIVOS_EXTERIOR
     */
    //    DECLARACION_ANUAL_ACTIVOS_EXTERIOR(41, "Declaración anual de activos en el exterior"),
    /**
     * O-42 OBLIGADO_LLEVAR_CONTABILIDAD
     */
    //    OBLIGADO_LLEVAR_CONTABILIDAD(42, "Obligado a Llevar Contabilidad"),
    /**
     * O-99 OTRO_TIPO_OBLIGADO
     */
    OTRO_TIPO_OBLIGADO(99, "Otro tipo de obligado");

    @Getter
    private final Integer code;

    @Getter
    private final String description;

    @Getter
    private final String typeName;

    private Obligation(Integer code, String description) {
        this.code = code;
        this.description = description;
        this.typeName = "TIPOS OBLIGACIONES:2016";
    }

    public static Obligation fromCode(String code) {

        String sanitizedValue = code.toUpperCase().trim();

        if (sanitizedValue.startsWith("O")) {
            sanitizedValue = sanitizedValue.substring(2);
        }

        for (Obligation value : Obligation.values()) {

            if (value.getCode() == Integer.parseInt(sanitizedValue)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Code: " + code + " is not in the obligations list (section 53 of RUT)");
    }

    @Override
    public String toString() {
        return "O-" + String.format("%02d", code);
    }

}
