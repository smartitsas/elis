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

public enum CustomUserCode {

    AGENTE_CARGA_INTERNACIONAL(1, "Agente de carga internacional"),
    AGENTE_MARITIMO(2, "Agente marítimo"),
    ALMACEN_GENERAL_DEPOSITO(3, "Almacén general de depósito"),
    COMERCIALIZADORA_INTERNACIONAL(4, "Comercializadora internacional (C.I.)"),
    COMERCIANTE_ESPECIAL_INIRIDA_CUMARIBO_PRIMAVERA_O_PTO_CARRENO(5, "Comerciante de la zona aduanera especial de Inírida, Puerto Carreño, Cumaribo y Primavera"),
    COMERCIANTE_ESPECIAL_LETICIA(6, "Comerciantes de la zona de régimen aduanero especial de Leticia"),
    COMERCIANTE_ESPECIAL_MAICAO_URIBIA_MANAURE(7, "Comerciantes de la zona de régimen aduanero especial de Maicao, Uribia y Manaure"),
    COMERCIANTE_ESPECIAL_URABA_TUMACO_GUAPI(8, "Comerciantes de la zona de régimen aduanero especial de Urabá, Tumaco y Guapí"),
    COMERCIANTE_SAN_ANDRES_PROVIDENCIA_SANTA_CATALINA(9, "Comerciantes del puerto libre de San Andrés, Providencia y Santa Catalina"),
    DEPOSITO_PUBLICO_APOYO_LOGISTICO_INTERNACIONAL(10, "Depósito público de apoyo logístico internacional"),
    DEPOSITO_PRIVADO_PROCESAMIENTO_INDUSTRIAL(11, "Depósito privado para procesamiento industrial"),
    DEPOSITO_PRIVADO_TRANSFORMACION_ENSAMBLE(12, "Depósito privado de transformación o ensamble"),
    DEPOSITO_FRANCO(13, "Depósito franco"),
    DEPOSITO_PRIVADO_AERONAUTICO(14, "Depósito privado aeronáutico"),
    DEPOSITO_PRIVADO_DISTRIBUCION_INTERNACIONAL(15, "Depósito privado para distribución internacional"),
    DEPOSITO_PRIVADO_PROVISIONES_DE_ABORDO(16, "Depósito privado de provisiones de a bordo para consumo y para llevar"),
    DEPOSITO_PRIVADO_ENVIOS_URGENTES(17, "Depósito privado para envíos urgentes"),
    DEPOSITO_PRIVADO(18, "Depósito privado"),
    DEPOSITO_PUBLICO(19, "Depósito público"),
    DEPOSITO_PUBLICO_DISTRIBUCION_INTERNACIONAL(20, "Depósito público para distribución internacional"),
    EXPORTADOR_CAFE(21, "Exportador de café"),
    EXPORTADOR(22, "Exportador"),
    IMPORTADOR(23, "Importador"),
    INTERMEDIARIO_TRAFICO_POSTAL_Y_ENVIOS_URGENTES(24, "Intermediario de tráfico postal y envíos urgentes"),
    OPERADOR_TRANSPORTE_MULTIMODAL(25, "Operador de transporte multimodal"),
    SOCIEDAD_INTERMEDIACION_ADUANERA(26, "Sociedad de intermediación aduanera"),
    TITULAR_PUERTOS_MUELLES_SERVICIO_PUBLICO_PRIVADO(27, "Titular de puertos y muelles de servicio público o privado"),
    TRANSPORTADOR_AEREO_REGIMEN_IMPORTACION_EXPORTACION(28, "Transportador aereo régimen de importación y/o exportación"),
    TRANSPORTISTA_NACIONAL_TRANSITO_ADUANERO(29, "Transportista nacional para operaciones del régimen de tránsito aduanero"),
    USUARIO_COMERCIAL_ZONA_FRANCA(30, "Usuario comercial zona franca"),
    USUARIO_INDUSTRIAL_BIENES_ZONA_FRANCA(32, "Usuario industrial de bienes zona franca"),
    USUARIO_INDUSTRIAL_SERVICIOS_ZONA_FRANCA(34, "Usuario industrial de servicios zona franca"),
    USUARIO_OPERADOR_ZONA_FRANCA(36, "Usuario operador de zona franca"),
    USUARIO_ADUANERO_PERMANENTE(37, "Usuario aduanero permanente"),
    USUARIO_ALTAMENTE_EXPORTADOR(38, "Usuario altamente exportador"),
    USUARIO_ZONAS_ECONOMICAS_ESPECIALES_EXPORTACION(39, "Usuario de zonas económicas especiales de exportación"),
    DEPOSITO_PRIVADO_INSTALACIONES_INDUSTRIALES(40, "Deposito privado de instalaciones industriales"),
    BENEFICIARIO_PROGRAMA_ESPECIAL_PEX(41, "Beneficiarios de programas especiales de exportación PEX"),
    DEPOSITO_PRIVADO_TRANSITO_SAN_ANDRES(42, "Depósitos privados para mercancías en tránsito San Andrés"),
    OBSERVADOR_OPERACIONES_IMPORTACION(43, "Observadores de las operaciones de importación"),
    USUARIO_SISTEMAS_ESPECIALES_IMPORTACION_EXPORTACION(44, "Usuarios sistemas especiales Importación exportación"),
    TRANSPORTADOR_MARITIMO_IMPORTACION_O_EXPORTACION(46, "Transportador maritimo régimen de importación y/o exportación"),
    TRANSPORTADOR_TERRESTRE_IMPORTACION_O_EXPORTACION(47, "Transportador terrestre régimen de importación y/o exportación"),
    AEROPUERTO_SERVICIO_PUBLICO_O_PRIVADO(48, "Aeropuerto de servicio publico o privado"),
    TRANSPORTADOR_FLUVIAL_IMPORTACION(49, "Transportador fluvial régimen de importación"),
    USUARIO_INDUSTRIAL_ZONA_FRANCA_ESPECIAL(50, "Usuario industrial zona franca especial"),
    AGENCIA_ADUANAS_1(53, "Agencias de aduanas 1"),
    USUARIO_OPERADOR_ZONA_FRANCA_ESPECIAL(54, "Usuario Operador Zona Franca Especial"),
    AGENCIA_ADUANAS_2(55, "Agencias de aduanas 2"),
    AGENCIA_ADUANAS_3(56, "Agencias de aduanas 3"),
    AGENCIA_ADUANAS_4(57, "Agencias de aduanas 4"),
    TRANSPORTADOR_AEREO_NACIONAL(58, "Transportador aéreo nacional"),
    TRANSPORTADOR_AEREO_MARITIMO_FLUVIAL_CABOTAJE(60, "Transportador aéreo, marítimo o fluvial modalidad Cabotaje"),
    IMPORTADOR_ALIMENTOS_CONSUMO_HUMANO_ANIMAL(61, "Importador de alimentos de consumo humano y animal"),
    IMPORTADOR_OCASIONAL(62, "Importador Ocasional"),
    IMPORTADOR_MAQUINARIA_DECRETO_2261_2012(63, "Importador de maquinaría y sus partes Decreto 2261 de 2012"),
    BENEFICIARIO_PROFIA(64, "Beneficiario Programa de Fomento Industria Automotriz-PROFIA"),
    OTRO_TIPO_AGENTE_ADUANERO(99, "Otro tipo de agente aduanero"),;

    @Getter
    private final Integer code;

    @Getter
    private final String description;

    @Getter
    private final String typeName;

    private CustomUserCode(Integer code, String description) {
        this.code = code;
        this.description = description;
        this.typeName = "TIPOS REPRESENTACIÓN:2016";
    }

    @Override
    public String toString() {
        return "A-"+String.format("%02d", code);
    }
    

}
