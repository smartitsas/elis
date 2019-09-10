package co.com.elis.core.util;

import co.com.elis.core.document.address.CountrySubdivision;
import java.util.HashMap;
import java.util.Scanner;

public class CountrySubdivisionFactory {

    private static CountrySubdivisionFactory instance;

    private final HashMap<Integer, CountrySubdivision> subdivisions;

    private CountrySubdivisionFactory() {
        subdivisions = new HashMap<>();
        readAll();
    }

    public static CountrySubdivisionFactory getInstance() {
        if (instance == null) {
            instance = new CountrySubdivisionFactory();
        }
        return instance;
    }

    public CountrySubdivision findById(int id) {
        return subdivisions.get(id);
    }

    private void readAll() {
        Scanner scanner = new Scanner(CountrySubdivisionFactory.class.getResourceAsStream("/countrysubentities.csv"));
        while (scanner.hasNext()) {
            String[] result = scanner.nextLine().split(",");
            CountrySubdivision countrySubdivision = CountrySubdivision.builder()
                    .id(Integer.parseInt(result[0]))
                    .countrySubentityCode(Integer.parseInt(result[1]))
                    .countrySubentityName(result[2])
                    .cityCode(Integer.parseInt(result[3]))
                    .cityName(result[4])
                    .build();

            subdivisions.put(countrySubdivision.getId(), countrySubdivision);
        }
    }

}
