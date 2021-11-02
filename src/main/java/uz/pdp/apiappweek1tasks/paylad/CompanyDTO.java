package uz.pdp.apiappweek1tasks.paylad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {
    private String corpName;
    private String directorName;
    private String street;
    private String homeNumber;
}
