package ontap.example.ontap.dto;

import lombok.Data;

@Data
public class AddressDTO {

    private String phone;

    private String street;

    private String ward;

    private String district;

    private String city;

    private boolean isDefault;
}
