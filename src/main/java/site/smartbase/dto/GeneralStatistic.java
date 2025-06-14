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
    private Long added;
    private Long preScreens;
    private Long englishCheck;
    private Long interviews;
    private Long offers;
    private Long hires;

    public GeneralStatistic(Long userId, String firstName, String lastName, String imagePath, Long added) {
        this.userId = userId;
        this.added = added;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imagePath = imagePath;
    }
}
