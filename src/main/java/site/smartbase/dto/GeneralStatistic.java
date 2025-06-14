package site.smartbase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralStatistic {
    private Long userId;
    private String firstName;
    private String lastName;
    private String imagePath;
    private Integer added;
    private Integer preScreens;
    private Integer englishCheck;
    private Integer interviews;
    private Integer offers;
    private Integer offersAccepted;
}
