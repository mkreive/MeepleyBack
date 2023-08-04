package lt.monikos.meepley.entity;

import lombok.Data;

@Data
public class Token {

    private String sub;

    private String name;

    private String email;

}
