package uz.pdp.apiappweek1tasks.paylad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDTO {
    private String name;
    private String phoneNumber;
    private String street;
    private String homeNumber;
    private Integer departmentId;
}
