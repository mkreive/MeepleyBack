package lt.monikos.meepley.requestModels;

import lombok.Data;

@Data
public class AddGameRequest {

    private String title;

    private String designer;

    private String publisher;

    private String intro;

    private String description;

    private int copies;

    private String category;

    private String complexity;

    private String players;

    private String playingTime;

    private byte[] img;
}
