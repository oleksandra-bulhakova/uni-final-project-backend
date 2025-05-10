package site.smartbase.entity;

import jakarta.persistence.*;
import lombok.*;
import site.smartbase.enums.ContactType;
import site.smartbase.enums.OwnableType;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContactType type;

    private String contact;

    @Enumerated(EnumType.STRING)
    private OwnableType ownableType;

    private Long ownerId;
}
