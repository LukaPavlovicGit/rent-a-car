package raf.rentacar.notificationservice.dto;

public class EmailDto {

    private Long id;
    private String type;
    private String text;

    public EmailDto() { }

    public EmailDto(Long id, String type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
