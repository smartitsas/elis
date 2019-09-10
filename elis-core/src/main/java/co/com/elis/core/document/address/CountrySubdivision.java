package co.com.elis.core.document.address;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
public class CountrySubdivision {

    @Getter
    @NotNull( message = "ELIS_CORE_VAL_ADDRESS_ID")
    private final Integer id;

    @Getter
    @NotNull( message = "ELIS_CORE_VAL_ADDRESS_COUNTRYSUB_CODE")
    private final Integer countrySubentityCode;

    @Getter
    @NotEmpty( message = "ELIS_CORE_VAL_ADDRESS_COUNTRYSUB_NAME" )
    private final String countrySubentityName;

    @Getter
    @NotNull( message = "ELIS_CORE_VAL_ADDRESS_CITY_CODE")
    private final Integer cityCode;

    @Getter
    @NotEmpty( message = "ELIS_CORE_VAL_ADDRESS_CITY_NAME")
    private final String cityName;
    
    
    public String getCountry() {
        return "CO";
    }
    
    

}
